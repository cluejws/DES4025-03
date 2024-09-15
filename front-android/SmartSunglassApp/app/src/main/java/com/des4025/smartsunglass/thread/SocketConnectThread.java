package com.des4025.smartsunglass.thread;

import android.speech.tts.TextToSpeech;

import com.des4025.smartsunglass.service.SocketService;
import com.des4025.smartsunglass.service.TTSService;
import com.des4025.smartsunglass.util.MessageUtil;

import java.io.IOException;
import java.net.UnknownHostException;

public class SocketConnectThread extends Thread {
    @Override
    public void run() {
        try {
            // 1. 장애물 탐지 소켓 생성
            SocketService.initSocket();

            // 2. 장애물 탐지 연결 완료 TTS
            TTSService.getExecuteTTS().setPitch(1.0f);
            TTSService.getExecuteTTS().setSpeechRate(1.0f);
            TTSService.getExecuteTTS().speak(MessageUtil.CONNECT_SOCKET, TextToSpeech.QUEUE_FLUSH, null);
            try {
                Thread.sleep(3000); // 장애물 탐지 연결 완료 TTS 위함
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (UnknownHostException uhe) { // 소켓 생성 시 전달 되는 호스트(www.unknown-host.com)의 IP를 식별할 수 없음.
            //System.out.println(" 생성 Error : 호스트 의 IP 주소를 식별할 수 없음.(잘못된 주소 값 또는 호스트 이름 사용)");
        } catch (IOException ioe) { // 소켓 생성 과정 에서 I/O 에러 발생.
            //System.out.println(" 생성 Error : 네트 워크 응답 없음");
        } catch (SecurityException se) { // security manager 에서 허용 되지 않은 기능 수행.
            //System.out.println(" 생성 Error : 보안(Security) 위반에 대해 보안 관리자(Security Manager)에 의해 발생. (프록시(proxy) 접속 거부, 허용되지 않은 함수 호출)");
        } catch (IllegalArgumentException le) { // 소켓 생성 시 전달 되는 포트 번호(65536)이 허용 범위(0~65535)를 벗어남.
            //System.out.println(" 생성 Error : 메소드 에 잘못된 파라 미터가 전달 되는 경우 발생.(0~65535 범위 밖의 포트 번호 사용, null 프록시(proxy) 전달)");
        }
    }
}
