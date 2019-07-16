package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TestClientSocket {

	public static void main(String args[]) throws IOException {
		final String host = "localhost";
                System.out.print("Insert portNumber");
		Scanner in = new Scanner(System.in);     
                Integer portNumber = Integer.valueOf(in.next());
          
                //final int portNumber = 84;
		System.out.println("Creating socket to '" + host + "' on port " + portNumber);
                Integer numreads = 0;
                
		while (true) {
			Socket socket = new Socket(host, portNumber);
                        
                        InputStreamReader isr = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(isr);
                        
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                        numreads+=1;
                        Long dt = System.currentTimeMillis();
                        
			System.out.println(numreads+":"+dt+" server says:" + br.readLine());

			BufferedReader userInputBR = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                        //Scanner sc = new Scanner(System.in, "UTF-8");
                        
                        String userInput = userInputBR.readLine();
			//String userInput = new String(System.in, "UTF8");
                        
                        //String s = new String(bytes, "UTF-8");

			out.println(userInput);

			System.out.println(numreads+":"+dt+"server says:" + br.readLine());

			if ("exit".equalsIgnoreCase(userInput)) {
				socket.close();
				break;
			}
		}
	}
}