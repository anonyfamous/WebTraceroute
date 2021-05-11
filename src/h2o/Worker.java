package h2o;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class Worker implements Runnable {
	
	private Socket socket;
	
	public Worker(Socket client){
		this.socket = client;
	}

	@Override
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			is.read(new byte[2048]);
			//is.close();
			
			String ip = socket.getInetAddress().getHostAddress();
			boolean isNotCached = Cache.pre(ip);
			String result = null;
			if(isNotCached){
				result = tracert(ip);
				Cache.put(ip, result);
			}else{
				 result = Cache.get(ip);
			}
			
			StringBuilder sb = new StringBuilder();
			sb.append("<html><body>");
			sb.append("<h2>快速回程路由测试</h2>");
			sb.append("<pre>" + result + "</pre>");
			sb.append("</body></html>");
			byte[] data = sb.toString().getBytes("UTF-8");
			
			
			OutputStream os = socket.getOutputStream();
			os.write("HTTP/1.0 200 OK\r\n".getBytes());
			os.write("Content-Type:text/html;charset=utf-8\r\n".getBytes());
			os.write(("Content-Length:" + data.length + "\r\n").getBytes());
			os.write("Server:h2o\r\n".getBytes());
			os.write(("Date:"+new Date()+"\r\n").getBytes());
			os.write(("Fresh:" + (isNotCached ? "Y" : "N") + "\r\n").getBytes());
			os.write("\r\n".getBytes());
			os.write(data);
			os.flush();
			os.close();
			socket.close();
		}catch(Exception e){
			try {
				System.out.println("Ex>" + this.hashCode() + ">" + e.getMessage());
				
				OutputStream os = socket.getOutputStream();
				os.write("HTTP/1.0 200 Exception\r\n".getBytes());
				os.write("Content-Type:text/html;charset=utf-8\r\n".getBytes());
				os.write(("Content-Length:" + String.valueOf(e.getMessage()).getBytes("UTF-8").length + "\r\n").getBytes());
				os.write("Server:h2o\r\n".getBytes());
				os.write(("Date:"+new Date()+"\r\n").getBytes());
				os.write("\r\n".getBytes());
				os.write(String.valueOf(e.getMessage()).getBytes("UTF-8"));

				os.flush();
				os.close();
				
				socket.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}finally{
			System.out.println("End>" + this.hashCode() + ">" + socket.getInetAddress().getHostAddress());
		}
	}
	
	private String tracert(String ip) throws IOException, InterruptedException{
		 StringBuilder result = new StringBuilder();

	        Process process = null;
	        BufferedReader bufrIn = null;
	        BufferedReader bufrError = null;

	        try {
	            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
	            process = Runtime.getRuntime().exec("/usr/bin/timeout 10 /usr/sbin/traceroute -w 2 -m 25 -n " + ip);

	            // 方法阻塞, 等待命令执行完成（成功会返回0）
	            process.waitFor();

	            // 获取命令执行结果, 有两个结果: 正常的输出 和 错误的输出（PS: 子进程的输出就是主进程的输入）
	            bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
	            bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));

	            // 读取输出
	            String line = null;
	            bufrIn.readLine();//cut1
	            bufrIn.readLine();//cut2
	            while ((line = bufrIn.readLine()) != null) {
	                result.append(line).append('\n');
	            }
	            while ((line = bufrError.readLine()) != null) {
	                result.append(line).append('\n');
	            }

	        } finally {
	            bufrIn.close();
	            bufrError.close();

	            // 销毁子进程
	            if (process != null) {
	                process.destroy();
	            }
	        }

	        // 返回执行结果
	        return result.toString();
	    }

}
