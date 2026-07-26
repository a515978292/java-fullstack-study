# Git / GitHub 操作备忘

---

### 🗒️ 把项目上传到 GitHub（首次）（记录时间：2026-07-14）

- **是什么**：把本地文件夹变成 git 仓库并推到 GitHub。GitLab / GitHub 概念完全一样，git 命令通用。
- **前提**：装了 `git` 和 `gh`（GitHub 命令行），且 `gh auth status` 显示已登录。
- **步骤**：
  ```bash
  git init                  # 1. 初始化仓库
  git branch -m main        # 2.（可选）默认分支 master → main
  # 3. 写好 .gitignore 挡掉不该传的（见下）
  git add -A                # 4. 暂存所有文件
  git status --short        # 5. 确认没有 target/.idea/本地配置
  git commit -m "首次提交"   # 6. 打包成一个存档点
  gh repo create java-fullstack-study --public --source=. --remote=origin --push  # 7. 建远程仓库并推送
  ```
- **一句话记住它**：**init → gitignore → add → commit → gh repo create --push。**

---

### 🗒️ 以后更新 GitHub（日常）（记录时间：2026-07-14）

- **场景**：学完新内容，想同步到 GitHub。
- **三条命令**：
  ```bash
  git add -A
  git commit -m "第2周：Spring Boot 第一个接口"
  git push
  ```
- **一句话记住它**：**add → commit（写清这次改了啥）→ push。**

---

### 🗒️ .gitignore：哪些东西不该传（记录时间：2026-07-14）

- **是什么**：一份「忽略清单」，让 git 不追踪某些文件。新手最容易犯的错就是把编译产物、依赖一股脑传上去。
- **Java 项目常见要忽略的**：
  ```
  target/                          # Maven 编译产物（别人会自己编译，传了占地方）
  *.class
  .idea/                           # IntelliJ 本地配置（每人电脑不同）
  *.iml
  .claude/settings.local.json      # 含本机路径的本地配置
  .DS_Store                        # macOS 系统文件
  ```
- **前端类比**：就像前端项目里 `node_modules/`、`dist/` 从来不上传，一个道理。
- **一句话记住它**：**编译产物、本地配置、系统文件，一律不传；能重新生成的就别传。**

---

### 我的仓库
- **GitHub**：https://github.com/a515978292/java-fullstack-study （公开）
