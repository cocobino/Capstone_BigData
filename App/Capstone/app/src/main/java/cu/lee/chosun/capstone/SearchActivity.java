package cu.lee.chosun.capstone;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    private static String TAG = "capstone";

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
    ListView mListViewList;
    EditText mEditTextSearchKeyword;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mListViewList = (ListView) findViewById(R.id.listView_main_list1);
        mEditTextSearchKeyword = (EditText) findViewById(R.id.editText_main_searchKeyword);


        Button button_search = (Button) findViewById(R.id.button_main_search);
        button_search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mArrayList.clear();

                GetData task = new GetData();
                task.execute( mEditTextSearchKeyword.getText().toString());
            }
        });
        mArrayList = new ArrayList<>();
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;


        @Override

        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = ProgressDialog.show(SearchActivity.this,
                    "Please Wait", null, true, true);

        }



        @Override

        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

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

            String searchKeyword = params[0];
            String serverURL = "http://192.168.0.11/query.php";
            String postParameters = "Bname=" + searchKeyword;


            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();



                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


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
                    SearchActivity.this, mArrayList, R.layout.list_item,
                    new String[]{TAG_Bname, TAG_Btype, TAG_Bconsist, TAG_Bdate, TAG_Baddr, TAG_Bcheck, TAG_Bgrade, TAG_BLcheck},
                    new int[]{R.id.Bname, R.id.Btype, R.id.Bconsist, R.id.Bdate, R.id.Bcheck, R.id.Bgrade, R.id.BLcheck}
            );

            mListViewList.setAdapter(adapter);
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);

        }


    }


}