linux

  1. tcpdump抓包过程？
     1. 先查看网卡：ip a | grep
     2. tcpdump -i ens192 -nn host 192.168.3.63 port 5533 -Xs0
  2. 为什么fork函数成功调用后有两个返回值？
     1. 因为子进程在复制时复制了父进程的堆栈，所以两个进程都停留在fork函数中
  3. arthas缺少类库
     1. sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
     2. apk add libstdc++