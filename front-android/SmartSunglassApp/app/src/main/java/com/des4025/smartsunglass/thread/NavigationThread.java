package com.des4025.smartsunglass.thread;

import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.des4025.smartsunglass.service.LocationService;
import com.des4025.smartsunglass.service.RequestService;
import com.des4025.smartsunglass.service.STTService;
import com.des4025.smartsunglass.service.TTSService;
import com.des4025.smartsunglass.util.MessageUtil;

public class NavigationThread extends Thread {
    @Override
    public void run() {
        // 1. 경로 안내 시작 TTS
        String message = STTService.getEndPoint() + MessageUtil.TO_NAVIGATE_START;
        TTSService.getExecuteTTS().setPitch(1.0f);
        TTSService.getExecuteTTS().setSpeechRate(1.0f);
        TTSService.getExecuteTTS().speak(message, TextToSpeech.QUEUE_FLUSH, null);
        try {
            Thread.sleep(5000); // 경로 안내 시작 TTS 위함
        } catch (Exception e) {
            Log.e("error", e.toString());
        }

        // 2. 무한 반복(경로 안내 요청)
        while (true) {
            // 경로 안내 요청
            RequestService.makeRequest(LocationService.getLongitude(), LocationService.getLatitude(), STTService.getEndPoint());
            try {
                Thread.sleep(60000); // 경로 안내 요청 위함
            } catch (Exception e) {
                Log.e("error", e.toString());
            }
        }
    }
}
