# 交通信息化工单与设备台账系统

这是一个面向交通信息化场景的 Spring Boot 后端练习项目，主要模拟“高速路段设备台账、巡检记录、故障工单、运维统计”这些常见业务。项目不是企业真实项目经历，而是为了秋招面试时能把后端接口、数据库表、Excel 导入导出、接口文档和测试讲清楚。

适合投递方向：宁夏交投/智慧交通信息化、运营商政企业务支撑、能源国企数字化、本地政务信息化、实施、运维、测试、Java 后端实习等岗位。

![Knife4j 接口文档截图](docs/images/knife4j-api.png)

## 项目能做什么

- 路段基础数据：维护高速路段名称、编码、区域、里程。
- 设备台账：管理摄像头、传感器、信息发布屏等设备资产。
- 巡检记录：记录设备巡检人、巡检时间、巡检结果和备注。
- 故障工单：记录设备故障、优先级、报修人和处理状态。
- 操作日志：记录新增设备、更新状态、导入 Excel 等关键操作。
- 驾驶舱统计：统计设备状态、工单状态、路段数量、设备数量。
- Excel 导入导出：设备台账支持导出为 Excel，也支持按资产编号导入新增/更新。
- Swagger/Knife4j 文档：启动后可直接在网页上查看和调试接口。

## 实际怎么使用

可以把它理解成一个“小型交通运维后台”的后端接口。真实单位如果要用，一般会再配一个前端页面；这个项目目前主要展示后端能力，所以用 Knife4j 或 Postman 来模拟前端调用。

典型使用流程：

1. 基础数据准备：系统里先有银川、石嘴山、中卫等高速路段数据。
2. 录入设备台账：把摄像头、传感器、信息发布屏等设备录入系统，也可以用 Excel 批量导入。
3. 日常巡检：运维人员巡检设备后，提交巡检记录，比如画面是否清晰、传感器数据是否正常。
4. 发现故障：如果设备异常，就创建故障工单，写清楚故障描述和优先级。
5. 工单流转：值班人员把工单从待处理改为处理中、已解决或已关闭。
6. 统计查看：管理人员打开统计接口，看设备总数、告警设备数、未处理工单数。
7. 台账导出：需要汇报或交接时，把设备台账导出成 Excel。

如果面试官问“实际给别人怎么用”，可以这样回答：

> 这个项目目前是后端接口项目，真正上线时会给它配一个前端页面。前端页面调用这些接口后，普通用户看到的就是设备台账列表、工单列表、巡检表单和统计看板。我现在用 Knife4j 来模拟前端调用接口，可以现场演示查询设备、创建工单、更新状态和导出 Excel。

## 系统模块图

```mermaid
flowchart LR
    User["浏览器 / Postman / Knife4j"] --> Controller["Controller 接口层"]
    Controller --> Device["设备台账模块"]
    Controller --> Ticket["故障工单模块"]
    Controller --> Inspection["巡检记录模块"]
    Controller --> Dashboard["驾驶舱统计模块"]
    Controller --> Excel["Excel 导入导出模块"]
    Device --> Repo["Spring Data JPA Repository"]
    Ticket --> Repo
    Inspection --> Repo
    Dashboard --> Repo
    Excel --> Repo
    Repo --> DB[("H2 / MySQL")]
    Device --> Log["操作日志"]
    Ticket --> Log
    Inspection --> Log
    Excel --> Log
```

## 技术栈

- Java 21
- Spring Boot 3.5.7
- Spring Web MVC
- Spring Data JPA
- Bean Validation
- H2 内存数据库，默认开箱即用
- MySQL 运行配置
- Apache POI，处理 Excel 导入导出
- springdoc-openapi + Knife4j，生成接口文档
- JUnit 5 + MockMvc，做接口测试

## 本地启动

推荐先用默认 H2 方式启动，不需要提前装数据库。

```powershell
cd C:\Users\cbx\Documents\work1\traffic-ops-workbench
C:\Users\cbx\Tools\apache-maven-3.9.16\bin\mvn.cmd spring-boot:run
```

也可以用项目自带脚本：

```powershell
.\mvnw.cmd spring-boot:run
```

启动成功后打开：

- Knife4j 接口文档：[http://localhost:8080/doc.html](http://localhost:8080/doc.html)
- Swagger UI：[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON：[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- 健康检查：[http://localhost:8080/api/health](http://localhost:8080/api/health)
- H2 控制台：[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

H2 控制台连接信息：

```text
JDBC URL: jdbc:h2:mem:traffic_ops
User Name: sa
Password: 留空
```

## MySQL 版本运行

如果要切到 MySQL，可以先建库：

```sql
CREATE DATABASE traffic_ops_workbench DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

PowerShell 示例：

```powershell
$env:MYSQL_URL="jdbc:mysql://localhost:3306/traffic_ops_workbench?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&useSSL=false"
$env:MYSQL_USERNAME="root"
$env:MYSQL_PASSWORD="你的密码"
C:\Users\cbx\Tools\apache-maven-3.9.16\bin\mvn.cmd spring-boot:run "-Dspring-boot.run.profiles=mysql"
```

MySQL 配置文件在 `src/main/resources/application-mysql.yml`。默认运行仍然使用 H2，方便 HR 或面试官直接启动看效果。

## 接口示例

接口字段名采用后端常见英文命名，接口文档里补了中文说明。例如 `assetCode` 表示“资产编号”，`roadSectionId` 表示“所属路段 ID”，`installedAt` 表示“安装日期”。

健康检查：

```http
GET /api/health
```

返回示例：

```json
{
  "success": true,
  "data": {
    "service": "traffic-ops-workbench",
    "status": "UP"
  },
  "message": "ok",
  "timestamp": "2026-05-30T02:10:50"
}
```

查询设备台账：

```http
GET /api/devices?status=ONLINE
```

新增设备：

```http
POST /api/devices
Content-Type: application/json
```

```json
{
  "name": "收费站入口抓拍摄像机",
  "assetCode": "CAM-YC-009",
  "deviceType": "Camera",
  "status": "ONLINE",
  "roadSectionId": 1,
  "location": "银川北收费站入口车道",
  "installedAt": "2025-03-01"
}
```

更新设备状态：

```http
PATCH /api/devices/1/status
Content-Type: application/json
```

```json
{
  "status": "MAINTENANCE",
  "operator": "运维值班员"
}
```

新建故障工单：

```http
POST /api/tickets
Content-Type: application/json
```

```json
{
  "deviceId": 2,
  "title": "传感器采集数据波动",
  "description": "夜间采集值波动较大，需要复核采集链路和供电情况。",
  "priority": "HIGH",
  "reporter": "调度中心"
}
```

导出设备台账 Excel：

```http
GET /api/devices/export
```

导入设备台账 Excel：

```powershell
curl.exe -F "file=@C:\Users\cbx\Desktop\devices.xlsx" http://localhost:8080/api/devices/import
```

Excel 表头顺序：

```text
设备名称, 资产编号, 设备类型, 状态, 路段编码, 路段名称, 区域, 安装位置, 安装日期
```

其中 `状态` 可填：`ONLINE`、`WARNING`、`OFFLINE`、`MAINTENANCE`、`FAULT`。`路段编码` 可先通过 `GET /api/road-sections` 查询。

## 主要接口

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/health` | 健康检查 |
| GET | `/api/dashboard` | 首页统计 |
| GET | `/api/road-sections` | 路段列表 |
| GET | `/api/devices` | 查询设备台账 |
| POST | `/api/devices` | 新增设备 |
| PATCH | `/api/devices/{id}/status` | 更新设备状态 |
| GET | `/api/devices/export` | 导出设备 Excel |
| POST | `/api/devices/import` | 导入设备 Excel |
| GET | `/api/tickets` | 查询故障工单 |
| POST | `/api/tickets` | 新建故障工单 |
| PATCH | `/api/tickets/{id}/status` | 更新工单状态 |
| GET | `/api/inspections` | 查询巡检记录 |
| POST | `/api/inspections` | 新增巡检记录 |
| GET | `/api/operation-logs` | 查询操作日志 |

## 测试说明

运行测试：

```powershell
C:\Users\cbx\Tools\apache-maven-3.9.16\bin\mvn.cmd test
```

当前测试覆盖：

- Spring Boot 上下文启动
- 健康检查接口
- 路段基础数据接口
- 设备台账查询接口
- 设备台账 Excel 导出
- 设备台账 Excel 导入并查询
- OpenAPI 接口文档生成

## 面试时可以这样讲

这个项目模拟交通信息化运维后台的一小块核心业务。我把高速路段作为基础数据，把摄像头、传感器、信息发布屏作为设备台账，再围绕设备做巡检记录、故障工单和状态统计。后端使用 Spring Boot + JPA 实现 REST 接口，默认用 H2 方便演示，也提供 MySQL profile。为了贴近实施/运维/测试岗位，我补了 Knife4j 接口文档、Excel 导入导出和 MockMvc 测试，面试时可以直接打开接口页面演示查询、新增、状态流转和台账导出。
