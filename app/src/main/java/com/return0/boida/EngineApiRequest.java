package com.return0.boida;

import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class EngineApiRequest {

    String URL = "https://boida.bubblecell.win/api/success";

    public boolean apiRequest(String sourceUrl) {
        try {

            // 1. Callable 작업(구현체) 생성
            Callable<Boolean> implementLambda = () -> {
                try {
                    URL targeturl = new URL(URL + "?vidURL=" + sourceUrl); // 요청을 보낼 엔진 URL
                    HttpURLConnection connection = (HttpURLConnection) targeturl.openConnection(); // URL에 대한 연결을 염

                    connection.setRequestMethod("GET"); // HTTP 요청 방식을 POST로 설정

                    int responseCode = connection.getResponseCode(); // 서버로부터 받은 HTTP 응답 코드를 가져옴
                    System.out.println("Response Code : " + responseCode); // 응답 코드를 콘솔에 출력
                    if (responseCode == 200) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); // 서버로부터의 응답을 읽기 위한 BufferedReader를 생성
                        String inputLine;
                        StringBuffer response = new StringBuffer(); // 서버 응답을 저장할 StringBuffer 객체를 생성
                        while ((inputLine = in.readLine()) != null) { // 서버 응답의 끝까지 한 줄씩 읽어 들임
                            response.append(inputLine); // 읽은 데이터를 StringBuffer 객체에 추가
                        }
                        in.close(); // BufferedReader를 닫아 리소스를 해제
                        // json을 파싱하여 사용
                        JsonParser par = new JsonParser();
                        JsonObject jsonObj = (JsonObject) par.parse(String.valueOf(response));

                        if (jsonObj.get("result").getAsString().equals("success")) {
                            System.out.println("API Request Success (response code 200 & 'success' matched)");
                            return true;
                        } else {
                            System.out.println("API Response not matched with 'success'");
                            return false;
                        }
                    } else {
                        System.out.println("API Request Failed (response code not 200)");
                        return false;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };

            // 2. Thread Pool 생성
            ExecutorService executorService = Executors.newCachedThreadPool();

            // 3. submit()을 이용해 Callable 작업을 전달하고 Future 인스턴스를 리턴받음 (작업이 시작됨)
            Future<Boolean> submit = executorService.submit(implementLambda);

            // 4. Future 인스턴스를 이용하여 Callable 작업 결과를 받음
            Boolean result = submit.get();

            // 5. 잊지말고 Thread Pool 종료
            executorService.shutdown();

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


