package com.sd.exam_2;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.channels.AsynchronousChannelGroup;
import java.sql.*;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText fa,sa,mn;
    Button bt;
    TextView tv;
//    ArrayList<String> mnf = new ArrayList<String>();
//    ArrayList<String> mns = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fa = findViewById(R.id.txtfa);
        sa = findViewById(R.id.txtsa);
        mn = findViewById(R.id.txtmn);
        bt = findViewById(R.id.btn);
        tv = findViewById(R.id.tvc);

        new connect().execute("");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new check().execute("");
            }
        });
    }

    private class check extends AsyncTask<String,String,String>{
        int f = Integer.parseInt(fa.getText().toString());
        int s = Integer.parseInt(sa.getText().toString());
        int m = Integer.parseInt(mn.getText().toString());
        int b1,b2;
        boolean ch = false;
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection c = DBConnect.getConnection();
                String sql = "select * from money where account = " + f + "";
                ResultSet rs = c.createStatement().executeQuery(sql);

                while (rs.next()){
                    b1 = Integer.parseInt(rs.getString("balance"));
                }
                rs.close();

                String sql2 = "select * from money where account = " + s + "";
                ResultSet rs2 = c.createStatement().executeQuery(sql2);

                while(rs2.next()){
                    b2 = Integer.parseInt(rs2.getString("balance"));
                }
                rs.close();
                if(m>b1){
                    ch = false;
                }else{
                    ch=true;
                    int q1 = b1-m;
                    int q2 = b2+m;
                    String sql3 = "update money set balance = "+ q1 +" where account = " + f + "";
                    PreparedStatement stm = c.prepareStatement(sql3);

                    int r = stm.executeUpdate();

                    String sql4 = "update money set balance = "+ q2 +" where account = " + s + "";
                    PreparedStatement stm2 = c.prepareStatement(sql4);

                    int r2 = stm2.executeUpdate();


                }
            }catch (Exception ex){
                ex.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if(ch){
                tv.setText("Trading Complete");
            }else{
                tv.setText("Low Money");
            }

        }
    }

    private class connect extends AsyncTask<String,String,String> {

        String smg="";

        @Override
        protected void onPreExecute() {
            tv.setText("Connecting...");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection c = DBConnect.getConnection();
                if(c==null){
                    smg="Connection fail...";
                }else{
                    smg="Connection COMPLETE";
                }
            }catch (Exception ex){
                smg="Connection Fail";
                ex.printStackTrace();
            }
            return smg;
        }

        @Override
        protected void onPostExecute(String s) {
            tv.setText(s);
        }
    }
}