# Configuration of the trainee environment for S3/EMR

## Setup of AWS accounts
For each trainee an AWS account in the capabilities AWS account should be created (via Okta preferably and the user should be a member of the 'de_training_group'. This will afford the user the ability to download the necessary ssh private keys the user needs to setup SSH tunnels, use kubectl with Kubernettes, and access the S3 bucket used for the training exercises.

After the user is created they should login and download their AWS access key id/secret access key

## AWS local Setup

As many of the trainees may already have AWS credentials the process is designed to leverage the ability of users to have more than one access profile, using de_training as the aws profile.

Edit ~/.aws/credentials and add the following information
```
[de_training]
aws_access_key_id = <aws_access_key_id>
aws_secret_access_key = <aws_secret_access_key>
```

## install JQ

You need to install JQ to allow the bash scripts to parse JSON output. To do so we'll use homebrew on OS X devices. For other OS's use the
appropriate package manager.

```brew install jq```

## Accessing EMR


The Training EMR cluster is provisioned on a private unreachable AWS subnet for security purposes. Access to the EMR master and slave nodes is provided by an SSH bastion host, bastion.training.twdps.io using a dynamic ssh tunnel and a SOCKS proxy.

### Download the private key

The first step in the local setup process is to download the ssh private key 'dataeng-infra.pem' from the AWS parameter store by running the following:

```./get-private-key.sh ```

this will download the private key

### Setup ssh tunnel

Issue the following in your terminal window and leave open for the entire work process:

```ssh -i dataeng-infra.pem -ND 9500 ec2-user@bastion.training.twdps.io```

###Configure FoxyProxy for Google Chrome

In order to leverage the various web based tools available with EMR it is ncessary to setup a SOCKS proxy within a web browser.

You can configure FoxyProxy for Google Chrome, Mozilla Firefox, and Microsoft Internet Explorer. FoxyProxy provides a set of proxy management tools that allow you to use a proxy server for URLs that match patterns corresponding to the domains used by the Amazon EC2 instances in your Amazon EMR cluster.

##### To install and configure FoxyProxy using Google Chrome

See https://chrome.google.com/webstore/search/foxy%20proxy and follow the links and instructions to add FoxyProxy to Chrome.

Manage extensions in Chrome (go to chrome://extensions).

Choose Options for FoxyProxy Standard.

On the FoxyProxy page, choose Import/Export.

On the Import/Export page, choose Choose File, browse to the location of the proxy-settings.xml file in this repo, select the file, and choose Open.

Choose Replace when prompted to overwrite the existing settings.

For Proxy mode, choose Use proxies based on their predefined patterns and priorities.

### Access Web interfaces

To open the web interfaces, in your browser's address bar, type 'emr.training.twdps.io' followed by the port number or URL.

| Resource | Link |
| -------- | ---- |
|YARN ResourceManager |	http://emr.training.twdps.io:8088/ |
|Hadoop HDFS NameNode |	http://emr.training.twdps.io:50070/ |
|Spark HistoryServer	| http://emr.training.twdps.io:18080/ |
|Zeppelin	| http://emr.training.twdps.io:8890/ |
|Hue	| http://emr.training.twdps.io:8888/ |
|Ganglia | http://emr.training.twdps.io/ganglia/ |


## S3 Bucket

The S3 bucket for the training exercise is twna-dataeng-training-storage

## Access to Kubernettes

To access the k8 cluster it's necessary to download the private key and setup your k8 kubeconfig

First you need to install kops, we'll do this via homebrew as well:

```brew install kops```

Once this is done you can run:

```./set_k8_context.sh```

This should download the appropriate keys, setup your kubectl configuration and as a test display all the running pods within the k8 cluster
