package com.example.knockknock;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.knockknock.Login.ValidateRequest;

import org.json.JSONObject;

public class registerActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private String cli_id;
    private String cli_pw;
    private String cli_name;
    private String cli_num;
    private AlertDialog dialog;
    private boolean validate=false;
    static public String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText=(EditText) findViewById(R.id.idText);
        final EditText passwordText=(EditText) findViewById(R.id.passwordText);
        final EditText nameText=(EditText) findViewById(R.id.nameText);
        final EditText number=(EditText) findViewById(R.id.number);

        final Button validateButton=(Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String cli_id=idText.getText().toString();
                if(validate){
                    return;
                }
                if(cli_id.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity.this);
                    dialog=builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>(){

                @Override
                public void onResponse(String response){

                    try{

                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success=jsonResponse.getBoolean("success");
                        if(success){
                            AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity.this);
                            dialog=builder.setMessage("사용할 수 있는 아이디입니다.")
                                    .setPositiveButton("확인", null)
                                    .create();
                            dialog.show();
                            idText.setEnabled(false);
                            validate = true;
                            idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity.this);
                            dialog=builder.setMessage("사용할 수 없는 아이디입니다.")
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
            ValidateRequest validateRequest=new ValidateRequest(cli_id,responseListener);
            RequestQueue queue = Volley.newRequestQueue(registerActivity.this);
            queue.add(validateRequest);
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                String cli_id= idText.getText().toString();
                String cli_pw=passwordText.getText().toString();
                final String cli_name=nameText.getText().toString();
                String cli_num=number.getText().toString();

                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity.this);
                    dialog=builder.setMessage("먼저 중복 체크를 해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                if(cli_id.equals("")||cli_pw.equals("")||cli_name.equals("")||cli_num.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity.this);
                    dialog=builder.setMessage("빈 칸 없이 입력해 주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener=new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");


                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity.this);
                                dialog=builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();

                                finish();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity.this);
                                dialog=builder.setMessage("회원 등록에 실패했습니다.")
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
                RegisterRequest registerRequest=new RegisterRequest(cli_id, cli_pw, cli_name, cli_num, responseListener);
                RequestQueue queue = Volley.newRequestQueue(registerActivity.this);
                queue.add(registerRequest);
            }
        });
    }

    @Override
    protected  void onStop(){
        super.onStop();;
        if(dialog!=null){
            dialog.dismiss();
            dialog=null;
        }
    }
}
