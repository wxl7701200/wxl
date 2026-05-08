# wxl — 用户管理系统

## 📋 项目简介

基于 **Spring MVC 3.2 + Hibernate 4.2 + MySQL** 的 Web 用户管理系统，提供用户注册、登录、会话管理等功能。采用经典的 **SSH（Spring + Struts/SpringMVC + Hibernate）** 架构模式，使用 Maven 构建。

## 🔧 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| **框架** | Spring MVC | 3.2.4.RELEASE |
| | Spring Core / Context / ORM / Web | 3.2.4.RELEASE |
| **ORM** | Hibernate Core / EntityManager | 4.2.5.Final |
| **数据库** | MySQL 5.x | — |
| **连接池** | Alibaba Druid | 1.0.2 |
| **构建工具** | Maven | — |
| **容器** | Servlet 3.0 | — |
| **前端** | JSP + jQuery + Ace Admin 模板 | — |
| **JSON** | Jackson | 1.9.11 |
| **日志** | Log4j + SLF4J | 1.2.17 / 1.6.6 |
| **其他** | Apache POI, Axis (WebService), 条形码(barcode4j), 阿里云ONS消息队列 | — |

## 📁 项目结构

```
wxl/
├── pom.xml                                          # Maven 构建配置
├── README.md                                         # 本文件
├── .settings/                                        # Eclipse IDE 配置
│
├── src/main/java/
│   └── wxl/lt/
│       ├── controller/
│       │   └── IndexController.java                  # 控制器：登录/注册接口
│       │
│       ├── service/
│       │   ├── GetUerInfService.java                 # 服务接口
│       │   └── impl/
│       │       └── GetUserInfServiceImpl.java        # 服务实现
│       │
│       ├── dao/
│       │   ├── GetUserInfDao.java                    # 数据访问接口
│       │   └── impl/
│       │       ├── BaseDao.java                      # 通用 DAO 基类（泛型）
│       │       └── GetUserInfDaoImpl.java            # 用户信息 DAO 实现
│       │
│       ├── model/
│       │   ├── IdEntity.java                         # 实体基类（统一定义 Long 类型自增 ID）
│       │   ├── TbUser.java                           # 用户实体（tb_user 表）
│       │   ├── TbUserInf.java                        # 用户扩展信息实体（tb_user_inf 表）
│       │   └── TbTest.java                           # 测试实体（tb_test 表）
│       │
│       ├── form/
│       │   └── UserForm.java                         # 表单对象（登录/注册表单绑定）
│       │
│       ├── pageModel/
│       │   └── PageMessage.java                      # 前端 AJAX 响应封装（成功/失败 + 消息 + 列表数据）
│       │
│       ├── framework/
│       │   ├── constant/
│       │   │   └── GlobalConstant.java               # 全局常量定义
│       │   └── interceptors/
│       │       └── SecurityInterceptor.java          # 权限拦截器
│       │
│       └── utils/
│           ├── MD5Util.java                          # MD5 加密工具类
│           ├── IpUtils.java                          # IP 地址获取工具类
│           └── StringUtil.java                       # 字符串判空工具类
│
├── src/main/resources/
│   ├── config.properties                             # 数据库连接 & 上传配置
│   ├── log4j.properties                              # 日志配置
│   ├── spring.xml                                    # Spring 核心配置
│   ├── spring-hibernate.xml                          # 数据源 & Hibernate & 事务配置
│   ├── spring-mvc.xml                                # Spring MVC 配置 & 拦截器 & 视图解析
│   └── META-INF/
│       └── persistence.xml                           # JPA 持久化配置
│
└── src/main/webapp/
    ├── index.jsp                                     # 首页（欢迎页面）
    ├── assets/                                       # 前端静态资源（Ace Admin 模板）
    │   ├── css/
    │   ├── js/
    │   ├── font/
    │   └── images/
    └── WEB-INF/
        ├── web.xml                                   # Web 部署描述符
        └── view/
            ├── login.jsp                             # 登录页面
            └── index.jsp                             # 主页面（登录后）
```

## ⚙️ 核心功能

### 1. 用户登录
- **接口**: `POST /index/userLogin`
- 支持 **用户名 / 邮箱 / 手机号** 三种方式登录
- 密码以 MD5 加密传输后进行比对
- 登录成功后将用户信息存入 `HttpSession`
- 记录最后登录时间

### 2. 用户注册
- **接口**: `POST /index/userRegister`
- 注册时校验：用户名唯一、邮箱唯一、手机号唯一
- 自动记录注册时间和注册 IP 地址
- 密码使用 MD5 加密存储

### 3. 会话安全
- **SecurityInterceptor** 权限拦截器对所有页面（除 `/index/login`, `/index/userLogin`）进行访问控制
- 未登录用户自动跳转到首页

## 🗄️ 数据库设计

### tb_user — 用户主表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT (自增) | 主键 |
| user_name | VARCHAR(40) | 用户名 |
| password | VARCHAR(50) | 密码（MD5 加密） |
| user_email | VARCHAR(255) | 邮箱 |
| user_mobile_no | VARCHAR(11) | 手机号 |
| user_ip | VARCHAR(255) | 注册 IP |
| register_time | DATETIME | 注册时间 |
| last_login_time | DATETIME | 最后登录时间 |
| del_flag | VARCHAR(1) | 删除标记 |

### tb_user_inf — 用户扩展信息表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT (自增) | 主键 |
| user_age | INT | 年龄 |
| user_city | VARCHAR(255) | 城市 |
| user_province | VARCHAR(255) | 省份 |

### tb_test — 测试表

| 字段 | 类型 | 说明 |
|------|------|------|
| id | VARCHAR (自增) | 主键 |
| user_name | VARCHAR(255) | 用户名 |
| user_age | VARCHAR(255) | 年龄 |

> 注：`hibernate.hbm2ddl.auto=update`，Hibernate 会根据实体类自动建表/更新表结构。

## 🏗️ 架构分析

### 架构分层
```
┌──────────────────────────────────────────────┐
│                 前端 (JSP + jQuery)            │
│        Ace Admin 模板 + AJAX 请求              │
├──────────────────────────────────────────────┤
│           Controller 层 (IndexController)     │
│        @RequestMapping 处理 HTTP 请求          │
├──────────────────────────────────────────────┤
│           Service 层 (GetUserInfServiceImpl)   │
│             业务逻辑 + 事务管理                 │
├──────────────────────────────────────────────┤
│           DAO 层 (GetUserInfDaoImpl)           │
│          HQL 查询 + Hibernate CRUD             │
├──────────────────────────────────────────────┤
│             数据库 (MySQL + Druid 连接池)       │
└──────────────────────────────────────────────┘
```

### 请求流程（登录为例）
```
浏览器 → POST /index/userLogin
  → CharacterEncodingFilter (UTF-8)
  → SecurityInterceptor.preHandle (免检)
  → IndexController.userLogin()
    → MD5Util.md5(password)
    → GetUserInfServiceImpl.getUserLoginInf()
      → GetUserInfDaoImpl.getUserLoginInf()
        → HQL: from TbUser where userName=x or userEmail=x or userMobileNo=x
    → 密码比对
    → 更新最后登录时间
    → 存入 session
  → 返回 JSON (PageMessage)
```

## 🔐 安全机制

| 措施 | 实现方式 | 状态 |
|------|----------|------|
| 密码加密 | MD5 摘要 | ✅ 已实现 |
| 会话控制 | SecurityInterceptor + Session | ✅ 已实现 |
| 字符编码 | CharacterEncodingFilter (UTF-8) | ✅ 已实现 |
| SQL 注入 | HQL 字符串拼接（**存在风险**） | ⚠️ 需改进 |
| OpenSessionInView | 已配置（解决延迟加载） | ✅ |

### ⚠️ 已知安全问题

1. **HQL 注入风险** — DAO 层使用字符串拼接方式构造 HQL，如：
   ```
   from TbUser u where u.userName='"+form.getUserName()+"'
   ```
   **建议改为**参数绑定：
   ```java
   String hql = "from TbUser u where u.userName=:userName or u.userEmail=:email";
   query.setParameter("userName", form.getUserName());
   ```

2. **MD5 不加盐** — 密码仅做简单 MD5，建议改为 `bcrypt` 或 `scrypt`。

3. **密码字段长度偏小** — `password` 字段 `VARCHAR(50)`，MD5 摘要 32 字符后剩余空间小。

## 💡 改进建议

| 优先级 | 建议 | 说明 |
|--------|------|------|
| 🔴 高 | 修复 HQL 注入 | 所有拼接查询改为参数绑定 |
| 🔴 高 | 密码算法升级 | MD5 → bcrypt/Spring Security |
| 🟡 中 | 前端框架升级 | JSP + jQuery → Vue/React |
| 🟡 中 | Spring 版本升级 | 3.x → 5.x/6.x |
| 🟡 中 | 增加单元测试 | 补充 JUnit 测试覆盖 |
| 🟢 低 | 配置外置化 | 数据库密码等敏感配置加密 |
| 🟢 低 | RESTful API 规范化 | 统一响应格式 |

## 📝 开发备注

- 项目最初使用 Eclipse IDE 开发（`.settings/` 目录）
- 开发环境数据库：`localhost:3306/lt`，用户 `root`
- Maven 仓库使用了阿里云镜像
- 前端模板基于 **Ace Admin**（Bootstrap 3 风格）
- 项目备注了 WSDL/WebService 支持（Axis）、阿里云消息队列（ONS）、条形码生成（barcode4j）等扩展能力

---

> 分析完成时间：2026-05-08
> 基于 `wxl` 仓库主分支代码自动生成
