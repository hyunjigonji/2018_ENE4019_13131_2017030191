package webclient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class webclient {
	public static void main(String[] args) throws IOException{
		webclient client = new webclient();
		String s;
		s = client.getWebContentByGet("http://166.104.143.225:58327/test/index.html");
		System.out.println(s);
		
		Scanner keyboard = new Scanner(System.in);
		String num = "2017030191/";
		num += keyboard.nextLine();
		
		s = client.getWebContentByPost("http://166.104.143.225:58327/test/picResult", num);
		System.out.println(s);
		
		s = client.getWebContentByPost("http://166.104.143.225:58327/test/postHandleTest", num);
		System.out.println(s);
	}
	
	public String getWebContentByGet(String urlString, final String charset, int timeout) throws IOException {
		if(urlString == null || urlString.length() == 0) {
			return null;
		}
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString : ("http://" + urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (Compatible; MSIE 6.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");;
		conn.setRequestProperty("Accept", "text/html");
		conn.setConnectTimeout(timeout);
		
		try {
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		
		InputStream input = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
		String line = null;
		StringBuffer sb = new StringBuffer();
		
		while((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		
		if(reader != null) {
			reader.close();
		}
		if(conn != null) {
			conn.disconnect();
		}
		
		return sb.toString();
	}
	
	public String getWebContentByPost(String urlString, String data, final String charset, int timeout) throws IOException {
		if(urlString == null || urlString.length() == 0) {
			return null;
		}
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString : ("http://" + urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		
		connection.setRequestProperty("Content-Type", "test/xml;charset=UTF-8");
		
		//connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows vista");
		connection.setRequestProperty("User-Agent", "2017030191/Hyunji Lee/WEBCLIENT/COMPUTERNETWORK");
		
		connection.setRequestProperty("Accept", "text/xml");
		connection.setConnectTimeout(timeout);
		connection.connect();
		
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		
		byte[] content = data.getBytes("UTF-8");
		
		out.write(content);
		out.flush();
		out.close();
		
		try {
			if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
		String line;
		StringBuffer sb = new StringBuffer();
		
		while((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		if(reader != null) {
			reader.close();
		}
		if(connection != null) {
			connection.disconnect();
		}
		return sb.toString();
	}
	
	public String getWebContentByPost(String urlString, String data) throws IOException{
		return getWebContentByPost(urlString, data, "UTF-8", 5000);
	}
	
	public String getWebContentByGet(String urlString) throws IOException {
		return getWebContentByGet(urlString, "iso-8859-1", 5000);
	}
}

