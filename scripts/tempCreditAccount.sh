#!/bin/bash

username=$1
echo ${username}
curl -X POST -H "Content-Type: application/json" -d '{"username":"smith", "amount":"1.0", "crypto": "Bitcoin"}' http://localhost:8081/api/v1/tmp/credit-account
