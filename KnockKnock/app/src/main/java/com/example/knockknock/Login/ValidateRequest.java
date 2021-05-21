package com.example.knockknock.Login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    final static private  String URL = "https://js06m13.cafe24.com/UserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String cli_id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("cli_id",cli_id);


    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
