/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates

 * and open the template in the editor.
 */
package ipwire;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import connection.RS232;
import connection.TestServerSocket;

/**
 *
 * @author HP
 */
public class IpWire {

    /**
     * @param args the command line arguments
     */
    //default debug mode true;
    static Boolean sdebuggmode = true;
    
    public static void main(String[] args) {
        // TODO code application logic 

        //bool to configure prints on console on debugger mode

        Scanner sn = new Scanner(System.in);
        
        String us="User:";
        print(us, Boolean.FALSE);
        
        String usuario = sn.nextLine();
        
        us="Password:";
        print(us, Boolean.FALSE);
         
        String password = sn.nextLine();  
        
        us="Lang:[ES, EN]";
        print(us, Boolean.FALSE);
         
        //languaje ES ESPAÑOL, EN ENGLISH
        String languaje = sn.nextLine();  
        
        if (languaje.equals("ES")|| languaje.equals("EN")){
         
        print("\033[H\033[2J", Boolean.FALSE);
           
        Connection conn = null;
        
        try{
         conn = DriverManager.getConnection("jdbc:h2:~/connstation", usuario, password);
        // add application code here
        if (!conn.isClosed()){
        
        String s = " _______               __          __   __              \n" +
"|_     _|.-----.-----.|  |_.---.-.|  |_|__|.-----.-----.\n" +
" _|   |_ |     |__ --||   _|  _  ||   _|  ||  _  |     |\n" +
"|_______||__|__|_____||____|___._||____|__||_____|__|__|\n" +
"                                                        \n" +
"";  
        
        List listranslations = null;
        
        try {
            print(s, Boolean.FALSE);
            
            listranslations = getSystemStringProperties(languaje);

            } catch (Exception ex) {
                print(ex.getLocalizedMessage(), Boolean.TRUE);
            }
        
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
 
        while (!salir) {
 
            print("0. "+listranslations.get(0), Boolean.FALSE);
            print("1. "+listranslations.get(1), Boolean.FALSE);
            print("2. "+listranslations.get(2), Boolean.FALSE);
            print("3. "+listranslations.get(3), Boolean.FALSE);
            print("4. "+listranslations.get(4), Boolean.FALSE);
            print("5. "+listranslations.get(5), Boolean.FALSE);
            print("6. "+listranslations.get(6), Boolean.FALSE);
            print("7. "+listranslations.get(7), Boolean.FALSE);
            print("8. "+listranslations.get(8), Boolean.FALSE);
            print("9. "+listranslations.get(9), Boolean.TRUE);
 
            try {
 
   
                opcion = sn.nextInt();
 
                switch (opcion) {
                    case 0:
                        Properties prop = new Properties();
                        
                        try (InputStream input = new FileInputStream("config.properties")) {

                               // load a properties file
                               prop.load(input);

                               // get the property value and print it out
                               
                               print(prop.getProperty("db.url"), Boolean.FALSE);
                               print(prop.getProperty("db.instation"), Boolean.FALSE);
                               print(prop.getProperty("db.version"), Boolean.TRUE);

                           } catch (IOException ex) {
                            print(ex.getLocalizedMessage(), Boolean.TRUE);
                           }  
                        
                        Statement st = conn.createStatement();
                        
                        try{
                            
                        String createhandler = prop.getProperty("db.createhandler");
                        Boolean rt = st.execute(createhandler);
                        
                        print(String.valueOf(listranslations.get(11))+rt, Boolean.FALSE);
                        
                        }catch(Exception e){
                            print(e.getLocalizedMessage(), Boolean.TRUE);
                        }
                        
                        try{
                            
                        String createscript=prop.getProperty("db.createscript");    
                        Boolean rt = st.execute(createscript);
                        
                        print(String.valueOf(listranslations.get(12))+rt, Boolean.FALSE);
                        
                        }catch(Exception e){
                        print(e.getLocalizedMessage(), Boolean.TRUE);
                        }
                        
                        try{
                        
                        String createsystem=prop.getProperty("db.createsystem");    
                        
                        Boolean rt = st.execute(createsystem);
                        
                        print(String.valueOf(listranslations.get(13))+rt, Boolean.TRUE);
                        
                        }catch(Exception e){
                            print(e.getLocalizedMessage(), Boolean.TRUE);
                        }
    
                        break;
                    case 1:
                        Scanner sn1 = new Scanner(System.in);

                        String us1 = "Name:[String]";
                        print(us1, Boolean.FALSE);
                        String name = sn1.nextLine();

                        String us2 = "Type:[Number 1 Web Socket - 2 RS232]";
                        print(us2, Boolean.FALSE);
                        Integer type = Integer.valueOf(sn1.nextLine());
                        
                        String us3 = "Instrument:";
                        print(us3, Boolean.FALSE);
                        String instrument = sn1.nextLine();
                        
                        String us4 = "URL:";
                        print(us4, Boolean.FALSE);
                        String url1 = sn1.nextLine();
                        
                        String us5 = "Port:";
                        print(us5, Boolean.FALSE);
                        String port = sn1.nextLine();
                        
                        String us6 = "LogFile (URL):";
                        print(us6, Boolean.FALSE);
                        String logfile = sn1.nextLine();
                        
                        Integer baud = 0;
                        Integer databit = 0;
                        Integer stopbit = 0;
                        String parity = "";
                        String flowcontrol = "";
                        Integer timeout = 0;
                                
                        if(type==2){
                            
                        String us7 = "Bauds(bit/s):[50,75,110,134.5,150,300,600,1200,1800,2400,4800,7200,9600,14400,19200,38400,56000,57600,76800,115200,128000,23400,256000,460800]";
                        
                        print(us7, Boolean.FALSE);
                        baud = Integer.valueOf(sn1.nextLine());
                        
                        String us8 = "DataBit:[5,6,7,8,9]";
                        print(us8, Boolean.FALSE);
                        databit = Integer.valueOf(sn1.nextLine());
                        
                        String us9 = "StopBit:[1,2]";
                        print(us9, Boolean.FALSE);
                        stopbit = Integer.valueOf(sn1.nextLine());
                        
                        String us10 = "Parity:[None(0), Odd(1), Even(2), Mark(3), Space(4)]";
                        print(us10, Boolean.FALSE);
                        parity = String.valueOf(sn1.nextLine());
                        
                        String us11 = "FlowControl:[Xon,Xoff]";
                        print(us11, Boolean.FALSE);
                        flowcontrol = String.valueOf(sn1.nextLine());
                        
                        String us12 = "Timeout:[Number Seconds]";
                        print(us12, Boolean.FALSE);
                        timeout = Integer.valueOf(sn1.nextLine());
                        }
                        
                        try{
                        
                        switch (type) {
                            //web socket
                             
                                
                            case 1:
                                {
                                    String sqlstring0 = "INSERT INTO HANDLER (NAME, TYPE, INSTRUMENT, URL, PORT, LOGFILE) VALUES (?, ?, ?, ?, ?, ?)";
                                    
                                    PreparedStatement st1 = conn.prepareStatement(sqlstring0);
                                    
                                    st1.setString(1, name);
                                    st1.setInt(2, type);
                                    st1.setString(3, instrument);
                                    st1.setString(4, url1);
                                    st1.setString(5, port);
                                    st1.setString(6, logfile);
                                    
                                    Boolean numrec = st1.execute();
                                    
                                    print(String.valueOf(listranslations.get(19)) + numrec, Boolean.TRUE);
                                   
                                    break;
                                }
                            //rs232 instrument handler
                            
                            case 2:
                                {
                                    //String sqlstring0 = "INSERT INTO HANDLER (NAME, TYPE, INSTRUMENT, URL, PORT, BAUD, DATABIT, STOPBIT, PARITY) VALUES ('" + name + "','" + type + "','" + instrument + "','" + url1 + "','" + port + "','" + baud + "','" + databit + "','" + stopbit + "','" + parity + "')";
                                    String sqlstring0 = "INSERT INTO HANDLER (NAME, TYPE, INSTRUMENT, URL, PORT, LOGFILE, BAUD, DATABIT, STOPBIT, PARITY, FLOWCONTROL, TIMEOUT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                                    
                                    PreparedStatement st1 = conn.prepareStatement(sqlstring0);
                                    
                                    st1.setString(1, name);
                                    st1.setInt(2, type);
                                    st1.setString(3, instrument);
                                    st1.setString(4, url1);
                                    st1.setString(5, port);
                                    st1.setString(6, logfile);
                                    st1.setInt(7, baud);
                                    st1.setInt(8, databit);
                                    st1.setInt(9, stopbit);
                                    st1.setString(10, parity);
                                    st1.setString(11, flowcontrol);
                                    st1.setInt(12, timeout);
                                    
                                    Boolean numrec = st1.execute();
                                    
                                    print(String.valueOf(listranslations.get(19)) + numrec, Boolean.TRUE);
                                    
                                    break;
                                }
                        }
                            
                        }catch(Exception e){
                            print(e.getLocalizedMessage(), Boolean.TRUE);
                        }
                        break;

                    case 2:
                        
                        try{
                            
                        String sqlstring0 = "select * from HANDLER ";
                        Statement st1 = conn.createStatement();
                        
                        ResultSet rs = st1.executeQuery(sqlstring0);
                     
                        ResultSetMetaData metadata = rs.getMetaData();
                        int columnCount = metadata.getColumnCount();    
                        
                        while (rs.next()) {
                            String row = "";
                            for (int i = 1; i <= columnCount; i++) {
                                row += rs.getString(i) + ", ";          
                            }
                            print(row, Boolean.FALSE);
                        }
                    
                        print(String.valueOf(listranslations.get(18))+ rs, Boolean.TRUE);

                        }catch(Exception e){
                        print(e.getLocalizedMessage(), Boolean.FALSE);
                        }
  
                        break;
                    case 3:
                        Scanner sn2 = new Scanner(System.in);
                        
                        String us7 = String.valueOf(listranslations.get(17));
                        print(us7, Boolean.FALSE);
                        Integer instrumentID = Integer.valueOf(sn2.nextLine());
                        
                        String sqlstring0 = "SELECT * FROM HANDLER WHERE ID = ?";
                        PreparedStatement st1 = conn.prepareStatement(sqlstring0);
                        st1.setInt(1, instrumentID);
                        
                        ResultSet rs = st1.executeQuery();
                        
                        if(rs.next()){
                           Integer typeinstrument = rs.getInt("TYPE");
                           String logFile = rs.getString("LOGFILE");
                           String url = rs.getString("URL");
                           Integer comport0 = rs.getInt("PORT");
                           
                           Integer baud0 = 0;
                           Integer databit0 = 0;
                           Integer stopbit0 = 0;
                           Integer parity0=0;
                           String flowcontrol0="";
                           Integer timeout0=0;
                           
                           if (typeinstrument==2){
                           baud0 = rs.getInt("BAUD");
                           databit0 = rs.getInt("DATABIT");
                           stopbit0 = rs.getInt("STOPBIT");
                           parity0=rs.getInt("PARITY");
                           flowcontrol0=rs.getString("FLOWCONTROL");
                           timeout0=rs.getInt("TIMEOUT");
                           }
                           
                           switch(typeinstrument){
                               case 1:
                                   
                                   String params = "result=";
                                   Thread t1 = new Thread(new TestServerSocket(comport0, logFile, url, params));
                                   t1.start();
                                   
                                   break;
                                   //to serial communication
                               case 2:
                                   String param = "result=";
                                   Thread t2 = new Thread(new RS232(comport0, logFile, url, param, baud0, databit0, stopbit0, parity0, flowcontrol0, timeout0));
                                   t2.start();
                                  
                                   break;
                           }
                        }
                        
                        break;
                    case 4:
                        Scanner sn3 = new Scanner(System.in);
                        
                        String us8 = String.valueOf(listranslations.get(17));
                        print(us8, Boolean.FALSE);
                        
                        try{
                        Integer instrumentIDD = Integer.valueOf(sn3.nextLine());
                        
                        String sqlstringd = "DELETE FROM HANDLER WHERE ID = ?";
                        PreparedStatement st2 = conn.prepareStatement(sqlstringd);
                        st2.setInt(1, instrumentIDD);
                        
                        Boolean rs1 = st2.execute();
                        
                        print(String.valueOf(listranslations.get(16))+ rs1, Boolean.TRUE);
                        }catch(Exception e){
                        print(String.valueOf(e.getLocalizedMessage()), Boolean.TRUE);
                        }
                        break;
                    case 5:
                        
                        break;
                    case 6:
                        
                        break;
                    case 7:
                        
                        break;
                    case 8:
                        
                        
                        break;
                    case 9:
                        
                        salir = true;
                        break;
                    default:
                        print(String.valueOf(listranslations.get(14)), Boolean.FALSE);
                }
            } catch (InputMismatchException e) {
                print(String.valueOf(listranslations.get(20)), Boolean.FALSE);
                sn.next();
            }
        }
        }else{
        print("access denied!", Boolean.FALSE);
        }
        
        }catch(Exception e){
        print(e.getLocalizedMessage(), Boolean.TRUE);    
        }finally{
            try{
            conn.close();
            }catch(Exception e){
                print(e.getLocalizedMessage(), Boolean.TRUE);
            }
        }
     }else{
            print("Invalid option selected:"+languaje, Boolean.FALSE);    
        }
    }

    static private void setDebuggMode(Boolean b){
    IpWire.sdebuggmode = b;
    };

    static private Boolean getDebuggMode(){
    return IpWire.sdebuggmode;
    };
    
    public static List<String> getSystemStringProperties(String languaje) {
        Properties prop = new Properties();
        
         try (InputStream input = new FileInputStream("translation_"+languaje+".properties")) {

                               prop = new Properties();

                               // load a properties file
                               prop.load(input);
         }catch(Exception e){
             
         }
                               
    // result list
    List<String> result = new LinkedList<>();

    // defining variable for assignment in loop condition part
    String value;

    // next value loading defined in condition part
    for(int i = 0; (value = prop.getProperty("menu." + i)) != null; i++) {
        result.add(value);
    }

    // return
    return result;
}
    
private static void print(String imprimir, Boolean printline){
        System.out.println(imprimir);
        
        if (printline){
        System.out.println("\n");
        }
    }
}