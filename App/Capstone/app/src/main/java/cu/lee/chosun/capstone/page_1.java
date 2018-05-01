package cu.lee.chosun.capstone;

/**
 * Created by Lee on 2018-05-01.
 */
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Lee on 2017-11-13.
 */
public class page_1 extends android.support.v4.app.Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.page1,container,false);
        LinearLayout background=(LinearLayout)linearLayout.findViewById(R.id.backgound1);//원하는 이미지

        return linearLayout;
    }
}