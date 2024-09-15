package com.des4025.smartsunglass;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.des4025.smartsunglass.service.LocationService;
import com.des4025.smartsunglass.service.PermissionService;
import com.des4025.smartsunglass.service.RequestService;
import com.des4025.smartsunglass.service.STTService;
import com.des4025.smartsunglass.service.SocketService;
import com.des4025.smartsunglass.service.TTSService;
import com.des4025.smartsunglass.thread.NavigationThread;
import com.des4025.smartsunglass.thread.SocketConnectThread;
import com.des4025.smartsunglass.thread.SocketStartThread;
import com.des4025.smartsunglass.util.MessageUtil;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public Button SocketButton;
    public Button ObjectButton;
    public Button NavigationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Navigation Permission 강제
        //Permission 체크
        PermissionService.enforcePermission(this);
        PermissionService.checkPermission(this);

        // 1. 초기화
        // 1-1: 화면 관련 = 장애물 탐지 소켓 연결 버튼 초기화
        SocketButton = findViewById(R.id.SocketButton);
        SocketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SocketConnectThread socketConnectThread = new SocketConnectThread();
                socketConnectThread.start();
            }
        });

        // 1-1: 화면 관련 = 장애물 탐지 소켓 실행 버튼 초기화
        ObjectButton = findViewById(R.id.ObjectButton);
        ObjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SocketStartThread socketStartThread = new SocketStartThread();
                socketStartThread.start();
            }
        });

        // 1-1: 화면 관련 = 경로 안내 버튼 초기화
        NavigationButton = findViewById(R.id.NavigatingButton);
        NavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 무한 반복(목적지 입력)
                while (true) {
                    // 1. 목적지 입력 요청 TTS
                    TTSService.getExecuteTTS().setPitch(1.0f);
                    TTSService.getExecuteTTS().setSpeechRate(1.0f);
                    TTSService.getExecuteTTS().speak(MessageUtil.END_POINT_REQUEST, TextToSpeech.QUEUE_FLUSH, null);
                    try {
                        Thread.sleep(3000); // 목적지 입력 요청 TTS 위함
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    // 2. 목적지 입력 STT = 목적지 설정
                    STTService.initEndPointRecognizer(getApplicationContext());
                    STTService.getEndPointRecognizer().setRecognitionListener(STTService.getEndPointRecognitionListener());
                    STTService.getEndPointRecognizer().startListening(STTService.getEndPointSoundIntent());
                    try {
                        Thread.sleep(3000); // 목적지 입력 STT 위함
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    // 3. 목적지 입력 없음 TTS
                    if (STTService.getEndPoint().isEmpty()) {
                        TTSService.getExecuteTTS().setPitch(1.0f);
                        TTSService.getExecuteTTS().setSpeechRate(1.0f);
                        TTSService.getExecuteTTS().speak(MessageUtil.END_POINT_NOTHING, TextToSpeech.QUEUE_FLUSH, null);
                        try {
                            Thread.sleep(3000); // 목적지 입력 없음 TTS 위함
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        continue;
                    }

                    // 4. 목적지 입력 완료
                    // 경로 안내 스레드 = 초기화 및 실행
                    NavigationThread navigationThread = new NavigationThread();
                    navigationThread.start();
                }
            }
        });

        // 1-2: tts 관련 = 초기화(startTTS)
        // 1-2: 앱 사용 방법 TTS
        TTSService.initStartTTS(this);

        // 1-3: tts 관련 = 초기화(executeTTS)
        TTSService.initExecuteTTS(this);

        // 1-4: stt 관련 = 초기화(목적지 관련)
        STTService.initEndPointSoundIntent(getPackageName());

        // 1-5: http 통신 관련 = 초기화(경로 안내 관련)
        RequestService.initRequestQueue(this);

        // 2. 좌표 얻기 스레드 = 초기화 및 실행
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Timer timer = new Timer();
                    timer.schedule(timerTask, 0, 1000);
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    LocationService.setLocation(getSystemService(Context.LOCATION_SERVICE)); // 좌표 얻기
                }
            };
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            SocketService.getSocket().close();
        } catch (IOException e) {
            Log.e("error", e.toString());
        }
    }

    @Override
    public void onDestroy() {
        if (TTSService.getExecuteTTS() != null) {
            TTSService.getExecuteTTS().stop();
            TTSService.getExecuteTTS().shutdown();
            TTSService.setExecuteNull();
        }
        if (TTSService.getStartTTS() != null){
            TTSService.getStartTTS().stop();
            TTSService.getStartTTS().shutdown();
            TTSService.setStartTTSNull();
        }
        super.onDestroy();
    }
}