# wxl — 用户管理系统

## 📋 项目简介

基于 **Spring MVC 3.2.4 + Hibernate 4.2.5 + MySQL** 的经典 SSH 架构 Web 用户管理系统，由 @wxlHonest 开发。提供用户注册、登录、会话管理等功能，采用 Maven 构建，前端使用 Ace Admin 模板。

## 🔧 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| **核心框架** | Spring Framework (MVC/Core/Context/ORM/JDBC/Beans/AOP/Web/Expression/Test) | 3.2.4.RELEASE |
| **ORM** | Hibernate (Core/EntityManager/Ehcache/JPA 2.0) | 4.2.5.Final |
| **数据库** | MySQL Connector | 5.1.29 |
| **连接池** | Alibaba Druid | 1.0.2 |
| **JSON** | Jackson Mapper | 1.9.11 |
| | Gson | 2.2.2 |
| **日志** | Log4j | 1.2.17 |
| | SLF4J (api + log4j12) | 1.6.6 |
| **Web** | Servlet API | 3.0-alpha-1 |
| | JSP API | 2.1 |
| | JSTL | 1.2 |
| **文件处理** | Commons FileUpload | 1.2.2 |
| | Commons IO | 2.4 |
| | Commons Codec | 1.7 |
| **Office** | Apache POI | 3.14-beta1 |
| | Apache POI OOXML | 3.14-beta1 |
| **HTTP** | Apache HttpClient | 4.3 |
| **WebService** | Apache Axis | 1.4 |
| | Javax WSDL (BIRT) | 1.5.1 |
| | Javax XML RPC | 1.1.1 |
| | Commons Discovery | 0.4 |
| **消息队列** | Aliyun ONS Client | 1.2.1 |
| **条形码** | Barcode4J | 2.0 |
| **构建** | Maven (WAR Plugin 2.2, Compiler 3.1, Surefire 2.18.1) | — |
| **Java** | 1.7 | — |

## 📁 项目结构

```
wxl/
├── pom.xml
├── README.md
├── .settings/                          # Eclipse IDE 配置
│
├── src/main/java/
│   └── wxl/lt/
│       ├── controller/
│       │   └── IndexController.java    # 控制器：登录/注册接口
│       ├── service/
│       │   ├── GetUerInfService.java   # 服务接口
│       │   └── impl/GetUserInfServiceImpl.java  # 服务实现
│       ├── dao/
│       │   ├── GetUserInfDao.java              # 数据访问接口
│       │   └── impl/
│       │       ├── BaseDao.java                 # 通用 DAO 基类（泛型 CRUD）
│       │       └── GetUserInfDaoImpl.java       # 用户 DAO 实现
│       ├── model/
│       │   ├── IdEntity.java           # 实体基类 (Long id, AUTO_INCREMENT)
│       │   ├── TbUser.java             # 用户主表实体
│       │   ├── TbUserInf.java          # 用户扩展信息实体
│       │   └── TbTest.java             # 测试实体
│       ├── form/
│       │   └── UserForm.java           # 表单绑定对象
│       ├── pageModel/
│       │   └── PageMessage.java        # AJAX 响应封装
│       ├── framework/
│       │   ├── constant/GlobalConstant.java    # 全局常量
│       │   └── interceptors/SecurityInterceptor.java  # 权限拦截器
│       └── utils/
│           ├── MD5Util.java            # MD5 加密工具
│           ├── IpUtils.java            # IP 获取工具
│           └── StringUtil.java         # 字符串判空工具
│
├── src/main/resources/
│   ├── config.properties               # 数据库连接配置
│   ├── log4j.properties                # 日志配置
│   ├── spring.xml                      # Spring 核心配置
│   ├── spring-hibernate.xml            # 数据源 + Hibernate + 事务
│   ├── spring-mvc.xml                  # MVC 配置 + 拦截器 + 视图
│   └── META-INF/persistence.xml        # JPA 持久化配置
│
└── src/main/webapp/
    ├── index.jsp                       # 首页
    ├── assets/                         # Ace Admin 模板资源
    │   ├── css/
    │   ├── js/
    │   ├── font/
    │   └── images/
    └── WEB-INF/
        ├── web.xml                     # 部署描述符
        └── view/
            ├── login.jsp               # 登录页面
            └── index.jsp               # 主页
```

## ⚙️ 核心功能

### 1. 用户登录
- **接口**: `POST /index/userLogin`
- 支持 **用户名 / 邮箱 / 手机号** 三种方式登录
- 密码 MD5 加密后比对
- 登录成功存入 `session.setAttribute("systemUser", ...)`
- 记录最后登录时间

### 2. 用户注册
- **接口**: `POST /index/userRegister`
- 注册前检查用户名唯一、邮箱唯一、手机号唯一
- 自动记录注册时间 + 客户端 IP
- 密码 MD5 加密存储

### 3. 会话安全
- `SecurityInterceptor` 拦截所有请求（除 `/index/login`, `/index/userLogin`）
- 未登录用户自动跳转首页

## 🗄️ 数据库设计

### tb_user — 用户主表（继承 IdEntity）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT (PK, AUTO_INCREMENT) | 主键 |
| user_name | VARCHAR(40) | 用户名 |
| password | VARCHAR(50) | 密码 |
| user_email | VARCHAR(255) | 邮箱 |
| user_mobile_no | VARCHAR(11) | 手机号 |
| user_ip | VARCHAR(255) | 注册 IP |
| register_time | DATETIME | 注册时间 |
| last_login_time | DATETIME | 最后登录时间 |
| del_flag | VARCHAR(1) | 软删除标记 |

### tb_user_inf — 用户扩展信息表（继承 IdEntity）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT (PK, AUTO_INCREMENT) | 主键 |
| user_age | INT | 年龄 |
| user_city | VARCHAR(255) | 城市 |
| user_province | VARCHAR(255) | 省份 |

### tb_test — 测试表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | VARCHAR(255) (PK) | 主键（⚠️ 问题见后） |
| user_name | VARCHAR(255) | 用户名 |
| user_age | VARCHAR(255) | 年龄 |

> `hibernate.hbm2ddl.auto=update`，Hibernate 自动建表/更新建表

## 🏗️ 架构分析

```
┌───────────────────────────────────────────┐
│           前端 (JSP + Ace Admin)           │
│          jQuery AJAX → JSON 交互           │
├───────────────────────────────────────────┤
│     Controller 层 (IndexController)        │
│   @RequestMapping → @ResponseBody JSON     │
├───────────────────────────────────────────┤
│     Service 层 (GetUserInfServiceImpl)      │
│         业务逻辑 + 事务边界                 │
│      AOP 事务: add*/save*/update*/get*     │
├───────────────────────────────────────────┤
│     DAO 层 (GetUserInfDaoImpl + BaseDao)   │
│       HQL 查询 → Hibernate CRUD 操作       │
├───────────────────────────────────────────┤
│          Hibernate 4 + Druid 连接池         │
├───────────────────────────────────────────┤
│              MySQL 5.x 数据库               │
└───────────────────────────────────────────┘
```

### 登录请求流程示例

```
浏览器 → POST /index/userLogin
  → CharacterEncodingFilter (UTF-8)
  → SecurityInterceptor.preHandle → 放行（免检路径）
  → IndexController.userLogin()
    → MD5Util.md5(password)
    → GetUserInfServiceImpl.getUserLoginInf()
      → GetUserInfDaoImpl.getUserLoginInf()
        → HQL: from TbUser where userName=x or email=x or mobile=x
      → 密码比对
    → 更新最后登录时间
    → session.setAttribute("systemUser", user)
  → 返回 PageMessage (JSON)
```

## 🔐 安全机制

| 措施 | 实现 | 状态 |
|------|------|------|
| 密码加密 | MD5（单次，无盐） | ⚠️ 弱 |
| 访问控制 | SecurityInterceptor（session 存在性检查） | ⚠️ 二元模型 |
| 字符编码 | CharacterEncodingFilter (UTF-8, force) | ✅ |
| Hibernate | 自动建表 (update) | ⚠️ 生产风险 |
| OpenSessionInView | Filter 已声明但未配 filter-mapping | ❌ 死配置 |

## 🚨 已知问题与安全漏洞（按优先级）

### 🔴 P0 — 立即修复

#### 1. HQL 注入漏洞（严重）
`GetUserInfDaoImpl` 中所有查询都使用**字符串拼接**构造 HQL：
```java
// 当前实现（危险）
String hql = "from TbUser u where u.userName='"+form.getUserName()+"' ...";

// 注入示例：输入 ' or '1'='1 可绕过登录验证
// BaseDao 已有参数化方法 but 未使用！
```
`BaseDao` 已有 `get(String hql, Map<String, Object> params)` 安全版本，但 DAO 实现未使用。

#### 2. 空指针 Bug
`GetUserInfServiceImpl.java:39` — 检查的是 `user` 字段（始终 new 过非空），而非 `userLogin` 本地变量：
```java
private TbUser user = new TbUser();  // 永远非空
// ...
if (user != null) {  // 永远 true！应该检查 userLogin != null
    if (userLogin.getPassword().equals(...)) { // NPE！
```

#### 3. MD5 密码安全问题
- 单次 MD5，无盐，彩虹表攻击可秒破
- `md5For16()` 取子串进一步降低熵值
- 应替换为 bcrypt / Argon2 / PBKDF2

### 🟠 P1 — 尽快修复

| # | 问题 | 说明 |
|---|------|------|
| 4 | 数据库密码明文 | config.properties 中 `jdbc_password=634111` 暴露在版本控制中 |
| 5 | hbm2ddl.auto=update | 生产环境可能意外修改表结构 |
| 6 | 无 RBAC | 所有登录用户拥有相同权限 |
| 7 | 无暴力破解防护 | 登录接口无频率限制/验证码/锁定机制 |
| 8 | 无 CSRF 防护 | 无 CsrfFilter 或同步令牌 |
| 9 | 会话固定漏洞 | 登录后无 session ID 轮换 |
| 10 | 线程安全问题 | Service 层 `private TbUser user` 是单例共享可变状态 |
| 11 | TbTest ID 类型错误 | `String` 类型主键使用 `IDENTITY` 自增（MySQL 不支持） |
| 12 | 接口名拼写错误 | `GetUerInfService` → 应为 `GetUserInfService` |
| 13 | UserForm 职责混乱 | 同时承担输入表单和输出响应（违反单一职责） |
| 14 | @Validated 无效 | UserForm 字段无 JSR-303 注解 |

### 🟢 P3 — 后续改进

| # | 建议 |
|---|------|
| 15 | Spring 3.x 版本较旧，建议升级至 5.x/6.x |
| 16 | 缺少单元测试覆盖 |
| 17 | password 字段 VARCHAR(50) 偏小，升级加密算法后需扩宽 |
| 18 | 事务 AOP 无 timeout 配置，查询可能长时间占用连接 |
| 19 | 数据库表间无外键关联（tb_user 与 tb_user_inf 独立表） |
| 20 | 建议引入 Spring Security 替代手写拦截器 |

## 📝 开发备注

- 使用 Eclipse IDE 开发（保留 `.settings/` 配置文件）
- 开发环境数据库：`jdbc:mysql://localhost:3306/lt`
- 代码中包含 Axis WebService、阿里云 ONS 消息队列、barcode4j 条形码等扩展能力（部分依赖被注释）
- 前端基于 Ace Admin（Bootstrap 3 风格）管理模板

---

> 分析基于 `wxl` 仓库源码自动生成  
> 生成时间：2026-05-08
