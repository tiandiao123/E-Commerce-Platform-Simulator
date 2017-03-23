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

