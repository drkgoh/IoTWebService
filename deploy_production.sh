#!/bin/bash
mkdir prod
cd prod
rm -rf IoTWebService
git clone https://github.com/newbiesunited/IoTWebService.git
cd IoTWebService
git checkout master
cd ..
rm -rf /opt/tomcat/webapps/IoTWebService/
mkdir /opt/tomcat/webapps/IoTWebService/
cp -R IoTWebService/build/web/*  /opt/tomcat/webapps/IoTWebService/
cp config.json /opt/tomcat/webapps/IoTWebService/WEB-INF/classes/config/config.json
cd ..
sudo systemctl restart tomcat
