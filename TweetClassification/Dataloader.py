import xlrd

__author__ = "Anirban Roy, December 2015"

class DataLoader:
    'This class will load the MS Excel file containing tweet provided in the constructor into memory for tweet processing'
    filename = ''
    sheet_obama = None
    sheet_romney = None

    #constructor accepting the filename as argument
    def __init__(self, filename):
      self.filename = filename

    # loads the data in the sheets. Sheet0 corresponds to Obama tweets and sheet1 to Romney tweets
    def loadData(self):
        xl_workbook = xlrd.open_workbook(self.filename)
        #modify the below code to read from different sheets
        self.sheet_obama = xl_workbook.sheet_by_index(0)
        self.sheet_romney = xl_workbook.sheet_by_index(1)
        return self



