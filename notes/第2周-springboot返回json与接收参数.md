# 第 2 周 · Spring Boot 接口返回 JSON & 接收参数

---

### 🗒️ 构造方法（constructor）（记录时间：2026-07-30）

- **是什么**：和类同名、**没有返回类型**的特殊方法，`new Person("张腾", 30)` 时**自动调用**，负责给对象的字段初始化赋值。前端类比：JS class 里的 `constructor(name, age) {...}`，一模一样。
- **对比第 1 周的写法**：以前是造完对象再一行行填值（`p.name="张腾"; p.age=30;`），现在用构造方法一次性塞进去，更专业、更常用。
- **关键代码**：
  ```java
  public class Person {
      private String name;
      private int age;

      // 构造方法：new 的时候自动跑，把传进来的值填进字段
      public Person(String name, int age) {
          this.name = name;
          this.age = age;
      }
  }
  ```
- **一句话记住它**：**构造方法 = `new` 时自动跑的初始化器（≈ JS 的 constructor），和类同名、无返回类型。**

---

### 🗒️ `this` 是什么 + 为什么重名时必须写（记录时间：2026-07-30）

- **是什么**：`this` = **当前正在被 `new` 出来的这个对象自己**。用来在「字段」和「参数」重名时区分谁是谁。
- **踩了什么坑 / 卡在哪**：`this.name = name` 里两个 `name` 到底谁给谁赋值，一开始把方向说反了。
- **正确理解**：等号永远是**右边的值 → 赋给左边**。
  ```java
  public Person(String name, int age) {
      this.name = name;
      //   ↑        ↑
      // 实例字段   传进来的参数（值的来源）
  }
  ```
  - 左边 `this.name` = 当前对象的字段（被赋值的目标）
  - 右边 `name` = 参数（`new Person("张腾",30)` 里的 "张腾"）
- **为什么重名时必须写 `this`**：参数和字段都叫 `name`，光写 `name = name` 会被 Java 当成「参数赋值给参数自己」，字段永远是空的 → 返回 `{"name":null,"age":0}`。
- **亲手验证的边界**（很重要）：如果把参数改名，就**不用** `this` 了——因为不再重名：
  ```java
  public Person(String personName, int personAge) {
      name = personName;   // 没写 this 也对，因为没有重名冲突
      age  = personAge;
  }
  ```
  - 重名（`name`/`name`）→ **必须** `this.name = name`
  - 不重名（`name`/`personName`）→ `name = personName` 就够了
- **实际开发用哪种**：几乎都用「同名 + `this`」那种，可读性最好，是公司代码里的标准写法。
- **一句话记住它**：**`this` = 当前对象；它专门用来「消除字段和参数的重名歧义」，重名必须写，不重名可省。**

---

### 🗒️ 为什么 return 对象就变成 JSON 了 + getter 决定 key（记录时间：2026-07-30）

- **是什么**：接口方法 `return` 一个对象（如 Person），Spring 会自动把它转成 JSON 返回给浏览器。但它**不直接碰 `private` 字段**，而是靠 **getter**（`getXxx()`）读值。
- **完整链路**（浏览器访问 `/hello?name=jiji&age=31`）：
  ```
  URL 的 ?name=jiji&age=31
     → @RequestParam 把值塞进方法参数 name、age
     → new Person(name, age) 走构造方法，填进实例字段
     → Spring 调 getName()/getAge() 读出值
     → 拼成 {"name":"jiji","age":31} 返回
  ```
- **关键坑**：字段是 `private`，Spring 进不去，**没有 getter，转出来的 JSON 就是空的**。所以 getter 必须写。
- **JSON 的 key 从哪来**：看的是 **getter 的名字**，不是字段名——`getName()` 去掉 `get`、首字母小写 → key 叫 `name`；`getAge()` → key 叫 `age`。
- **一句话记住它**：**Spring 转 JSON 只认 `getXxx()`：没有 getter JSON 就是空的；getter 的名字（去 get、首字母小写）决定 JSON 的 key。**

---

### 🗒️ `@RequestParam`：接口接收 URL 参数（记录时间：2026-07-30）

- **是什么**：让接口能接住 URL 里的 query 参数（`?name=jiji&age=31`）——就是前端最熟的 query string。加了它，接口才「活」：前端传什么就返回什么，不再写死。
- **关键代码**（三处变化）：
  ```java
  import org.springframework.web.bind.annotation.RequestParam;  // ① 新增 import

  @GetMapping("/hello")
  public Person hello(@RequestParam String name, @RequestParam int age) {  // ② 括号里接参数
      return new Person(name, age);   // ③ 用接进来的值，不写死
  }
  ```
  - `@RequestParam` 告诉 Spring：这个值从 URL 的 `?name=...` 里取
  - Spring 靠**名字对应**：URL 里 `name=` → 塞给参数 `name`；`age=` → 塞给参数 `age`
- **踩到的坑（下周伏笔）**：`@RequestParam` 默认**必填**。
  - 漏传 age → 400（缺必填参数）
  - `age=abc` → 500（abc 转不成 int）
  - 这就是「乱传参数就崩」，留到第 6 周「健壮性」解决。
- **一句话记住它**：**`@RequestParam` = 接住 URL 的 `?key=value`（靠名字对应），默认必填，漏传/传错类型会崩。**

---

### 🗒️ 复习：`@RestController` 是干嘛的（记录时间：2026-07-30）

- **是什么**：贴在类上，告诉 Spring 两件事——① 「我是接口类，启动时扫我」，把里面的 `@GetMapping` 登记成路由；② 「方法的返回值**直接当响应数据**发出去（自动转 JSON）」，而不是当网页名去找模板。
- **它是两个注解合体**：`@RestController = @Controller + @ResponseBody`
  - 只 `@Controller`：Spring 以为返回的是网页名 → 去找 HTML 模板 → 找不到报错
  - 加 `@ResponseBody`（或直接用 `@RestController`）：返回值 = 数据本身
- **踩了什么坑**：这次一度忘了它的含义，靠复习补回。少了它，接口方法就没人调用，浏览器访问直接 404（呼应第 1 周「导进来≠用上」的坑）。
- **前端类比**：≈ Express 的 `app.get('/hello', (req,res)=>res.json(...))`——声明这是个 API 路由，返回 JSON 数据，不渲染页面。
- **一句话记住它**：**`@RestController` = 「我是 API 接口类 + 返回值直接当 JSON 发出」，少了它接口就 404。**

---
