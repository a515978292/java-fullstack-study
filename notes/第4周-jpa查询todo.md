# 第 4 周 · Spring Data JPA 查询 Todo

### 🗒️ `Todo` 实体与数据库表映射（记录时间：2026-08-20）

- **是什么**：`Todo.java` 描述 Java 中一条 Todo 数据，并通过 JPA 注解对应已经存在的 `todo` 表。它不是用来重新创建数据库表的。
- **怎么解决的 / 正确做法**：先声明四个 Java 字段，再标记表、主键、自增策略和名称不同的列。
- **关键代码或命令**：

  ```java
  @Entity
  @Table(name = "todo")
  public class Todo {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Integer id;

      private String title;
      private Boolean done;

      @Column(name = "created_at")
      private LocalDateTime createdAt;
  }
  ```

  - `@Entity`：告诉 JPA，这个类是数据库实体。
  - `@Table(name = "todo")`：对应数据库中的 `todo` 表。
  - `@Id`：`id` 对应主键。
  - `@GeneratedValue(...IDENTITY)`：id 由 MySQL 的 `AUTO_INCREMENT` 生成。
  - `@Column(name = "created_at")`：Java 的 `createdAt` 对应数据库的 `created_at`。
  - getter：让其他代码读取 `private` 字段，也让返回 JSON 时能取到字段值。
- **一句话记住它**：`Todo` 负责说明“一条 Todo 在 Java 中长什么样，以及它和表中各列怎样对应”。

### 🗒️ `TodoRepository` 提供数据库操作（记录时间：2026-08-20）

- **是什么**：`TodoRepository` 继承 `JpaRepository` 后，可以直接使用查询、保存和删除方法，不需要自己写实现类。
- **怎么解决的 / 正确做法**：告诉 `JpaRepository` 要操作的实体是 `Todo`，主键类型是 `Integer`。
- **关键代码或命令**：

  ```java
  public interface TodoRepository
          extends JpaRepository<Todo, Integer> {
  }
  ```

  已获得的常用方法：

  ```text
  findAll()       查询全部
  findById()      按 id 查询
  save()          新增或修改
  deleteById()    按 id 删除
  ```

  启动日志从：

  ```text
  Found 0 JPA repository interfaces.
  ```

  变成：

  ```text
  Found 1 JPA repository interface.
  ```

  证明 Spring 已识别该 Repository，并自动创建了实际对象。
- **一句话记住它**：Repository 负责提供操作数据库的方法，具体实现由 Spring 自动生成。

### 🗒️ Spring 把 Repository 交给 Controller（记录时间：2026-08-20）

- **是什么**：Controller 需要 Repository 才能查询数据库；Repository 不是自己 `new` 的，而是 Spring 创建后通过构造方法传进来的，这个过程叫依赖注入。
- **踩了什么坑 / 卡在哪**：一开始误以为 `repository` 是自己 `new` 出来的；还曾把字段写在 class 的大括号外面，导致语法报红。
- **怎么解决的 / 正确做法**：字段必须写在 class 内；构造方法接收 Spring 传入的对象，再保存到字段中。
- **关键代码或命令**：

  ```java
  @RestController
  public class TodoController {
      private TodoRepository todoRepository;

      public TodoController(TodoRepository repository) {
          todoRepository = repository;
      }
  }
  ```

  ```java
  private TodoRepository todoRepository;
  ```

  其中 `TodoRepository` 是类型，`todoRepository` 是字段名。
- **一句话记住它**：Spring 创建 Repository，Controller 用构造方法接收并保存它。

### 🗒️ `GET /todos` 查询并返回 JSON（记录时间：2026-08-20）

- **是什么**：浏览器访问 `/todos` 时，Controller 调用 `findAll()` 查询全部 Todo，并返回 `List<Todo>`。
- **怎么解决的 / 正确做法**：先引入 `GetMapping` 和 `List`，再把 URL、方法返回类型和数据库查询组合起来。
- **关键代码或命令**：

  ```java
  import org.springframework.web.bind.annotation.GetMapping;
  import java.util.List;
  ```

  `import` 只告诉 Java 类型来自哪个包，本身不会执行数据库查询。

  ```java
  @GetMapping("/todos")
  public List<Todo> getTodos() {
      return todoRepository.findAll();
  }
  ```

  - `@GetMapping("/todos")`：接收 `GET /todos`。
  - `List<Todo>`：返回一个 Todo 列表，里面可以有 0、1 或多条数据。
  - `todoRepository.findAll()`：真正触发数据库查询。

  实际验证地址：

  ```text
  http://localhost:8080/todos
  ```

  Hibernate 自动生成并执行了类似 SQL：

  ```sql
  SELECT id, created_at, done, title
  FROM todo;
  ```

  实际链路：

  ```text
  GET /todos
  → TodoController.getTodos()
  → todoRepository.findAll()
  → Hibernate 生成 SELECT
  → MySQL 返回数据
  → List<Todo>
  → Spring 转成 JSON
  ```
- **一句话记住它**：Controller 接请求，Repository 查数据，Todo 接住每行数据，Spring 最后返回 JSON。

### 🗒️ 本次仍需复练的内容（记录时间：2026-08-20）

- **是什么**：功能已经跑通，但以下代码还不能脱离提示独立写出和讲清。
- **怎么解决的 / 正确做法**：下一次先对照现有代码逐行复述，再增加 `GET /todos/{id}`，不要直接复制新接口。
- **关键内容**：
  - 字段、方法、构造方法的结构和位置。
  - Spring 构造方法注入。
  - `@Entity`、`@Table`、`@Id`、`@GeneratedValue`、`@Column` 的各自职责。
  - `List<Todo>` 和完整 GET 方法的独立书写。
- **一句话记住它**：功能跑通只是第一步，能独立解释每一行后才算真正掌握。
