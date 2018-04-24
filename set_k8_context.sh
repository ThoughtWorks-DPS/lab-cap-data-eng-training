#!/bin/bash

export AWS_PROFILE=de_training
export AWS_REGION=us-west-2


KEY_NAME="dataeng-k8"

echo "download private key"

KEY_VALUE=$(aws ssm get-parameters --names $KEY_NAME --with-decryption --region us-west-2 --profile $AWS_PROFILE | jq '.["Parameters"][0]["Value"]' -r)

rm -f $KEY_NAME

echo "$KEY_VALUE" >> $KEY_NAME
chmod 400 $KEY_NAME
ssh-add $KEY_NAME

echo "setup your local kubectl config"
kops export kubecfg dataeng.training.twdps.io --state s3://dataeng-tf-backend/

echo "SHOW ALL RUNNING PODS"

kubectl get pods --all-namespaces
