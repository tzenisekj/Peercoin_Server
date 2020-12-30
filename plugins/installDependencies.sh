#!/bin/bash

if [ -d "${PWD}/lib/" ]; then
	rm -r lib/
	echo "cleaning '${PWD}/lib/'"
fi

if ! type "python2" > /dev/null; then
	if ! type "python" > /dev/null; then
		echo "python not installed or not in PATH"
	else
		mkdir lib/
		python -m pip install --target=/home/smitty/Documents/repos/carryone/PeerCoin/plugins/lib -r requirements.txt
	fi
else
	mkdir lib/
	python2 -m pip install --target=/home/smitty/Documents/repos/carryone/PeerCoin/plugins/lib -r requirements.txt
fi
