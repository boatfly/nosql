# Reids conf tips:

## daemonize no（默认）
redis默认不是以守护进程的方式运行。改为"yes"
当以守护进程的方式运行，redis默认会把pid写入/var/run/redis.pid文件
pidfile /var/run/redis.pid

## timeout 0(默认)
当客户端限制多长时间后关闭连接，如果指定为0，则关闭此功能。

## loglevel verbose (默认)
四个级别：debug,verbose,notice,warning

## Maxmemory-policy
当内存使用达到Maxmemory时，可以使用如下五种移除策略：
- volatile-lru
  - 使用LRU算法（最近最少使用）移除key，只对设置了过期时间的键
- allkeys-lru
  - 使用LRU算法移除key
- volatile-random
  - 在过期集合中移除随机的key，只对设置了过期时间的键
- allkeys-random
  - 移除随机的key
- volatile-ttl
  - 移除那些ttl值最小的key，即那些即将过期的key
- noeviction （`默认`）
  - 不进行移除，只是在进行写操作是，返回错误信息

## Maxmemory-samples
设置样本数量，LRU算法和最小TTL算法都并非是精确的算法，而是估算值，所以您可以设置样本的大小。
redis会默认检查这么多个key，并选择LRU的那个进行移除。

## database 16 
数据库的数量，默认0号数据库，select 0-15 可以切换数据库

## dir ./
指定本地数据库存放目录

## save <seconds> <changes>
save 900 1          //15分钟内改了1次
save 300 10         //5分钟内改了10次
save 60  10000      //1分钟内改了一万次

`命令号输入save(全部阻塞)/bgsave（异步运行） 可以手动马上生成内存快照，备份`，同样flushdb/flushall 同样能立即触发备份。

## dbfilename dump.rdb

## appendonly no 
使用AOF方式持久化。redis默认使用RDB方式。
- appendfilename "appendonly.aof"

### 当AOF文件损坏
当AOF文件损坏时，可以使用：redis-check-aof指令查看并修复问题（该指令可以将aof文件中所有不符合语法规范的指令统统删除）
```aidl
redis-check-aof --fix appendonly.aof
```

### Appendfsync everysec
- allways 实时追加
- everysec 每秒追加
- no

### Rewrite
- auto-aof-rewrite-min-size 64mb //实际生产环境，一定是个大的值，譬如3gb
- auto-aof-rewrite-percentage 100  //一倍

## RDB和AOF可以共存，启动首先加载的是AOF(appendonly.aof)