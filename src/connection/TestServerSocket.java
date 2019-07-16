package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import webrequest.WebRequest;

public class TestServerSocket implements Runnable{
static Integer portNumber = 0;
static String pathLogFile = "";
static String url = "";
static String params = "";
    
        private static void setPortNumber(Integer portNumber){
            TestServerSocket.portNumber = portNumber;
        }
        
        public static Integer getPortNumber(){
          return TestServerSocket.portNumber;  
        }

        public static String getURL(){
          return TestServerSocket.url;  
        }

        public static String getParams(){
          return TestServerSocket.params;  
        }

        private static void setURL(String url){
            TestServerSocket.url = url;
        }
        
        private static void setParams(String params){
            TestServerSocket.params = params;
        }
        
        private static void setPathLogFile(String pathLogFile){
            TestServerSocket.pathLogFile = pathLogFile;
        }
        
        public static String getPathLogFile(){
          return TestServerSocket.pathLogFile;  
        }

        public TestServerSocket(Integer portNumber, String logFile, String url, String params) {
                setPortNumber(portNumber);
                setPathLogFile(logFile);
                setURL(url);
                setParams(params);
        }

    @Override
    public void run() {
          
		System.out.println("Creating server socket on port " + TestServerSocket.getPortNumber());
		ServerSocket serverSocket;
                
    try {
        serverSocket = new ServerSocket(TestServerSocket.getPortNumber());    
                Integer cont = 0;
                
                Logger logger = null;
                
                        try {  
                            logger = Logger.getLogger("MyLog");  
                            FileHandler fh;  

                            // This block configure the logger with handler and formatter  
                            fh = new FileHandler(TestServerSocket.getPathLogFile());  
                            logger.addHandler(fh);
                            SimpleFormatter formatter = new SimpleFormatter();  
                            fh.setFormatter(formatter);  

                            // the following statement is used to log any messages  
                            logger.info("Starting log service...");  
                            

                        } catch (SecurityException e) {  
                            System.out.println(e.getLocalizedMessage());  
                        } catch (IOException e) {  
                            System.out.println(e.getLocalizedMessage());  
                        }  
                
		while (true) {
			Socket socket = serverSocket.accept();
                        
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);
                        
			pw.println("Opening socket port: " + TestServerSocket.getPortNumber());

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			String str = br.readLine();
                        
                          
                        
                        cont+=1;
                        Long dt = System.currentTimeMillis();
                        String cadena = cont+":"+dt+":Inbound communication: " + str;
                        pw.println(cadena);
                        
                        logger.info(cadena);
                        
			pw.close();
			socket.close();
                        
                        String response = "";
                        
                        try{

                            WebRequest wr = new WebRequest(getURL(), getParams()+str, Boolean.TRUE);
                            response = wr.getResponse();
                            
                            logger.info(response);
                            
                        }catch(Exception e){
                            System.out.println(e.getLocalizedMessage());
                        }
                           
			System.out.println(cont+":"+dt+":Response from TestServerSocket:" + response);
		}
                
                } catch (IOException ex) {
                Logger.getLogger(TestServerSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
    }

}