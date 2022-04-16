import requests
from monero_config import monero_config
from com.peercoin.integration.currency import JythonCurrency
from com.peercoin.core.currency import CurrencyMethods


ATOMIC_MONERO=0.000000000001
class MoneroCurrencyMethods(CurrencyMethods):

    def __init__(self):
        pass
    
    def getBalance(self):
        return requests.get(monero_config['url']+"/balance").json()['balance']
    
    def createAddress(self):
        return requests.get(monero_config['url']+"/newaddress").json()['address']
    
    def pay(self, destination, amount):
            if requests.post(monero_config['url']+"/pay",data={"destination": destination, "amount": amount}).json()['response'] == 'true':
                return True
            return False
    
    def getAddressUnconfirmed(self, address):
        tmp = requests.get(monero_config['url'] + "/addressbalance",headers='address: ' + address).json()
        return tmp['balance'] - tmp['unlocked_balance']
        # indices = self.monero_http_client.get_address_index({'address': address})
        # return self.monero_http_client.get_balance({"address_indices":[indices['index']['minor']]})['balance'] - self.getAddressConfirmed(address)

    
    def getAddressConfirmed(self, address):
        tmp = requests.get(monero_config['url'] + "/addressbalance",headers='address: ' + address).json()
        return tmp['unlocked_balance']
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
        return MoneroCurrencyMethods()