from com.peercoin.integration.currency import JythonCurrency
from currency_methods_impl import CurrencyMethodsImpl

class Franc(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "Swiss franc"

    def getTicker(self):
        return "CHF"

    def getClassName(self):
        return "fiat"

    def getCurrencyMethods(self):
        return CurrencyMethodsImpl()
