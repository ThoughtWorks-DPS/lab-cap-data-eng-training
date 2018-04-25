# Getting started with Apache Kafka

## Setup your environment

If you don't have gradle for java development install it now with homebrew.

```brew install gradle```

Then make sure you've downloaded the private AWS key (see the parent README.md)


## Running Kafka Locally for development

Note: These instructions apply currently only to OS-X

installation of Kafka is simply leveraging homebrew

```
brew install zookeeper
brew install kafka
```

Now lets get our services up and running
```
brew services start zookeeper
brew services start kafka
```
Reminder: Zookeeper is on port 2182 and kafka on 9092

## Access to the training Kafka Cluster in AWS

In order to setup ssh access add the following to your ~/.ssh/config file

```
Host bastion.training.twdps.io
User ec2-user
IdentityFile ~/.ssh/dataeng-infra.pem

Host 10.2.*.*
User ec2-user
ForwardAgent yes
IdentityFile ~/.ssh/dataeng-infra.pem
ProxyCommand ssh -A ec2-user@bastion.training.twdps.io -q -W %h:%p
```
You'll need the IP address of one of the Kafka brokers [will add how later ]

once you have the IP address you can ssh via the bastion host leveraging the configuration you've added to the ~/.ssh/config file

```
ssh -A 10.2.49.81
```

WARNING: You'll be accessing the shared ec2-user's home directory so please do not create any artifacts at the root level.

## Getting started

Step one will be to review the list of topics currently active within the cluster

```
/opt/kafka/bin/kafka-topics.sh --zookeeper 10.2.48.100:2181,10.2.49.100:2181,10.2.50.100:2181 --list
```

Example output
```
__consumer_offsets
bikestream
```

Notice the __consumer_offsets topic, this is used to keep track of consumer offsets in the background

Step two is to create a topic of your own to work with. For this example we'll set the replication factor to be 3, using all of the available
brokers in our cluster. We'll also be setting up 3 partitions.

```
/opt/kafka/bin/kafka-topics.sh --create --zookeeper 10.2.48.100:2181,10.2.49.100:2181,10.2.50.100:2181 --replication-factor 3 --partitions 3 --topic <your topic name>

```
Now lets send some test data. Run the producer and then type a few messages into the console to send to the server.

```
/opt/kafka/bin/kafka-console-producer.sh --broker-list 10.2.48.178:9092,10.2.50.228:9092,10.2.49.81:9092 --topic <your topic name>
Some message text
More message text
```

Now we can open another terminal window, ssh as above and lets setup a consumer to read the data

```
/opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server 10.2.48.178:9092 --offset 'earliest'  --topic ranbir_test
```

Lets look into our topic a bit more and find out how it's organized on the cluster.

```
/opt/kafka/bin/kafka-topics.sh --describe --zookeeper 10.2.48.100:2181,10.2.49.100:2181,10.2.50.100:2181 --topic ranbir_test

Topic:ranbir_test	PartitionCount:1	ReplicationFactor:3	Configs:
Topic: ranbir_test	Partition: 0	Leader: 0	Replicas: 0,1,2	Isr: 0,1,2

```
