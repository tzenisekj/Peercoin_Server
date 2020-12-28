#!/bin/bash

if [ -d "${PWD}/config/" ]; then
	rm -r config/
	echo "cleaning '${PWD}/config/'"
fi
if [ -d "${PWD}/wallet/" ]; then
	rm -r wallet/
	echo "cleaning '${PWD}/wallet/'"
fi
mkdir wallet
mkdir config
mkdir config/web
original_dir=${PWD}
cd scripts/
./configureElectrum.sh

