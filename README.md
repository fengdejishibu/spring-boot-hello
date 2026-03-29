# Spring Boot Web 接口开发 - 截图指南

## 项目位置
`C:\Users\27403\spring-boot-hello`

## 截图保存位置
`C:\Users\27403\Desktop\截图`

---

## 一、在 IDEA 中打开项目

1. 打开 IntelliJ IDEA
2. 选择 File → Open
3. 找到并选择 `C:\Users\27403\spring-boot-hello` 文件夹
4. 点击 OK，等待 Maven 下载依赖完成

---

## 二、截图1：项目创建完成后的工程结构

**截图内容：**
- IDEA 左侧项目目录结构
- 展开 src/main/java
- 能看到启动类 HelloApplication.java
- 能看到控制器类 HelloController.java

**保存文件名：** `图1_项目工程结构.png`

---

## 三、截图2：pom.xml 中 Web 依赖

**截图内容：**
- 打开 pom.xml 文件
- 截取包含 spring-boot-starter-web 的部分
- 能看到项目基本信息

**保存文件名：** `图2_pom依赖.png`

---

## 四、截图3：项目创建后的开发界面

**截图内容：**
- 项目打开后的主界面
- 左侧目录结构 + 中间代码区
- 能看出项目已成功创建

**保存文件名：** `图3_IDEA开发界面.png`

---

## 五、截图4：启动类代码

**截图内容：**
- HelloApplication.java 文件
- 显示 @SpringBootApplication 注解
- 显示 main 方法
- 显示 SpringApplication.run(...)

**保存文件名：** `图4_启动类代码.png`

---

## 六、截图5：控制器类代码

**截图内容：**
- HelloController.java 文件
- 显示 @RestController 注解
- 显示 @GetMapping("/hello")
- 显示返回字符串的方法

**保存文件名：** `图5_控制器类代码.png`

---

## 七、运行项目

1. 在 HelloApplication.java 文件中
2. 右键点击，选择 Run 'HelloApplication'
3. 或者点击类左侧的绿色运行按钮

等待控制台显示：
```
Started HelloApplication in X seconds
```

---

## 八、截图6：控制台启动成功

**截图内容：**
- IDEA 底部控制台
- 能看见 "Started HelloApplication..."
- 能看见端口信息 "8080"

**保存文件名：** `图6_控制台启动成功.png`

---

## 九、浏览器访问测试

1. 打开 Chrome 或 Edge 浏览器
2. 地址栏输入：`http://localhost:8080/hello`
3. 页面应显示：`Hello Spring Boot`

---

## 十、截图7：浏览器访问结果

**截图内容：**
- 浏览器完整页面
- 地址栏显示 `http://localhost:8080/hello`
- 页面显示 `Hello Spring Boot`

**保存文件名：** `图7_浏览器访问结果.png`

---

## 截图完成后

将所有截图保存到：`C:\Users\27403\Desktop\截图`

然后告诉我，我帮你整理成完整的实验报告！
