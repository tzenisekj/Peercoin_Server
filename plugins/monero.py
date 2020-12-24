from com.peercoin.integration.currency import JythonCurrency

class Monero(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "Monero"

    def getTicker(self):
        return "XMR"

    def getClassName(self):
        return "crypto"