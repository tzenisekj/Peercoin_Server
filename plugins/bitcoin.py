from com.peercoin.integration.currency import JythonCurrency

class Bitcoin(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "Bitcoin"

    def getTicker(self):
        return "BTC"

    def getClassName(self):
        return "crypto"