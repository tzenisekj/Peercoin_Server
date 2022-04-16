from com.peercoin.integration.currency import JythonCurrency
from currency_methods_impl import CurrencyMethodsImpl

class Euro(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "Euro"

    def getTicker(self):
        return "EUR"

    def getClassName(self):
        return "fiat"

    def getCurrencyMethods(self):
        return CurrencyMethodsImpl()
