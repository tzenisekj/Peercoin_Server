#import pyjsonrpc
from monerorpc.authproxy import AuthServiceProxy, JSONRPCException
from monero_config import monero_config
import logging

ATOMIC_MONERO=0.000000000001
class Monero:

    def __init__(self):
        logging.basicConfig()
        logging.getLogger("MoneroRPC").setLevel(logging.DEBUG)
        self.monero_http_client = AuthServiceProxy(service_url=monero_config['url']+'/json_rpc', username=monero_config['username'], password=monero_config['password'])
    
    def getBalance(self):
        return {'balance': float(self.monero_http_client.get_balance()['unlocked_balance'])}
    
    def createAddress(self):
        return {'address': self.monero_http_client.create_address({'count':1})['address']}
    
    def pay(self, destination, amount):
        try:
            self.monero_http_client.transfer({'destinations': [{"address": destination, 'amount': int(amount/ATOMIC_MONERO)}], "priority": 0})
            return {"response": True}
        except:
            return {"response": False}
    
    def getAddressBalance(self, address):
        indices = self.monero_http_client.get_address_index({'address': address})
        return {'balance': self.monero_http_client.get_balance({"address_indices":[indices['index']['minor']]}) }

