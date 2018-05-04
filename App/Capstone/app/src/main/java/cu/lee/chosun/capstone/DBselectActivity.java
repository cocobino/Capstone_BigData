package cu.lee.chosun.capstone;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DBselectActivity extends AppCompatActivity {

    private static String TAG = "phptest_DBselectActivity";

    private static final String TAG_JSON="webnautes";
    private static final String TAG_Bname = "Bname";
    private static final String TAG_Btype = "Btype";
    private static final String TAG_Bconsist = "Bconsist";
    private static final String TAG_Bdate = "Bdate";
    private static final String TAG_Baddr = "Baddr";
    private static final String TAG_Bcheck = "Bcheck";
    private static final String TAG_Bgrade = "Bgrade";
    private static final String TAG_BLcheck = "BLcheck";

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbselect);


        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mlistView = (ListView) findViewById(R.id.listView_main_list);
        mArrayList = new ArrayList<>();

        GetData task = new GetData();
        task.execute("http://192.168.0.11/getjson.php");


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Button searchBtn = (Button)findViewById(R.id.searchBtn);
        /*searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(DBselectActivity.this, SearchActivity.class);
                startActivity(it);
            }
        });*/
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.search_actionbar, null);

        actionBar.setCustomView(actionbar);

        //액션바 양쪽 공백 없애기
        android.support.v7.widget.Toolbar parent = (android.support.v7.widget.Toolbar) actionbar.getParent();
        parent.setContentInsetsAbsolute(0,0);

        return true;
    }




    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(DBselectActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);


                String Bname = item.getString(TAG_Bname);
                String Btype = item.getString(TAG_Btype);
                String Bconsist = item.getString(TAG_Bconsist);
                String Bdate = item.getString(TAG_Bdate);
                String Baddr = item.getString(TAG_Baddr);
                String Bcheck = item.getString(TAG_Bcheck);
                String Bgrade = item.getString(TAG_Bgrade);
                String BLcheck = item.getString(TAG_BLcheck);

                HashMap<String,String> hashMap = new HashMap<String,String>();

                hashMap.put(TAG_Bname, Bname);
                hashMap.put(TAG_Btype, Btype);
                hashMap.put(TAG_Bconsist, Bconsist);
                hashMap.put(TAG_Bdate, Bdate);
                hashMap.put(TAG_Baddr, Baddr);
                hashMap.put(TAG_Bcheck, Bcheck);
                hashMap.put(TAG_Bgrade, Bgrade);
                hashMap.put(TAG_BLcheck, BLcheck);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    DBselectActivity.this, mArrayList, R.layout.list_item,
                    new String[]{TAG_Bname, TAG_Btype, TAG_Bconsist, TAG_Bdate, TAG_Baddr, TAG_Bcheck, TAG_Bgrade, TAG_BLcheck},
                    new int[]{R.id.Bname, R.id.Btype, R.id.Bconsist, R.id.Bdate, R.id.Bcheck, R.id.Bgrade, R.id.BLcheck}
            );

            mlistView.setAdapter(adapter);
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }

    }

}