package com.example.knockknock.Seat;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knockknock.Count.headcount;
import com.example.knockknock.Count.teamCount;
import com.example.knockknock.Login.LoginActivity;
import com.example.knockknock.Mypage;
import com.example.knockknock.R;
import com.example.knockknock.Restaurant_Click;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class waitingActivity extends AppCompatActivity {


    public static int sequence = 0;
    private EditText edtWait;
    private Button insertBtn;
    private TextView waitingteamText;
    String res_code5;
    String cli_id3;
    int count =0;
    private static String TAG = "waiting_php";

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_waiting);
        //사용할 엑티비티 선언
//        if (android.os.Build.VERSION.SDK_INT > 9) { //oncreate 에서 바로 쓰레드돌릴려고 임시방편으로 넣어둔소스
//
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//            StrictMode.setThreadPolicy(policy);
//
//        }

        cli_id3 =  LoginActivity.id;
        res_code5 = Restaurant_Click.res_code;

        edtWait = (EditText)findViewById(R.id.waitngPeople) ;
        insertBtn = (Button)findViewById(R.id.waitingWriteBtn);
        waitingteamText = (TextView)findViewById(R.id.waitintgTeam);


        String url_teamcount = "https://js06m13.cafe24.com/waitingCount.php";
        waitingteamCount selectDB_teamcount = new waitingteamCount(url_teamcount,null);
        selectDB_teamcount.execute(); //AsynTask 실행






        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtWait.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "인원을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    InsertData task = new InsertData();
                    task.execute("https://js06m13.cafe24.com/waitingInsert.php", cli_id3, res_code5, edtWait.getText().toString());
//                    Intent intent = new Intent(getApplicationContext(), Restaurant_Click.class);
//                    startActivity(intent);
                }

            }
        });

    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(waitingActivity.this, "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            //Log.d("Tag : ", s); // php에서 가져온 값을 최종 출력함
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                String edt1Text = (String) params[1];
                String edt2Text = (String) params[2];
                String edt3Text = (String) params[3];

                String link= (String) params[0];
                String data = URLEncoder.encode("cli_id3", "UTF-8") + "=" + URLEncoder.encode(edt1Text, "UTF-8");
                data += "&" + URLEncoder.encode("res_code5", "UTF-8") + "=" + URLEncoder.encode(edt2Text, "UTF-8");
                data += "&" + URLEncoder.encode("people", "UTF-8") + "=" + URLEncoder.encode(edt3Text, "UTF-8");

                Log.d("resTag : ", edt2Text);
                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
                outputStreamWriter.write(data);
                outputStreamWriter.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                Log.d("tag : ", sb.toString()); // php에서 결과값을 리턴
                return sb.toString();

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }
    }


    class waitingteamCount extends AsyncTask<Void,Void,String> {
        private String url;
        private ContentValues values;
        String rs;//결과저장 변수

        public waitingteamCount(String url1, ContentValues values1){
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
            doJSONParser_teamcount(s); //파서로 전체 출력
        }
    }


    public void doJSONParser_teamcount(String str){
        try{
            String result = "";
            JSONObject jsonObject = new JSONObject(str);
            //jsonarray에 headcount key값을 저장
            JSONArray jsonArray = jsonObject.getJSONArray("waitingCount");

            ArrayList<teamCount> h_list = new ArrayList<teamCount>();

            for(int i=0 ; i<jsonArray.length();i++){
                //values값 저장
                JSONObject output = jsonArray.getJSONObject(i);
                teamCount tc = new teamCount();
                tc.setCli_code3(output.getString("cli_id3"));
                tc.setRes_code5(output.getString("res_code5"));
                tc.setPeople(output.getString("people"));
                h_list.add(tc);
            }

            for(int j=0 ; j<jsonArray.length();j++){
                if (h_list.get(j).getRes_code().equals(res_code5)) {
                    count++;
                }
            }

            waitingteamText.setText(Integer.toString(count));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}