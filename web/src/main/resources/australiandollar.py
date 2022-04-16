
from com.peercoin.integration.currency import JythonCurrency
from currency_methods_impl import CurrencyMethodsImpl

class AustralianDollar(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "Australian Dollar"

    def getTicker(self):
        return "AUD"

    def getClassName(self):
        return "fiat"

    def getCurrencyMethods(self):
        return CurrencyMethodsImpl()
