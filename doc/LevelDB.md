# LevelDB
LevelDB是一个功能上类Redis的key/value存储引擎。Redis是一个基于纯内存的存储系统，而LevelDB是基于内存 + SSD的架构，内存存储最新的修改和热数据（可理解为缓存），SSD作为全量数据的持久化存储，所以LevelDB具备比redis更高的存储量，且具备良好的写入性能，读性能就略差了，主要原因是由于冷数据需要进行磁盘IO。
Facebook在levelDB的基础上优化了 RocksDB。

## Blood lineage

levelDB -> rocksDB -> TiDB

## Redis! why LevelDB?

当我们将 Redis 拿来做缓存用时，背后肯定还有一个持久层数据库记录了全量的冷热数据。Redis 和持久层数据库之间的数据一致性是由应用程序自己来控制的。

LevelDB 将 Redis 缓存和持久层合二为一，一次性帮你搞定缓存和持久层。有了 LevelDB，你的代码可以简化成下面这样:
```
function getUser(String userId) User {
  return leveldb.get(userId);
}

function updateUser(String userId, User user) {
  leveldb.set(userId, user);
}
```
而且你再也不用当心缓存一致性问题了，LevelDB 的数据更新要么成功要么不成功，不存在中间薛定谔状态。LevelDB 的内部已经内置了内存缓存和持久层的磁盘文件，用户完全不用操心内部是数据如何保持一致的。