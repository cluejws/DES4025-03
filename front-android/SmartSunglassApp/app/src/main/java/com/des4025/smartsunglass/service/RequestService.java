package com.des4025.smartsunglass.service;

import android.speech.tts.TextToSpeech;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.des4025.smartsunglass.MainActivity;
import com.des4025.smartsunglass.util.ConfigUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestService {

    // 경로 안내 요청 Queue
    private static RequestQueue navigateRequestQueue;

    public static void initRequestQueue(MainActivity activity) {
        navigateRequestQueue = Volley.newRequestQueue(activity);
    }

    public static void makeRequest(Double longitude, Double latitude, String endPoint) {
        // 1. 경로 안내 요청, API 주소 설정
        String url =
                "http://" + ConfigUtil.HTTP_HOST
                + ":" + ConfigUtil.HTTP_PORT
                + "/navigation?longitude=" + longitude.toString()
                + "&latitude=" + latitude.toString()
                + "&endPoint=" + endPoint;

        // 2. 경로 안내 요청, 정의
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            // 1. 경로 안내 데이터 수신
                            jsonObject = new JSONObject(response);

                            // 2. 경로 안내 데이터 정제
                            String str = jsonObject.getString("description");
                            str = str.replace("{", "");
                            str = str.replace("}", "");
                            str = str.replace("\"navigator\":", "");
                            str = str.replace("\"", "");
                            String message = str;

                            // 3. 경로 안내 TTS
                            TTSService.getExecuteTTS().setPitch(1.0f);
                            TTSService.getExecuteTTS().setSpeechRate(1.0f);
                            TTSService.getExecuteTTS().speak(message, TextToSpeech.QUEUE_FLUSH, null);
                            try {
                                Thread.sleep(5000); // 경로 안내 TTS 위함
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        // 3. 경로 안내 요청, 설정
        request.setRetryPolicy(new DefaultRetryPolicy(
                2000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        request.setShouldCache(false);

        // 4. 경로 안내 요청, Queue 에 추가
        navigateRequestQueue.add(request);
    }
}
