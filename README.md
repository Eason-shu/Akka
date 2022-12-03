# 一 基本介绍

## 1.1 什么是Akka?

- Akka一词据说来源于瑞典的一座山，我们说到Akka时，通常是指一个分布式工具集，用于协调远程计算资源来进行一些工作。
- Akka是Actor并发模型的一种现代化实现。现在的Akka可以认为是从许多其他技术发展演化而来的，它借鉴了Erlang的Actor模型实现，同时又引入了许多新特性，帮助构建能够处理如今大规模问题的应用程序。

## 1.2 Actor模型？

![img](https://cdn.nlark.com/yuque/0/2022/webp/12426173/1669376179942-04f676fc-d861-4dfb-8246-69765f65cc05.webp)

- Actor并发模型最早出现于一篇叫作《A Universal Modular Actor Formalism for Artificial Intelligence》的论文，该论文发表于1973年，提出了一种并发计算的理论模型，Actor就源于该模型。
- 在Actor模型中，Actor是一个并发原语；更简单地说，可以把一个Actor看作是一个工人，就像能够工作或是处理任务的进程和线程一样。
- 把Actor看成是某个机构中拥有特定职位及职责的员工可能会对理解有所帮助，比如说一个寿司餐馆，餐馆的职员需要做各种各样不同的工作，给客人准备餐盘就是其中之一。

## 1.3 Actor与消息传递

对象语言的编程的并发问题？

- 在面向对象编程语言中，对象的特点之一就是能够被直接调用：一个对象可以访问或修改另一个对象的属性，也可以直接调用另一个对象的方法。
- 这在只有一个线程进行这些操作时是没有问题的，但是如果多个线程同时读取并修改同一个值，那么可能就需要进行同步并加锁。

Actor的消息传递

- Actor和对象的不同之处在于其不能被直接读取、修改或是调用。
- Actor只能通过消息传递的方式与外界进行通信。
- 简单来说，消息传递指的是一个Actor可以接收消息（在我们的例子中该消息是一个对象），本身可以发送消息，也可以对接收到的消息作出回复。
- Actor每次只同步处理一个消息，邮箱本质上是等待Actor处理的一个工作队列。

![img](https://cdn.nlark.com/yuque/0/2022/jpeg/12426173/1669377474103-7a539334-a751-4131-abe2-3a192c6ae4da.jpeg)

## 1.4 专业术语

-  **Actor**：一个表示工作节点的并发原语，同步处理接收到的消息。Actor可以保存并修改内部状态。
- **消息**：用于跨进程（比如多个Actor之间）通信的数据。
- **消息传递**：一种软件开发范式，通过传递消息来触发各种行为，而不是直接触发行为。
- **邮箱地址**：消息传递的目标地址，当Actor空闲时会从该地址获取消息进行处理。
- **邮箱**：在Actor处理消息前具体存储消息的地方。可以将其看作是一个消息队列。
- **Actor系统**：多个Actor的集合以及这些Actor的邮箱地址、邮箱和配置等。

理解：

1. 比如有一个寿司餐馆，其中有3个Actor：客人、服务员以及厨师
2. 首先，客人向服务员点单，服务员将客人点的菜品写在一张纸条上，然后将这张纸条放在厨师的邮箱中（将纸条贴在厨房的窗户上）
3. 当厨师有空闲的时候，就会获取这条消息（客人点的菜品），然后就开始制作寿司（处理消息），直至寿司制作完成
4. 寿司准备好以后，厨师会发送一条消息（盛放寿司的盘子）到服务员的邮箱（厨房的窗户），等待服务员来获取这条消息，此时厨师可以去处理其他客人的订单
5. 当服务员有空闲时，就可以从厨房的窗户获取食物的消息（盛放寿司的盘子），然后将其送到客人的邮箱（比如餐桌），当客人准备好的时候，他们就会处理消息（吃寿司）
6. 随着越来越多的客人来到餐厅，我们可以想象服务员每次接收一位客人的下单，并将订单交给厨房，接着厨师处理订单制作寿司，最后服务员将寿司交给客人，每个任务都可以并发进行。

优点

- Actor模型的另一个好处就是可以消除共享状态。因为一个Actor每次只处理一条消息，所以可以在Actor内部安全地保存状态。
- Actor通过减少共享状态来解决这一问题。如果我们把状态移到Actor内部，那么只有该Actor才能访问其内部的状态（实际上只有一个线程能够访问这些内部状态）。

## 1.5 Actor的容错机制

- Actor模型也是通过监督机制来提供容错性的。监督机制基本上是指把处理响应错误的责任交给出错对象以外的实体。
- 这意味着一个Actor可以负责监督它的子Actor，它会监控子Actor的运行错误，并且根据子Actor生命周期中的运行表现执行相应的操作。
- 当一个正在运行的Actor发生错误时，监督机制提供的默认处理方式是重新启动发生错误的Actor（实际上是重新创建）。

![img](https://cdn.nlark.com/yuque/0/2022/jpeg/12426173/1669380170105-2f222463-d0bd-4bde-b56e-dc0e43db2804.jpeg)

## 1.6 环境配置

### 1.6.1 Jdk 环境安装

[windows安装JDK步骤_anglePlato的博客-CSDN博客_windows安装jdk](https://blog.csdn.net/anglePlato/article/details/125769403?ops_request_misc=%7B%22request%5Fid%22%3A%22166938083416800215038023%22%2C%22scm%22%3A%2220140713.130102334..%22%7D&request_id=166938083416800215038023&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~baidu_landing_v2~default-1-125769403-null-null.142^v66^control,201^v3^add_ask,213^v2^t3_control1&utm_term=Window Jdk 安装&spm=1018.2226.3001.4187)

### 1.6.2 Scala环境安装

- 首先确保要有Java的环境
- 官网下地址：https://www.scala-lang.org/download/2.11.7.html



![img](https://cdn.nlark.com/yuque/0/2022/png/12426173/1669431183610-ce81da11-8ce5-4cf8-aee0-f58d138d605c.png)



- 配置环境变量



设置 SCALA_HOME 变量：单击新建，在变量名栏输入：**SCALA_HOME**: 变量值一栏输入：**D:\Program Files（x86）\scala** 也就是 Scala 的安装目录，根据个人情况有所不同，如果安装在 C 盘，将 D 改成 C 即可。
![img](https://cdn.nlark.com/yuque/0/2022/png/12426173/1669431183492-5e56d350-2e87-491b-9a9b-ae9e744fe3d6.png)



- 设置 Path 变量：找到系统变量下的"Path"如图，单击编辑。在"变量值"一栏的最前面添加如下的路径： %SCALA_HOME%\bin;%SCALA_HOME%\jre\bin;



![img](https://cdn.nlark.com/yuque/0/2022/png/12426173/1669431183536-180b5881-e747-44a8-b961-546cb62534ed.png)
设置 Path 变量：找到系统变量下的"Path"如图，单击编辑。在"变量值"一栏的最前面添加如下的路径： %SCALA_HOME%\bin;%SCALA_HOME%\jre\bin;
**注意：**后面的分号 **；** 不要漏掉。
![img](https://cdn.nlark.com/yuque/0/2022/png/12426173/1669431183557-50ac9007-cb83-47a7-9a75-cbdcb624cf66.png)



- 验证



![img](https://cdn.nlark.com/yuque/0/2022/png/12426173/1669431183582-bd0fc2de-1ffa-42ba-a193-5a7c45381613.png)

### 1.6.3 安装Typesafe Activator

Typesafe Activator的安装包内打包了Scala、Akka、Play，简单构建工具（Simple Build Tool, SBT）以及其他一些特性，比如项目结构与模板。

下载地址：

https://developer.lightbend.com/start/

从Typesafe下载Typesafe Activator: http://www.typesafe.com/get-started运行安装程序，按照屏幕上的步骤安装。

### 1.6.4 SBT打包环境配置

- 官网：http://www.scala-sbt.org/download.html
- 配置环境变量

SBT_HOME

![img](https://cdn.nlark.com/yuque/0/2022/png/12426173/1669431868528-0950b214-b8d7-41cb-b980-4876f3568902.png)

PATH

![img](https://cdn.nlark.com/yuque/0/2022/png/12426173/1669431912335-23c78d7a-fd5b-4772-aa45-e89846e1213c.png)

- 修改配置文件

![img](https://cdn.nlark.com/yuque/0/2022/png/12426173/1669432016768-866a899b-d8d8-4fbd-9a53-812c88af4942.png)

```java
-Xmx512M
-XX:MaxPermSize=256m
-XX:ReservedCodeCacheSize=128m
-Dsbt.log.format=true
-Dsbt.override.build.repos=true
-Dconsold.encoding=UTF-8
-Dsbt.log.format=true
-Dsbt.ivy.home=D:\Environment\sbt\.ivy
-Dsbt.boot.directory=D:\Environment\sbt\boot
-Dsbt.global.base=D:\Environment\sbt\.sbt
-Dsbt.repository.config=D:\Environment\sbt\conf\repo.properties  # 仓库镜像配置
```

![img](https://cdn.nlark.com/yuque/0/2022/png/12426173/1669432101906-a66e896c-c939-4106-9d80-3f0fcb0f015f.png)

- 仓库设置

```java
[repositories]
local
huaweicloud-maven: https://repo.huaweicloud.com/repository/maven/
maven-central: https://repo1.maven.org/maven2/
sbt-plugin-repo: https://repo.scala-sbt.org/scalasbt/sbt-plugin-releases, [organization]/[module]/(scala_[scalaVersion]/)(sbt_[sbtVersion]/)[revision]/[type]s/[artifact](-[classifier]).[ext]
```

- 验证

![img](https://cdn.nlark.com/yuque/0/2022/png/12426173/1669432177610-66d93808-df46-4986-9982-589d78890d47.png)

### 1.6.5 Idea 配置SBT打包环境

- 插件安装

![img](https://cdn.nlark.com/yuque/0/2022/png/12426173/1669432242334-759d5dcf-3aeb-4a19-aaec-ec03cd1350e5.png)

- SBT 软件配置

![img](https://cdn.nlark.com/yuque/0/2022/png/12426173/1669432293502-c47ef38d-41e3-4d4f-a829-16c954d56b94.png)

到这我们的基本环境搭建完毕

