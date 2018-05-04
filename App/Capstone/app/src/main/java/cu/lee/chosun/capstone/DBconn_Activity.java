package cu.lee.chosun.capstone;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DBconn_Activity extends Activity {

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_Bname = "Bname";
    private static final String TAG_Btype = "Btype";
    private static final String TAG_Bconsist = "Bconsist";
    private static final String TAG_Bdate = "Bdate";
    private static final String TAG_Baddr = "Baddr";
    private static final String TAG_Bcheck = "Bcheck";
    private static final String TAG_Bgrade = "Bgrade";
    private static final String TAG_BLcheck = "BLcheck";



    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbconn);

        list = (ListView)findViewById(R.id.listView1);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://192.168.0.11/PHP_connection.php");
}

    protected void showList() {
                try {
                        JSONObject jsonObj = new JSONObject(myJSON);
                        peoples = jsonObj.getJSONArray(TAG_RESULTS);

                        for (int i = 0; i < peoples.length(); i++) {
                                JSONObject c = peoples.getJSONObject(i);

                                String Bname = c.getString(TAG_Bname);
                                String Btype = c.getString(TAG_Btype);
                                String Bconsist = c.getString(TAG_Bconsist);
                                String Bdate = c.getString(TAG_Bdate);
                                String Baddr = c.getString(TAG_Baddr);
                                String Bcheck = c.getString(TAG_Bcheck);
                                String Bgrade = c.getString(TAG_Bgrade);
                                String BLcheck = c.getString(TAG_BLcheck);

                                HashMap<String, String> persons = new HashMap<String, String>();

                            persons.put(TAG_Bname, Bname);
                            persons.put(TAG_Btype, Btype);
                            persons.put(TAG_Bconsist, Bconsist);
                            persons.put(TAG_Bdate, Bdate);
                            persons.put(TAG_Baddr, Baddr);
                            persons.put(TAG_Bcheck, Bcheck);
                            persons.put(TAG_Bgrade, Bgrade);
                            persons.put(TAG_BLcheck, BLcheck);

                                personList.add(persons);
                            }

                        ListAdapter adapter = new SimpleAdapter(
                                        DBconn_Activity.this, personList, R.layout.list_item,
                                        new String[]{TAG_Bname, TAG_Btype, TAG_Bconsist, TAG_Bdate, TAG_Baddr, TAG_Bcheck, TAG_Bgrade, TAG_BLcheck},
                                        new int[]{R.id.Bname, R.id.Btype, R.id.Bconsist, R.id.Bdate, R.id.Bcheck, R.id.Bgrade, R.id.BLcheck}
                        );

                        list.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }




    private void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

                    @Override
            protected String doInBackground(String... params) {

                        String uri = params[0];

                        BufferedReader bufferedReader = null;
                        try {
                            URL url = new URL(uri);
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            StringBuilder sb = new StringBuilder();

                            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                            String json;
                            while ((json = bufferedReader.readLine()) != null) {
                                sb.append(json + "\n");
                            }

                            return sb.toString().trim();

                        } catch (Exception e) {
                            return null;
                        }
                    }

            @Override

            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);

    }



}


