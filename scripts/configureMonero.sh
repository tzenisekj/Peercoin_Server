#!/bin/bash


if [ -z ${MONERO_WALLET_NAME+x} ]; then export MONERO_WALLET_NAME="moneroDefault"; else echo "wallet is set to '$MONERO_WALLET_NAME'"; fi
if [ -z ${MONERO_WALLET_PASSWORD+x} ]; then
    echo "Using default password"; MONERO_WALLET_PASSWORD="ourBoiLovesHisIceCream"
fi
if [ -z ${MONERO_DAEMON_ADDY+x} ]; then
    echo "Using default password"; MONERO_DAEMON_ADDY="node.xmr.to:18081"
fi
if ! type "monero-wallet-cli" > /dev/null; then
    echo "monero wallet cli not installed. Skipping, ensure wallet with $MONERO_WALLET_NAME exists"
fi
echo "building monero wallet"
monero-wallet-cli --generate-new-wallet ${PWD}/../wallet/${MONERO_WALLET_NAME} --password ${MONERO_WALLET_PASSWORD} --use-english-language-names --daemon-address ${MONERO_DAEMON_ADDY}
if [ -z ${MONERO_RPC_NAME+x} ]; then MONERO_RPC_NAME="monero"; else echo "wallet is set to '$MONERO_RPC_NAME'"; fi
if [ -z ${MONERO_RPC_PASSWORD+x} ]; then
    echo "Using default password"; MONERO_RPC_PASSWORD="whydoeshedothistome"
fi

if ! type "monero-wallet-rpc" > /dev/null; then
    echo "monero wallet rpc not installed"
else
    echo "starting monero wallet rpc daemon"
    monero-wallet-rpc --daemon-address ${MONERO_DAEMON_ADDY} --rpc-bind-port 7779 --wallet-file ${PWD}/../wallet/${MONERO_WALLET_NAME} --password ${MONERO_WALLET_PASSWORD} --rpc-login ${MONERO_RPC_NAME}:${MONERO_RPC_PASSWORD} --detach
fi

python lib/generateMoneroConfig.py ${MONERO_RPC_NAME} ${MONERO_RPC_PASSWORD}
