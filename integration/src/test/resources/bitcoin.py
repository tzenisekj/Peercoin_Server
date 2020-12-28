from com.peercoin.integration.currency import JythonCurrency
from com.peercoin.core.currency import CurrencyMethods
import pyjsonrpc
from config import bitcoin_config

class BitcoinCurrencyMethods(CurrencyMethods):

    def __init__(self):
        self.http_client = pyjsonrpc.HttpClient(
            url=bitcoin_config['url'],
            username=bitcoin_config['username'],
            password=bitcoin_config['password']
        )

    
    def getBalance(self):
        return float(self.http_client.call('getbalance')['confirmed'])
        
    
    def createAddress(self):
        return self.http_client.call('createnewaddress')
    
    def pay(self, destination, amount):
        try:
            self.http_client.call('payto', destination=destination, amount=amount, password=bitcoin_config['wallet_password'])
            return True
        except:
            return False
    
    def getAddressUnconfirmed(self, address):
        return float(getAddressBalance(self.http_client, address)['unconfirmed'])
    
    def getAddressConfirmed(self, address):
        return float(getAddressBalance(self.http_client, address)['confirmed'])
    
def getAddressBalance(http_client, address):
    tmp = http_client.call("getaddressbalance",address)
    return tmp


class Bitcoin(JythonCurrency):

    def __init__(self):
        pass

    def getName(self):
        return "Bitcoin"

    def getTicker(self):
        return "BTC"

    def getClassName(self):
        return "crypto"
    
    def getCurrencyMethods(self):
        return BitcoinCurrencyMethods()
