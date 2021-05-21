package com.example.knockknock.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.knockknock.Client_Info;
import com.example.knockknock.Count.teamCount;
import com.example.knockknock.MainActivity;
import com.example.knockknock.R;
import com.example.knockknock.Seat.RequestHttpURLConnection;
import com.example.knockknock.Seat.waitingActivity;
import com.example.knockknock.registerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;

    static public String id;
    static public String num;
    static public String cli_code;
    static public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, registerActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });


        final EditText idText=(EditText) findViewById(R.id.idText);
        final EditText passwordTextText=(EditText) findViewById(R.id.passwordText);

        final Button login_Button=(Button) findViewById(R.id.login_button);

        login_Button.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                final String cli_id=idText.getText().toString();
                final String cli_pw=passwordTextText.getText().toString();

                Response.Listener<String> responseLister=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");


                            if(success){

                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(intent);

                                id=cli_id;
                                setUser task = new setUser("https://js06m13.cafe24.com/mypage.php",null);
                                task.execute();
                                finish();


                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog=builder.setMessage("로그인에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest=new LoginRequest(cli_id, cli_pw, responseLister);
                RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }

    @Override
    protected  void onStop(){
        super.onStop();
        if(dialog !=null){
            dialog.dismiss();
            dialog=null;
        }
    }


    class setUser extends AsyncTask<Void,Void,String> {
        private String url;
        private ContentValues values;
        String rs;//결과저장 변수

        public setUser(String url1, ContentValues values1){
            this.url = url1;
            this.values = values1;
        }


        @Override
        protected String doInBackground(Void... voids) {
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            rs =requestHttpURLConnection.request(url,values);
            return rs; // 여기서 바로 실행하지 않음. onPostExcute에서 실행
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //실행, 출력
            //txtView.setText(s); //파서없이 전체 출력
            doJSONParser(s); //파서로 전체 출력
        }
    }
    public void doJSONParser(String str){
        try{
            String result = "";
            JSONObject jsonObject = new JSONObject(str);
            //jsonarray에 headcount key값을 저장
            JSONArray jsonArray = jsonObject.getJSONArray("response");

            ArrayList<Client_Info> list = new ArrayList<Client_Info>();

            for(int i=0 ; i<jsonArray.length();i++){
                //values값 저장
                JSONObject output = jsonArray.getJSONObject(i);
                Client_Info hc = new Client_Info();
                hc.setCli_code(output.getString("cli_code"));
                hc.setCli_name(output.getString("cli_name"));
                hc.setCli_id(output.getString("cli_id"));
                hc.setCli_num(output.getString("cli_num"));
                list.add(hc);
            }

            for(int j=0 ; j<jsonArray.length();j++){
                if (list.get(j).getCli_id().equals(id)) {
                    name = list.get(j).getCli_name();
                    cli_code = list.get(j).getCli_code();
                    num=list.get(j).getCli_num();
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
