__author__ = "Anirban Roy, December 2015"

# ****************************************************************************************************************
# Start here!!
# The script uses the tweets dataset during the 2012 polling event in United States
# for candidates Obama and Romney.
# The classification has the following steps:
# 1. Load data using the DataLoader class.
# 2. Use DataPreparer class to clean the data and vectorize into bag of words models after feature extraction.
# 3. Use the Classification class to apply a model (Example Naive Bayes Multinomial) to the vectorized data.
# 4. Print the results for accuracy and other performance parameters.
# 5. Repeat steps 2 to 4 for othe data models.
# ****************************************************************************************************************

from sklearn.naive_bayes import MultinomialNB
from sklearn.feature_extraction.text import TfidfVectorizer
from Dataloader import DataLoader
from Preparedata import DataPreparer
from Classification import Classification
from sklearn.feature_extraction.text import CountVectorizer

# Name of dataset file to be placed in the working directory of the script
inputFile="tweet_poll_event_dataset.xlsx";


# Step 1
data_loader = DataLoader(inputFile).loadData()

# Step 2
prepare_data_obama = DataPreparer(data_loader.sheet_obama)
prepare_data_obama.clean_data()
prepare_data_romney = DataPreparer(data_loader.sheet_romney)
prepare_data_romney.clean_data()

# Processing Obama data
#Vectorize the tweets to create sparse matrix for the words as features
# Experiments shows that using unigram features increases the accuracy for TF-IDF Vectorizer
print ":::: Obama Data ::::"
vectorizer = TfidfVectorizer(sublinear_tf=True, max_df=0.5)
prepare_data_obama.createTrain_TestSet()
prepare_data_obama.vectorizeData(vectorizer)

#Step 3
#Create classification model
multi_naiveBayes_classifier = MultinomialNB()
#initialize classifier
classifier = Classification(prepare_data_obama.data_X_train, prepare_data_obama.data_Y_train,
                            prepare_data_obama.data_X_test, prepare_data_obama.data_Y_test, multi_naiveBayes_classifier)
#Step 4
#classify and print performance results
classifier.classify()
classifier.printResults()


# Step 5: Repeating with count Vectorizer with Multinomial Naive Bayes
# Experiments shows that using tetragram features increases the accuracy of model with CountVectorizer
vectorizer = CountVectorizer(ngram_range=(1, 4))
prepare_data_obama.createTrain_TestSet()
prepare_data_obama.vectorizeData(vectorizer)

#Step 5(3)
#Create classification model
multi_naiveBayes_classifier = MultinomialNB()
#initialize classifier
classifier = Classification(prepare_data_obama.data_X_train, prepare_data_obama.data_Y_train,
                            prepare_data_obama.data_X_test, prepare_data_obama.data_Y_test, multi_naiveBayes_classifier)
#Step 5(4)
#classify and print performance results
classifier.classify()
classifier.printResults()

# # Processing Romney data
# #Vectorize the tweets to create sparse matrix for the words as features
# # Experiments shows that using unigram features increases the accuracy for TF-IDF Vectorizer
print ":::: Romney Data ::::"
vectorizer = TfidfVectorizer(sublinear_tf=True, max_df=0.5)
prepare_data_romney.createTrain_TestSet()
prepare_data_romney.vectorizeData(vectorizer)

#Step 5(3)
#Create classification model
multi_naiveBayes_classifier = MultinomialNB()
#initialize classifier
classifier = Classification(prepare_data_romney.data_X_train, prepare_data_romney.data_Y_train,
                            prepare_data_romney.data_X_test, prepare_data_romney.data_Y_test, multi_naiveBayes_classifier)
#Step 5(4)
#classify and print performance results
classifier.classify()
classifier.printResults()


# Step 5: Repeating with count Vectorizer with Multinomial Naive Bayes
# Experiments shows that using tetragram features increases the accuracy of model with CountVectorizer
vectorizer = CountVectorizer(ngram_range=(1, 4))
prepare_data_romney.createTrain_TestSet()
prepare_data_romney.vectorizeData(vectorizer)

#Step 5(3)
#Create classification model
multi_naiveBayes_classifier = MultinomialNB()
#initialize classifier
classifier = Classification(prepare_data_romney.data_X_train, prepare_data_romney.data_Y_train,
                            prepare_data_romney.data_X_test, prepare_data_romney.data_Y_test, multi_naiveBayes_classifier)
#Step 5(4)
#classify and print performance results
classifier.classify()
classifier.printResults()

