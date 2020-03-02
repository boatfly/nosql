# Kettle
ETL（Extract-Transform-Load）工具，开源，by Java，无需安装。

已更名：PDI(Pentaho Data Integration)

两种脚本文件：
- transformation
  - 完成对数据的基础转换
  - Steps 步骤 或者 控件
    - 读取文件
    - 过滤数据行
    - 数据清洗
    - ...
  - Hops 节点连接
    - Steps里的Step通过Hop进行连接，当以一个单向通道，允许数据从一个步骤流向下一个步骤。
    - 并发，基于行级缓存
- job
  - 完成整个工作流程的控制
  - Get files
  - Verifies
  - Executes
  
## Kettle 结构
- Spoon
  - 构建ETL job和transfermation的工具。
- Data Integration Server
  - ETL Server

## Kettle 核心组件
- Spoon
  - 通过图形接口，用于编辑作业和转换的桌面应用
- Pan
  - 一个独立的应用程序，用于执行由Spoon编辑的转换和作业
- Kitchen
  - 一个独立的应用程序，用于执行由Spoonb编辑的作业
- Carte
  - 轻量级的web容器，用于建立专用、远程的ETL Server
  
## 可视化编程语言 VPL







