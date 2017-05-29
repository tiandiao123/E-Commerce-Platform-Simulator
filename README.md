Project Tutor: Peter

Project Member:Cuiqing Li

Update Time:3/23/2017

### Description of the project:
This is a project which is used to simulate a large scale E-Commerce. Also, I am going to use Java to implement this project based on based on TPCW benchmark and classical multi-tier architecture. The platform has following properties: Client Emulator, Distributed Database System, back-end monitor system, read in and write out controlling system. More details will be filled in later on! 


##############################################################################
# tpcw.properties for build.xml.
# Copyright 2003 by Jan Kiefer.
#
# This file is distributed "as is". It comes with no warranty and the 
# author takes no responsibility for the consequences of its use.
#
# Usage, distribution and modification is allowed to everyone, as long 
# as reference to the author(s) is given and this license note is included.
##############################################################################

# set the JDBC parameters
#jdbc.driver=com.mysql.jdbc.Driver
jdbc.driver=com.mckoi.JDBCDriver
jdbc.path=jdbc:mckoi://localhost/?user=admin&password=admin
jdbc.connPoolMax=100

sql.bigCharType=varchar(500)
#sql.bigCharType=tinyblob

# set the values you want for tpcw
num.item=1000
num.eb=10

# use the right session string for your servlet container
#sessionIdString=$sessionid$
sessionIdString=jsessionid=

standardUrl=http://localhost:8080
#servletUrlPath=/servlet
servletUrlPath=
tpcwUrlPath=/tpcw


