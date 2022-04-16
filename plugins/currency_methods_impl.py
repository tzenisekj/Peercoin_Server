from com.peercoin.core.currency import CurrencyMethods

class CurrencyMethodsImpl(CurrencyMethods):

    def __init__(self):
        pass

    def getBalance(self):
        return 0.0
    
    def createAddress(self):
        return "temp_address"
    
    def pay(self, destination, amount):
        return False
    
    def getAddressConfirmed(self, address):
        return 0.0
    
    def getAddressUnconfirmed(self, address):
        return 0.0

    def getLastPrice(self):
        return 0.0