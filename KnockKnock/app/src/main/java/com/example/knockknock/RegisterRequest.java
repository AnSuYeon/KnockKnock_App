package com.example.knockknock;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private  String URL = "https://js06m13.cafe24.com/UserRegister.php";
    private Map<String, String> parameters;

    public RegisterRequest(String cli_id, String cli_pw, String cli_name, String cli_num, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("cli_id",cli_id);
        parameters.put("cli_pw",cli_pw);
        parameters.put("cli_name",cli_name);
        parameters.put("cli_num",cli_num);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
