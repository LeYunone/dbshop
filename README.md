# 🏪DBSHOP
[![JDK](https://img.shields.io/badge/JDK-1.8+-green.svg)](https://www.oracle.com/java/technologies/downloads)
[![Stars](https://img.shields.io/github/stars/leyunone/dbshop.svg?style=social)](https://github.com/Cocowwy/ShowDB)

## 🔍背景

在开发新需求或变更/迭代旧功能时，经常会发生数据库表字段变更或表新增/修改等操作；

而在版本控制的开发流程中，我们会将代码，以及数据库变动的SQL语句一致发布到测试环境，再到正式环境；

那么在这个过程中，我们都要严格的记录数据库级别的变动，如果刻意为之很容易影响开发思路；

所以，非常有必要用到 对比开发环境数据库 / 测试环境数据库 的表差异、字段差异，然后自动转换成所需的转换Sql的工具

## 📙功能点

- 开箱即用，支持API调用、页面交互、配置文件自动流三种模式
- 可进行：两表之间、两个数据库之间、多个数据库之间...的对比，并输出对比后的表、字段、元属性级结果
- 可支持：SQL语句类型的转化策略 [tinyint(1) = tinyint(1) ]、[datetime(??) = datetime(0) ]... 等转化策略
- 可自动封装，但不限于：1、新增字段； 2、删除字段 ；3、字段类型变更；4、字段字节变更；5、字段备注变更；6、表备注变更；7、字段自增新增和删除；8、表主键新增或删除.....的SQL语句
- 支持多种数据库

## 使用

依赖SpringBoot环境

引入依赖：

```xml
<dependency>
  <groupId>com.leyunone</groupId>
  <artifactId>dbshop-service</artifactId>
  <version>1.0.1-RELEASE</version>
</dependency>
```

### API版本

目前为试用版本，只支持MySql数据库，并且只有两种类型转换规则

**创建单元测试Unit**

```java
@SpringBootTest
public class ApiTestService {

    @Autowired
    private DbShopStartAPIService dbShopStartAPIService;
    
    @Test
    public void startTest(){
        DbShopDbDTO leftQuery = new DbShopDbDTO();
        leftQuery.setUrl("jdbc:mysql://localhost:3306/test2023?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        leftQuery.setDbName("test2023");
        leftQuery.setUserName("root");
        leftQuery.setPassWord("root");
        DbShopDbDTO rightQuery = new DbShopDbDTO();
        rightQuery.setUrl("jdbc:mysql://localhost:3306/test2023-1?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&nullCatalogMeansCurrent=true");
        rightQuery.setDbName("test2023-1");
        rightQuery.setUserName("root");
        rightQuery.setPassWord("root");
        SqlRuleDTO sqlRuleDTO = new SqlRuleDTO();
        sqlRuleDTO.setGoRemark(1);
        sqlRuleDTO.setGoDeep(1);
        sqlRuleDTO.setLeftOrRight(0);
        sqlRuleDTO.setDeleteTable(1);
        sqlRuleDTO.setTransformReg(CollectionUtil.newArrayList(DataTypeRegularEnum.BIT1_TO_TINYINT1,DataTypeRegularEnum.DATETIME_TO_0));
        dbShopStartAPIService.leftRightDb(leftQuery,rightQuery,sqlRuleDTO);
    }
}
```


## **所有依赖**

| 依赖           | 说明                |
| -------------- | ------------------- |
| dbshop-api     | dbshop-API版本      |
| dbshop-web     | dbshop-页面版本     |
| dbshop-service | dbshop-自动装配版本 |

## TODO

开发中....

## 🚩FINALY

意见收纳地与联系方式：[https://leyunone.com/](https://leyunone.com/)

