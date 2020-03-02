# Mysql 性能分析

## Mysql Query Optimizer
## 常见瓶颈
- CPU
- IO
- 硬件
  - top,free,iostat,vmstat
## Explain
使用explain关键字可以模拟优化器执行SQL,从而知道mysql是如何处理你的sql语句的。分析你的查询语句或者表结构的性能瓶颈。

`explain + sql` 可以看到：
- 表的读取顺序
  - 由 id 来约定
- 数据读取操作的操作类型
  - 有 select_type 来说明
- 哪些索引可以使用
  - possible_keys
- 哪些索引被实际使用
  - key
- 表之间的引用
  - ref
- 每张表有多少行被优化器查询
  - rows

执行计划包含的如下信息：
- *id
  - select查询的序列号，包含一组数字，表示查询中执行select子句或者操作表的顺序
    - id相同，执行顺序由上至下
    - id递增，id值越大优先级越高，越先被执行
    - id有相同有不同，id值大的优先执行，id相同的顺序执行 （Derived 衍生，由虚表产生。）
- *select_type
  - 查询的类型，主要用于区别普通查询、联合查询、子查询等复杂查询
  - SIMPLE
    - 简单的select查询，查询中不包含子查询或者union
  - PRIMARY
    - 查询中若包含任何复杂的子部分，最外层查询则被标记为PRIMARY
  - SUBQUERY
    - 在select或者where中包含子查询
  - DERIVED
    - 在from列表中包含的子查询被标记为Derived（衍生），mysql会递归执行这些子查询，把结果放在临时表中。
  - UNION
    - 若第二个select在union之后，则标记为union
  - UNION RESULT
    - 从union表中获取结果的result
- table
  - 表
- *type
  - 访问类型
    - 较为重要的一个指标！
    - 从最好到最坏一次是：`system > const > eq_ref > ref > range > index > ALL`
    - 优化点：保证查询至少达到`range`级别，最好能达到ref。
    - ALL
    - index
      - full index scan.
    - range
      - 只检索给定范围的行，between,>,<,in等；
    - ref
      - 非唯一性索引扫描，返回匹配某个单独值得所有行。
    - eq_ref
      - 唯一索引扫描，对于每个索引键，表中只有一条记录与之匹配。常见于主键和唯一索引扫描。
    - const,system
      - const表示通过索引一次就找到了，primary key或者unique索引。
    - NULL
- possible_keys
  - 显示可能被应用到这张表的索引，一个或多个。
  - 不一定被查询实际使用
- *key
  - 实际使用到的索引
- ken_len
- ref
  - 查询中与其他表关联的字段，外键关系建立索引。
- *rows
  - 根据表统计信息及索引情况，大致估算出找到所需记录所需要读取的行数。
- *Extra
  - Using filesort


