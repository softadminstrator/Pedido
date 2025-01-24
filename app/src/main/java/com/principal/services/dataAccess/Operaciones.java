package com.principal.services.dataAccess;

import org.json.JSONObject;


public class Operaciones {

    Api api;
    static String res="";

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
