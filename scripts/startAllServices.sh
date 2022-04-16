echo "starting all services"
if [[ "$OSTYPE" == "linux-gnu"* ]]; then
	sudo systemctl start rabbitmq
	sudo systemctl start mongodb
	echo "rabbitmq status:"
	sudo systemctl status rabbitmq | grep "Active:"
	echo "mongodb status:"
	sudo systemctl status mongodb | grep "Active:"
fi
