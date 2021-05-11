package h2o;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	private static ExecutorService executorService = Executors.newFixedThreadPool(16);
	
	private static boolean running = true;

	public static void main(String[] args) {
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
            	if(running){
	            	executorService.shutdown();
	                System.out.println("Shutting down...");
            	}else{
            		 System.out.println("Force shutdown with abandon task count:" + executorService.shutdownNow().size());
            		if(executorService.isTerminated()){
            			System.exit(0);
            		}
            	}
            }
        });

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(80, 20);
			System.out.println("Start up!");
			while (running) {
				Socket socket = serverSocket.accept();
				Worker worker = new Worker(socket);
				System.out.println("Start>" + worker.hashCode() + ">" + socket);
				executorService.execute(worker);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}