# jhwop — 用户管理系统

## 项目简介

**jhwop**（`com.jhw:jhwop:0.0.1-SNAPSHOT`）是一个基于 **Spring MVC 3.2.4 + Hibernate 4.2.5 + MySQL** 的经典 SSH 架构 Web 用户管理系统，由 **@wxlHonest** 开发。项目以 WAR 包形式部署，提供用户注册、登录、会话管理等基础功能。前端采用 **Ace Admin**（Bootstrap 3）管理模板，通过 jQuery AJAX 与后端 JSON 接口交互。

项目使用 Maven 构建，Java 1.7 编译目标，IDE 为 Eclipse（保留 `.settings/` 目录）。数据源使用 Alibaba Druid 连接池，包扫描路径为 `wxl.lt`。

---

## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| **核心框架** | Spring MVC | 3.2.4.RELEASE |
| | Spring Core / Beans / Context / JDBC / ORM / AOP / Web / Expression | 3.2.4.RELEASE |
| | Spring Context Support / Test | 3.2.4.RELEASE |
| **ORM** | Hibernate Core | 4.2.5.Final |
| | Hibernate EntityManager / Ehcache | 4.2.5.Final |
| | Hibernate JPA 2.0 API | 1.0.1.Final |
| **数据库** | MySQL Connector Java | 5.1.29 |
| **连接池** | Alibaba Druid | 1.0.2 |
| **JSON 序列化** | Jackson Mapper ASL | 1.9.11 |
| | Gson | 2.2.2 |
| **日志** | Log4j | 1.2.17 |
| | SLF4J API / SLF4J-Log4j12 | 1.6.6 |
| **Servlet** | Servlet API | 3.0-alpha-1 (provided) |
| | JSP API | 2.1 (provided) |
| | JSTL | 1.2 |
| **文件上传** | Commons FileUpload | 1.2.2 |
| | Commons IO | 2.4 |
| | Commons Codec | 1.7 |
| **HTTP 客户端** | Apache HttpClient | 4.3 |
| **Office 文档** | Apache POI | 3.14-beta1 |
| | Apache POI OOXML | 3.14-beta1 |
| **WebService** | Apache Axis | 1.4 |
| | Javax WSDL (BIRT Runtime) | 1.5.1 |
| | Javax XML RPC | 1.1.1 |
| | Commons Discovery | 0.4 |
| **消息队列** | Aliyun ONS Client | 1.2.1 |
| **条形码** | Barcode4J | 2.0 |
| **邮件** | Javax Mail / Activation | 1.4.1 / 1.1.1 |
| **AOP** | AspectJ Weaver | 1.7.1 |
| **其他** | Apache Ant | 1.8.2 |
| | Apache XBean Spring | 4.5 |
| | JUnit | 4.11 (test) |
| **构建工具** | Maven WAR Plugin | 2.2 |
| | Maven Compiler Plugin (source/target 1.7) | 3.1 |
| | Maven Surefire Plugin (skipTests=true) | 2.18.1 |
| **Java** | JDK | 1.7 |

---

## 项目结构

```
wxl/
├── pom.xml                                  # Maven 项目配置
├── README.md                                # 项目文档
├── .settings/                               # Eclipse IDE 配置
├── original/                                # 参考代码（jeecg-boot 模块）
│
├── src/main/java/
│   └── wxl/lt/
│       ├── controller/
│       │   └── IndexController.java          # 登录/注册控制器
│       ├── service/
│       │   ├── GetUerInfService.java          # 用户服务接口（⚠️ 拼写错误）
│       │   └── impl/
│       │       └── GetUserInfServiceImpl.java # 用户服务实现
│       ├── dao/
│       │   ├── GetUserInfDao.java             # 用户 DAO 接口
│       │   └── impl/
│       │       ├── BaseDao.java               # 通用泛型 DAO 基类
│       │       └── GetUserInfDaoImpl.java     # 用户 DAO 实现（HQL 拼接）
│       ├── model/
│       │   ├── IdEntity.java                  # 实体基类（Long id, AUTO_INCREMENT）
│       │   ├── TbUser.java                    # 用户实体（继承 IdEntity）
│       │   ├── TbUserInf.java                 # 用户扩展信息实体
│       │   └── TbTest.java                    # 测试实体（⚠️ String id Bug）
│       ├── form/
│       │   └── UserForm.java                  # 表单/响应混合对象
│       ├── pageModel/
│       │   └── PageMessage.java               # AJAX 响应封装
│       ├── framework/
│       │   ├── constant/
│       │   │   └── GlobalConstant.java         # 全局常量定义
│       │   └── interceptors/
│       │       └── SecurityInterceptor.java    # 权限拦截器
│       └── utils/
│           ├── MD5Util.java                   # MD5 加密工具
│           ├── IpUtils.java                   # 客户端 IP 获取工具
│           └── StringUtil.java                # 通用字符串工具类
│
├── src/main/resources/
│   ├── config.properties                      # 数据库连接等配置
│   ├── log4j.properties                       # 日志配置
│   ├── spring.xml                             # Spring 核心配置
│   ├── spring-hibernate.xml                   # 数据源 + Hibernate + 事务
│   └── spring-mvc.xml                         # MVC 配置 + 拦截器 + 视图解析
│
├── src/main/java/META-INF/
│   └── persistence.xml                        # JPA 持久化单元配置
│
└── src/main/webapp/
    ├── index.jsp                              # 首页
    ├── assets/                                # Ace Admin 模板静态资源
    │   ├── css/  js/  font/  images/
    └── WEB-INF/
        ├── web.xml                            # 部署描述符
        └── view/
            ├── login.jsp                      # 登录页面
            └── index.jsp                      # 系统主页
```

---

## 核心功能

### 1. 用户登录

- **接口**: `POST /index/userLogin`
- 支持**用户名 / 邮箱 / 手机号**三种方式登录
- 密码使用 MD5 加密后与数据库比对
- 登录成功后将用户信息存入 `session.setAttribute("systemUser", user)`
- 更新最后登录时间（`lastLoginTime`）
- 返回 `PageMessage` JSON 对象，包含 `success`、`errorMsg` 等字段

### 2. 用户注册

- **接口**: `POST /index/userRegister`
- 注册前校验用户名、邮箱、手机号三者唯一性
- 自动记录注册时间（`registerTime`）和客户端 IP（`userIp`）
- 密码使用 MD5 加密后存储
- 返回 `PageMessage` JSON 对象，包含成功/失败信息

### 3. 登录页面

- **接口**: `GET /index/login`
- 返回登录 JSP 视图

### 4. 会话保护

- `SecurityInterceptor` 拦截所有请求（除 `/index/login`、`/index/userLogin` 白名单路径）
- 检查 `session.getAttribute("systemUser")` 是否存在
- 未登录用户自动 `forward` 到首页

### 5. 通用 CRUD 基类

`BaseDao<T>` 提供泛型数据访问方法：

| 方法 | 说明 |
|------|------|
| `save(T o)` | 保存实体 |
| `get(Class<T> c, Serializable id)` | 按主键查询 |
| `get(String hql)` | HQL 查询单条 |
| `get(String hql, Map params)` | 参数化 HQL 查询单条 |
| `delete(T o)` | 删除实体 |
| `update(T o)` | 更新实体 |
| `saveOrUpdate(T o)` | 保存或更新 |
| `find(String hql)` | HQL 查询列表 |
| `find(String hql, int page, int rows)` | 分页 HQL 查询 |
| `findBySql(String sql)` | 原生 SQL 查询 |
| `executeHql(String hql)` | 执行 HQL 更新 |
| `executeSql(String sql)` | 执行 SQL 更新 |
| `count(String hql)` | HQL 计数 |

---

## 数据库设计

### tb_user — 用户主表

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 主键（继承 IdEntity） |
| user_name | VARCHAR(40) | — | 用户名 |
| password | VARCHAR(50) | — | 密码（MD5 密文） |
| user_email | VARCHAR(255) | — | 邮箱 |
| user_mobile_no | VARCHAR(11) | — | 手机号 |
| user_ip | VARCHAR(255) | — | 注册 IP |
| register_time | DATETIME | — | 注册时间 |
| last_login_time | DATETIME | — | 最后登录时间 |
| del_flag | VARCHAR(1) | — | 删除标记 |

### tb_user_inf — 用户扩展信息表

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 主键（继承 IdEntity） |
| user_age | INT | — | 年龄 |
| user_city | VARCHAR(255) | — | 城市 |
| user_province | VARCHAR(255) | — | 省份 |

### tb_test — 测试表

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | VARCHAR(255) | PK（⚠️ 见 Bug 分析） | 主键 |
| user_name | VARCHAR(255) | — | 用户名 |
| user_age | VARCHAR(255) | — | 年龄 |

> 配置 `hibernate.hbm2ddl.auto=update`，Hibernate 在启动时自动根据实体类更新表结构。

---

## 架构分析

```
┌───────────────────────────────────────────────────┐
│              浏览器 (JSP + Ace Admin)              │
│          jQuery AJAX → JSON 请求/响应              │
├───────────────────────────────────────────────────┤
│            web.xml                                │
│  CharacterEncodingFilter (UTF-8, forceEncoding)   │
│  OpenSessionInViewFilter (声明但未生效⚠️)          │
├───────────────────────────────────────────────────┤
│         DispatcherServlet (springMVC)             │
│         spring-mvc.xml 配置加载                    │
│  ┌─────────────────────────────────────────────┐  │
│  │      SecurityInterceptor (权限拦截器)        │  │
│  │  白名单: /index/login, /index/userLogin      │  │
│  ├─────────────────────────────────────────────┤  │
│  │    Controller 层 (IndexController)          │  │
│  │  @RequestMapping → @ResponseBody JSON       │  │
│  ├─────────────────────────────────────────────┤  │
│  │  Service 层 (GetUserInfServiceImpl)          │  │
│  │     业务逻辑层 + AOP 事务边界                │  │
│  │  tx:advice: save*/update*/delete*/get*      │  │
│  ├─────────────────────────────────────────────┤  │
│  │     DAO 层 (GetUserInfDaoImpl → BaseDao)     │  │
│  │       HQL 拼接 → Hibernate CRUD              │  │
│  └─────────────────────────────────────────────┘  │
├───────────────────────────────────────────────────┤
│          Hibernate 4 (LocalSessionFactoryBean)    │
│          Alibaba Druid 连接池                      │
├───────────────────────────────────────────────────┤
│              MySQL 5.x (localhost:3306/lt)         │
└───────────────────────────────────────────────────┘
```

### 登录请求完整流程

```
浏览器 POST /index/userLogin
  → CharacterEncodingFilter (设置 UTF-8)
  → SecurityInterceptor.preHandle()
      → 检查 URL 是否在 excludeUrls 白名单中
      → 匹配 /index/userLogin → 放行
  → DispatcherServlet 分发到 IndexController.userLogin()
      → @Validated UserForm（实际无校验注解，无效）
      → MD5Util.md5(form.getPassword())  // 密码 MD5 加密
      → GetUserInfServiceImpl.getUserLoginInf(form)
          → StringUtil.isNotEmpty() 校验参数非空
          → GetUserInfDaoImpl.getUserLoginInf(form)
              → 字符串拼接构造 HQL:
                "from TbUser u where u.userName='xxx' or u.userEmail='xxx' or u.userMobileNo='xxx'"
              → BaseDao.get(hql) 执行查询
          → 比对 userLogin.getPassword() 与 form.getPassword()
              → 若匹配: 更新 lastLoginTime, 返回 LOGIN_OK
              → 不匹配: 返回 PASSWORD_ERROR
          → 若 userLogin 为 null: 返回 LOGINNAME_ERROR
      → 根据返回标志设置 PageMessage
      → session.setAttribute("systemUser", user)
  → MappingJacksonHttpMessageConverter 序列化为 JSON
  → 浏览器接收 PageMessage JSON 响应
```

---

## 安全分析

### 现有安全措施

| 措施 | 实现方式 | 评估 |
|------|----------|------|
| 密码加密 | MD5 单次哈希，无盐 | ❌ 极弱 |
| 访问控制 | SecurityInterceptor 检查 session 是否存在 "systemUser" | ⚠️ 二元权限模型 |
| 字符编码 | CharacterEncodingFilter 强制 UTF-8 | ✅ 有效 |
| 会话管理 | HttpSession 存储用户信息 | ⚠️ 无 Session 固定防护 |
| 参数校验 | @Validated 注解 | ❌ 无效（字段无 JSR-303 约束） |

### 安全缺陷总览

| 风险项 | 严重程度 | 说明 |
|--------|----------|------|
| HQL 注入 | 🔴 严重 | 所有查询使用字符串拼接，未使用参数化查询 |
| 密码安全 | 🔴 严重 | MD5 单次无盐，彩虹表可破；16 位模式进一步降低熵值 |
| 明文数据库密码 | 🔴 严重 | `config.properties` 中 `jdbc_password=634111` 明文暴露 |
| 无 CSRF 防护 | 🟠 高危 | 无 CsrfFilter、无同步令牌机制 |
| 无 RBAC | 🟠 高危 | 所有登录用户权限完全相同 |
| 空指针 Bug | 🟠 高危 | Service 层判断逻辑错误，可能 NPE |
| 线程安全问题 | 🟠 高危 | Service 单例持有可变状态字段 |
| 无暴力破解防护 | 🟡 中危 | 登录接口无频率限制、无验证码、无账户锁定 |
| 会话固定 | 🟡 中危 | 登录后不轮换 Session ID |
| OpenSessionInView | 🟡 低危 | web.xml 声明了 filter 但无 filter-mapping，配置无效 |

---

## 已知问题详情

### 🔴 1. HQL 注入漏洞（严重）

**文件**: `src/main/java/wxl/lt/dao/impl/GetUserInfDaoImpl.java`

所有查询均使用 Java 字符串拼接构造 HQL，未使用参数化查询：

```java
// 第 29-30 行 — 登录查询（可注入）
String hql = "from TbUser u where u.userName='"+form.getUserName()
    +"' or u.userEmail='"+form.getUserName()
    +"' or u.userMobileNo='"+form.getUserName()+"'";

// 第 53 行 — 用户查重（可注入）
String hql = "from TbUser u where u.userName='"+form.getUserName()
    +"' or u.userEmail='"+form.getUserEmail()
    +"' or u.userMobileNo='"+form.getUserMobileNo()+"'";
```

**攻击示例**：输入用户名 `' or '1'='1` 即可绕过登录验证，返回第一条用户记录。

**修复方案**：`BaseDao` 中已有参数化方法 `get(String hql, Map<String, Object> params)`，应替换为：

```java
String hql = "from TbUser u where u.userName=:uname or u.userEmail=:uname or u.userMobileNo=:uname";
Map<String, Object> params = new HashMap<>();
params.put("uname", form.getUserName());
return this.get(hql, params);
```

---

### 🔴 2. 空指针异常 Bug

**文件**: `src/main/java/wxl/lt/service/impl/GetUserInfServiceImpl.java` 第 36-51 行

```java
private TbUser user = new TbUser();  // 类字段，永远非 null

@Override
public Integer getUserLoginInf(UserForm form) {
    if (StringUtil.isNotEmpty(form.getUserName()) && StringUtil.isNotEmpty(form.getPassword())) {
        TbUser userLogin = getUserInfDao.getUserLoginInf(form);
        if (user != null) {                           // ← BUG: 应该是 userLogin != null
            if (userLogin.getPassword().equals(form.getPassword())) {  // ← userLogin 可能为 null → NPE
                user = userLogin;
                ...
```

**问题分析**：
- 判断条件 `user != null` 永远为 true（类字段初始化了 `new TbUser()`）
- 当数据库查不到用户时，`userLogin` 为 `null`，执行 `userLogin.getPassword()` 触发 **NullPointerException**
- 这是一个典型的变量名混淆 Bug（`user` 字段 vs `userLogin` 局部变量）

**修复方案**：将 `if (user != null)` 改为 `if (userLogin != null)`

---

### 🔴 3. MD5 密码算法过弱

**文件**: `src/main/java/wxl/lt/utils/MD5Util.java`

| 问题 | 说明 |
|------|------|
| **算法** | MD5 已被证明存在碰撞攻击，不应用于密码存储 |
| **无盐** | 相同密码产生相同密文，彩虹表可批量破解 |
| **无迭代** | 单次哈希，GPU 暴力破解速度极快 |
| **16 位模式** | `md5For16()` 取 32 位中间 16 位，熵值减半，碰撞概率大幅上升 |
| **重复实现** | `StringUtil` 中存在另一个 `md5()` 方法，代码重复 |

**示例**：`admin` → `21232f297a57a5a743894a0e4a801fc3`，可在任何 MD5 彩虹表中秒查。

**修复方案**：使用 bcrypt / Argon2 / PBKDF2 替代。如 `password` 字段 `VARCHAR(50)` 不够存储 bcrypt（约 60 字符），需要同步扩展字段。

---

### 🟠 4. 明文数据库密码

**文件**: `src/main/resources/config.properties` 第 7-8 行

```properties
jdbc_username=root
jdbc_password=634111
```

数据库用户名和密码以明文形式存储在配置文件中，且该文件已提交到版本控制系统（Git）。任何人获取代码即可看到数据库凭据。

**修复方案**：
- 将 `config.properties` 加入 `.gitignore`
- 使用环境变量或 JNDI 注入敏感配置
- 生产环境使用加密配置（如 Jasypt）

---

### 🟠 5. 无 RBAC 权限模型

**文件**: `src/main/java/wxl/lt/framework/interceptors/SecurityInterceptor.java`

当前权限拦截器仅检查 session 中是否存在 `systemUser` 属性，无法区分用户角色。任何登录成功的用户都拥有完全相同的访问权限。系统不支持角色（管理员/普通用户）、权限（读/写/删）等细粒度控制。

---

### 🟠 6. 无 CSRF 防护

项目中不存在任何跨站请求伪造防护：
- 无 Spring Security 的 `CsrfFilter`
- 无同步令牌（Synchronizer Token）模式
- 无 `SameSite` Cookie 属性设置
- 所有 POST 接口（登录、注册）无 `Origin` / `Referer` 校验

攻击者可构造恶意页面，诱导已登录用户发起非本人意愿的请求。

---

### 🟠 7. 线程安全问题

**文件**: `src/main/java/wxl/lt/service/impl/GetUserInfServiceImpl.java` 第 27 行

```java
@Service
public class GetUserInfServiceImpl implements GetUerInfService {
    @Autowired
    private GetUserInfDao getUserInfDao;

    private TbUser user = new TbUser();  // ← 共享可变状态！
```

Spring Service 默认是**单例**（singleton），`user` 字段被所有请求线程共享。

- 线程 A 登录成功，`user = userA`
- 线程 B 登录成功，`user = userB`（覆盖线程 A 的值）
- 线程 A 调用 `selectUserLogin()` 返回的却是线程 B 的用户

这是一个典型的并发竞态条件（race condition）。

---

### 🟠 8. TbTest 实体 String ID 类型 Bug

**文件**: `src/main/java/wxl/lt/model/TbTest.java`

```java
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(unique=true, nullable=false)
public String getId() {      // ← String 类型的主键
    return this.id;
}
```

问题：
1. 主键声明为 `String` 类型但使用 `GenerationType.IDENTITY`（数据库自增）
2. MySQL 的 `AUTO_INCREMENT` 生成的是数值，而 Hibernate 期望 String 类型的 UUID 或手动赋值
3. 参照 `IdEntity` 基类使用 `Long` 类型主键，`TbTest` 应继承 `IdEntity` 保持一致

---

### 🟡 9. 接口名拼写错误

**文件**: `src/main/java/wxl/lt/service/GetUerInfService.java`

接口名 `GetUerInfService` 中 **"Uer"** 应为 **"User"**，这是一个拼写错误。该拼写错误同时传播到了：

- `IndexController.java` 第 36 行：`private GetUerInfService gtUerInfService;`（变量名缩写也丢失了 s）

正确的命名应为 `GetUserInfService`。

---

### 🟡 10. UserForm 职责混乱

**文件**: `src/main/java/wxl/lt/form/UserForm.java`

`UserForm` 同时承担两种职责：
1. **输入**：接收前端登录/注册表单数据（`userName`, `password`, `userEmail`, `userMobileNo`）
2. **输出**：包含响应字段（`errorMsg`, `success`, `list`, `flag`）

这些输出字段在表单提交时完全无用，且与 `PageMessage` 职责重叠，违反单一职责原则。

---

### 🟡 11. @Validated 无效

**文件**: `src/main/java/wxl/lt/controller/IndexController.java`

```java
@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
@ResponseBody
public PageMessage userLogin(@Validated UserForm form, HttpSession session) {
```

`UserForm` 类中没有任何 JSR-303 校验注解（`@NotNull`, `@Size`, `@Email` 等），`@Validated` 注解完全不起作用。用户名、密码等字段可以为空字符串直接传入后端。

---

### 🟢 12. 其他改进项

| # | 问题 | 说明 |
|---|------|------|
| a | Spring 3.2.4 版本过旧 | 已于 2016 年停止维护，存在已知 CVE，建议升级至 Spring 5.x/6.x |
| b | Hibernate 4.2.5 版本过旧 | 建议升级至 Hibernate 5.x/6.x |
| c | 缺少单元测试 | `maven-surefire-plugin` 配置 `skipTests=true`，测试被跳过 |
| d | password 字段长度不足 | `VARCHAR(50)` 无法存储 bcrypt（约 60 字符）密文 |
| e | 事务无 timeout 配置 | 查询可能长时间占用数据库连接 |
| f | 登录失败无日志记录 | 无法追踪暴力破解尝试 |
| g | 数据库无外键 | `tb_user` 与 `tb_user_inf` 无显式外键关联 |
| h | 无 SQL 慢查询监控 | Druid 的 stat filter 已启用但缺少慢查询阈值配置 |
| i | web.xml 静态资源映射冗余 | 14 个 `servlet-mapping` 逐个映射静态文件扩展名，应使用 Spring MVC 的 `mvc:resources` |
| j | `hibernate.hbm2ddl.auto=update` | 生产环境应设为 `validate` 或 `none`，避免自动修改表结构 |

---

## 开发备注

- **开发环境**：Eclipse IDE，JDK 1.7，Tomcat（Servlet 3.0）
- **数据库**：`jdbc:mysql://localhost:3306/lt?useUnicode=true&characterEncoding=utf-8`
- **Maven 仓库**：使用 Maven Central 和 Alibaba OpenSource 镜像仓库
- **前端模板**：Ace Admin（Bootstrap 3 风格管理模板）
- **扩展能力**：代码包含 Axis WebService、阿里云 ONS 消息队列、Barcode4J 条形码、Apache POI 文档处理等依赖配置
- **备注**：`src/main/webapp/WEB-INF/web.xml` 缺少 `openSessionInViewFilter` 的 `<filter-mapping>`，该 filter 声明但实际未生效

---

> 本文档基于 `wxl` 仓库源码静态分析生成，覆盖了项目结构、技术栈、架构流程、安全隐患及所有已知代码缺陷。
> 生成时间：2026-05-08
