import sys
import subprocess
import json
import os
import platform
import time

if platform.system() == 'Linux':
    electrum = 'electrum'
elif platform.system() == 'Darwin':
    electrum = "/Applications/Electrum.app/Contents/MacOS/run_electrum"

def main():
    if (len(sys.argv) != 2):
        print("ERROR: Incorrect set of arguements for generateElectrumWallet.py. 1 password arguement is required")
        return
    wallet_path = subprocess.check_output([electrum, "getconfig", "wallet_path"])
    wallet_path = wallet_path[:len(wallet_path)-1]
    wallet = createWallet(wallet_path, sys.argv[1])
    writeConfig(wallet_path, sys.argv[1])
    writeSensitive(wallet)
    load_wallet(wallet_path, sys.argv[1])

def load_wallet(wallet_path, wallet_password):
    subprocess.check_output([electrum, 'load_wallet', '-w', wallet_path, '-W', wallet_password])
    
def writeConfig(wallet_path, wallet_password):
    user = subprocess.check_output([electrum, "getconfig", "rpcuser"]).strip()
    password = subprocess.check_output([electrum, "getconfig", "rpcpassword"]).strip()
    file = open(os.path.join(os.getcwd(), "../config/web/bitcoin_config.py"), "w+")
    file.write("bitcoin_config = {\n")
    if os.environ.get("ELECTRUM_HOSTNAME"):
        hostname = os.environ.get("ELECTRUM_HOSTNAME")
    else:
        hostname = "http://localhost"
    file.write('\t"url": "' + hostname + ":7777\",\n")
    file.write('\t"username": "' + user.decode("utf-8") + "\",\n")
    file.write('\t"password": "' + password.decode("utf-8") + "\",\n")
    file.write('\t"wallet_password": "' + wallet_password + "\",\n")
    file.write('\t"api_key": "' + '65a4d8a5-5728-48b4-8b0b-81c35e62251a' + "\"\n")
    file.write("}\n")
    print("wrote config/web/bitcoin_config.py")

def writeSensitive(walletOutput):
    pass

def createWallet(path, password):
    print("wallet created at " + path.decode("utf-8"))
    tmp = load_subprocess([electrum, "create", "-w", path, "-W", password])
    return tmp

def load_subprocess(commands):
    output = subprocess.check_output(commands)
    tmp = json.loads(output)
    return tmp

if __name__ == "__main__":
    main()
