from sklearn.metrics import classification_report
from sklearn.metrics import accuracy_score

__author__ = "Anirban Roy, December 2015"

class Classification:
    'Learn a Train dataset and perform the classification using Machine learning algorithmic models and benchmark results'
    train_data_X = None
    train_data_Y = None
    test_data_X = None
    test_data_Y = None
    classifer = None
    predicted = None

    def __init__(self, trainX, trainY, testX, testY, classifier):
        'Initialize the class memebers'
        self.train_data_X = trainX
        self.train_data_Y = trainY
        self.test_data_X = testX
        self.test_data_Y = testY
        self.classifer = classifier

    def classify(self):
        'Learn and perform the classification'
        if self.classifer == None:
            raise Exception("No classifer provided. Cannot proceed with classification")
        self.classifer.fit(self.train_data_X, self.train_data_Y)
        self.predicted = self.classifer.predict(self.test_data_X)
        return self


    def printResults(self):
        'Display the results of then classification'
        report = classification_report(self.test_data_Y, self.predicted)
        accuracy = accuracy_score(self.test_data_Y, self.predicted)
        print "Using Classifier: ",self.classifer.__class__.__name__
        print "Classifier Performance:\n=======================\n", report
        print "Accuracy=", accuracy, "\n\n"