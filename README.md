# WebTraceroute
This is a java program.
Fast traceroute back to the client on web.


## Usage
- Compile
 1. Port is listening on 80 by default, you may change it in Main.java
 2. Install jdk/openjdk version 8 or above
 3. It's recommanded to export as a jar file

- Running
java -jar WebTraceroute.jar

- Test
http://IP_ADDRESS

## Notice
For security reason, **DO NOT** accept any params from the client even if you want to modify source code such as attempting to trace to the IP address which is specified by client.

------------

这是一个java项目。用于通过网页形式快速追踪回程路由。

使用方法

	编译
		1.默认端口为80，可通过Main.java来修改
		2.安装jdk/openjdk 8以上版本
		3.建议打成jar包

	运行
		java -jar WebTraceroute.jar

	访问
		浏览器打开 http://你的IP地址 即可

提示
出于安全考虑，目前只允许追踪客户端来源的IP地址，任何情况下都**不应该**接受来自客户端的参数，例如你想修改源码让客户端追踪指定的IP地址。
