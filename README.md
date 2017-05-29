Project Tutor: Peter

Project Member:Cuiqing Li

Update Time:3/23/2017

## Description of the project:
This is a project which is used to simulate a large scale E-Commerce. Also, I am going to use Java to implement this project based on based on TPCW benchmark and classical multi-tier architecture. The platform has following properties: Client Emulator, Distributed Database System, back-end monitor system, read in and write out controlling system. More details will be filled in later on! 

## Software Installation:
# elasticDB node setup
1. set master and slaves in set_env.sh
2. make sure you have installed mysql 5.5 on all the nodes in the queues. 
3. make sure that you have cloned tpcw in lesson 1
4. make sure those nodes in the queues have root access to each other without passwd (lesson 2)

# elasticDB eclipse setup and debug
1. import code to your eclipse
2. install mvn plugin in eclipse
3. run mvn eclipse:eclipse and mvn dependency:resolve

# elasticDB property (lesson 3)
1. modify the scripts/set_env.sh to set the MASTER, SLAVE and CANDIDATE
2. modify the scripts/set_env.sh to set the server that we would like to destroy (to test availability)

# elasticDB experiment setup (do this before you run each time)
1. ./testConnection to test the access of each other
2. ./prepareMasterSlaves to get ready for master, slave and candidates.

# elasticDB run
1. From eclipse, just run without any parameter
2. Or, from eclipse, in order to test scalability run with -c 
3. Or, from eclipse, in order to test availability and scalability run with -c -d
4. Or, you can also run from CommandLine accordingly

# elasticDB monitor (lesson 4)
1. run ./enableMonitors.sh, this will run dstats and open windows for MASTER, SLAVE and CANDIDATE
2. open your browser to point to monitorIp:8080/WebContent/elasticdb.jsp

## Resource Referenced:
Installer and customization from: http://tpcw.deadpixel.de/
PostgreSQL changes from: http://pharm.ece.wisc.edu/tpcw/tpcw-postgresql.html
Original available at: http://pharm.ece.wisc.edu/tpcw.shtml

For installation and usage instructions see http://tpcw.deadpixel.de/
Alternative results analysis using Python at: https://gist.github.com/4086237


