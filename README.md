[Nyoot Android SDK](https://github.com/nyoot-sdk/nyoot-sdk-android/blob/main/README.md)
====
- SDK 파일 용량은 34KB이며, 최신 버전의 Nyoot SDK 사용을 권장합니다.
- 최신 버전의 Android Studio 사용을 권장합니다.
- JDK11이상
- Nyoot SDK는 Android 4.3(JellyBean, API Level 18) 이상 기기에서 동작합니다. 
- 또한 [Google Play의 대상 API 레벨 요구사항](https://developer.android.com/distribute/best-practices/develop/target-sdk?hl=ko)을 충족하기 위해서는 targetSdkVersion 33이상, compileSdkVersion 33이상을 적용하여야 합니다.(2023년 8월부터 적용)

Android SDK 연동
----
Nyoot Android SDK를 연동하기 위해서는 아래와 같은 사전 절차가 필요합니다.
1) Nyoot SDK 연동 담당자(nyoot@doohub.co.kr)에게 연락하여 Nyoot Android SDK 연동 요청을 합니다.
2) 매체 등록 후 담당자를 통해 APP 소유자에게 발급되는 고유 ID(publihser id)를 제공 받습니다.

자세한 SDK 연동 방법은 [Android SDK 연동 가이드](https://github.com/nyoot-sdk/nyoot-sdk-android/blob/main/Android%20SDK%20%EC%97%B0%EB%8F%99%20%EA%B0%80%EC%9D%B4%EB%93%9C.md) 에서 확인하실 수 있습니다.

Release Note
----
- Nyoot 1.0.0 최초 배포

공지사항 (필독)
----
안드로이드 12 신규 정책에 따라 Google 광고ID (GAID) 획득을 위해 아래와 같이 업데이트 사항을 필독 부탁드립니다.

1. Google 광고ID (GAID) 수집을 위한 퍼미션 추가 
   - manifest 파일에 아래 퍼미션을 추가해야만 안드로이드 12 환경에서 광고ID 를 획득할 수 있습니다.
     ```xml
     <uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
     ```
2. Nyoot SDK 최신 버전으로 업데이트
   - 1.0.0 이상 버전으로 업데이트를 해주셔야 안드로이드 12 환경에서 정상적인 광고ID 수집이 가능하며,
   - Google 광고ID 가 없는 경우에는 [App set ID](https://developer.android.com/training/articles/app-set-id) 값을 수집하여 분석 및 Fraud 방어를 문제 없이 수행 할 수 있도록 지원합니다.
   - 퍼미션 추가와 함께, Nyoot 안드로이드 SDK 또한 최신 버전으로 업데이트가 필요합니다.

3. 개인정보 처리방침 (Pivacy Policy) 업데이트 
   - 3rd Party SDK 가 수집하는 항목에서 디바이스 레벨의 App set ID 항목이 추가 되었습니다. 귀사 서비스의 개인정보 처리방침에 추가가 필요한 경우 업데이트를 부탁 드립니다.

4. target api level 33 버전 업
   - 신규 앱 또는 업데이트 된 앱 (Google Play에 이미 게시된 앱의 새로운버전)은 2023년 8월 31일까지 Android 13(api 33) 수준으로 상향해야 합니다. Nyoot SDK 1.0.0 버전은 Android 13에 최적화 API Level 33로 적용 완료된 점 안내드립니다. 최적화 SDK를 적용하지 않으면 일부 기능이 정상동작하지 않거나, 앱이 강제로 종료될 수 있으므로 Nyoot SDK 최신버전으로 업데이트 권장드립니다. 

5. android:exported 속성 선언
   - 추가적으로 앱이 Android 12 이상을 타겟팅하는 경우 AndroidManifest.xml에 등록된 Activity가 intent-filter 태그를 포함하고 있으면 android:exported 속성을 명시적으로 선언해야합니다.(intent-filter를 사용하지만 명시적으로 선언된 값이 없으면 Android 12 이상을 실행하는 기기에 앱을 설치할 수 없습니다.) 대부분의 경우 exported="false"로 설정하지만, MAIN/LAUNCHER Activity는 android:exported="true"로 설정을 추가해주시면 됩니다.
자세한 내용은 [동작 변경사항: Android 12를 타겟팅하는 앱](https://developer.android.com/about/versions/12/behavior-changes-12?hl=ko)에서 확인할 수 있습니다.
 
안내사항
----
Google Play 의 데이터 보안정책 업데이트에 따라, 앱 개발사(자)는 Google 측에 해당 앱이 수집하는 데이터의 종류와 범위를 설문 양식에 작성하여 제출해야 합니다.

아래의 일정 참고하여 기한 안에 Play Console 에서 데이터 보안 양식 작성이 필요함을 알려드립니다.

- 22년 4월 말 : Google Play 스토어에서 보안섹션이 사용자에게 표기
- 22년 7월 20일 : 양식 제출 및 개인정보처리방침 승인 기한 (양식과 관련해 해결되지 않은 문제가 있는 경우 신규 앱 제출 및 앱 업데이트 거부될수 있습니다)

업데이트된 정책을 준수하실 수 있도록 Nyoot SDK 에서 수집하는 데이터 항목에 대해 안내드립니다.

Nyoot SDK 에서 광고 및 분석 목적으로 다음 데이터를 공유합니다.

카테고리|데이터 유형
---|---
기기 또는 기타 식별자|Android 광고 ID 를 공유
앱 활동|인스톨 된 앱
앱 정보 및 성능|오류 로그, 앱 관련 기타 진단 데이터

Nyoot SDK 에서 전송하는 모든 사용자 데이터는 전송 중에 암호화 되며 사용자가 데이터 삭제를 요청할 수 있는 방편을 제공하지 않습니다.

문의
----
Nyoot SDK 설치 관련하여 문의 사항은 고객센터 **1544-8867** 또는
<nyoot@doohub.co.kr> 로 문의주시기 바랍니다.
