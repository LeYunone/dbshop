# 🏪DBSHOP

[![JDK](https://img.shields.io/badge/JDK-1.8+-green.svg)](https://www.oracle.com/java/technologies/downloads)
[![Stars](https://img.shields.io/github/stars/leyunone/dbshop.svg?style=social)]()

## 🔍背景

在开发新需求或变更/迭代旧功能时，经常会发生数据库表字段变更或表新增/修改等操作，于是版本控制的开发流程中，我们会将代码，以及数据库变动的SQL语句一致发布到测试环境，再到正式环境；

那么在这个过程中，非常容易忽略或忘记自己曾对表、对实体类的变更，而如果刻意为之编写业务的过程中加之记录的话很容易影响开发思路；

虽然目前一些数据库工具中，比如 `Navicat Premium`  为我们提供了一个数据结构对比的工具使用，但随着数据库的逐渐复杂与项目数据库年代的更迭。这些工具无法为我们开发者做到自由度高的比对（比如当两张表的备注发生任何变更时，都会产生SQL），对比的规则都由工具方定义；

所以作为开发者的我们，更希望的是可以自己定义对比过程中的规则，比如无视备注不同，自定义数据优化规则等等...

因此这个工具诞生了，为了如下的展望：

- 自定义对比时的规则
- 根据自己需要，可配置对比产生的SQL类型，比如我希望 `int(1)` 和 `bit(1)` 这种都为 `tinyint(1)`
- 结合项目与数据库，可双向检查代码中没有用到的表和字段，并将结果打印
- 可视化页面操作，最终达到逛数据库和逛超市一样自由

## 📙功能点

- 开箱即用，支持API调用、页面交互、配置文件自动流三种模式
- 可进行：两表之间、两个数据库之间、多个数据库之间...的对比，并输出对比后的表、字段、元属性级结果
- 可支持：SQL语句类型的转化策略 [tinyint(1) = tinyint(1) ]、[datetime(??) = datetime(0) ]... 等转化策略
- 可自动封装，但不限于：1、新增字段； 2、删除字段 ；3、字段类型变更；4、字段字节变更；5、字段备注变更；6、表备注变更；7、字段自增新增和删除；8、表主键新增或删除.....的SQL语句
- 已提供：双向检查无用表、双向检查无用字段、数据库对比API和页面操作
- 支持多种数据库

## 使用

依赖SpringBoot环境

引入依赖：

```xml
<dependency>
  <groupId>com.leyunone</groupId>
  <artifactId>dbshop-core</artifactId>
  <version>1.0.2-SNAPSHOT</version>
</dependency>
```

### API版本（创建单元测试Unit）

#### 数据库对比（数据库或指定表）

目前为试用版本，只支持MySql数据库，并且只有两种类型转换规则

```java
@SpringBootTest
public class ApiTestService {

    @Autowired
    private DbShopStartAPIService dbShopStartAPIService;
    
    @Test
    public void startTest(){
        DbShopDbDTO leftQuery = new DbShopDbDTO();
        leftQuery.setUrl("jdbc:mysql://127.0.0.1:3306/2024_test?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        leftQuery.setDbName("2024_test");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("root");
        DbShopDbDTO rightQuery = new DbShopDbDTO();
        rightQuery.setUrl("jdbc:mysql://127.0.0.1:3306/2024_test_1?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        rightQuery.setDbName("2024_test_1");
        rightQuery.setUserName("root");
        rightQuery.setPassWord("root");
        SqlRuleDTO sqlRuleDTO = new SqlRuleDTO();
        //备注级对比 0关闭 1开启
        sqlRuleDTO.setGoRemark(true);
        //深度对比 0关闭 1开启
        sqlRuleDTO.setGoDeep(true);
        //那张为主表 0 左表  1 右表
        sqlRuleDTO.setLeftOrRight(0);
        //是否封装删除表sql
        sqlRuleDTO.setDeleteTable(true);
        //类型转化规则        	       
        sqlRuleDTO.setTransformReg(MyCollectionUtils.newArrayList(DataTypeRegularEnum.BIT1_TO_TINYINT1, DataTypeRegularEnum.DATETIME_TO_0));
        dbShopStartAPIService.leftRightDbCompare(leftQuery, rightQuery, sqlRuleDTO);
    }
}
```

#### 检查无用表

```java
    @Test
    public void checkUseLessTableTest() {
        DbShopDbDTO leftQuery = new DbShopDbDTO();
        leftQuery.setUrl("jdbc:mysql://127.0.0.1:3306/2024_test?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        leftQuery.setDbName("2024_test");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("root");
        //TODO 该对象为使用者自行指定的修饰实体类的表名注解 为空时，使用工具已搭配的主流注解：1\Mybatis-plus 2\....
        //AnnotateLoadingManager.AnnotateObject annotateObject = new AnnotateLoadingManager.AnnotateObject(VersionDescribe.class, "describe");
        dbShopStartAPIService.checkUselessTable(leftQuery, annotateObject);
    }
```

#### 查询无用字段

```java
    @Test
    public void checkUseLessFieldTest() {
        DbShopDbDTO leftQuery = new DbShopDbDTO();
        leftQuery.setUrl("jdbc:mysql://127.0.0.1:3306/2024_test?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        leftQuery.setDbName("2024_test");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("root");
        //TODO 该对象为使用者自行指定的修饰实体类的字段名注解 为空时，使用工具已搭配的主流注解：1\Mybatis-plus 2\....
        //AnnotateLoadingManager.AnnotateObject annotateObject = new AnnotateLoadingManager.AnnotateObject(VersionDescribe.class, "describe");
        dbShopStartAPIService.checkTableColumnToCode(leftQuery,annotateObject);
    }
```

### 页面版本

引入依赖

```xml
<dependency>
  <groupId>com.leyunone</groupId>
  <artifactId>dbshop-web</artifactId>
  <version>1.0.2-SNAPSHOT</version>
</dependency>
```

启动项目，打开页面：http://localhost:{@your port}/{@your servlet-api}/dbshop/home

## **所有依赖**

| 依赖        | 说明                |
| ----------- | ------------------- |
| dbshop-api  | dbshop-API版本      |
| dbshop-web  | dbshop-页面版本     |
| dbshop-core | dbshop-自动装配版本 |

## TODO

已经实际帮助到我非常多的工具，希望也能帮助到大家

欢迎任何需求任何意见，工具还在开发中，功能敬请期待...

## 🚩FINALY

意见收纳地与联系方式：[https://leyunone.com/](https://leyunone.com/)