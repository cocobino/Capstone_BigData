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
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD = "address";

    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbconn);

        list = (ListView)findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://192.168.43.34/PHP_connection.php");
}

    protected void showList() {
                try {
                        JSONObject jsonObj = new JSONObject(myJSON);
                        peoples = jsonObj.getJSONArray(TAG_RESULTS);

                        for (int i = 0; i < peoples.length(); i++) {
                                JSONObject c = peoples.getJSONObject(i);
                                String id = c.getString(TAG_ID);
                                String name = c.getString(TAG_NAME);
                                String address = c.getString(TAG_ADD);

                                HashMap<String, String> persons = new HashMap<String, String>();
                                            persons.put(TAG_ID, id);
                                persons.put(TAG_NAME, name);
                                persons.put(TAG_ADD, address);

                                personList.add(persons);
                            }

                        ListAdapter adapter = new SimpleAdapter(
                                        DBconn_Activity.this, personList, R.layout.list_item,
                                        new String[]{TAG_ID, TAG_NAME, TAG_ADD},
                                        new int[]{R.id.id, R.id.name, R.id.address}
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


