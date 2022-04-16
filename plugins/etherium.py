from com.peercoin.integration.currency import JythonCurrency
from currency_methods_impl import CurrencyMethodsImpl

class Etherium(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "Etherium"

    def getTicker(self):
        return "ETH"

    def getClassName(self):
        return "crypto"

    def getCurrencyMethods(self):
        return CurrencyMethodsImpl()