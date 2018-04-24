#!/bin/bash
KEY_NAME="dataeng-infra"
FILE="$KEY_NAME.pem"



KEY_VALUE=$(aws ssm get-parameters --names $KEY_NAME --with-decryption --region us-west-2 --profile de_training | jq '.["Parameters"][0]["Value"]' -r)

echo "$KEY_VALUE" > $FILE
chmod 400 $FILE
cp $FILE ~/.ssh/
# add key to ssh management
ssh-add $File
