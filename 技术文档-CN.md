# 阿里云 ACR + GitHub Actions 部署 Spring Boot 项目

## 1. 系统架构

```
GitHub (main分支)
    │
    │  push 触发
    ▼
GitHub Actions CI/CD
    │
    ├─→ Maven 构建 JAR
    │
    ├─→ Docker 构建镜像
    │
    ├─→ 推送镜像到阿里云 ACR
    │
    └─→ SSH 远程部署到 ECS
              │
              ▼
        阿里云 ECS (Docker 运行)
```

---

## 2. 快速开始

### 2.1 前置条件

- [x] 阿里云账号 (已开通 ACR 个人版)
- [x] 阿里云 ECS 服务器 (已安装 Docker)
- [x] GitHub 账号

### 2.2 在服务器上安装 Docker

如果服务器还未安装 Docker，执行以下命令：

```bash
# Alibaba Cloud Linux / CentOS / RHEL
yum install -y podman-docker

# 验证
docker --version
```

### 2.3 在服务器上创建部署目录

```bash
mkdir -p /opt/spring-boot-hello
```

---

## 3. 配置 GitHub Secrets

1. 登录 GitHub，进入你的仓库
2. 点击 `Settings` → `Secrets and variables` → `Actions`
3. 点击 `New repository secret`，添加以下 Secrets：

| Secret 名称 | 值 |
|------------|-----|
| `ACR_REGISTRY` | 阿里云 ACR 地址 (格式: `xxx.region.personal.cr.aliyuncs.com`) |
| `ACR_USERNAME` | 阿里云 AccessKey ID |
| `ACR_PASSWORD` | 阿里云 AccessKey Secret |
| `ECS_HOST` | ECS 服务器公网 IP |
| `ECS_PORT` | SSH 端口 (默认 22) |
| `ECS_SSH_KEY` | *(你的 SSH 私钥全文)* |

---

## 4. 配置阿里云 AccessKey

### 4.1 创建 AccessKey

1. 登录阿里云 RAM 控制台：https://ram.console.aliyun.com
2. 左侧菜单 → 身份管理 → 用户
3. 点击 `创建用户`，填写用户信息
4. 创建成功后，在用户详情页点击 `创建 AccessKey`
5. **立即保存 AccessKey ID 和 AccessKey Secret**（Secret 只显示一次）

### 4.2 配置 ACR 密码

ACR 个人版的密码就是 AccessKey ID 和 AccessKey Secret。

---

## 5. 推送代码触发 CI/CD

```bash
git init
git add .
git commit -m "feat: 配置 CI/CD 部署"
git branch -M main
git remote add origin https://github.com/你的用户名/spring-boot-hello.git
git push -u origin main
```

推送成功后：
1. 进入 GitHub 仓库 → `Actions` 标签页
2. 可以看到 CI/CD workflow 正在运行
3. 点击 workflow 查看详细日志

---

## 6. 验证部署

### 6.1 检查容器状态

在 GitHub Actions 日志中或服务器上执行：

```bash
docker ps | grep spring-boot-hello
```

### 6.2 访问应用

```
http://YOUR_ECS_IP:8080
```

### 6.3 查看日志

```bash
docker logs -f spring-boot-hello
```

---

## 7. 项目迁移清单

要把这套 CI/CD 流程迁移到其他项目，需要修改/配置的内容：

### 7.1 必定需要修改的配置

| 配置项 | 迁移到新项目时 |
|--------|---------------|
| GitHub Secrets | 全部重新配置 |
| `pom.xml` 中的 artifactId | 修改为新项目名称 |
| `Dockerfile` | 可能需要调整 Java 版本、端口等 |
| `.github/workflows/deploy.yml` 中的镜像名称 | 修改为新项目的镜像名 |
| ACR 镜像地址 | 修改为新项目的镜像名 |

### 7.2 可能需要调整的配置

| 配置项 | 说明 |
|--------|------|
| Java 版本 | 如果不是 Java 17，需修改 `setup-java` 版本 |
| Maven 构建命令 | 如果使用 Gradle，需修改为 `gradle build` |
| Docker 镜像 | 如果使用不同基础镜像，修改 `Dockerfile` |
| 部署路径 | `/opt/spring-boot-hello` 改为新路径 |
| 端口映射 | `8080:8080` 如果应用端口不同需修改 |
| 环境变量 | 如果应用需要额外环境变量，添加 `env` 配置 |

### 7.3 迁移步骤

1. 复制以下文件到新项目：
   - `.github/workflows/deploy.yml`
   - `Dockerfile`
   - `deploy.sh`

2. 修改 `pom.xml` 的 `artifactId`

3. 创建新的 GitHub Secrets

4. 修改 `deploy.yml` 中的镜像名称（如果 ACR 仓库名不同）

5. 提交并推送代码

---

## 8. 常见问题

### 8.1 Docker 镜像拉取失败

```bash
# 在服务器上登录 ACR
docker login YOUR_ACR_REGISTRY -u <AccessKeyID> -p <AccessKeySecret>
```

### 8.2 SSH 连接失败

检查：
- ECS 安全组是否开放了 22 端口
- SSH 密钥是否正确配置
- `ECS_SSH_KEY` Secret 是否包含完整的私钥内容（从 `-----BEGIN RSA PRIVATE KEY-----` 到 `-----END RSA PRIVATE KEY-----`）

### 8.3 端口 8080 无法访问

检查：
- ECS 安全组是否开放了 8080 端口入站
- 应用是否正常启动：`docker logs spring-boot-hello`
- 端口映射是否正确：`docker port spring-boot-hello`

### 8.4 GitHub Actions 超时

可以在 `deploy.yml` 中添加超时配置：

```yaml
- name: Deploy to Alibaba Cloud ECS
  uses: appleboy/ssh-action@v1.0.3
  timeout: 300s  # 5分钟超时
  with:
    # ... 其他配置
```

---

## 9. 自动化流程说明

每次 push 到 `main` 分支时：

1. **Checkout** - 拉取最新代码
2. **Setup Java** - 配置 JDK 17
3. **Maven Build** - 编译打包 `mvn clean package`
4. **Login ACR** - 使用 Secrets 登录阿里云镜像仓库
5. **Build & Push** - 构建 Docker 镜像并推送到 ACR
6. **SSH Deploy** - 远程连接 ECS，执行部署脚本

---

## 10. 安全建议

1. **定期轮换 AccessKey**：在阿里云 RAM 控制台定期更换 AccessKey
2. **不要提交敏感信息**：服务器地址、密码、密钥等不要写在代码中
3. **限制安全组端口**：只开放必要的端口 (22, 8080)
4. **使用最小权限用户**：避免使用 root 权限运行应用
5. **启用 Docker 日志轮转**：避免日志文件过大
