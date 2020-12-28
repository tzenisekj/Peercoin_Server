if ! type "electrum" > /dev/null; then
	echo "electrum not installed"
else
	if [ -z ${WALLET_NAME+x} ]; then WALLET_NAME="default"; else echo "wallet is set to '$WALLET_NAME'"; fi
	if [ -z ${WALLET_PASSWORD+x} ]; then
		echo "Using default password"; WALLET_PASSWORD="ourBoiLovesHisIceCream"
	fi
	echo "starting electrum daemon"
	electrum daemon -d
	echo "set electrum rpc port 7777:"
	electrum setconfig rpcport 7777
	cd ..
	new_path=${PWD}/wallet/${WALLET_NAME}
	cd scripts/
	echo "set wallet path ${new_path}:"
	electrum setconfig wallet_path $new_path
	python lib/generateElectrumWallet.py ${WALLET_PASSWORD}
fi
