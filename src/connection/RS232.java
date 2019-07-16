/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

    Necesario poner la libreria win32com.dll en la carpeta lib del jre
requires jre 32bits to load win32com.dll library

 */
package connection;

import static connection.TestServerSocket.getParams;
import static connection.TestServerSocket.getURL;
import java.io.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.comm.*;
import webrequest.WebRequest;

public class RS232 implements Runnable, SerialPortEventListener {
    static CommPortIdentifier portId;
    static Enumeration portList;

    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;
    static Logger logger;

    static Integer portNumber = 0;
    static Integer baud = 0;
    static Integer dataBit = 0;
    static Integer stopBit = 0;
    static Integer parity = 0;
    static String flowControl = "";
    static Integer timeOut = 0;
    static String logfile="";
    static String url="";
    static String param="";
    
    /*
    st1.setString(5, port);
                                    st1.setString(6, logfile);
                                    st1.setInt(7, baud);
                                    st1.setInt(8, databit);
                                    st1.setInt(9, stopbit);
                                    st1.setString(10, parity);
                                    st1.setString(11, flowcontrol);
                                    st1.setInt(12, timeout);
    */
    private static void setPort(Integer portNumber){
        RS232.portNumber=portNumber;
    }
    private static void setBaud(Integer baud){
        RS232.baud=baud;
    }
    private static void setDataBit(Integer dataBit){
        RS232.dataBit = dataBit;
    }
    private static void setStopBit(Integer stopBit){
        RS232.stopBit=stopBit;
    }
    private static void setParity(Integer parity){
        RS232.parity=parity;
    }
    private static void setFlowControl(String flowControl){
        RS232.flowControl=flowControl;
    }
    private static void setTimeOut(Integer timeOut){
        RS232.timeOut=timeOut;
    }
    public static String getParams(){
        return RS232.param;  
    }
    public static Logger getLogger(){
        return RS232.logger;  
    }
    public static String getURL(){
        return RS232.url;  
    }

    private static Integer getPort(){
        return RS232.portNumber;
    }
    
    public static String getLogFile(){
        return RS232.logfile;  
    }

    private static void setURL(String url){
        RS232.url = url;
    }

    private static void setLogFile(String logfile){
        RS232.logfile = logfile;
    }
    
    private static void setParams(String params){
        RS232.param = params;
    }
        
    private static Integer getBaud(){
        return RS232.baud;
    }
    private static Integer getDataBit(){
        return RS232.dataBit ;
    }
    private static Integer getStopBit(){
        return RS232.stopBit;
    }
    private static Integer getParity(){
        return RS232.parity;
    }
    private static String getFlowControl(){
        return RS232.flowControl;
    }
    private static Integer getTimeOut(){
        return RS232.timeOut;
    }
    
    public RS232(Integer comport, String logfile, String url, String param, Integer baud, Integer databit, Integer stopbit, Integer parity, String flowcontrol, Integer timeout) {
     
    setLogFile(logfile);
    setURL(url);
    setParams(param);
    setPort(comport);
    setBaud(baud);
    setDataBit(databit);
    setStopBit(stopbit);
    setParity(parity);
    setFlowControl(flowcontrol);
    setTimeOut(timeout);
    
        portList = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier port = null;
                
        try
         {    
         portId = CommPortIdentifier.getPortIdentifier("COM"+RS232.getPort());
         }
         catch(Exception e)
         {
              System.out.println("ERROR " +e);
         }
        
        /*
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == RS232.getPort()) {
                
                 //String comport = "COM1";
                 if (portId.getName().equals("COM"+RS232.getPort())) {
                     //query the database tables definition from instrument and load the parameter from connection using the idInstrument
                     
                     //Integer timeout = 2000;
          */           
                     try {
                         serialPort = (SerialPort) portId.open("SimpleReadApp", RS232.getTimeOut());
                         //RS232 reader = new RS232(2000, );
                     } catch (PortInUseException ex) {
                         Logger.getLogger(RS232.class.getName()).log(Level.SEVERE, null, ex);
                     }
                     /*
                }
            }
        }
        */
    }

    @Override
    public void run() {
        logger = null;
                
                try {  
                            logger = Logger.getLogger("MyLog");  
                            FileHandler fh;  

                            // This block configure the logger with handler and formatter  
                            fh = new FileHandler(RS232.getLogFile());  
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
                
                
        try {
            serialPort = (SerialPort) portId.open("SimpleReadApp", RS232.getPort());
        } catch (PortInUseException e) {
            System.out.println(e);
            //serialPort.close();
        }
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
            System.out.println(e);
            serialPort.close();
        }
	try {
            serialPort.addEventListener(this);
	} catch (TooManyListenersException e) {
            System.out.println(e);
            serialPort.close();
        }
        serialPort.notifyOnDataAvailable(true);
        try {
            /*
            serialPort.setSerialPortParams(9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
            */
            serialPort.setSerialPortParams(
                RS232.getBaud(),//9600
                RS232.getDataBit(),//SerialPort.DATABITS_8,
                RS232.getStopBit(),//SerialPort.STOPBITS_1,
                RS232.getParity() //SerialPort.PARITY_NONE);
            ); 
        } catch (UnsupportedCommOperationException e) {
            System.out.println(e);
            serialPort.close();
        }
        
    }

    public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[20];

            try {
                
                while (inputStream.available() > 0) {
                    int numBytes = inputStream.read(readBuffer);
                }
                
                String texto = new String(readBuffer);
                System.out.print(texto);
                
                try{
                                        
                            String textoN = texto.replace("\n", "").replace("\r", "");
                            WebRequest wr = new WebRequest(getURL(), getParams()+textoN, Boolean.TRUE);
                            getLogger().info(wr.getResponse());
                            
                }catch(Exception e){
                    System.out.println(e.getLocalizedMessage());
                }
                
            } catch (IOException e) {System.out.println(e);}
            break;
        }
    }
}