package com.principal.mundo.wsstarlap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LLamarVendedores {


    public String  GetVendedores() {
        String resultado="";
        try
        {

        //URL postUrl = new URL("https://192.168.20.246:44309/api/Finance/GetVendedoresWS");
            URL postUrl = new URL("http://192.168.20.246:90/api/Finance/GetVendedoresWS");
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjMzNTdFQjAwMzBGMDRFQjAiLCJuYmYiOjE2Nzg4MjU0NzgsImV4cCI6MTY3ODg2NTA3OCwiaWF0IjoxNjc4ODI1NDc4LCJpc3MiOiJodHRwczovL2xvY2FsaG9zdDo0NDMwOS8iLCJhdWQiOiJodHRwczovL2xvY2FsaG9zdDo0NDMwOS8ifQ.QABnGGLLjs8CTZf_ocMhq6xwASIvuwxqpJo5UiU9gjc";
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
            connection.setRequestMethod("POST");

// Setting Header Parameters
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Accept", "application/json");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

// Adding Body payload for POST request
            String params = "{\n" +
                    "    _idWeb:\"140\"\n" +
                    "}";

            OutputStreamWriter  wr = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            wr.write(params);
            wr.close();

            connection.connect();

// Getting Response
            int statusCode = connection.getResponseCode();

// Checking http status code for 201 (Created)
            if (statusCode == HttpURLConnection.HTTP_OK) {
                StringBuffer jsonResponseData = new StringBuffer();
                String readLine = null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((readLine = bufferedReader.readLine()) != null) {
                    jsonResponseData.append(readLine + "\n");
                }

                bufferedReader.close();
                resultado=jsonResponseData.toString();
                //JSONObject jsonObj = new JSONObject(resultado.toString());
                JSONArray arr = new JSONArray(resultado.toString());
                System.out.println(jsonResponseData.toString());

            } else {
                System.out.println(statusCode);
            }










        }
        catch (Exception e )
        {
            resultado=e.toString();
        }
        return resultado;
    }

}
