package com.des4025.smartsunglass.service;

import android.speech.tts.TextToSpeech;

import com.des4025.smartsunglass.MainActivity;
import com.des4025.smartsunglass.util.MessageUtil;

import java.util.Locale;


public class TTSService {

    private static TextToSpeech startTTS;

    private static TextToSpeech executeTTS;

    public static void initStartTTS(MainActivity activity) {
        startTTS = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                // 앱 사용 방법 TTS
                startTTS.speak(MessageUtil.HELLO, TextToSpeech.QUEUE_FLUSH, null);
                try {
                    Thread.sleep(3000); // 앱 사용 방법 TTS 위함
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static TextToSpeech getStartTTS() {
        return startTTS;
    }

    public static void setStartTTSNull() {
        startTTS = null;
    }

    public static void initExecuteTTS(MainActivity activity) {
        executeTTS = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != android.speech.tts.TextToSpeech.ERROR) {
                    executeTTS.setLanguage(Locale.KOREAN);
                }
            }
        });
    }

    public static TextToSpeech getExecuteTTS() {
        return executeTTS;
    }

    public static void setExecuteNull() {
        executeTTS = null;
    }
}
