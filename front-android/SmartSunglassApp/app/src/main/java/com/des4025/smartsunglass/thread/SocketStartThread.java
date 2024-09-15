package com.des4025.smartsunglass.thread;

import android.speech.tts.TextToSpeech;

import com.des4025.smartsunglass.service.SocketService;
import com.des4025.smartsunglass.converter.StringConverter;
import com.des4025.smartsunglass.service.TTSService;

import java.io.IOException;
import java.io.InputStream;

public class SocketStartThread extends Thread {
    @Override
    public void run() {
        try {
            // 무한 반복(장애물 탐지)
            while (true) {
                // 1. 장애물 탐지 데이터 수신
                byte[] buffer = new byte[1024];
                InputStream input = SocketService.getSocket().getInputStream();
                int bytes = input.read(buffer);

                // 2. 장애물 탐지 데이터 정제
                String data = StringConverter.byteArrayToHex(buffer);
                data = data.substring(0, bytes * 3);
                data = StringConverter.hexToAscii(data);
                String message = StringConverter.strToAns(data);

                // 3. 장애물 탐지 데이터 TTS
                TTSService.getExecuteTTS().setPitch(1.0f);
                TTSService.getExecuteTTS().setSpeechRate(1.0f);
                TTSService.getExecuteTTS().speak(message, TextToSpeech.QUEUE_FLUSH, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
