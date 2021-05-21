package com.example.knockknock.Login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private  String URL = "https://js06m13.cafe24.com/UserLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String cli_id, String cli_pw, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("cli_id",cli_id);
        parameters.put("cli_pw",cli_pw);


    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
