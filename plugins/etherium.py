from com.peercoin.integration.currency import JythonCurrency

class Etherium(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "Etherium"

    def getTicker(self):
        return "ETH"

    def getClassName(self):
        return "crypto"