# jhwop 项目代码分析报告

## 项目简介

**jhwop**（GroupId: `com.jhw`, ArtifactId: `jhwop`, 版本: `0.0.1-SNAPSHOT`）是一个基于 **Spring MVC 3.2.4 + Hibernate 4.2.5 + MySQL** 经典 SSH 架构的 Java Web 用户管理系统，打包方式为 WAR。项目由 **@wxlHonest**（袁友林）开发，提供用户注册、登录、会话管理等功能。前端使用 **Ace Admin**（Bootstrap 3）管理模板，通过 jQuery AJAX 与后端 JSON 接口交互。

项目使用 Maven 构建，Java 1.7 编译目标，开发 IDE 为 Eclipse（保留 `.settings/` 目录）。数据源采用 Alibaba Druid 连接池，并集成了阿里云消息队列 ONS、Apache POI 报表、Apache Axis WebService、Barcode4J 条形码等扩展组件。包扫描路径为 `wxl.lt`。

同时仓库中包含配套前端工程 `ant-design-vue-jeecg/`（基于 Ant Design Vue 的 JEECG 低代码平台前端）和 JEECG-Boot 原始后端代码备份 `original/`（未参与构建）。

---

## 技术栈

| 类别 | 技术 | 版本号 | 说明 |
|------|------|--------|------|
| **核心框架** | Spring MVC | 3.2.4.RELEASE | Web 层控制器与 REST 接口 |
| | Spring Core | 3.2.4.RELEASE | IoC 容器核心 |
| | Spring Beans | 3.2.4.RELEASE | Bean 工厂与管理 |
| | Spring Context | 3.2.4.RELEASE | 应用上下文 |
| | Spring Context Support | 3.2.4.RELEASE | 扩展支持（Quartz、邮件等） |
| | Spring Expression | 3.2.4.RELEASE | SpEL 表达式语言 |
| | Spring Web | 3.2.4.RELEASE | Web 支持组件 |
| | Spring JDBC | 3.2.4.RELEASE | JDBC 抽象层 |
| | Spring ORM | 3.2.4.RELEASE | ORM 集成支持 |
| | Spring Test | 3.2.4.RELEASE | 单元测试支持 |
| **AOP** | AspectJ Weaver | 1.7.1 | Spring AOP 织入 |
| **ORM** | Hibernate Core | 4.2.5.Final | ORM 核心引擎 |
| | Hibernate EntityManager | 4.2.5.Final | JPA EntityManager 实现 |
| | Hibernate Ehcache | 4.2.5.Final | 二级缓存支持 |
| | Hibernate JPA 2.0 API | 1.0.1.Final | JPA 注解 API |
| **数据库** | MySQL Connector/J | 5.1.29 | JDBC 驱动 |
| **连接池** | Alibaba Druid | 1.0.2 | 数据库连接池与 SQL 监控 |
| **JSON 序列化** | Jackson (Codehaus) | 1.9.11 | Spring MVC JSON 消息转换 |
| | Google Gson | 2.2.2 | JSON 解析处理 |
| **日志** | Log4j | 1.2.17 | 日志实现（⚠️ 已停止维护） |
| | SLF4J API | 1.6.6 | 日志门面 |
| | SLF4J-Log4j12 | 1.6.6 | SLF4J → Log4j 桥接 |
| **Servlet** | javax.servlet | 3.0-alpha-1 | Servlet API（scope: provided） |
| | JSP API | 2.1 | JSP 支持（scope: provided） |
| | JSTL | 1.2 | JSP 标准标签库 |
| **文件操作** | commons-fileupload | 1.2.2 | 文件上传组件 |
| | commons-io | 2.4 | IO 工具库 |
| | commons-codec | 1.7 | 编解码工具 |
| **HTTP 客户端** | Apache HttpClient | 4.3 | HTTP 请求发送 |
| **Office 文档** | Apache POI | 3.14-beta1 | Excel 读写 |
| | Apache POI OOXML | 3.14-beta1 | Excel 2007+ 格式支持 |
| **WebService** | Apache Axis | 1.4 | SOAP/WSDL 客户端 |
| | Javax WSDL | 1.5.1 | WSDL 解析（BIRT Runtime） |
| | Javax XML RPC | 1.1.1 | XML-RPC API |
| | Commons Discovery | 0.4 | 服务发现 |
| **消息队列** | Aliyun ONS Client | 1.2.1 | 阿里云消息队列 |
| **条形码** | Barcode4J | 2.0 | 条形码生成 |
| **邮件** | Javax Mail | 1.4.1 | JavaMail API |
| | Javax Activation | 1.1.1 | JavaBeans 激活框架 |
| **构建** | Apache Ant | 1.8.2 | 项目构建辅助 |
| | Apache XBean Spring | 4.5 | Spring XML Schema 扩展 |
| **测试** | JUnit | 4.11 | 单元测试框架（scope: test） |
| **前端模板** | Bootstrap 3 (ACE Admin) | — | 管理界面模板 |
| | jQuery | 2.0.3/1.10.2 | JS 库 |
| **构建插件** | Maven WAR Plugin | 2.2 | WAR 打包 |
| | Maven Compiler Plugin | 3.1 | Java 编译（source/target: 1.7） |
| | Maven Surefire Plugin | 2.18.1 | 测试执行（skipTests=true） |
| **JDK** | Java | 1.7 | 编译与运行环境 |

---

## 完整目录树

```
wxl/
├── pom.xml                                          # Maven 项目配置文件
├── README.md                                        # 项目文档（本文件）
├── .gitignore                                       # Git 忽略配置
├── .settings/                                       # Eclipse IDE 项目配置
│   ├── org.eclipse.wst.common.project.facet.core.prefs.xml
│   └── org.eclipse.wst.common.project.facet.core.xml
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── META-INF/
│   │   │   │   └── persistence.xml                  # JPA 持久化单元配置
│   │   │   └── wxl/lt/                              # 核心业务包 (wxl.lt)
│   │   │       ├── controller/
│   │   │       │   └── IndexController.java         # 用户登录/注册控制器
│   │   │       ├── service/
│   │   │       │   ├── GetUerInfService.java        # 用户服务接口（⚠️ 拼写错误: Uer→User）
│   │   │       │   └── impl/
│   │   │       │       └── GetUserInfServiceImpl.java # 用户服务实现
│   │   │       ├── dao/
│   │   │       │   ├── GetUserInfDao.java           # 用户数据访问接口
│   │   │       │   └── impl/
│   │   │       │       ├── BaseDao.java             # 泛型 Hibernate DAO 基类
│   │   │       │       └── GetUserInfDaoImpl.java   # 用户 DAO 实现
│   │   │       ├── model/                           # JPA 实体类（数据表映射）
│   │   │       │   ├── IdEntity.java                # 实体基类（统一 ID 策略）
│   │   │       │   ├── TbUser.java                  # 用户表实体 (tb_user)
│   │   │       │   ├── TbUserInf.java               # 用户信息表实体 (tb_user_inf)
│   │   │       │   └── TbTest.java                  # 测试表实体 (tb_test)
│   │   │       ├── form/
│   │   │       │   └── UserForm.java                # 用户表单对象（输入/输出混合）
│   │   │       ├── pageModel/
│   │   │       │   └── PageMessage.java             # AJAX JSON 响应封装
│   │   │       ├── framework/
│   │   │       │   ├── constant/
│   │   │       │   │   └── GlobalConstant.java      # 全局常量定义
│   │   │       │   └── interceptors/
│   │   │       │       └── SecurityInterceptor.java # 登录权限拦截器
│   │   │       └── utils/
│   │   │           ├── IpUtils.java                 # 客户端 IP 获取工具
│   │   │           ├── MD5Util.java                 # MD5 加密工具
│   │   │           └── StringUtil.java              # 通用字符串工具类
│   │   │
│   │   ├── resources/
│   │   │   ├── config.properties                    # 主配置文件（数据库/上传参数）
│   │   │   ├── log4j.properties                     # Log4j 日志配置
│   │   │   ├── spring.xml                           # Spring 根配置
│   │   │   ├── spring-hibernate.xml                 # Spring + Hibernate 整合配置
│   │   │   └── spring-mvc.xml                       # Spring MVC 配置
│   │   │
│   │   └── webapp/
│   │       ├── index.jsp                            # 入口 JSP（转发到登录页）
│   │       ├── assets/                              # Ace Admin 静态资源
│   │       │   ├── avatars/                         # 头像图片 (8 files)
│   │       │   ├── css/                             # 样式文件 (18 files + images/)
│   │       │   ├── font/                            # 字体文件
│   │       │   ├── images/                          # 图片资源
│   │       │   │   └── gallery/                     # 相册图片 (12 files)
│   │       │   └── js/                              # JavaScript 脚本
│   │       │       ├── jquery-2.0.3.min.js          # jQuery 2.0.3
│   │       │       ├── jquery-1.10.2.min.js         # jQuery 1.10.2
│   │       │       ├── bootstrap.min.js             # Bootstrap 3 JS
│   │       │       ├── ace-elements.min.js          # ACE UI 组件
│   │       │       ├── ace.min.js                   # ACE 主题核心
│   │       │       ├── ace-extra.min.js             # ACE 扩展
│   │       │       ├── jquery.validate.min.js       # jQuery 表单验证
│   │       │       ├── jquery-ui-1.10.3.*.min.js    # jQuery UI
│   │       │       ├── jquery.dataTables.min.js     # DataTables 表格组件
│   │       │       ├── fullcalendar.min.js          # FullCalendar 日历
│   │       │       ├── flot/                        # Flot 图表
│   │       │       ├── fuelux/                      # Fuel UX 组件
│   │       │       ├── jqGrid/                      # jqGrid 表格
│   │       │       ├── markdown/                    # Markdown 编辑器
│   │       │       ├── x-editable/                  # 行内编辑
│   │       │       ├── date-time/                   # 日期时间组件
│   │       │       ├── dropzone.min.js              # 拖拽上传
│   │       │       ├── typeahead-bs2.min.js         # 自动补全
│   │       │       ├── chosen.jquery.min.js         # 下拉增强
│   │       │       ├── select2.min.js               # Select2 下拉
│   │       │       ├── bootbox.min.js               # 对话框
│   │       │       ├── html5shiv.js                 # IE HTML5 兼容
│   │       │       ├── respond.min.js               # IE 响应式兼容
│   │       │       ├── excanvas.min.js              # IE Canvas 兼容
│   │       │       └── lt/
│   │       │           ├── common.js                # 项目通用 JS
│   │       │           └── login.js                 # 登录页 AJAX 逻辑
│   │       └── WEB-INF/
│   │           ├── web.xml                          # Web 部署描述符
│   │           └── view/
│   │               ├── include.jsp                  # JSP 公共片段
│   │               ├── index.jsp                    # 首页视图
│   │               └── login.jsp                    # 登录/注册视图
│   │
│   └── test/
│       └── java/                                    # 单元测试目录（空）
│
├── ant-design-vue-jeecg/                            # 前端工程（Ant Design Vue 版 JEECG）
│   ├── package.json                                 # NPM 依赖配置
│   ├── babel.config.js                              # Babel 配置
│   ├── Dockerfile                                   # Docker 构建文件
│   ├── .dockerignore
│   ├── .editorconfig
│   ├── .eslintignore
│   ├── .gitattributes
│   ├── .gitignore
│   ├── .prettierrc                                  # Prettier 格式化配置
│   ├── idea.config.js                               # IDEA IDE 配置
│   ├── LICENSE                                      # 开源协议
│   ├── package-lock.json
│   ├── README.md
│   ├── readme1.md
│   ├── readme2.md
│   ├── public/
│   │   ├── index.html                               # SPA 入口 HTML
│   │   ├── logo.png
│   │   ├── avatar2.jpg / goright.png
│   │   ├── color.less
│   │   ├── v2.js
│   │   └── tinymce/                                 # 富文本编辑器
│   └── src/
│       ├── main.js                                  # Vue 应用入口
│       ├── App.vue                                  # 根组件
│       ├── api/                                     # API 请求封装
│       │   ├── api.js / index.js / login.js
│       │   ├── manage.js / GroupRequest.js
│       ├── assets/                                  # 前端静态资源
│       │   ├── background.svg / checkcode.png
│       ├── components/                              # Vue 组件库
│       │   ├── chart/ / dict/ / layouts/ / tinymce/
│       │   ├── tools/ / _util/ / jeecg/
│       ├── config/                                  # 应用配置
│       ├── router/                                  # Vue Router 路由
│       ├── store/                                   # Vuex 状态管理
│       ├── utils/                                   # 前端工具函数
│       └── views/                                   # 页面组件
│
└── original/                                        # JEECG-Boot 原始后端代码（参考/备份）
    ├── pom.xml                                      # Spring Boot 父 POM
    ├── jeecg-boot-base-common/                      # 公共模块
    │   └── pom.xml
    └── jeecg-boot-module-system/                    # 系统模块（含 Mapper、资源配置等）
        └── pom.xml
```

---

## 核心功能说明

### 1. 用户登录（`POST /index/userLogin`）

**流程**：

1. 用户在 `login.jsp` 页面输入用户名（支持用户名/邮箱/手机号三合一登录）和密码
2. 前端 `login.js` 通过 jQuery AJAX 发送 POST 请求
3. `SecurityInterceptor.preHandle()` 检测到 URL 命中白名单 `/index/userLogin`，直接放行
4. `IndexController.userLogin()` 接收 `@Validated UserForm`（实际校验无效，UserForm 无任何 JSR-303 约束注解）
5. 调用 `MD5Util.md5(form.getPassword())` 对密码明文做 MD5 加密
6. 调用 `GetUserInfServiceImpl.getUserLoginInf(form)`：
   - 校验 `userName` 和 `password` 非空
   - 调用 `GetUserInfDaoImpl.getUserLoginInf(form)` 通过 HQL 拼接查询用户
   - 比对 `userLogin.getPassword()` 与 `form.getPassword()` (已 MD5)
   - 密码匹配：更新 `lastLoginTime`，返回 `LOGIN_OK(1)`
   - 密码不匹配：返回 `PASSWORD_ERROR(0)`
   - 用户不存在：返回 `LOGINNAME_ERROR(-1)`
7. 登录成功后，Controller 将用户对象存入 `session.setAttribute("systemUser", user)`
8. 返回 `PageMessage` JSON（success/errorMsg）给前端

**支持的登录方式**：用户名 (`userName`)、邮箱 (`userEmail`)、手机号 (`userMobileNo`) 均可作为登录凭证，通过 HQL OR 条件查询实现。

### 2. 用户注册（`POST /index/userRegister`）

**流程**：

1. 用户在 `login.jsp` 注册面板填写邮箱、手机号、用户名、密码、确认密码
2. 前端 `login.js` 做基础校验（用户名 6-10 位、密码 6-12 位、两次密码一致）
3. 请求到达 `IndexController.userRegister()`：
   - `checkUser(form)` 调用 `getUserInfDao.checkUserUser(form)` 检查唯一性
   - 按优先级检查：用户名 → 邮箱 → 手机号（任一重复则返回错误信息）
4. 通过检查后，`BeanUtils.copyProperties(form, user)` 复制属性到实体
5. 自动填充：`registerTime`（当前时间）、`userIp`（客户端 IP）、`password`（MD5 加密）
6. 调用 `getUserInfDao.userRegister(user)` → `BaseDao.save(user)` 保存到数据库
7. 返回 `PageMessage` JSON（success/successMsg 或 errorMsg）

### 3. 登录页面（`GET /index/login`）

返回 `login.jsp` 视图。JSP 使用 ACE Admin 模板，包含三个面板：
- **登录面板**（`#login-box`）：用户名 + 密码 + 记住我 + Login 按钮
- **忘记密码面板**（`#forgot-box`）：邮箱输入 + 发送按钮（仅有 UI，后端无实现）
- **注册面板**（`#signup-box`）：邮箱 + 手机 + 用户名 + 密码 + 确认密码 + 注册按钮

### 4. 权限拦截器（SecurityInterceptor）

实现 `org.springframework.web.servlet.HandlerInterceptor` 接口：

- **拦截范围**：`/**` 所有请求（`spring-mvc.xml` 中配置）
- **白名单 URL**：
  - `/index/login` — 登录页面
  - `/index/userLogin` — 登录接口（⚠️ 注册接口 `/index/userRegister` 未加入白名单但依赖 URL 前缀匹配实际可访问）
- **拦截逻辑**：
  - 获取 `request.getSession().getAttribute("systemUser")`
  - 若 URL 在白名单或包含 `/index/login` → 放行
  - 若 session 中无 `systemUser` → `forward` 到根路径 `/`
  - 否则放行
- **缺陷**：
  - 仅做二元判断（是否登录），无角色/权限区分
  - 注册接口 `/index/userRegister` 未在 excludeUrls 中显式声明

### 5. 通用数据访问层（BaseDao）

泛型基类 `BaseDao<T>` 提供了完整的 Hibernate CRUD 操作封装：

| 方法类别 | 方法列表 | 说明 |
|----------|----------|------|
| **增** | `save(T o)`, `saveOrUpdate(T o)` | 序列化返回主键 / 无返回值 |
| **删** | `delete(T o)` | 删除实体 |
| **改** | `update(T o)` | 更新实体 |
| **查（单条）** | `get(Class<T> c, Serializable id)` | 按主键查询 |
| | `get(String hql)` | HQL 查询（无参数） |
| | `get(String hql, Map params)` | HQL 查询（参数绑定） |
| **查（列表）** | `find(String hql)` | HQL 列表查询 |
| | `find(String hql, Map params)` | 带参数 HQL 列表查询 |
| | `find(String hql, int page, int rows)` | HQL 分页查询 |
| | `find(String hql, Map params, int page, int rows)` | 带参数 HQL 分页查询 |
| | `findBySql(String sql)` | 原生 SQL 查询 |
| | `findBySql(String sql, Map params)` | 带参数原生 SQL 查询 |
| | `findBySql(String sql, int page, int rows)` | 原生 SQL 分页 |
| | `findBySql(String sql, Map params, int page, int rows)` | 带参数原生 SQL 分页 |
| **统计** | `count(String hql)` | HQL 计数 |
| | `count(String hql, Map params)` | 带参数 HQL 计数 |
| | `countBySql(String sql)` | 原生 SQL 计数（返回 BigInteger） |
| | `countBySql(String sql, Map params)` | 带参数原生 SQL 计数 |
| **更新** | `executeHql(String hql)` | 执行 HQL DML |
| | `executeHql(String hql, Map params)` | 带参数 HQL DML |
| | `executeSql(String sql)` | 执行原生 SQL DML |
| | `executeSql(String sql, Map params)` | 带参数原生 SQL DML |

**关键设计**：
- 通过 `@Autowired` 注入 `SessionFactory`，使用 `getCurrentSession()` 获取当前事务 Session
- `get(String hql, Map<String, Object> params)` 等方法已提供安全的参数绑定机制
- 支持 HQL 与原生 SQL 两种查询模式
- 分页方法使用 `setFirstResult` + `setMaxResults` 实现

---

## 数据库表结构设计

> 以下表结构通过分析 JPA 实体类 JPA 注解逆向推导得出。所有实体均使用 `@Entity` + `@Table` 注解映射。
> 主键生成策略为 `@GeneratedValue(strategy = GenerationType.IDENTITY)`（数据库自增）。
> ORM 配置 `hibernate.hbm2ddl.auto=update` 允许 Hibernate 在应用启动时自动同步表结构。

### tb_user — 用户主表

| 字段名 | Java 类型 | JDBC 类型 | 长度/精度 | 是否为空 | 约束 | 说明 |
|--------|-----------|-----------|-----------|----------|------|------|
| id | Long | BIGINT | — | NOT NULL | PK, AUTO_INCREMENT | 主键（继承自 IdEntity） |
| user_name | String | VARCHAR | 40 | — | — | 用户名 |
| password | String | VARCHAR | 50 | — | — | 密码（MD5 密文存储） |
| user_email | String | VARCHAR | 255 | — | — | 用户邮箱 |
| user_mobile_no | String | VARCHAR | 11 | — | — | 手机号码 |
| user_ip | String | VARCHAR | 255 | — | — | 注册时客户端 IP 地址 |
| register_time | java.util.Date | DATETIME | — | — | — | 注册时间 |
| last_login_time | java.util.Date | DATETIME | — | — | — | 最后登录时间 |
| del_flag | String | VARCHAR | 1 | — | — | 删除标记（软删除） |

- 实体类继承 `IdEntity`，获取统一的 `Long id` 主键
- 实现 `Serializable` 接口
- 命名查询：`@NamedQuery(name="TbUser.findAll", query="SELECT t FROM TbUser t")`

### tb_user_inf — 用户扩展信息表

| 字段名 | Java 类型 | JDBC 类型 | 长度/精度 | 是否为空 | 约束 | 说明 |
|--------|-----------|-----------|-----------|----------|------|------|
| id | Long | BIGINT | — | NOT NULL | PK, AUTO_INCREMENT | 主键（继承自 IdEntity） |
| user_age | int | INT | — | — | — | 用户年龄 |
| user_city | String | VARCHAR | 255 | — | — | 所在城市 |
| user_province | String | VARCHAR | 255 | — | — | 所在省份 |

- 实体类继承 `IdEntity`
- 目前与 `tb_user` 无显式外键关联

### tb_test — 测试表

| 字段名 | Java 类型 | JDBC 类型 | 长度/精度 | 是否为空 | 约束 | 说明 |
|--------|-----------|-----------|-----------|----------|------|------|
| id | String | VARCHAR | 255 | NOT NULL | PK（⚠️） | 主键 |
| user_name | String | VARCHAR | 255 | — | — | 用户名 |
| user_age | String | VARCHAR | 255 | — | — | 年龄 |

- ⚠️ **类型冲突**：id 声明为 `String` 类型但使用 `@GeneratedValue(strategy=GenerationType.IDENTITY)`（需要数值类型），与 MySQL 自增（BIGINT）不兼容。此实体不继承 `IdEntity`，且与 `IdEntity` 的设计不一致。

---

## 架构分层分析

### 分层架构图

```
┌──────────────────────────────────────────────────────────────────┐
│                        前端展示层                                 │
│  ┌───────────────────────────┐  ┌───────────────────────────────┐ │
│  │  JSP 视图层 (ACE Admin)   │  │  Vue SPA (ant-design-vue-jeecg)│ │
│  │  - login.jsp (登录/注册)   │  │  - Ant Design Vue 组件库       │ │
│  │  - index.jsp (系统首页)    │  │  - Vuex 状态管理               │ │
│  │  - jQuery + AJAX          │  │  - Axios HTTP 请求             │ │
│  │  - Bootstrap 3 + ACE 主题  │  │  - Vue Router 路由             │ │
│  └─────────────┬─────────────┘  └───────────────┬───────────────┘ │
│                │  JSON/AJAX                      │  HTTP/REST     │
└────────────────┼────────────────────────────────┼────────────────┘
                 │                                │
                 ▼                                │
┌────────────────────────────────────────────────┼────────────────┐
│               Web 容器 (Servlet 3.0 / Tomcat)    │                │
│  ┌──────────────────────────────────────────────┴────────────────┐│
│  │  web.xml                                                      ││
│  │  ├─ CharacterEncodingFilter (UTF-8, forceEncoding=true)      ││
│  │  ├─ ContextLoaderListener (Spring 根上下文)                   ││
│  │  ├─ DispatcherServlet["springMVC"] → url-pattern: /          ││
│  │  └─ 14 个 default servlet-mapping (.css/.js/.png/...)       ││
│  └──────────────────────────────────────────────────────────────┘│
│  ┌──────────────────────────────────────────────────────────────┐│
│  │          DispatcherServlet → spring-mvc.xml 加载              ││
│  │  ┌────────────────────────────────────────────────────────┐  ││
│  │  │        SecurityInterceptor (权限拦截器)                 │  ││
│  │  │  - 拦截所有请求 /**                                     │  ││
│  │  │  - 白名单: /index/login, /index/userLogin              │  ││
│  │  │  - 验证: session.getAttribute("systemUser")            │  ││
│  │  │  - 未登录 → forward("/")                               │  ││
│  │  └────────────────────────┬───────────────────────────────┘  ││
│  │  ┌────────────────────────┴───────────────────────────────┐  ││
│  │  │          Controller 层 (IndexController)               │  ││
│  │  │  @Controller @RequestMapping("/index")                 │  ││
│  │  │  ├─ GET  /index/login        → 登录视图 login.jsp      │  ││
│  │  │  ├─ POST /index/userLogin    → 用户登录 (JSON 返回)    │  ││
│  │  │  ├─ POST /index/userRegister → 用户注册 (JSON 返回)    │  ││
│  │  │  └─ GET  /index/test         → 测试 (void 方法)        │  ││
│  │  └────────────────────────┬───────────────────────────────┘  ││
│  └───────────────────────────┼──────────────────────────────────┘│
│                              │ @Autowired 依赖注入                │
│                              ▼                                    │
│  ┌──────────────────────────────────────────────────────────────┐│
│  │       Service 业务层 (GetUserInfServiceImpl)                  ││
│  │  @Service                                                     ││
│  │  ├─ getUserLoginInf(form)  → 登录验证 + 密码比对 + 更新登录时间││
│  │  ├─ userRegister(user)     → 注册 → 调用 DAO.save()          ││
│  │  ├─ checkUserUser(form)    → 用户名/邮箱/手机号唯一性检查     ││
│  │  └─ selectUserLogin()      → 返回当前登录用户（线程不安全）   ││
│  │                                                                ││
│  │  ┌─ AOP 事务边界 (spring-hibernate.xml) ──────────────────┐  ││
│  │  │  tx:advice 环绕 execution(* wxl.lt.service..*Impl.*)  │  ││
│  │  │  - save*/update*/delete*/add* → REQUIRED               │  ││
│  │  │  - get*/find*/load*/search*   → REQUIRED, readOnly     │  ││
│  │  │  - *                           → REQUIRED               │  ││
│  │  └────────────────────────────────────────────────────────┘  ││
│  └───────────────────────────┬──────────────────────────────────┘│
│                              │ @Autowired 依赖注入                │
│                              ▼                                    │
│  ┌──────────────────────────────────────────────────────────────┐│
│  │       DAO 数据访问层                                           ││
│  │  ┌────────────────────────────────────────────────────────┐  ││
│  │  │  BaseDao<T> (泛型 Hibernate DAO 基类)                   │  ││
│  │  │  @Autowired SessionFactory                              │  ││
│  │  │  - CRUD: save/get/delete/update/saveOrUpdate            │  ││
│  │  │  - HQL: find/count/executeHql (支持分页、参数绑定)      │  ││
│  │  │  - SQL: findBySql/countBySql/executeSql (原生查询)      │  ││
│  │  └────────────────────────────────────────────────────────┘  ││
│  │  ┌────────────────────────────────────────────────────────┐  ││
│  │  │  GetUserInfDaoImpl (extends BaseDao<TbUser>)           │  ││
│  │  │  @Repository                                            │  ││
│  │  │  - getUserLoginInf(form)  → HQL 字符串拼接查询          │  ││
│  │  │  - userRegister(user)     → 调用 this.save(user)        │  ││
│  │  │  - checkUserUser(form)    → HQL 字符串拼接查询          │  ││
│  │  │  - updateUser(user)       → 调用 this.update(user)      │  ││
│  │  └────────────────────────────┬───────────────────────────┘  ││
│  └────────────────────────────────┼─────────────────────────────┘│
│                                   │ Hibernate Session             │
│                                   ▼                               │
│  ┌──────────────────────────────────────────────────────────────┐│
│  │  Hibernate 4.2.5.Final                                       ││
│  │  └─ LocalSessionFactoryBean (注解扫描 wxl.lt.model)          ││
│  │     └─ 扫描包: wxl.lt.model (TbUser, TbUserInf, TbTest)     ││
│  └───────────────────────────┬──────────────────────────────────┘│
│  ┌───────────────────────────┴──────────────────────────────────┐│
│  │  Alibaba Druid 1.0.2 连接池                                   ││
│  │  ├─ maxActive=20, minIdle=0                                  ││
│  │  ├─ maxWait=60000 (60s)                                      ││
│  │  ├─ removeAbandoned=true, timeout=1800s (30min)              ││
│  │  └─ filters=stat (SQL 监控)                                   ││
│  └───────────────────────────┬──────────────────────────────────┘│
│                              │ JDBC                               │
│                              ▼                                    │
│  ┌──────────────────────────────────────────────────────────────┐│
│  │  MySQL 5.x 数据库                                            ││
│  │  └─ jdbc:mysql://localhost:3306/lt?useUnicode=true&...       ││
│  └──────────────────────────────────────────────────────────────┘│
└──────────────────────────────────────────────────────────────────┘
```

### 请求完整流程图（用户登录）

```
┌──────────┐     POST /index/userLogin       ┌───────────────────────────────┐
│  浏览器   │ ───────────────────────────────▶ │  web.xml                      │
│ login.jsp │     userName=admin              │  CharacterEncodingFilter      │
│           │     password=123456             │  → 强制 UTF-8 编码             │
└──────────┘                                 └───────────────┬───────────────┘
                                                            │
                                               ┌────────────▼───────────────┐
                                               │  SecurityInterceptor        │
                                               │  preHandle()                │
                                               │  → URL 匹配 /index/userLogin│
                                               │  → 命中白名单 → return true  │
                                               │  → 放行                     │
                                               └───────────────┬───────────────┘
                                                               │
                                               ┌───────────────▼───────────────┐
                                               │  DispatcherServlet            │
                                               │  → 路由到 IndexController      │
                                               │    .userLogin()                │
                                               └───────────────┬───────────────┘
                                                               │
                    ┌──────────────────────────────────────────▼──────────────────────────────────────────┐
                    │  IndexController.userLogin(@Validated UserForm, HttpSession)                        │
                    │                                                                                     │
                    │  1. PageMessage pm = new PageMessage()          // 初始化响应对象                    │
                    │  2. form.setPassword(MD5Util.md5("123456"))     // 密码 MD5 → "e10adc3949ba59a..." │
                    │  3. flag = gtUerInfService.getUserLoginInf(form) // 调用 Service 层                 │
                    │     ├─ StringUtil.isNotEmpty(admin) && isNotEmpty(md5...) → true                    │
                    │     ├─ TbUser userLogin = dao.getUserLoginInf(form)                                │
                    │     │   └─ HQL: "from TbUser u where u.userName='admin' or                         │
                    │     │           u.userEmail='admin' or u.userMobileNo='admin'"                     │
                    │     │   └─ BaseDao.get(hql) → 返回 TbUser{userName=admin, password=e10adc...}     │
                    │     ├─ if (user != null) → true (user 是类字段，永远非 null)                        │
                    │     ├─ userLogin.getPassword().equals(md5...) → true                                │
                    │     ├─ user = userLogin (更新类字段)                                                │
                    │     ├─ userLogin.setLastLoginTime(new Date())                                      │
                    │     ├─ dao.updateUser(userLogin)                                                    │
                    │     └─ return GlobalConstant.LOGIN_OK (1)                                          │
                    │  4. session.setAttribute("systemUser", user) // 写入 Session                       │
                    │  5. 返回 pm (默认 success=true) → JSON 响应                                         │
                    └──────────────────────────────────────────────────────────────────────────────────────┘
                                                               │
                                               ┌───────────────▼───────────────┐
                                               │  MappingJacksonHttpMessage     │
                                               │  Converter 序列化为 JSON       │
                                               │  {"success":true,"flag":0}     │
                                               └───────────────┬───────────────┘
                                                               │
                                               ┌───────────────▼───────────────┐
                                               │  浏览器接收 JSON               │
                                               │  login.js 处理 → 跳转首页     │
                                               └───────────────────────────────┘
```

---

## 安全机制分析

### 现有安全措施

| 安全机制 | 实现方式 | 安全评估 | 详细说明 |
|----------|----------|----------|----------|
| 密码加密存储 | MD5 无盐单次哈希 | ❌ 极弱 | 32位MD5；提供16位模式（截取中间16位），熵值减半 |
| 访问控制 | SecurityInterceptor 检查 session | ⚠️ 基础 | 仅做二元判断（是否登录），无RBAC细粒度控制 |
| 字符编码防护 | CharacterEncodingFilter 强制 UTF-8 | ✅ 有效 | 防止编码绕过攻击 |
| 会话管理 | HttpSession 存储 systemUser | ⚠️ 基础 | 无Session固定防护、无超时配置、无并发控制 |
| 参数校验 | @Validated 注解 | ❌ 无效 | UserForm 无任何 JSR-303 约束注解 |
| CSRF 防护 | 无 | ❌ 缺失 | 无同步令牌、无 SameSite Cookie、无 Origin 校验 |
| 暴力破解防护 | 无 | ❌ 缺失 | 无登录频率限制、无验证码、无账户锁定 |
| 角色权限控制 | 代码定义了 ADMIN_LOGINTYPE 常量但未使用 | ❌ 缺失 | 无 RBAC/ACL 实现 |
| HTTPS 强制 | 无安全传输保证配置 | ❌ 缺失 | 登录凭证明文传输 |
| SQL 注入防护 | 预留了参数绑定方法但实际使用字符串拼接 | ❌ 存在注入 | BaseDao 有安全方法，但 DAO 实现未使用 |
| XSS 防护 | 无输出转义 | ❌ 缺失 | JSP 直接输出用户数据 |

### 密码学安全详细分析

| 风险项 | 详细说明 |
|--------|----------|
| **算法过时** | MD5 已被证明存在碰撞攻击（2004年），NIST 已不再推荐 |
| **无盐值** | 相同密码生成相同哈希，彩虹表秒查。例如 `admin`→`21232f297a7a57a5a743894a0e4a801fc3` |
| **无迭代** | 单次哈希，GPU 暴力破解可达数十亿次/秒 |
| **16位模式** | `md5For16()` 取 32 位中间 16 位，熵值从 128bit 降至 64bit，碰撞概率大幅上升 |
| **密码字段长度** | `VARCHAR(50)` 不足以存储 BCrypt（约60字符），阻碍密码安全升级 |

---

## 已知问题与安全漏洞

### 🔴 极高优先级（CRITICAL — 必须立即修复）

#### 1. SQL 注入漏洞（HQL 拼接）

**位置**: `src/main/java/wxl/lt/dao/impl/GetUserInfDaoImpl.java:29-31, 53`

**问题代码**:
```java
// 登录查询 (第29-31行)
String hql = "from TbUser u where u.userName='"+form.getUserName()
    +"' or u.userEmail='"+form.getUserName()
    +"' or u.userMobileNo='"+form.getUserName()+"'";

// 用户查重 (第53行)
String hql = "from TbUser u where u.userName='"+form.getUserName()
    +"' or u.userEmail='"+form.getUserEmail()
    +"' or u.userMobileNo='"+form.getUserMobileNo()+"'";
```

**攻击示例**: 输入用户名 `' or '1'='1` → HQL 变为 `where u.userName='' or '1'='1' or ...` → 绕过认证返回第一条记录

**本质**: BaseDao 中已提供参数绑定方法（`get(String hql, Map<String, Object> params)`），但 `GetUserInfDaoImpl` 未使用，而是直接通过 Java 字符串拼接构造 HQL。

#### 2. 密码验证逻辑缺陷 — NPE + 绕过风险

**位置**: `src/main/java/wxl/lt/service/impl/GetUserInfServiceImpl.java:36-51`

**问题代码**:
```java
private TbUser user = new TbUser();  // 类字段，初始化后永远非 null

@Override
public Integer getUserLoginInf(UserForm form) {
    if (StringUtil.isNotEmpty(form.getUserName()) && StringUtil.isNotEmpty(form.getPassword())) {
        TbUser userLogin = getUserInfDao.getUserLoginInf(form);  // 局部变量
        if (user != null) {                       // BUG: 应为 userLogin != null
            if (userLogin.getPassword().equals(form.getPassword())) { // userLogin 可能为 null → NPE!
                user = userLogin;
                userLogin.setLastLoginTime(new Date());
                getUserInfDao.updateUser(userLogin);
                return GlobalConstant.LOGIN_OK;
            } else {
                return GlobalConstant.PASSWORD_ERROR;
            }
        }
    }
    return GlobalConstant.LOGINNAME_ERROR;
}
```

**分析**:
- `if (user != null)` 检查的是类成员变量 `private TbUser user = new TbUser()`，永远为 `true`
- 当数据库查不到用户时，`userLogin` 为 `null`，执行 `userLogin.getPassword()` 触发 **NullPointerException**
- 变量名混淆（`user` 字段 vs `userLogin` 局部变量）导致判断条件失效

**综合风险**: 结合 SQL 注入漏洞，攻击者可注入任意 SQL 绕过所有认证。

#### 3. 数据库密码明文硬编码

**位置**: `src/main/resources/config.properties:7-8`

```properties
jdbc_username=root
jdbc_password=634111
```

- 使用 `root` 账户连接数据库（最高权限）
- 密码 `634111` 以明文存储在已提交 Git 的配置文件中
- 任何拥有代码仓库访问权限的人均可获取数据库完全控制权
- 缺少环境变量/JNDI/加密配置等安全实践

#### 4. 线程安全问题 — Service 单例持有可变状态

**位置**: `src/main/java/wxl/lt/service/impl/GetUserInfServiceImpl.java:27`

```java
@Service  // Spring 默认单例
public class GetUserInfServiceImpl implements GetUerInfService {
    @Autowired
    private GetUserInfDao getUserInfDao;

    private TbUser user = new TbUser();  // 共享可变状态！
```

**竞态场景**:
1. 用户A登录 → `user = userA`
2. 用户B登录 → `user = userB`（覆盖A的user）
3. 用户A调用 `selectUserLogin()` → 返回的是用户B的信息
4. 可能导致越权访问和数据泄露

---

### 🟠 高优先级（HIGH — 应尽快修复）

#### 5. Log4j 1.2.17 已知严重漏洞

**位置**: `pom.xml:84`

Log4j 1.2.17 于 2015 年停止维护，存在多个已知 CVE：
- **CVE-2019-17571** (CVSS 9.8): SocketServer 反序列化漏洞
- **CVE-2022-23307** (CVSS 8.8): CVE-2021-4104 修复不完整
- **CVE-2022-23305** (CVSS 9.8): JDBCAppender SQL 注入
- **CVE-2022-23302** (CVSS 8.8): JMSSink 反序列化

虽然项目当前配置仅使用了 ConsoleAppender 和 DailyRollingFileAppender（未使用漏洞Appender），但框架本身已无安全支持。

#### 6. 无暴力破解防护

- 登录接口无频率限制（Rate Limiting）
- 无验证码机制
- 无账户锁定策略（连续失败 N 次）
- 攻击者可以无限制尝试用户名/密码组合

#### 7. 无 CSRF 防护

- 无 Spring Security 的 CsrfFilter
- 无同步令牌（Synchronizer Token）模式
- 无 `SameSite` Cookie 属性
- 所有 POST 接口（login, register）无 `Origin`/`Referer` 校验
- 攻击者可构造恶意页面诱导已登录用户执行非自愿操作

#### 8. @Validated 注解无效

**位置**: `src/main/java/wxl/lt/controller/IndexController.java:56,98`

```java
public PageMessage userLogin(@Validated UserForm form, ...)
```

`UserForm` 类中没有任何 JSR-303 约束注解（无 `@NotNull`、`@Size`、`@Email` 等），`@Validated` 完全无效。用户名、密码等字段可以为空字符串直接传入后端。

#### 9. 调试后门代码

**位置**: `src/main/java/wxl/lt/utils/MD5Util.java:15-18`

```java
public static void main(String[] args) {
    String s = "admin";
    System.out.println(md5(s));
}
```

`MD5Util` 类可以独立运行（包含 main 方法），用于计算任意文本的 MD5 值。这是调试遗留代码，存在于生产代码中。

#### 10. 测试接口暴露

**位置**: `src/main/java/wxl/lt/controller/IndexController.java:143-146`

```java
@RequestMapping(value = "/test", method = RequestMethod.GET)
public void test(){
    System.out.println(11111);
}
```

公开可访问的测试接口，使用 `System.out.println` 输出，属于开发调试代码遗留。

---

### 🟡 中优先级（MEDIUM）

#### 11. 第三方统计脚本 HTTP 引用

**位置**: `src/main/webapp/WEB-INF/view/login.jsp:254-257`

```html
<div style="display: none">
<script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540'
    language='JavaScript' charset='UTF-8'></script>
</div>
```

- 使用 HTTP（非 HTTPS）协议加载外部 JS 脚本
- 存在中间人攻击（MITM）风险
- 攻击者可劫持该脚本注入恶意 JS 代码（XSS）
- 引用的统计服务（CNZZ）可能已变更或不可用

#### 12. Session 固定攻击风险

登录成功后未调用 `session.invalidate()` 也未轮换 Session ID（如 Spring Security 的 `SessionFixationProtectionStrategy`）。攻击者可通过固定 Session ID 劫持用户会话。

#### 13. SecurityInterceptor 拦截逻辑问题

**位置**: `src/main/java/wxl/lt/framework/interceptors/SecurityInterceptor.java:62`

```java
String url = requestUri.substring(contextPath.length());
```

- 若 `contextPath` 为空字符串则正常；若部署在非根路径可能产生异常
- 白名单仅包含 `/index/login` 和 `/index/userLogin`，但注册接口 `/index/userRegister` 未明确加入白名单
- 依赖 URL 前缀 `/index/login` 匹配才能放行注册请求（注册路径不包含 login 子串）
- 实际上注册接口无 session 验证即可访问，可能是期望行为但未在配置中明确体现

#### 14. 无 HTTPS 安全传输

`web.xml` 中未配置 `<transport-guarantee>CONFIDENTIAL</transport-guarantee>`，登录过程中用户名/密码通过 HTTP 明文传输。

#### 15. password 字段长度不足

`TbUser.password` 映射为 `VARCHAR(50)`，仅够存储 32 位 MD5。若替换为 BCrypt（密文约 60 字符），需扩展字段长度。

#### 16. 数据库配置风险

- `hibernate.hbm2ddl.auto=update` — 生产环境可能意外修改表结构
- `hibernate.show_sql=true` — 日志中打印完整 SQL（含用户敏感数据）

---

### 🟢 低优先级（LOW）

#### 17. TbTest 实体 String ID 类型 Bug

**位置**: `src/main/java/wxl/lt/model/TbTest.java:24-28`

```java
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(unique=true, nullable=false)
public String getId() { return this.id; }
```

`String` 类型主键与 `GenerationType.IDENTITY`（数值自增）冲突。应改为继承 `IdEntity` 使用 `Long` 类型。

#### 18. 接口名拼写错误

**位置**: `src/main/java/wxl/lt/service/GetUerInfService.java`

接口名 `GetUerInfService` → 正确为 `GetUserInfService`（"Uer" → "User"）。同样传播到 `IndexController.java:36`：`private GetUerInfService gtUerInfService`（变量名缩写也丢失了 's'）。

#### 19. UserForm 与 PageMessage 职责重叠

`UserForm.java` 包含响应字段（`errorMsg`, `success`, `list`, `flag`），与 `PageMessage` 的职责高度重叠。UserForm 应只关注输入数据绑定。

#### 20. System.out.println 用于生产日志

多处使用 `System.out.println` 替代日志框架：
- `IndexController.java:145`: `System.out.println(11111);`
- `GetUserInfDaoImpl.java:45`: `System.out.println("注册失败");`

无法通过 Log4j 管理，无法按级别过滤，无法输出到文件。

#### 21. Thumbs.db 等无用文件已提交

- `src/main/webapp/assets/avatars/Thumbs.db`
- `src/main/webapp/assets/css/images/Thumbs.db`

Windows 缩略图缓存文件，不应提交到 Git 仓库。

#### 22. 缺少单元测试

- `maven-surefire-plugin` 配置了 `skipTests=true`
- `src/test/java/` 目录为空，无任何测试用例

#### 23. web.xml 静态资源映射冗余

14 个 `default` servlet-mapping 逐个映射静态文件扩展名（`.css`, `.js`, `.png`, `.gif`, `.jpg`, `.ico`, `.doc`, `.xls`, `.docx`, `.xlsx`, `.txt`, `.swf`, `.woff`, `.woff2`, `.ttf`），应简化为 Spring MVC 的 `<mvc:resources>` 配置。

#### 24. 无 SQL 慢查询监控配置

Druid 连接池的 `filters=stat` 已启用，但未配置慢查询阈值（`connectionProperties` 中的 `druid.stat.slowSqlMillis`），无法有效监控慢查询。

#### 25. JDK 1.7 已停止维护

Java 7 于 2015 年公开更新终止，2019 年扩展支持终止。存在已知安全漏洞且无官方补丁。

---

## 改进建议

### 第一阶段：安全加固（P0 — 立即修复，1周内）

| # | 改进项 | 具体措施 | 影响范围 |
|---|--------|----------|----------|
| 1 | **修复 SQL 注入** | 将 `GetUserInfDaoImpl` 中所有 HQL 字符串拼接改为参数绑定（`get(String hql, Map params)`） | DAO 层 |
| 2 | **修复登录绕过 Bug** | 将 `GetUserInfServiceImpl.java:40` 的 `if (user != null)` 改为 `if (userLogin != null)` | Service 层 |
| 3 | **移除数据库凭据** | 从 `config.properties` 中移除明文密码，改用 JNDI 数据源或环境变量 + 外部配置 | 配置文件 |
| 4 | **升级密码哈希** | 替换 MD5 为 BCrypt（推荐 jBCrypt 0.4），同步扩展 `password` 字段为 `VARCHAR(60)` | 工具类 + 表结构 |
| 5 | **修复线程安全问题** | 将 `GetUserInfServiceImpl.user` 字段移除，改用 ThreadLocal 或方法内局部变量 | Service 层 |

### 第二阶段：依赖升级与基础防护（P1 — 2周内）

| # | 改进项 | 具体措施 |
|---|--------|----------|
| 6 | **升级日志框架** | 从 Log4j 1.2.17 迁移到 Logback 1.2.x+ 或 Log4j 2.x |
| 7 | **添加 Spring Security** | 引入 Spring Security 替代手写 SecurityInterceptor，获得 CSRF/Session固定/记住我等安全特性 |
| 8 | **添加验证码** | 登录和注册接口接入图形验证码（如 Google Kaptcha） |
| 9 | **登录频率限制** | 基于 IP 或用户名的失败登录计数 + 锁定策略 |
| 10 | **强制 HTTPS** | 在 web.xml 中添加 `<transport-guarantee>CONFIDENTIAL</transport-guarantee>` |
| 11 | **移除第三方脚本** | 删除或使用 HTTPS 协议引用 CNZZ 统计脚本 |
| 12 | **移除调试代码** | 删除 `MD5Util.main()`、`IndexController.test()`、`System.out.println` |

### 第三阶段：架构优化（P2 — 1个月内）

| # | 改进项 | 具体措施 |
|---|--------|----------|
| 13 | **前后端分离** | 将 JSP 视图层下线，后端改造为纯 RESTful API，前端完全迁移至 `ant-design-vue-jeecg/` |
| 14 | **实现 RBAC** | 设计用户-角色-权限模型，在 SecurityInterceptor 中加入权限校验 |
| 15 | **统一异常处理** | 添加 `@ControllerAdvice` 全局异常处理器，替代 `System.out.println` 和 try-catch |
| 16 | **统一日志** | 全项目使用 SLF4J + Logback，按级别输出（ERROR/WARN/INFO/DEBUG） |
| 17 | **外部化配置** | 使用 Spring 的 `@PropertySource` + 环境变量覆盖机制 |
| 18 | **SQL 监控** | 配置 Druid 的慢查询阈值 `druid.stat.slowSqlMillis=5000` |
| 19 | **Session 安全** | 配置 Session 超时时间、HttpOnly/Secure Cookie、登录后 Session ID 轮换 |

### 第四阶段：代码质量与运维（P3 — 持续改进）

| # | 改进项 | 具体措施 |
|---|--------|----------|
| 20 | **编写单元测试** | 补充 Service 层和 DAO 层单元测试，覆盖率 > 70% |
| 21 | **修复 TbTest 实体** | 将 id 类型改为 Long 或继承 IdEntity |
| 22 | **修复接口命名** | `GetUerInfService` → `GetUserInfService` |
| 23 | **简化 web.xml** | 用 `<mvc:resources>` 替代 14 个静态资源 servlet-mapping |
| 24 | **添加 .gitignore** | 忽略 `.settings/`、`Thumbs.db`、`*.class`、`target/`、敏感配置文件 |
| 25 | **升级 JDK** | 从 JDK 1.7 升级到 JDK 17 LTS 或 21 LTS |
| 26 | **集成静态分析** | CI/CD 管道中集成 SonarQube / SpotBugs 进行代码质量检测 |
| 27 | **生产配置** | 修改 `hibernate.hbm2ddl.auto=validate`、`hibernate.show_sql=false` |

---

# 本文件由 Claude Code 自动生成
