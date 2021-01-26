from flask import Flask, jsonify, request
from monero import Monero
import sys

app = Flask(__name__)
monero = Monero()


@app.route('/balance', methods=['GET'])
def get_balance_method():
    return jsonify(monero.getBalance())

@app.route('/newaddress', methods=['GET'])
def get_new_address():
    return jsonify(monero.createAddress())

@app.route('/addressbalance', methods=['GET'])
def get_address_balance():
    addy = request.headers.get('address')
    if addy == None:
        return jsonify({"status": "invalid"})
    return jsonify(monero.getAddressBalance(addy))

@app.route('/pay', methods=['POST'])
def pay():
    addy=request.json.get('destination')
    amount = request.json.get('amount')
    return jsonify(monero.pay(addy, amount))


port=7780
if len(sys.argv) > 1:
    port=sys.argv[1]

app.run(debug=True, port=port)