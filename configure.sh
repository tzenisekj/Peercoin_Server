#!/bin/bash

original_dir=${PWD}

cd plugins
./installDependencies.sh


cd ${original_dir}

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
cd scripts/
./configureElectrum.sh
cd ${original_dir}

if [[ "$OSTYPE" == "linux-gnu"* ]]; then
	cd ${original_dir}
	run_services=1
	systemctl cat -- rabbitmq.service &> /dev/null
	if [ $? != 0 ]; then
		run_services=0
		echo "SERVICE rabbitmq MUST BE INSTALLED"
	fi
	systemctl cat -- mongodb.service &> /dev/null
	if [ $? != 0 ]; then
		run_services=0
		echo "SERVICE mongodb MUST BE INSTALLED"
	fi
	if [ $run_services == 1 ]; then
		cd scripts/
		sudo -k ./startAllServices.sh
	fi
fi

