package com.example.knockknock;

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

import com.example.knockknock.Count.headcount;
import com.example.knockknock.Count.tablecount;
import com.example.knockknock.Seat.RequestHttpURLConnection;
import com.example.knockknock.Seat.waitingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Restaurant_Click extends AppCompatActivity {

    private TextView txtView1,txtView2,txtView3,txtView4;
    private Button waitBtn_Intent,insertBtn;

    private EditText edtWait;
    static public String res_code;


    int headcount_1 =0;
    int headcount_2 =0;
    int headcount_4 =0;
    int headcount_8 =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant__click);

        TextView rest_name=(TextView)findViewById(R.id.rest_name);
        TextView restuarntgps=(TextView)findViewById(R.id.restuarntgps);
        TextView restuarnatnum=(TextView)findViewById(R.id.restuarnatnum);
        TextView restuarnattime=(TextView)findViewById(R.id.restuarnattime);


        Intent intent=getIntent();
        rest_name.setText(intent.getStringExtra("rest_name"));
        restuarntgps.setText(intent.getStringExtra("restuarntgps"));
        restuarnatnum.setText(intent.getStringExtra("restuarnatnum"));
        restuarnattime.setText(intent.getStringExtra("restuarnattime"));
        res_code = intent.getStringExtra("restuarnatcode");



        //사용할 엑티비티 선언
        waitBtn_Intent = (Button)findViewById(R.id.waitingBtn);
        waitBtn_Intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), waitingActivity.class);
                startActivity(intent);
            }
        });

        String url_headcount = "https://js06m13.cafe24.com/headcount1.php";
        headcountDB selectDB_headcount = new headcountDB(url_headcount,null);
        selectDB_headcount.execute(); //AsynTask 실행


        String url_table = "https://js06m13.cafe24.com/tablecount.php";
        tablecountDB selectDB_table = new tablecountDB(url_table,null);
        selectDB_table.execute(); //AsynTask 실행

    }

    class headcountDB extends AsyncTask<Void,Void,String> {
        private String url;
        private ContentValues values;
        String rs;//결과저장 변수

        public headcountDB(String url1, ContentValues values1){
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
            doJSONParser_headcount(s); //파서로 전체 출력
        }
    }


    public void doJSONParser_headcount(String str){
        try{
            String result = "";
            JSONObject jsonObject = new JSONObject(str);
            //jsonarray에 headcount key값을 저장
            JSONArray jsonArray = jsonObject.getJSONArray("tablemenu");

            ArrayList<headcount> h_list = new ArrayList<headcount>();

            for(int i=0 ; i<jsonArray.length();i++){
                //values값 저장
                JSONObject output = jsonArray.getJSONObject(i);
                headcount hc = new headcount();
                hc.setHeadcount(output.getString("headcount"));
                hc.setRes_code(output.getString("res_code8"));
                h_list.add(hc);
            }

            for(int j=0 ; j<jsonArray.length();j++){
                if (h_list.get(j).getRes_code().equals(res_code)) {
                    if(h_list.get(j).getHeadcount().equals("1"))
                        headcount_1++;
                    else if(h_list.get(j).getHeadcount().equals("2"))
                        headcount_2++;
                    else if(h_list.get(j).getHeadcount().equals("4"))
                        headcount_4++;
                    else if(h_list.get(j).getHeadcount().equals("8"))
                        headcount_8++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    class tablecountDB extends AsyncTask<Void,Void,String>{
        private String url;
        private ContentValues values;
        String rs;//결과저장 변수
        public tablecountDB(String url1, ContentValues values1){
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
            doJSONParser_table(s); //파서로 전체 출력

        }
    }

    public void doJSONParser_table(String str) {
        try {
            String result = "";
            JSONObject jsonObject = new JSONObject(str);
            //jsonarray에 headcount key값을 저장
            JSONArray jsonArray = jsonObject.getJSONArray("tablecount");

            String alone = null;
            String two = null;
            String four = null;
            String many = null;
            ArrayList<tablecount> list = new ArrayList<tablecount>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject output = jsonArray.getJSONObject(i);
                tablecount tc = new tablecount();
                tc.setAlone(Integer.parseInt(output.getString("alone")));
                tc.setTwo(Integer.parseInt(output.getString("two")));
                tc.setFour(Integer.parseInt(output.getString("four")));
                tc.setMany(Integer.parseInt(output.getString("many")));
                tc.setRes_code(output.getString("res_code7"));
                list.add(tc);
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                if (list.get(i).getRes_code().equals(res_code)) {
                    alone = Integer.toString(list.get(i).getAlone());
                    two = Integer.toString(list.get(i).getTwo());
                    four = Integer.toString(list.get(i).getFour());
                    many = Integer.toString(list.get(i).getMany());
                }
            }
            int seat1Num = Integer.parseInt(alone) - headcount_1;
            int seat2Num = Integer.parseInt(two) - headcount_2;
            int seat4Num = Integer.parseInt(four) - headcount_4;
            int seat8Num = Integer.parseInt(many) - headcount_8;


            txtView1 = findViewById(R.id.seat1);
            txtView1.setText(Integer.toString(seat1Num));

            txtView2 = findViewById(R.id.seat2);
            txtView2.setText(Integer.toString(seat2Num));

            txtView3 = findViewById(R.id.seat4);
            txtView3.setText(Integer.toString(seat4Num));

            txtView4 = findViewById(R.id.seatMany);
            txtView4.setText(Integer.toString(seat8Num));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
