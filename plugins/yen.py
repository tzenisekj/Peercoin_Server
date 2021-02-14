from com.peercoin.integration.currency import JythonCurrency
from currency_methods_impl import CurrencyMethodsImpl

class Yen(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "Japanese Yen"

    def getTicker(self):
        return "JPY"

    def getClassName(self):
        return "fiat"

    def getCurrencyMethods(self):
        return CurrencyMethodsImpl()
