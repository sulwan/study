```shell
去下载zookeeper,完后放在你的根目录里，解压缩，完后按照下边操作，就增加到服务里了！
cd /etc/init.d

#!/bin/bash
#chkconfig:2345 20 90  
#description:zookeeper  
#processname:zookeeper  
export JAVA_HOME=/usr/java/jdk1.8.0_202
case $1 in
        start) su root /data/zookeeper-3.4.13/bin/zkServer.sh start;;
        stop) su root /data/zookeeper-3.4.13/bin/zkServer.sh stop;;
        status) su root /data/zookeeper-3.4.13/bin/zkServer.sh status;;
        restart) su root /data/zookeeper-3.4.13/bin/zkServer.sh restart;;
        *) echo "require start|stop|status|restart" ;;
esac


:wq!

 chmod +x zookeeper
 
 chkconfig --add zookeeper
 
 chkconfig --list zookeeper
```

