# ✈️ 프로젝트 소개
> **시각장애인을 위한 스마트 선글라스**
- **동국대학교 2021 기업사회맞춤형 캡스톤디자인2 프로젝트, 팀 멋진코끼리**
- **프로젝트 기간** : 2021.09.07 ~ 2021.12.17 (3개월)
- **YouTube 발표 영상 링크** : https://www.youtube.com/watch?v=8NaU5azHdhg&list=PLysGR-hSRFyGE45dD5FJXEER9x7MNO9tc&index=4
- **온라인 포스터** : https://drive.google.com/file/d/1ra0BARaPDO-F_RKt2eJ07YMIEnfoZN3C/view?usp=sharing
- 영상처리 기술, 객체탐지 딥러닝 기술, GPS를 통한 내비게이팅을 활용하여 시각장애인들의 안전하고 편리한 외출을 보조하기 위한 ‘시각장애인을 위한 안전 길안내 서비스‘ 

<br><br>
# 📄 프로젝트 흐름도
![image](https://user-images.githubusercontent.com/75558861/214293305-1f1b1664-a1bc-47c0-acf4-0df8d0f8b38d.png)

<br><br>
# 🛠 개발 환경
> ![Java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white)
> ![Android](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)
> <br>
> ![JavaScript](https://img.shields.io/badge/JavaScript-323330?style=for-the-badge&logo=javascript&logoColor=F7DF1E)
> ![Node.js](https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=nodedotjs&logoColor=white)
> <br>
> ![Python](https://img.shields.io/badge/python-3776AB?style=for-the-badge&logo=python&logoColor=white)
> ![Tensorflow](https://img.shields.io/badge/TensorFlow-FF6F00?style=for-the-badge&logo=TensorFlow&logoColor=white)
> <br>
> ![AWS](https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white)

<br><br>
# 👩‍💻 기능 설계
| **설계 방향성**                                                                                                                                         |
|----------------------------------------------------------------------------------------------------------------------------------------------------|
| 본 프로젝트의 대상자는 시각장애인이므로 시간의 한계에 따라, <br> 앱을 연동하고 여러 가지 트리거(ex버튼)를 배치하는 것은 적합하지 않다는 판단이 있었습니다.<br/>따라서 필요 기능에 따라 **thread**로 구분하여, 여러 가지 트리거를 제거했습니다. |

### 1️⃣ 내비게이션 기능

| **기능 설계**                                                                                                                                                                                                                                                       |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| - **Android App**을 구동하면, 계속적으로 사용자의 현재 좌표인 '시작점(위도, 경도)'을 추출하는 **thread(현재 좌표 추출)** 를 수행합니다. <br/> - 사용자가 음성으로 '도착점(목적지명)'을 입력하면 자동으로 경로 안내를 시작하는 **thread(경로 안내)** 를 계속적으로 수행합니다.<br/>                                                                         |
| - **설계한 API서버**에 '시작점(위도, 경도)와 도착점(목적지명)'으로 요청이 들어오면, 경로 안내를 제공하기 위해 '도착점(위도, 경도)'을 추출해야 하므로, <br> **T-MAP 명칭(POI)통합 검색 API 서버**로  '도착점(위도, 경도)'을 추출하고 '시작점(위도, 경도) 도착점(위도, 경도)'로 새롭게 변환합니다.<br/> - 변환 값을 통해 **T-MAP 보행자 경로 안내 API서버**로, 경로 안내 정보만을 파싱하여 응답합니다. |

```
가. Android Studio - NavigationThread
      
1. 각 단계마다 thread sleep을 통해 STT 및 TTS 실행 
2. 사용자가 Android App에서 경로 안내 시작 버튼을 클릭 시, 시각장애인을 위한 목적지 입력 요청 메시지 TTS 출력
3. 사용자가 Android App에서, 목적지 입력 STT 실행 
4. 사용자가 목적지 입력 시, 설계한 API Server로 주기적으로 요청을 보내, 경로 안내 
```

```
나. Node.js - Routing
   
- /navigation : 
1. 쿼리 파라미터로 '현재 좌표 = 시작점(위도, 경도)와 도착점(목적지명)'를 획득
2. 문자열인 '도착점(목적지명)'을 '도착점(위도, 경도)'으로 변환 
3. T-MAP 보행자 경로 안내 API서버로 GET요청을 통해 사용자에게 경로 안내 서비스를 제공 
```

```
다. Node.js - Async Function
 
- GetPOIsearch : 
1. T-MAP 명칭(POI)통합검색 API서버로 GET요청을 통해 
   사용자가 입력한 '도착점(목적지명)'를 '도착점(위도, 경도)'으로 변환
     
- GetNavigator : 
1. T-MAP 보행자 경로 안내 API서버로 POST요청을 통해 
  사용자에게 경로 안내 서비스를 제공
```    

 

### 2️⃣ 장애물 탐지 기능
| **기능 설계**                                                                                                                                                  |
|------------------------------------------------------------------------------------------------------------------------------------------------------------|
| - 사용자가 소켓 통신 연결 버튼을 클릭하면, CoralBoard와 Android App 간에 소켓 통신으로 연결됩니다. <br/> - 사용자가 장애물 탐지 버튼을 클릭하면, 자동으로 장애물 탐지를 시작하는 **thread(장애물 탐지)** 를 계속적으로 수행합니다.<br/> |



```
가. Android Studio - SocketConnectThread 
       
1. 사용자가 Android App에서, 소켓 통신 연결 버튼 클릭 
2. CoralBoard Server Socket의 IP주소와 PORT에 맞게 Client Socket을 생성하여, CoralBoard와 연결 
3. 시각장애인을 위한 CoralBoard 연결 완료 메시지 TTS 출력
```

```
나. Android Studio - SocketStartThread
      
1. 사용자가 Android App에서 장애물 탐지 버튼을 클릭
2. 소켓 통신을 시작
3. 소켓으로부터 전달받은 byte배열을 16진수로 변환 뒤, 문자열로 변환
4. 장애물의 이름, 장애물의 위치로 전달하기 위해 출력값 정제
5. 시각장애인을 위한 장애물 탐지 메시지 TTS 출력  
```

<br><br>
# 👥 멤버
|이름|담당 업무|
|:------:|--------------|
|박상준|Mobilenet SSD 객체탐지 딥러닝 데이터셋 구축 및 학습|
|최용태|Mobilenet SSD 딥러닝 모델 구축, Coral board 제어 및 통신환경 개발|
|정원석|내비게이션 API 서버 구축, Android application 개발|
|홍진원|내비게이션 API 서버 구축, Android application 개발|
