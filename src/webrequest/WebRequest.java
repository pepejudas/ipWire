package webrequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebRequest {

	private String USER_AGENT = "Mozilla/5.0";

	private String GET_URL = "http://localhost:9090/SpringMVCExample";

	private String POST_PARAMS = "userName=Pankaj";

        private String RESPONSE = "";
        /*
    public WebRequest(String string, String string0, String string1, Boolean TRUE) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        */
        
        private void setUserAgent(String userAgent){
            this.USER_AGENT=userAgent;
        }
        
        private void setURL(String url){
            this.GET_URL=url;
        }       
        
        private void setParams(String params){
            this.POST_PARAMS=params;
        }
        
        private void setResponse(String response){
            this.RESPONSE = response;
        }

        public String getResponse(){
            return this.RESPONSE;
        }
        
        private String getUserAgent(){
            return this.USER_AGENT;
        }
        
        private String getURL(){
            return this.GET_URL;
        }       
        
        private String getParams(){
            return this.POST_PARAMS;
        }

        /**
         * 
         * @param userAgent
         * @param URL
         * @param params
         * @param postRequest: Boolean to define if the request is type get or post, if true post if false get
         * @return 
         * @throws java.io.IOException
         */
        public WebRequest(String URL, String params, Boolean postRequest) throws IOException{
            setUserAgent("Mozilla/5.0");
            setURL(URL);
            setParams(params);
            String response = "";
            
            if (postRequest){
                setResponse(sendPOST());
            }else{
                setResponse(sendGET());
            }
           
        }
        
	private String sendGET() throws IOException {
		URL obj = new URL(this.getURL());
                String responseReq = "";
                
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", this.getUserAgent());
		int responseCode = con.getResponseCode();
                responseReq = "GET Response Code :: " + responseCode;
                
		if (responseCode == HttpURLConnection.HTTP_OK) { 			
                    StringBuilder response;
                    try ( // success
                            BufferedReader in = new BufferedReader(new InputStreamReader(
                                    con.getInputStream()))) {
                        String inputLine;
                        response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
                    }

			// print result
			responseReq += response.toString();
		} else {
			responseReq += "GET request not worked";
		}
                
        return responseReq;
	}

	private String sendPOST() throws IOException {
		URL obj = new URL(this.getURL());
                String responseReq = "";
                
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", this.getUserAgent());

		// For POST only - START
		con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                os.write(this.getParams().getBytes());
                os.flush();
                os.close();
                // For POST only - END
            }

		int responseCode = con.getResponseCode();
		responseReq = "POST Response Code :: " + responseCode;

		if (responseCode == HttpURLConnection.HTTP_OK) { 			StringBuilder response;
                    try ( //success
                            BufferedReader in = new BufferedReader(new InputStreamReader(
                                    con.getInputStream()))) {
                        String inputLine;
                        response = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
                    }

			// print result
			responseReq += response.toString();
		} else {
			responseReq += "POST request not worked";
		}
                
        return responseReq;
	}

}