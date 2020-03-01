# Redis 持久化策略

## RDB（Redis DataBase）(redis默认使用)
`Snapshot快照`，恢复数据时直降快照文件读到内存里。
- Redis会单独创建（fork）一个子进程进行持久化，会先将数据写入一个临时文件，待持久化过程都结束了，再用这个临时文件替换上次持久化好的文件。
  - fork的作用是复制一个与当前主进程一样的进程。新进程的所有数据（变量、环境变量、程序计数器等）都和原进程一致，作为原进程的子进程。
- 整个过程，主进程是不进行任何io操作的，这确保了极高的性能。
- 缺点：最后一次持久化后的数据可能丢失。
- 保存的是dump.rdb文件
  - 恢复时，只需将备份好的文件替换dump.rdb。
  
### 触发
- 配置约定
- save(阻塞进行)、bgsave(异步进行)
- flushdb、flushall

### 修复dump文件
```
redis-check-dump --fix dump.rdb
```

### 恢复
- 将备份文件copy到redis目录，并启动redis服务；
- config get dir （获取dump文件备份目录）

## 优势
- 适合大规模的数据恢复
- 对数据完整性和一致性要求不高（因为最后一次持久化后的数据可能丢失。）

## 劣势
- redis意外down掉，最后一次持久化之前的数据就会丢失。
- fork时，内存中的数据被复制了一份，大致2倍的膨胀性需要考虑。

## AOF（Append Only File）
以日志形式记录每个写操作，将redis执行过的所有写指令记录下来。
- AOF保存的是appendonly.aof

### 当AOF文件损坏
当AOF文件损坏时，可以使用：redis-check-aof指令查看并修复问题（该指令可以将aof文件中所有不符合语法规范的指令统统删除）
```aidl
redis-check-aof --fix appendonly.aof
```

### Appendfsync everysec
- always 实时追加
- everysec 每秒追加
- no 从不同步

### Rewrite 压缩、精简、变小
AOF越来越大，精简指令记录，新增重写机制，只保留可以恢复数据的最小指令集。可以使用指令：bgrewriteaof
- 重写原理
  - 
- 触发机制
  - redis会记录上次重写时的aof文件大小，默认是当aofe文件大小是上次rewrite后大小的一倍且文件大小大于64m时触发
    - auto-aof-rewrite-min-size 64mb   //实际生产环境，一定是个大的值，譬如3gb
    - auto-aof-rewrite-percentage 100  //一倍

### 优势
- appendfsync always 同步持久化，每次发生数据变更会即时记录到磁盘，性能较差但数据完整性比较好；
- appendfsync everysec 每秒同步，只丢失1s数据。
### 劣势
- 相较于RDB，aof文件较大，恢复时间较长

## which one？
- 默认RDB
- 只做缓存，则可不开启持久化。
- 建议同时开启两种方式
  - 通常redis重启时，会优先载入aofwe文件来恢复数据，因为aof的数据完整性较高；
  