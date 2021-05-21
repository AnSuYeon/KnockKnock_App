package com.example.knockknock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knockknock.Count.teamCount;
import com.example.knockknock.Login.LoginActivity;
import com.example.knockknock.Seat.RequestHttpURLConnection;
import com.example.knockknock.Seat.waitingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Mypage extends AppCompatActivity {



    private TextView members;
    private TextView members2;
    private TextView members3;
    private Button wait;
    String cli_code3;
    String res_code5;
    int sequence=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        members = (TextView)findViewById(R.id.members);
        members2 = (TextView)findViewById(R.id.members2);
        members3=(TextView)findViewById(R.id.members3);
        wait = (Button)findViewById(R.id.button_wating);

        members.setText(LoginActivity.name);
        members2.setText(LoginActivity.id);
        members3.setText(LoginActivity.num);

        cli_code3 =  LoginActivity.cli_code;
        res_code5 = Restaurant_Click.res_code;


        //대기현황
        String waiting = "https://js06m13.cafe24.com/waitingCount.php";
        waitingCount selectDB_waiting = new waitingCount(waiting,null);
        selectDB_waiting.execute(); //AsynTask 실행
    }
    class waitingCount extends AsyncTask<Void,Void,String> {
        private String url;
        private ContentValues values;
        String rs;//결과저장 변수

        public waitingCount(String url1, ContentValues values1){
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

            String res_code = null;
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
                if (h_list.get(j).getCli_code3().equals(LoginActivity.id)) {
                   res_code = h_list.get(j).getRes_code();
                }
            }

            //가게별
            ArrayList <teamCount> countArrayList = new ArrayList<teamCount>();
            for(int a=0 ;a<jsonArray.length();a++){
                if (h_list.get(a).getRes_code().equals(res_code)) {
                    teamCount tc = new teamCount();
                    tc.setCli_code3(h_list.get(a).getCli_code3());
                    tc.setRes_code5(h_list.get(a).getRes_code());
                    tc.setPeople(h_list.get(a).getPeople());
                    countArrayList.add(tc);
                }
            }
            for(int a=0 ;a<countArrayList.size();a++){
                if (countArrayList.get(a).getCli_code3().equals(LoginActivity.id)) {
                    sequence = a+1;
                }
            }

            if(sequence == 0)
                wait.setText("대기명단을 작성하세요");
            else
                wait.setText(Integer.toString(sequence)+"번째 손님");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}

