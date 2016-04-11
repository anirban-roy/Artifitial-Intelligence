==========================================================
==  	  TWEET CLASSIFICATION USING PYTHON		==
==  (DATA MINING AND MACHINE LEARNING APPLICATION)	==
==========================================================
==========================================
== @Author: Anirban Roy			==
== University of Illinois at Chicago	==
== Department of Computer Science	==
== Fall 2015				==
==========================================

The input to the program is an excel file containing tweets in two sheets. Sheet0 is for Obama and sheet 1 is for Romney.
All tweets are labeled Positive=1, Negative=-1, Neutral=0 and Both=2 as opinions expressed by the tweet about Obama or Romney respectively.

The program loads the data, cleans it (Removed garbage values, stopwords, apply stemming), vectorizes it to represent into bag of words model, splits in train set(4/5th part) and test part(1/5th part) and finally applies a classifier to learn from train part to classify the test part. The performance stistics is printed for both Obama and Romney tweet sets.

Environment Setup:
=================
This program is developed using Python 2.7, NLTK and Scikit Learn libraries
Installation bundle used Anaconda 2. IDE used is Pycharm 5.0.4

Execution:
=========
Start by executing the below script:
TweetClassification.py

Sample Output:
==============
Given below is a sample output. Each run shuffles the data as such the output performance statistics may vary. Only classes 1, 0 and -1 are used for classification.


:::: Obama Data ::::
Using Vectorizer:  TfidfVectorizer
Using Classifier:  MultinomialNB
Classifier Performance:
=======================
             precision    recall  f1-score   support

         -1       0.55      0.74      0.63       537
          0       0.57      0.45      0.51       520
          1       0.63      0.52      0.57       426

avg / total       0.58      0.57      0.57      1483

Accuracy= 0.573836817262 


Using Vectorizer:  CountVectorizer
Using Classifier:  MultinomialNB
Classifier Performance:
=======================
             precision    recall  f1-score   support

         -1       0.58      0.72      0.64       508
          0       0.61      0.52      0.56       537
          1       0.65      0.58      0.61       438

avg / total       0.61      0.61      0.60      1483

Accuracy= 0.605529332434 


:::: Romney Data ::::
Using Vectorizer:  TfidfVectorizer
Using Classifier:  MultinomialNB
Classifier Performance:
=======================
             precision    recall  f1-score   support

         -1       0.46      0.97      0.62       590
          0       0.39      0.07      0.12       323
          1       0.77      0.12      0.20       206
          2       0.74      0.14      0.24       280

avg / total       0.54      0.47      0.37      1399

Accuracy= 0.472480343102 


Using Vectorizer:  CountVectorizer
Using Classifier:  MultinomialNB
Classifier Performance:
=======================
             precision    recall  f1-score   support

         -1       0.52      0.90      0.66       561
          0       0.47      0.19      0.27       337
          1       0.58      0.17      0.26       225
          2       0.64      0.53      0.58       276

avg / total       0.54      0.54      0.48      1399

Accuracy= 0.536097212294 