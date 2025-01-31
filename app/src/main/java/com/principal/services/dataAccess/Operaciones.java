package com.principal.services.dataAccess;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Operaciones {






    public  void getUsuario() throws JSONException {
        // create your json here
        UsuarioService usuario=null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Username", "facturacionmadamelili@gmail.com");
            jsonObject.put("Password", "7167462");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url("http://192.168.20.246:84/api/Account")
                .post(body)
                .build();

        Response response = null;
        try {
            //response = client.newCall(request).execute();

             client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful())
                    {
                        try
                        {
                            String jsonData = response.body().string();
                            Gson gson=new Gson();
                             UsuarioService user=gson.fromJson(jsonData,UsuarioService.class);
                            Log.i("Body res", user._NombreEmpresa);
                            getVendedores(user);

                        } catch (Exception e)
                        {
                            Log.i("Body res",e.toString());
                        }
                    }
                }
            });
            String resStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {



        } catch (Exception e) {
            Log.i("Error2****", e.getMessage());
            e.printStackTrace();
        }

    }

    public  void getVendedores(UsuarioService usuario) throws JSONException {
        // create your json here
        JSONObject jsonObject = new JSONObject();
        try {
           // jsonObject.put("Username", "facturacionmadamelili@gmail.com");
            jsonObject.put("", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = new Request.Builder()
                .url("http://192.168.20.246:84/api/Finance/GetVendedoresWS")
                .addHeader("Authorization", "Bearer " + usuario.get_token())
                .post(body)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();

            /**
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful())
                    {
                        try
                        {
                            String jsonData = response.body().string();
                            Log.i("Body res", jsonData);

                        } catch (Exception e)
                        {
                            Log.i("Body res",e.toString());
                        }
                    }
                }
            });
             */
            Log.i("Body res",response.body().string());
            //String resStr =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {



        } catch (Exception e) {
            Log.i("Error2****", e.getMessage());
            e.printStackTrace();
        }
    }
    private UsuarioService generateUsuario(JSONObject respuesta) {
        Log.i("***log",respuesta.toString());
        return null ;
    }

/**
public Operaciones ()
{
    api = RetrofitInstance.getApiService();
}

public String  llamarApi()
{

    try {

        JSONObject paramObject = new JSONObject();
        paramObject.put("Username", "facturacionmadamelili@gmail.com");
        paramObject.put("Password", "7167462");
        Call<UsuarioService> callAcount = api.Account(paramObject.toString());
        callAcount.enqueue(new Callback<UsuarioService>() {
            public void onResponse(Call<UsuarioService> call, Response<UsuarioService> response) {
                if(response.isSuccessful())
                {
                    res= response.toString();
                }
            }

            public void onFailure(Call<UsuarioService> call, Throwable throwable) {

            }
        });
    } catch (Exception e)
    {

    }
    return res;
}

**/

}
