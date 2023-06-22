package kr.co.nyoot.sample.nyootsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import kr.co.nyoot.sdk.core.Nyoot;

public class MainActivity extends AppCompatActivity {

    Button btn_nyoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_nyoot = findViewById(R.id.btn_nyoot);

        btn_nyoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestOfferwall();
            }
        });
    }

    public void requestOfferwall() {
        // 1. 초기화 (디바이스 정보 가져오기)
        Nyoot.init(this);

        // 2. Nyoot 사용자 설정
        Nyoot.setUserInfo("54", "gotcoin");

        // 3. Nyoot 바인딩 및 처리 결과 통지 받을 리스너 등록
        Nyoot.setListener(new Nyoot.NyootListener() {
            // 적립이 완료된 경우 호출된다.
            @Override
            public void onAccumulateSuccess() {
                Log.d("Nyoot Sample", "onAccumulateSuccess");
            }

            // nyoot 페이지가 닫힌 경우 호출된다.
            @Override
            public void onCloseOfferwall() {
                Log.d("Nyoot Sample", "onCloseOfferwall");
            }

            // nyoot 페이지 로딩이 완료된 경우 호출된다.
            @Override
            public void onLoadedOfferwall() {
                Log.d("Nyoot Sample", "onLoadedOfferwall");
            }

            // nyoot 페이지가 열린 경우 호출된다.
            @Override
            public void onStartedOfferwall() {
                Log.d("Nyoot Sample", "onStartedOfferwall");
            }

            // nyoot 로딩이 실패한 경우 호출된다.
            @Override
            public void onLoadedError(int errorCode, String errorMsg) {
                Log.d("Nyoot Sample", "failed to load. errorCode: " + errorCode + "errorMsg: " +errorMsg);
            }
        });

        // 4. Nyoot 로드
        Nyoot.showOfferWall(this);
    }
}