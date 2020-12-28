from com.peercoin.integration.currency import JythonCurrency
from currency_methods_impl import CurrencyMethodsImpl

class Monero(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "Monero"

    def getTicker(self):
        return "XMR"

    def getClassName(self):
        return "crypto"

    def getCurrencyMethods(self):
        return CurrencyMethodsImpl()