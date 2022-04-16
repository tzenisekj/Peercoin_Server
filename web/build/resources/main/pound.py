from com.peercoin.integration.currency import JythonCurrency
from currency_methods_impl import CurrencyMethodsImpl

class Pound(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "Pound sterling"

    def getTicker(self):
        return "GBP"

    def getClassName(self):
        return "fiat"

    def getCurrencyMethods(self):
        return CurrencyMethodsImpl()
