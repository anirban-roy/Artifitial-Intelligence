from nltk.corpus import stopwords
from nltk.stem.porter import *
from xlrd.sheet import ctype_text
import random
from sklearn.feature_extraction.text import CountVectorizer

__author__ = "Anirban Roy, December 2015"

class DataPreparer:
    'This class will cleans the data loaded and prepares it for the model to learn'
    raw_data = None
    stopword = None
    stemmer = None

    #raw tweets after cleaned
    cleaned_tweets_X = None
    cleaned_tweets_Y = None
    #training set and test sets
    data_X_train = None
    data_Y_train = None
    data_X_test = None
    data_Y_test = None

    _vectorizeFlag_ = False

    def __init__(self, dataloaded):
        self.raw_data = dataloaded
        self.stopword = stopwords.words('english')
        self.stemmer = PorterStemmer()
        self.cleaned_tweets_X = []
        self.cleaned_tweets_Y = []

    def isStopWord(self, word):
        if word in self.stopword:
            return True
        else:
            return False

    def clean_data(self):
        'Clean the tweet data after loading completed, remove stop words and apply stemming to the featured words in tweets'
        self.cleaned_tweets_X = []
        self.cleaned_tweets_Y = []
        for row_idx in range(0, self.raw_data.nrows):
            #fetch data from 4th column in excel sheet
            original_tweet = self.raw_data.cell(row_idx, 0)
            cell_type_tweet = ctype_text.get(original_tweet.ctype, 'unknown type')
            sentiment = self.raw_data.cell(row_idx, 1)
            cell_type_sentiment = ctype_text.get(sentiment.ctype, 'unknown type')

            # tweet should be text and sentiment value a number
            if cell_type_tweet == "text" and cell_type_sentiment == "number":
                original_tweet = original_tweet.value.lower()
                sentiment = int(sentiment.value)

                count=0
                #remove garbage values like: .,!"<>{}[]?
                tweet = original_tweet.replace('. ',' ').replace('..',' ').replace(';',' ').replace(', ',' ').replace('!',' ').replace('"',' ')\
                        .replace('<',' ').replace('>',' ').replace('{',' ').replace('}',' ').replace('[',' ').replace(']',' ')\
                        .replace('?',' ').replace("'",'').replace(',',' ').replace(',',' ').replace('%',' ')

                #tokenize into words
                wordList = tweet.split()

                sentence = ""
                for word in wordList:
                    #remove stop words and words having 1 and or 2 characters
                    value = self.isStopWord(word)
                    if value == False:
                        #remove the tags </e>, <a> etc.
                        if len(word)==2 and word[0:1]=='/':
                            continue
                        #only consider words which are more than 1 character in length
                        if len(word)>1:
                            word = self.stemmer.stem(word)
                            word = word.encode('utf8')
                            sentence = sentence + word + ' '
                            count = count + 1
                self.cleaned_tweets_X.append(sentence)
                self.cleaned_tweets_Y.append(sentiment)

    def shuffle_data(self):
        'Shuffle the data for multiple iterations'
        # return false in case no data loaded
        if len(self.cleaned_tweets_X) == 0:
            return False

        indexes = range(len(self.cleaned_tweets_X))
        random.shuffle(indexes)
        temp_X = []
        temp_Y = []
        for i in indexes:
            temp_X.append(self.cleaned_tweets_X[i])
            temp_Y.append(self.cleaned_tweets_Y[i])
        self.cleaned_tweets_X = temp_X
        self.cleaned_tweets_Y = temp_Y

    def createTrain_TestSet(self):
        'Prepare train and test sets, shuffles the data in the process'
        #shuffle the dataset, this increases the accuracy as per experiments
        self.shuffle_data()
        #create training and test data sets
        #Use 4/5th dataset as training set and 1/5th as test dataset
        self.data_X_train = self.cleaned_tweets_X[:len(self.cleaned_tweets_X)-len(self.cleaned_tweets_X)/5]
        self.data_Y_train = self.cleaned_tweets_Y[:len(self.cleaned_tweets_Y)-len(self.cleaned_tweets_Y)/5]
        self.data_X_test = self.cleaned_tweets_X[len(self.cleaned_tweets_X)-len(self.cleaned_tweets_X)/5:]
        self.data_Y_test = self.cleaned_tweets_Y[len(self.cleaned_tweets_Y)-len(self.cleaned_tweets_Y)/5:]
        #vectoriztion must follow after datasets has been created before classification may happen

    def vectorizeData(self, vectorizer=None):
        'Vectorize the data train and test sets. Represent as bag of words model'
        #  if no vectorizer provided, use the countvectorizer as the default to vectorize
        if vectorizer == None:
            vectorizer = CountVectorizer(ngram_range=(1, 4))
        print "Using Vectorizer: ", vectorizer.__class__.__name__
        self.data_X_train = vectorizer.fit_transform(self.data_X_train)
        self.data_X_test = vectorizer.transform(self.data_X_test)