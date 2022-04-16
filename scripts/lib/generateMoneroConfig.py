import sys
import subprocess
import json
import os
import platform

def main():
    if (len(sys.argv) != 3):
        print("ERROR: Incorrect set of arguements for generateMoneroConfig.py. 1 username arguement, 1 password arguement is required")
        return
    writeConfig("../wallet/" + os.environ.get("MONERO_WALLET_NAME"), sys.argv[1], sys.argv[2])

def writeConfig(wallet_path, rpc_username, rpc_password):
    user = rpc_username
    password = rpc_password
    file = open(os.path.join(os.getcwd(), "../config/web/monero_config.py"), "w+")
    file.write("monero_config = {\n")
    if os.environ.get("MONERO_HOSTNAME"):
        hostname = os.environ.get("MONERO_HOSTNAME")
    else:
        hostname = "http://localhost:7779"
    file.write('\t"url": "' + hostname + '",\n')
    file.write('\t"username": "' + user + "\",\n")
    file.write('\t"password": "' + password + "\",\n")
    file.write("}\n")
    print("wrote config/web/monero_config.py")

if __name__ == "__main__":
    main()