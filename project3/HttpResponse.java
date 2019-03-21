package ProxyServer;

import java.io.*;

public class HttpResponse {
	
	private String statusLine = "";
	private String headers = "";
	
	final static String CRLF = "\r\n";
	final static int BUF_SIZE = 8192;
	final static int MAX_OBJECT_SIZE = 100000;
	
	public byte[] body = new byte[MAX_OBJECT_SIZE];
	
	public HttpResponse(DataInputStream fromServer) {
		int length = -1;
		boolean gotStatusLine = false;
		
		try {
			BufferedReader readFromServer = new BufferedReader(new InputStreamReader(fromServer));
			String line = readFromServer.readLine();
			
			while(line.length() != 0) {
				if(!gotStatusLine) {
					statusLine = line;
					gotStatusLine = true;
				}
				else {
					headers += line + CRLF;
				}
			}
			
			if(line.startsWith("Content-Length") || line.startsWith("Content-length")){
				String[] tmp = line.split(" ");
				length = Integer.parseInt(tmp[1]);
			}
			line = readFromServer.readLine();
		}
		catch(IOException e) {
			System.out.println("Error reading headers from server: "+ e);
			return;
		}
			
		try {
			int bytesRead = 0;
			byte buf[] = new byte[BUF_SIZE];
			boolean loop = false;
			
			if(length == -1) {
				loop = true;
			}
			
			while(bytesRead < length || loop) {
				int res = fromServer.read(buf,0,BUF_SIZE);
				if(res == -1) {
					break;
				}
				
				for(int i = 0 ; i < res && (i + bytesRead) < MAX_OBJECT_SIZE ; i++) {
					body[i+bytesRead] = buf[i];
				}
				bytesRead += res;
			}
		}
		catch(IOException e) {
			System.out.println("Error reading response body: " + e);
			return;
		}
	}
	
	@Override
	public String toString() {
		String res = "";
		
		res = statusLine + CRLF;
		res += headers;
		res += CRLF;
		
		return res;
	}
}

