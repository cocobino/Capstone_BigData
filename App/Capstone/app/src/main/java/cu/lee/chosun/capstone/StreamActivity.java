package cu.lee.chosun.capstone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class StreamActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubeView;
    Button button;
    YouTubePlayer.OnInitializedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        button = (Button)findViewById(R.id.youtubebutton);

        youTubeView = (YouTubePlayerView)findViewById(R.id.youtubeView);



        //리스너 등록부분

        listener = new YouTubePlayer.OnInitializedListener(){



            //초기화 성공시

            @Override

            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.loadVideo("aR8Fe1lTKHo");//url의 맨 뒷부분 ID값만 넣으면 됨
//https://youtu.be/aR8Fe1lTKHo
            }



            @Override

            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };



        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //첫번째 인자는 API키값 두번째는 실행할 리스너객체를 넘겨줌
                youTubeView.initialize("AIzaSyCOXlNVOScN4k_YcvwA_fVkyPDUTAdMHLQ", listener);
            }
        });





    }

}