package com.example.quizapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<QaModel> arrayList;
    RecyclerView recyclerView;
    Button start;
    DialogViewPager dialogViewPager ;
    private boolean twice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<>();
        //recyclerView = findViewById(R.id.recyclerView);
        start = findViewById(R.id.start);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncData syncData = new SyncData();
                syncData.execute();
//                FragmentManager ft =getSupportFragmentManager();
//                FragmentTransaction fm = ft.beginTransaction();
//                dialogViewPager.show(fm,"BEast");
    }
});

    }

    public class SyncData extends AsyncTask<String, String, String> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("LOading");
            pd.setCancelable(false);
            pd.show();
            Log.v("Connection", "pre123");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Log.v("CTest", "meet");

                Connection conn = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/quiz", "student", "student");

                //Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.9:3306/quiz","student","student");

                Log.v("CTest", "Succless");
                Statement myStat = conn.createStatement();
                ResultSet myRs = myStat.executeQuery("select * from qa");
                if (myRs != null) {
                    while (myRs.next()) {
                        arrayList.add(new QaModel(myRs.getString("Questions"), myRs.getString("Answers")));
                    }
                    Log.v("meet123",arrayList.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.v("cTest", e.getMessage() + e.fillInStackTrace());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            loadFrag();

            //QuestionAdapter myAdapter = new QuestionAdapter( MainActivity.this,arrayList);
            //recyclerView.setAdapter(myAdapter);

        }
    }

    void loadFrag()
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", arrayList);
        FragmentManager ft =getSupportFragmentManager();
        FragmentTransaction fm = ft.beginTransaction();

        dialogViewPager = new DialogViewPager();

        dialogViewPager.setArguments(bundle);

        dialogViewPager.show(fm,"BEast");
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(twice){
            Intent i=new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            System.exit(0);
        }
        //super.onBackPressed();
        twice=true;
        Toast.makeText(this, "Press Back Again to Exit.",Toast.LENGTH_SHORT).show();
        Handler h= new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                twice=false;
            }
        },3000);
    }
}
