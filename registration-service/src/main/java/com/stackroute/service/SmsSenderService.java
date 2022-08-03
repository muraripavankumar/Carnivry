package com.stackroute.service;

import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class SmsSenderService {
    public  void sendSms(String message,String number)
    {
//		System.out.println(message);
//		System.out.println(number);
        try
        {

            String apiKey="JB730DhGaUg5XwknsMxEcNSyT9LPY2efHdmQoOWZAKjpFtvqCit6zkHqjKmIl4yPdSVOJUc8EN3fDTRW";
            String sendId="FTWSMS";
            //important step...
            message= URLEncoder.encode(message, StandardCharsets.UTF_8);
            String language="english";

            String route="p";
//            String otpSmsUrl= "https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&variables_values="+message+"&route=otp&numbers="+number;
//            String bulkSmsUrl= "https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&route="+route+"&numbers="+number;

            String myUrl="https://www.fast2sms.com/dev/bulk?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;

            //sending get request using java..



            URL url=new URL(myUrl);

            HttpsURLConnection con=(HttpsURLConnection)url.openConnection();


            con.setRequestMethod("GET");

            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("cache-control", "no-cache");
            System.out.println("Wait..............");

            int code=con.getResponseCode();

            System.out.println("Response code : "+code);


            StringBuffer response=new StringBuffer();

            BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));

            while(true)
            {
                String line=br.readLine();
                if(line==null)
                {
                    break;
                }
                response.append(line);
            }

            System.out.println(response);


        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}
