数据库比较工具

当应用工具，开箱即用，三个使用版本

## 使用版本

1、 自动读取项目数据库配置，选择对比目标数据库

2、 在线页面模式，输入数据库一、数据库二，选择对比

3、 控制台打印模式，配合（1），直接在控制台将对比结果输出



## 随手记开发记录：

### 数据库结果集

由于Connection的特性，即断开销毁；

所以在连接中需要将load的目标数据库一次性解析成

DB -> TALBE -> COLUMN

的关联形式

使用 **CompleteFuture** ，将主线程等待数据库Load加载信息的动作，拆成3个子线程完成；同事主线程等待结果，分别返回三个【DB、TABLE、COLUMN】的加载结果

【DB、TABLE、COLUMN】的存储模式采用**策略存储**；

URL+DB+TALBENAME+COLUMN

...

### 数据URL参数问题

```xml
jdbc:mysql://地址:3306/数据库名?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true
```

1、nullCatalogMeansCurrent=true问题

![](https://leyunone-img.oss-cn-hangzhou.aliyuncs.com/image/2023-06-13/4285c930-a0ed-46de-86eb-63e6c3f2fe47.png)

### 类型转化策略

见[策略工厂下的架构设计](https://leyunone.com/Interesting-design/strategy-factory-together.html)

由于Mysql驱动与JDBC的设计一致性问题；

会出现datetime 不设置长度，变成datetime(26)、tinyint(1)变成bit(1)，等等问题

所以需要一个处理流做两件事，一是拿到对应规则工厂，二是执行策略

并且需要对外放开，有结果集与无结果集两种处理模式供后续扩展

在多数据库类型下 ，类型转化工厂类型居多，所以还需使用模板模式控制各数据库类型的sql和类型转化规则

### SQL语句自动解析提取

在表/字段 进行 **对比** 操作后，将最终结果集交给sqlProduction类分析处理。

处理过程，采用消息模板+填充的方式

见 [消息模板的设计](https://leyunone.com/unidentified-business/message-center-design.html#%E8%AE%BE%E8%AE%A1)

预先设定好各sql语句，比如

```java
public enum SqlModelEnum {

    //SQL语句模板
    //采用{}进行内容填充

    ADD_COLUMN("ALTER TABLE {} ADD COLUMN {} {}({}) COMMENT '{}' ;", "新增字段"),
	.......
```

进行一系列主表/从表；名是否相同，类型是否相同，size是否相同，备注是否相同；得到一个对比出来的总sql集



## 达成目标

