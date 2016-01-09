# AWS_Practice
AWS Practice Repo

### Runs only from an ec2 server, so ssh into it for running it from commandline

## Commands (to be added in bootstrap)

```bash
#!/bin/bash
yum update -y
yum install git -y
wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo
yum install -y apache-maven
```

## Run:

```bash
sudo su
git clone https://github.com/ankcrimson/AWS_Practice.git
cd S3Sample
mvn clean install
mvn exec:java -Dexec.mainClass="com.amazonaws.samples.S3Sample"
```