from com.peercoin.integration.currency import JythonCurrency
from currency_methods_impl import CurrencyMethodsImpl

class CanadianDollar(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "Canadian Dollar"

    def getTicker(self):
        return "CAD"

    def getClassName(self):
        return "fiat"

    def getCurrencyMethods(self):
        return CurrencyMethodsImpl()
