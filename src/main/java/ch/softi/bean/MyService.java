package ch.softi.bean;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyService {

	static ServerSocket server = null;
	static OutputStreamWriter ow = null;
	
	public static void main(String[] args) {
		int port = 800;
		try {
			server = new ServerSocket(port);
			while (true) {
				Socket socket = server.accept();
			ow = new OutputStreamWriter(socket.getOutputStream());	
			ow.write("HTTP/1.0 200 ok");
			ow.write("Content-Type:text/html");
			ow.write("<html><body>");
			ow.write("hello , world");
			ow.write("</body></html>");
			ow.flush();
			ow.close();
			socket.close();
			
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
