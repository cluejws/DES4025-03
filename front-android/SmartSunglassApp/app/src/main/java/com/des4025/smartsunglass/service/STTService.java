package com.des4025.smartsunglass.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import java.util.ArrayList;

public class STTService {

    // 목적지(리스너 에서 입력)
    private static String endPoint = "";

    // 목적지 입력 리스너
    private static RecognitionListener endPointRecognitionListener = new RecognitionListener() {
        @Override
        public void onResults(Bundle results) {
            // 말을 하면 ArrayList 에 단어를 넣고 textView 에 단어를 이어줌
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < matches.size(); i++) {
                endPoint += matches.get(i);
            }
        }

        @Override
        public void onReadyForSpeech(Bundle bundle) {
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onRmsChanged(float v) {
        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onError(int i) {
        }

        @Override
        public void onPartialResults(Bundle bundle) {
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
        }
    };

    //목적지 입력 인식 객체
    private static SpeechRecognizer endPointRecognizer;

    //목적지 입력 내용
    private static Intent endPointSoundIntent;

    public static String getEndPoint() {
        return endPoint;
    }

    public static RecognitionListener getEndPointRecognitionListener() {
        return endPointRecognitionListener;
    }

    public static void initEndPointRecognizer(Context context) {
        endPointRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
    }

    public static SpeechRecognizer getEndPointRecognizer() {
        return endPointRecognizer;
    }

    public static void initEndPointSoundIntent(String packageName) {
        endPointSoundIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        endPointSoundIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName);
        endPointSoundIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
    }

    public static Intent getEndPointSoundIntent() {
        return endPointSoundIntent;
    }
}
