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

����һ��java��Ŀ������ͨ����ҳ��ʽ����׷�ٻس�·�ɡ�

ʹ�÷���

	����
		1.Ĭ�϶˿�Ϊ80����ͨ��Main.java���޸�
		2.��װjdk/openjdk 8���ϰ汾
		3.������jar��

	����
		java -jar WebTraceroute.jar

	����
		������� http://���IP��ַ ����

��ʾ
���ڰ�ȫ���ǣ�Ŀǰֻ����׷�ٿͻ�����Դ��IP��ַ���κ�����¶�**��Ӧ��**�������Կͻ��˵Ĳ��������������޸�Դ���ÿͻ���׷��ָ����IP��ַ��
