# 知识星球项目后端

## 项目目录

```mscript
KnowledgePlanet
├─ .gitignore
├─ README.md
├─ pom.xml 依赖配置文件
└─ src
 ├─ main
 │ ├─ java
 │ │ └─ com
 │ │ └─ major
 │ │ └─ knowledgePlanet
 │ │ ├─ DTO 数据传输对象
 │ │ ├─ KnowledgePlanetApplication.java 
 │ │ ├─ VO 视图对象
 │ │ ├─ config 配置
 │ │ ├─ constValue 常数
 │ │ ├─ controller controller层
 │ │ ├─ entity 实体层
 │ │ ├─ exception 统一异常处理
 │ │ ├─ mapper mapper层
 │ │ ├─ result 统一响应处理
 │ │ ├─ service service层
 │ │ └─ util 工具包
 │ └─ resources
 │ ├─ application-dev.yml 开发环境
 │ ├─ application-test.yml 测试环境
 │ ├─ application.yml 选择环境
 │ ├─ config
 │ │ └─ mail.setting 邮件配置
 │ └─ mapping
 │ ├─ ActivityMapper.xml
 │ ├─ CommentMapper.xml
 │ ├─ CompetitionMapper.xml
 │ ├─ LoginLogMapper.xml
 │ ├─ MessageMapper.xml
 │ ├─ NoticeMapper.xml
 │ ├─ PlanetMapper.xml
 │ ├─ ResourceMapper.xml
 │ ├─ TopicMapper.xml
 │ └─ UserMapper.xml
 └─ test
```

## 环境配置

* **application.yml**
![application.yml](https://covenant-1308013334.cos.ap-shanghai.myqcloud.com/repository/20230321160243.png)

active项根据对应环境配置文件后缀更改为dev或test
application-test.yml 测试环境配置文件
application-dev.yml 开发环境配置文件

* **Mysql配置**

  更改spring.datasource下 username,password,url项为自己的数据库服务
  
* **Redis配置**

  更改spring.redis下 host,password为自己的redis服务
  
* **server配置**
  
  更改server下IP,port为后端服务地址呵呵端口
  
* **JWT配置**

  saltValue为加密盐值
  
* **默认图片地址配置**

  defaultAvatar:默认头像
  defaultPlanetAvatar:默认星球封面
  defaultCompetitionAvatar:默认竟赛封面
  defaultPlanetMaxNumber:默认星球最大成员数
  
* **COS对象存储服务**

  COSController文件中配置
  ![COS](https://covenant-1308013334.cos.ap-shanghai.myqcloud.com/repository/20230321161410.png)
  
  购买腾讯云COS服务后可获得相应配置，图中*需要替换
  
* **邮件配置**

  mail.setting文件中配置发件人和授权码等信息
