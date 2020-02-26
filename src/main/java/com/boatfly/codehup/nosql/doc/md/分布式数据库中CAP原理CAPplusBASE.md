# 分布式数据库中CAP原理CAP+BASE

## 传统的ACID
- A(Atomicity) 原子性
- C(Consistency) 一致性
- I(Isolation) 独立性
- D(Durability) 持久性

## NoSQL的CAP
- C(Consistency) 强一致性
- A(Availability) 可用性
- P(Partition tolerance) 分区容错性

### CAP的3选2
- CAP核心理论：一个分布式系统不可能同时满足一致性、可用性和分区容错这三个特性；最多只能满足2个；
  - CA
    - 单点集群，满足一致性、可用性的系统，通常在可扩展性上不太强大；
    - 传统RDMS数据库
  - CP
    - 满足一致性、分区容错性的系统，通常性能不是特别高；
    - redis，mongodb
  - AP
    - 满足可用性、分区容错性的系统，通常对一致性要求不高；
    - 大多数网站架构：`弱一致性+AP`
    
#### 一致性和可用性的选择
大部分web实时系统并不要求严格的数据库事务，对读一致性要求较低，，有些场合对写一致性要求也不高，允许实现最终一致性。
比如说：发一条消息后，过几秒乃至十几秒之后，我的订阅者才看到这条动态是可以接受的。

## BASE
思想：通过让系统放松对某一时刻数据一致性的要求来换取系统整体伸缩性和性能上改观。
- 基本可用 Basically Available
- 软状态 Soft state
- 最终一致 Eventually consistent

 

