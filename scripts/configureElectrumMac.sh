if [ -f "/Applications/Electrum.app/Contents/MacOS/run_electrum" ]; then
	if [ -z ${WALLET_NAME+x} ]; then WALLET_NAME="default"; else echo "wallet is set to '$WALLET_NAME'"; fi
	if [ -z ${WALLET_PASSWORD+x} ]; then
		echo "Using default password"; WALLET_PASSWORD="ourBoiLovesHisIceCream"
	fi
	echo "starting electrum daemon"
	/Applications/Electrum.app/Contents/MacOS/run_electrum daemon -d
	echo "set electrum rpc port 7777:"
	/Applications/Electrum.app/Contents/MacOS/run_electrum setconfig rpcport 7777
	cd ..
	new_path=${PWD}/wallet/${WALLET_NAME}
	cd scripts/
	echo "set wallet path ${new_path}:"
	/Applications/Electrum.app/Contents/MacOS/run_electrum setconfig wallet_path $new_path
	python lib/generateElectrumWalletMac.py ${WALLET_PASSWORD}
else
	echo "electrum not installed"
fi
