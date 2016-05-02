import matplotlib.pyplot as plots
from sklearn.metrics import accuracy_score
from sklearn.metrics import roc_auc_score, roc_curve

__author__ = "Anirban Roy, December 2015"

class PerformanceMetrics:
    Y_test = None

    def __init__(self, ytest):
        'Initialize....'
        self.Y_test = ytest
        plots.figure()

    def showPlot(self):
        plots.xlabel('False positive rate')
        plots.ylabel('True positive rate')
        plots.title('ROC curve')
        plots.legend(loc='best')
        plots.savefig('roc_curve.png')
        plots.show()

    def printResults(self, y_probabilities, y_predicted, label_txt):
        'Display the results of then classification'
        accuracy = accuracy_score(self.Y_test, y_predicted)
        print "\n==============================================="
        print "Using Classifier: ",label_txt
        print "Accuracy=", accuracy, ""
        fpr, tpr, threshld = roc_curve(self.Y_test, y_probabilities)
        plots.plot(fpr, tpr, label=label_txt)
        print "Roc AUC for", label_txt, ": ", roc_auc_score(self.Y_test, y_probabilities, average='macro')