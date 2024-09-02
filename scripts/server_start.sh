#!/usr/bin/env bash
cd /home/ec2-user/server/target
nohup java -jar /home/ec2-user/server/target/inventory-app-backend-1.0-SNAPSHOT.jar > /home/ec2-user/server/application.log 2>&1 &
