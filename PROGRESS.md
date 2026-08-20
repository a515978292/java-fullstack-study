# 学习进度 · PROGRESS

> 这份文件是我（AI 教练）追踪你进度的地方。
> **每次学习开始**：我先读这里，知道你到哪了。
> **每次学习结束**：我更新这里（勾进度 + 写复盘）。
> 详细学习架构见 `ROADMAP.md`。

---

## 📍 当前位置
- **阶段**：阶段二 · 第 4 周（第 3 周于 2026-08-20 ⚠️ 部分通过后推进）
- **已完成**：第 1、2 周全部 ✅；第 3 周功能产出完成——MySQL `todo` 表 + SQL CRUD + Spring Data JPA 连接 + `GET /todos` 从数据库读取 3 条记录并返回 JSON ✅
- **当前代码链路**：`GET /todos` → `TodoController.getTodos()` → `todoRepository.findAll()` → Hibernate 自动生成 `SELECT` → MySQL → `List<Todo>` → JSON
- **卡在哪（下次开场第一件事）**：先不加新功能；让用户对着现有代码逐行讲清 `Todo` / `TodoRepository` / `TodoController`、构造方法注入和 `List<Todo>`，必要时做一个极小 Java 练习
- **下一步**：基础复述通过后，进入第 4 周 Read——实现 `GET /todos/{id}` 查询单条；再学习 `POST /todos` 新增
- **继续学时对我说**：「继续」或 `/study-start`

---

## ✅ 进度总览

### 阶段一：祛魅
- [x] 第 1 周：Java 环境 + 语法速览 → 产出：跑通 `main` 程序 ✅
- [x] 第 2 周：第一个 Spring Boot 接口 → 产出：`/hello` 返回 JSON + 接收参数 ✅
  - [x] Maven / pom.xml（≈ npm/package.json）✅（2026-07-29）
  - [x] `@RestController` + `@GetMapping` 写通 `/hello`，浏览器访问成功 ✅（2026-07-29）
  - [x] 接口返回 JSON（Person 对象 → getter → JSON）+ `@RequestParam` 接收参数 ✅（2026-07-30）

### 阶段二：CRUD 打天下
- [x] 第 3 周：数据库基础 + 连上程序 → 产出：从库里读出并返回 Todo ✅（2026-08-20，⚠️ 部分通过：功能完成，原理待补）
  - [x] 装 MySQL 9.7.1（Homebrew，后台服务，端口 3306，root 无密码）+ TablePlus 26.8.6，客户端连库成功 ✅（2026-07-30，环境保留不重来）
  - [x] 确认环境：`SHOW DATABASES;` / `USE study;` / `SHOW TABLES;` 跑通并看懂结果 ✅（2026-08-04）
  - [x] 能讲清层级：服务器(3306) → 数据库(study) → 表(todo) → 行 ✅（2026-08-04）
  - [x] 创建 `todo` 表（含 `AUTO_INCREMENT PRIMARY KEY` / `NOT NULL` / `DEFAULT` / 时间字段）✅（2026-08-04）
  - [x] 能答出 id 自增、主键唯一、`VARCHAR(50)` 限制 50 字符 ✅（2026-08-04）
  - [x] `INSERT` + `SELECT` 看到多行数据 ✅（2026-08-04）
  - [x] `UPDATE` + `DELETE` 各手敲一遍，并完成事务 `ROLLBACK` 实验 ✅（2026-08-04）
  - [x] `Todo.java` 实体 + `TodoRepository` + JPA 连库，`GET /todos` 返回数据库数据 ✅（2026-08-20）
- [~] 第 4 周：查 + 增 → 产出：能新增/查询 Todo（进行中）
  - [x] GET 列表：`GET /todos` → `findAll()` → 返回 JSON ✅（2026-08-20）
  - [ ] 补牢现有 GET 链路涉及的 Java/JPA 基础（见待补）
  - [ ] GET 详情：按 id 查询单条 Todo
  - [ ] POST 新增：请求体变成 Todo 并写入数据库
- [ ] 第 5 周：改 + 删 → 产出：完整增删改查
- [ ] 第 6 周：健壮性（校验/异常）→ 产出：乱传参数不崩

### 阶段三：靠拢公司代码
- [ ] 第 7 周：三层分层 + 读别人代码 → 产出：重构成规范结构
- [ ] 第 8 周：改代码 + 上线体验 → 产出：项目上线，拿到访问链接

### 🎯 结业考核
- [ ] 独立做一个真实项目并上线（选题待定，阶段三时决定）

---

## 🧩 待补 / 没真正掌握的点
> 学习中发现「还没吃透」的点记在这，避免糊弄过去。

- **写接口的注解**（`@RestController` + `@GetMapping` + `@RequestParam`）仍需反复练形成肌肉记忆——`@RestController` 的含义今天一度忘了，靠复习补回（= 接口类 + 返回值直接当 JSON 发出）
- ~~**`"文字" + 数字` 自动转字符串**~~：已理解（`+` 只要一边是文字，另一边数字就被自动转成文字再拼，如 `"我今年" + 30` → `"我今年30"`）✅
- **参数健壮性**：`@RequestParam` 默认必填，漏传 / 传非数字（`age=abc`）会 400/500 崩——已感受到，留到第 6 周「健壮性」解决
- **IntelliJ 项目结构**：模块 / SDK / 源代码根目录，有直觉但不系统（够用，暂不深挖）
- ~~接口返回 JSON~~：已理解（return 对象 → Spring 靠 getter 读值 + 定 key 名 → JSON）✅
- ~~Maven / `pom.xml`~~：已理解（≈ npm/package.json，依赖在 `<dependencies>`）✅
- **SQL 类型 ↔ Java 类型映射仍需巩固**：本次能在引导下写出 `Integer` / `String` / `Boolean` / `LocalDateTime`，但还没有独立完成过一次实体字段映射
- ~~**`AUTO_INCREMENT` / `PRIMARY KEY` 没讲透**~~：已能答出自动生成递增 id、主键保证唯一且非空 ✅（2026-08-04）
- **为什么后端必须用数据库**：这题当场答不上来，是我讲的答案（变量存内存→重启就丢；数据库存硬盘→持久 + 多实例共享 + 能高效查）。属于「听懂了但没自己产出」，下次开场要复述一遍
- **Java 代码结构不稳**：一度把字段写在 class 外；`private` / 类型 / 字段名已能辨认，但“字段、方法、构造方法”仍需用现有 Todo 代码再练
- **构造方法与依赖注入没吃透**：最初认为 Repository 是自己 `new` 的；经纠正后知道是 Spring 创建并传入，但还不能独立写出和解释构造方法
- **JPA 映射注解未独立掌握**：`@Entity` / `@Table` / `@Id` / `@GeneratedValue` / `@Column` 都是在逐行提示下完成，需要下一次逐个复述职责
- **GET 方法结构需复练**：已能答出 `import` 不执行查询、`List<Todo>` 可含 0/1/多条、`findAll()` 触发查询，但尚不能脱离提示独立写出并讲清完整方法

---

## 📝 学习复盘记录
> 每次学完，我在最上面追加一条。

### 2026-08-20 · 第 3 周收尾 · ⚠️ 部分通过（82/100）
- **学了什么**：Spring Data JPA 的最小链路；`Todo` 实体与 `todo` 表的字段映射；`JpaRepository<Todo, Integer>` 提供数据库操作；Spring 构造方法注入；`@GetMapping("/todos")`、`List<Todo>` 与 `findAll()`；Hibernate 自动生成 SQL。
- **做了什么**：亲手完成 `Todo.java`（4 个字段、JPA 注解、getter）、`TodoRepository`、`TodoController` 和数据库连接配置；Spring 启动日志从 `Found 0` 变成 `Found 1 JPA repository interface`；浏览器访问 `/todos` 返回数据库中 id 1/3/4 的 3 条 JSON，控制台出现 Hibernate `SELECT`；Maven 编译与 `curl` 实测均通过。
- **评分**：功能产出 **40/40** · 原理理解 **26/40** · 踩坑自检 **16/20** = **82/100**。
- **判定**：⚠️ **部分通过，允许推进到第 4 周**。功能产出完整且真实跑通；最终能指出 `todoRepository.findAll()` 触发查询、Repository 负责提供数据库操作方法、对象由 Spring 注入。但 Java/JPA 原理多处必须逐词拆解后才能回答，尚未达到“每段代码都能独立讲清”的完全通过标准。
- **还没真正掌握**：字段/方法/构造方法的结构；构造方法注入；JPA 五个映射注解；`List<Todo>` 和完整 GET 方法的独立书写；`Todo` 是“映射已有表”而不是“定义数据库表”。
- **踩坑与自检**：字段曾写到 class 外；一开始误认为 Repository 是自己 `new`；不知道两个 import 的用途；能主动明确说“看不懂”并要求降速，随后逐步答对 `GetMapping`、`List`、`findAll()` 和三者职责。
- **教练侧调整**：后续一次只引入一个新词；写代码前先明确文件与位置；逐词解释并让用户复述通过后才进入下一行，不再一次给出完整代码块赶进度。
- **下一步**：下一次先用 20～30 分钟对现有 `TodoController` 做逐行复述/极小 Java 练习；通过后实现 `GET /todos/{id}`，再进入 POST 新增。

### 2026-07-30 · 第 3 周第 1 次 · 🔶 未完成（不打分，产出未达成）
- **学了什么**：数据库为什么存在（内存 vs 硬盘、持久化、多实例共享、能高效查）、层级关系「服务器(3306) → 数据库(study) → 表(todo) → 行」、`CREATE DATABASE` / `SHOW DATABASES` / `USE`、SQL 类型与 Java 类型的对应（`INT`↔`int`、`VARCHAR(50)`↔`String`、`BOOLEAN`↔`boolean`）、`127.0.0.1:3306` 与 `localhost:8080` 是同一种「地址+端口」结构。
- **做了什么**：装 MySQL 9.7.1（brew，后台服务）+ TablePlus 26.8.6 → 建连接「本地学习库」并连上 → 跑通三条 SQL，`SHOW DATABASES` 里看到 `study`。
- **判定**：🔶 **未完成**。周目标「从库里读出一条 Todo」没达成，连今天的小目标（建表 + 插 3 条 + 查出来）也停在建表这一步，**所以不打分**。
- **踩的坑（真经）**：① `USE study;` 执行成功了（`Query OK`），但 TablePlus 左侧仍显示 `No database selected`——那是**界面状态**，不等于语句没生效；界面选库要按 ⌘+K，两者是两回事。② 建表时把 `title` 的类型写成 Java 的 `String`，SQL 里应该是 `VARCHAR(50)`；`done` 还手滑写成 `doone`。
- **没吃透**：`AUTO_INCREMENT`、`PRIMARY KEY`、`VARCHAR(50)` 的 50（全部没考过）；「为什么要用数据库」是我讲的、不是他答的。
- **教练侧的问题（记下来避免重犯）**：本次我两次错判「看不到你发的截图」，还拿界面提示当证据、误判 `USE study;` 没执行——都被当场纠正。**规则：要么引用图里的原文来证明我看到了，要么就别下判断；不许拿界面状态当执行结果。**
- **下一步**：新开对话，开场先跑 `USE study; SHOW TABLES; DESC todo;` 确认 `todo` 表真实状态，再补主键 → `INSERT` 3 条 → `SELECT` 看到 3 行。

### 2026-07-30 · 第 2 周第 2 次 · ✅ 通过（93/100）
- **学了什么**：构造方法（`new` 时自动赋值，≈ JS constructor）、`this`（当前对象 + 消除字段/参数重名歧义，还亲手验证「参数改名后可省 this」的边界）、getter 决定 JSON 的 key（Spring 靠 `getXxx()` 读 private 字段、去 get 首字母小写定 key）、`@RequestParam` 接收 URL query 参数、复习 `@RestController`（接口类 + 返回值直接当 JSON）。
- **做了什么**：在 hello-api 建 `Person`（字段+构造+getter）→ `/hello` 返回类型 String 改 Person → 浏览器看到 `{"name":"张腾","age":30}`；再加 `@RequestParam`，`?name=jiji&age=31` 返回 `{"name":"jiji","age":31}`（浏览器亲验）。
- **评分**：功能 40/40 · 原理 37/40 · 踩坑自检 16/20 = **93/100**。
- **判定**：✅ 通过。JSON 全链路（URL 参数 → 构造对象 → getter → JSON）能自己讲清，还主动做了 this 边界实验。
- **踩的坑（真经）**：① Person 在 hello-api 里不存在——第 1 周的 Person 在另一个项目 hello-java，两项目不共享代码（跨项目 import 找不到）；② 方法签名 `String` 没跟着改成 `Person`，返回类型对不上报红；③ `this.name = name` 一开始把赋值方向说反（永远右→左）。
- **没吃透**：`@RestController` 含义一度忘（已补）、参数健壮性（漏传/传错会崩，留第 6 周）。
- **下一步**：进第 3 周——数据库 + Spring Data JPA，产出「从库里读出一条 Todo」。

### 2026-07-29 · 第 2 周第 1 次 · ✅ 通过（90/100）
- **学了什么**：Maven/pom.xml(≈npm/package.json)、Spring Boot 脚手架、`@SpringBootApplication` 启动开关、写接口三件套（`@RestController`+`@GetMapping`+return）、内置 Tomcat、完整请求链路。
- **做了什么**：Spring Initializr 生成项目(Maven+Java17+Spring Web) → 写 `HelloController` → 启动 → **浏览器访问 `/hello` 看到返回**。
- **评分**：功能 40/40 · 原理 32/40 · 踩坑自检 18/20 = **90/100**。
- **判定**：✅ 通过。第一个后端接口从零跑通，请求链路能讲清。
- **踩的坑（真经）**：① import 了注解但没贴 `@RestController`/`@GetMapping` → 接口失踪（导进来≠用上）；② `@GetMapping` 忘带路径 `("/hello")`；③ 端口 8080 被占用（旧后端没关，Spring Boot 是服务器会一直挂着，重跑前先停旧的）。
- **没吃透**：注解肌肉记忆、接口返回 JSON（见待补）。
- **下一步**：接口返回 JSON + 接收参数，铺路第 3 周 CRUD。

### 2026-07-14 · 第 1 周第 3 次 · ✅ 通过（92/100）
- **学了什么**：字段（声明不赋值）、方法结构(`void`)、**类 vs 对象**（图纸 vs 实物、`new`、`.` 访问）、一个 public 类=一个同名文件。
- **做了什么**：新建 `Person.java`（字段 + `introduce()` 方法）→ 在 `Main` 里 `new` 对象、赋值、调方法，跑通「我叫jiji今年31岁」。
- **评分**：功能 40/40 · 原理 36/40 · 踩坑自检 16/20 = **92/100**。
- **判定**：✅ 通过。面向对象核心概念能用自己的话讲清（类是模板无值、对象才有值、方法取当前对象的值）。
- **踩的坑**：① `println` 用逗号（JS 习惯）→ Java 只收一个字符串，要全用 `+`；② 拼接时 `name`/`age` 复制没改 → 变量名手滑型 bug，拼完要念一遍。
- **没吃透**：`"文字"+数字`自动转换（见待补）、Maven/pom.xml（挪到第 2 周开头）。
- **下一步**：进第 2 周——第一个 Spring Boot 接口，开场补 Maven。

### 2026-07-13 · 第 1 周第 1 次 · ✅ 通过（94/100）
- **学了什么**：JDK(≈Node.js)、`java -version`(≈`node -v`)、IntelliJ 安装与建项目、`main` 入口、`System.out.println`(≈`console.log`)、`for` 循环、编译 vs 运行(`javac`≈`tsc` / `java`≈`node`)、模块/SDK 概念、`package` 作用。
- **做了什么**：装好环境 → 建 Maven 项目 → 跑通第一个 `main`(exit code 0) → 亲手改代码再跑通。
- **评分**：功能 40/40 · 原理 36/40 · 踩坑自检 18/20 = **94/100**。
- **判定**：✅ 通过。
- **踩的坑（真经）**：新建 IntelliJ 项目时「模块」没生成 → `<无 SDK>`、src 不能标记、无绿三角 → **改用 Maven 重建，一把解决**。
