from com.peercoin.integration.currency import JythonCurrency
from com.peercoin.core.currency import CurrencyMethods
import pyjsonrpc, json
from config import bitcoin_config

from requests import Request, Session
from requests.exceptions import ConnectionError, Timeout, TooManyRedirects

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

    # TODO: need to return live data; need to
    def getLastPrice(self):
        # url = 'https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest'

        # url = 'https://pro-api.coinmarketcap.com/v1/cryptocurrency/map'
        # Map
        # Note: in parame need start and limit.
        # Displays: first/last_historical_data, name and symbol. No price.

        # url = 'https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=5000'
        url = 'https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest'
        parameters = {
            'start': '1',
            # 'limit':'5000',
            'convert': 'USD'
        }
        headers = {
            'Accepts': 'application/json',
            # Preferred method
            'X-CMC_PRO_API_KEY': '65a4d8a5-5728-48b4-8b0b-81c35e62251a',
        }

        session = Session()
        session.headers.update(headers)

        try:
            response = session.get(url, params=parameters)
            data = json.loads(response.text)
            temp = data['data']
            livePrice = float(temp[0]['quote']['USD']['price'])
            return livePrice

        except (ConnectionError, Timeout, TooManyRedirects) as e:
            print(e)

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
