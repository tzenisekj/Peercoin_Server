from com.peercoin.integration.currency import JythonCurrency

class UnitedStatesDollar(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "United States Dollar"

    def getTicker(self):
        return "USD"

    def getClassName(self):
        return "fiat"