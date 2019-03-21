package webserver;

import java.util.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	public static void main(String[] args) {
		try { 
			ServerSocket serverSock = new ServerSocket(6788);
					
			while(true) {
				Socket connectionSock = serverSock.accept();
				
				HttpRequest request = new HttpRequest(connectionSock);
				
				Thread thread = new Thread(request);
				thread.start();
			}
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

