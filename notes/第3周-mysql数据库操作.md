# 第 3 周 · MySQL 数据库操作

### 🗒️ MySQL 服务、客户端与连接（记录时间：2026-08-13）

- **是什么**：MySQL 是真正运行和保存数据的数据库服务；TablePlus 和终端里的 `mysql` 都是连接、操作 MySQL 的客户端。MySQL 停止后，TablePlus 仍能打开，但无法连接数据库。
- **怎么解决的 / 正确做法**：本机通过 Homebrew 安装 MySQL，统一使用 `brew services` 管理，不和 `mysql.server` 混用。
- **关键代码或命令**：

  ```bash
  brew services list          # 查看状态
  brew services start mysql   # 启动
  brew services stop mysql    # 停止
  brew services restart mysql # 重启

  mysqladmin ping -u root     # 检查服务能否响应
  mysql -u root               # 进入 MySQL 命令行
  ```

  查看 MySQL 实际端口：

  ```bash
  mysql -u root -e "SELECT @@port AS port;"
  ```

  本机结果为 `3306`。`mysql.server status` 中括号里的数字是进程 ID（PID），不是端口。
- **一句话记住它**：MySQL 负责存数据，TablePlus 和 `mysql` 命令只负责连接和操作它。

### 🗒️ 数据库层级与基础检查（记录时间：2026-08-13）

- **是什么**：当前层级是“MySQL 服务 → `study` 数据库 → `todo` 表 → 一行 Todo 记录”。
- **怎么解决的 / 正确做法**：进入 MySQL 后，先确认数据库存在，再切换数据库并查看表。
- **关键代码或命令**：

  ```sql
  SHOW DATABASES;
  USE study;
  SHOW TABLES;
  ```

  - `Database changed`：已经切换到 `study`。
  - `Empty set`：查询成功，但当前没有数据；不是报错。
  - SQL 末尾要写分号 `;`。出现 `->` 表示语句还没结束；输入 `\c` 可以取消当前未完成的语句。
- **一句话记住它**：先选数据库，再操作里面的表；`Empty set` 是空结果，不是失败。

### 🗒️ 创建并检查 `todo` 表（记录时间：2026-08-13）

- **是什么**：用 `CREATE TABLE` 定义表名、字段类型和约束。
- **踩了什么坑 / 卡在哪**：曾把 `NOT NULL` 写成 `NOT FULL`，MySQL 返回 `ERROR 1064`；也曾把 TypeScript 的 `type Todo = {...}` 输入 MySQL，但 MySQL 终端只能执行 SQL。
- **怎么解决的 / 正确做法**：根据错误信息中 `near '...'` 指向的位置检查拼写、括号和逗号，并重新执行完整建表语句。
- **关键代码或命令**：

  ```sql
  CREATE TABLE todo (
      id INT AUTO_INCREMENT PRIMARY KEY,
      title VARCHAR(50) NOT NULL,
      done BOOLEAN NOT NULL DEFAULT FALSE,
      created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
  );
  ```

  字段含义：

  - `AUTO_INCREMENT`：插入新行时自动生成递增的 `id`。
  - `PRIMARY KEY`：保证 `id` 唯一且不能为空。
  - `VARCHAR(50)`：最多保存 50 个字符。
  - `NOT NULL`：不允许存 `NULL`，但仍允许空字符串 `''`。
  - `DEFAULT FALSE`：不填写 `done` 时默认保存 `0`。
  - `DEFAULT CURRENT_TIMESTAMP`：不填写 `created_at` 时自动保存插入时的当前日期时间。

  检查表和字段：

  ```sql
  SHOW TABLES;
  DESC todo;
  ```

  `DESC todo;` 中，`PRI` 表示主键，`auto_increment` 表示自动递增。MySQL 会把 `BOOLEAN` 显示为 `tinyint(1)`，其中 `0` 是 `FALSE`，`1` 是 `TRUE`。
- **一句话记住它**：建表就是明确每一列存什么，以及数据库必须替我们守住哪些规则。

### 🗒️ Todo 的增删改查（记录时间：2026-08-13）

- **是什么**：`INSERT`、`SELECT`、`UPDATE`、`DELETE` 分别负责新增、查询、修改和删除数据。
- **踩了什么坑 / 卡在哪**：曾把 `VALUES` 拼成 `VALUSE`，导致 `ERROR 1064`。
- **怎么解决的 / 正确做法**：先看报错指向的单词；执行 `UPDATE` 或 `DELETE` 前检查 `WHERE`，执行后再用 `SELECT` 验证结果。
- **关键代码或命令**：

  ```sql
  -- 新增：只提供 title，其余字段使用自动值或默认值
  INSERT INTO todo (title) VALUES ('学习 SQL');

  -- 新增：明确指定完成状态
  INSERT INTO todo (title, done) VALUES ('复习 Java', TRUE);

  -- 查询全部
  SELECT * FROM todo;

  -- 按 id 查询
  SELECT * FROM todo WHERE id = 3;

  -- 把 id=3 改成已完成
  UPDATE todo SET done = TRUE WHERE id = 3;

  -- 删除 id=2
  DELETE FROM todo WHERE id = 2;
  ```

  - `Query OK, 1 row affected`：写操作成功影响 1 行。
  - `Rows matched: 1, Changed: 1`：找到 1 行，并真正修改了 1 行。
  - 删除一行后，后面的 `id` 不会重新编号，出现 `1、3、4` 是正常的。
- **一句话记住它**：写操作必须带准确条件，执行后必须查询验证，尤其不能漏掉 `WHERE`。

### 🗒️ 事务与 `ROLLBACK`（记录时间：2026-08-13）

- **是什么**：事务把一组数据库修改包在一起；提交前可以确认结果，也可以整体撤销。
- **踩了什么坑 / 卡在哪**：直接删除 `id = 2` 后才执行 `ROLLBACK`，数据没有恢复。原因是 MySQL 默认自动提交，删除成功时已经生效。
- **怎么解决的 / 正确做法**：删除前先执行 `START TRANSACTION;`。确认正确就 `COMMIT;`，发现错误就在提交前执行 `ROLLBACK;`。用 `id = 4` 实验时，回滚后数据成功恢复。
- **关键代码或命令**：

  ```sql
  START TRANSACTION;

  DELETE FROM todo WHERE id = 4;
  SELECT * FROM todo WHERE id = 4;

  ROLLBACK;
  SELECT * FROM todo WHERE id = 4;
  ```

  结束事务：

  ```sql
  COMMIT;   -- 确认修改并结束事务
  ROLLBACK; -- 撤销未提交的修改并结束事务
  ```

  能通过 `ROLLBACK` 恢复，必须同时满足：操作前已开启事务，并且修改尚未提交。生产环境中如果误删已经提交，需要依靠备份和 binlog 恢复，不能靠普通 `ROLLBACK`。
- **一句话记住它**：先开事务、确认后再提交；一旦提交，普通回滚就来不及了。

### 🗒️ SQL 大小写习惯（记录时间：2026-08-13）

- **是什么**：`SELECT`、`INSERT`、`WHERE` 等 SQL 关键字通常不区分大小写；数据库名和表名应始终保持一致。
- **怎么解决的 / 正确做法**：关键字大写，数据库、表和字段名小写，多单词使用下划线。
- **关键代码或命令**：

  ```sql
  SELECT title, done
  FROM todo
  WHERE id = 3;
  ```

- **一句话记住它**：大小写多数时候不影响执行，但统一格式能减少错误并提高可读性。
