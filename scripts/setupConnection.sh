#!/bin/bash

HOME=/home/ubuntu/elasticDB

echo "HOME is set to $HOME"

SCRIPT_HOME=/home/ubuntu/elasticDB/scripts

echo "SCRIPT_HOME is set to $SCRIPT_HOME"

CURRENT_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "CURRENT_HOME is set to $CURRENT_HOME"

source $CURRENT_HOME/set_env.sh

TPCW_HOME=$CURRENT_HOME/../tpcw

TPCW_HOME="$( cd "$TPCW_HOME" && pwd )"

echo "TPCW_HOME is set to $TPCW_HOME"

# Check ssh from local to all servers
echo "*** Check ssh from local to all servers *********************************"
for i in $MASTER ${SLAVE[@]} ${CANDIDATE[@]}
do
echo "check access from local to $i"
ssh -o StrictHostKeyChecking=no -o BatchMode=yes root@$i "hostname"
done

echo "*** generate key on all servers *********************************"
for i in $MASTER ${SLAVE[@]} ${CANDIDATE[@]}
do
echo "remove key on $i"
ssh -o StrictHostKeyChecking=no -o BatchMode=yes root@$i "rm -rf /root/.ssh/id_rsa"
echo "generate key on $i"
ssh -o StrictHostKeyChecking=no -o BatchMode=yes root@$i "ssh-keygen -f /root/.ssh/id_rsa -t rsa -N ''"
done


# Check ssh to all servers
echo "*** make connection from all servers to all servers *********************************"

for i in $MASTER ${SLAVE[@]} ${CANDIDATE[@]}
do
for j in $MASTER ${SLAVE[@]} ${CANDIDATE[@]} 
do
echo "make connection from $i to $j"
ssh -o StrictHostKeyChecking=no -o BatchMode=yes root@$i "cat /root/.ssh/id_rsa.pub" | ssh -o StrictHostKeyChecking=no -o BatchMode=yes root@$j 'cat >> .ssh/authorized_keys'
done
done

