import pandas as pd
import numpy as np

from sklearn.cross_validation import train_test_split
from sklearn.ensemble import ExtraTreesClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.naive_bayes import BernoulliNB

from math import sqrt
from sklearn.metrics import mean_squared_error as MSE
from pybrain.datasets  import ClassificationDataSet
from pybrain.tools.shortcuts import buildNetwork
from pybrain.structure.modules import SoftmaxLayer
from pybrain.supervised.trainers import BackpropTrainer

from sklearn.feature_selection import SelectFromModel
from ShowPerformance import PerformanceMetrics

__author__ = "Anirban Roy, December 2015"
# The problem statement against this code can be found at https://www.kaggle.com/c/santander-customer-satisfaction
# This program uses 4 classification algorthims to model the dataset.
# 1. Decision trees         2. Random Forests
# 3. Naive Bayes            4. Neural Networks (MLP)
# Finally the classification accuracy along with the ROC AOC is displayed allowing a comparison between the
# classification algorithms.

train = pd.read_csv("input/train.csv")
test = pd.read_csv("input/test.csv")

# clean and split data

# remove constant columns (std = 0)
remove = []
for col in train.columns:
    if train[col].std() == 0:
        remove.append(col)

train.drop(remove, axis=1, inplace=True)
test.drop(remove, axis=1, inplace=True)

# remove duplicated columns
remove = []
cols = train.columns
for i in range(len(cols)-1):
    v = train[cols[i]].values
    for j in range(i+1,len(cols)):
        if np.array_equal(v,train[cols[j]].values):
            remove.append(cols[j])


train.drop(remove, axis=1, inplace=True)
test.drop(remove, axis=1, inplace=True)

# split data into train and test
test_id = test.ID
test = test.drop(["ID"],axis=1)

X = train.drop(["TARGET","ID"],axis=1)
y = train.TARGET.values

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.20, random_state=1729)
#print(X_train.shape, X_test.shape, test.shape)

## # Feature selection
clf = ExtraTreesClassifier(random_state=1729)
selector = clf.fit(X_train, y_train)
fs = SelectFromModel(selector, prefit=True)

X_train = fs.transform(X_train)
X_test = fs.transform(X_test)
test = fs.transform(test)
#print(X_train.shape, X_test.shape, test.shape)

# initialize performance matrix
performance = PerformanceMetrics(y_test)

# Train Models: Bayes, Tree and MLP
classifier_bayes = BernoulliNB()
classifier_bayes.fit(X_train, y_train)
classifier_forest = RandomForestClassifier(max_depth=5)
classifier_forest.fit(X_train, y_train)
classifier_decisionTree = DecisionTreeClassifier(max_depth=5)
classifier_decisionTree.fit(X_train, y_train)

## Data for MLP
target = y_train.reshape(-1,1)
networkTrainDataset = ClassificationDataSet(X_train.shape[1], 1, nb_classes=len(np.unique(y_train)))
networkTrainDataset.setField('input', X_train)
networkTrainDataset.setField('target', target)
networkTrainDataset._convertToOneOfMany()

target = y_test.reshape(-1,1)
networkTestDataset = ClassificationDataSet(X_test.shape[1], 1, nb_classes=len(np.unique(y_test)))
networkTestDataset.setField('input', X_test)
networkTestDataset.setField('target', target)
networkTestDataset._convertToOneOfMany()

##Train the MLP
epochs = 2
hidden_layer_size = X_train.shape[1]/2 #5
feedfwdNeuNet = buildNetwork(networkTrainDataset.indim, hidden_layer_size, networkTrainDataset.outdim, bias = True, outclass=SoftmaxLayer )#buildNetwork( X_train.shape[1], 5, len(np.unique(y_test)), outclass=SoftmaxLayer )
trainer = BackpropTrainer(feedfwdNeuNet, dataset=networkTrainDataset, momentum=0.1, verbose=False, weightdecay=0.01)
print "Training MLP..."
for i in range( epochs ):
	err = trainer.train()
	rmse = sqrt( err )
	print "Root Mean Square Error, epoch {}: {}".format( i + 1, rmse )," and Error:",err
print "Training MLP complete for ", epochs , " epochs"

#Prediction
# # calculate the performance
y_probabilities = classifier_bayes.predict_proba(X_test)[:,1]
y_predicted = classifier_bayes.predict(X_test)
performance.printResults(y_probabilities, y_predicted, 'Naive Bayes')

y_probabilities = classifier_forest.predict_proba(X_test)[:,1]
y_predicted = classifier_forest.predict(X_test)
performance.printResults(y_probabilities, y_predicted, 'Random Forests')

y_probabilities = classifier_decisionTree.predict_proba(X_test)[:,1]
y_predicted = classifier_decisionTree.predict(X_test)
performance.printResults(y_probabilities, y_predicted, 'Decision Tree')

predicted = feedfwdNeuNet.activateOnDataset(networkTestDataset)
predicted = predicted.argmax(axis=1)  # the highest output activation gives the class
predicted = predicted.reshape(y_test.shape)
mse = MSE( y_test, predicted )
performance.printResults(predicted, predicted, 'MLP')
print "MLP RMSE of prediction: ", sqrt(mse)

#Show results
performance.showPlot()

# ## # Classify test data
# predicted_probab = classifier.predict_proba(test)
# #
# outputfile = pd.DataFrame({"ID":test_id, "TARGET": predicted_probab[:,1]})
# outputfile.to_csv("output.csv", index=False)