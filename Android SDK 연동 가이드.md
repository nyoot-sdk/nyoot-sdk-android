NYOOT Android SDK 설치가이드
=====================================================

# 목차
1. [NYOOT 시작하기](#1-nyoot-시작하기)
	* [NYOOT SDK 추가](#nyoot-sdk-추가)
	* [AndroidManifest.xml 속성 지정](#androidmanifestxml-속성-지정)
	* [proguard 설정](#proguard-설정-nyoot-sdk-포함된-class는-난독화-시키지-않도록-주의)
	* [AndroidX 설정](#androidx-사용하는-경우)
2. [NYOOT 연동하기](#2-nyoot-연동하기)
	* [JAVA](#java-base-자세한-내용은-nyootsample-참조)
	* [NYOOT 설정 방법](#nyoot-설정-방법)
3. [Class Reference](#3-class-reference)

- - -

# 1. NYOOT 시작하기


### NYOOT SDK 추가

- 최상위 level build.gradle 에 maven repository 추가
```clojure
 allprojects {
   repositories {
    google()
    jcenter()
    maven {
      url "s3://repo.nyoot.co.kr/releases"
      credentials(AwsCredentials) {
        accessKey "AKIATAT4GC3TACMGQQS4"
        secretKey "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"
      }
    }
  }
}
```

- app level build.gradle 에 'dependencies' 추가
```clojure
dependencies {
  implementation 'com.google.android.gms:play-services-ads-identifier:18.0.1'
  implementation 'com.google.android.gms:play-services-appset:16.0.2'
  implementation 'kr.co.nyoot.sdk:nyoot-sdk:1.0.0'
}
```




### AndroidManifest.xml 속성 지정

#### 필수 퍼미션 추가
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
```

#### 네트워크 보안 설정 (targetSdkVersion 28 이상)

`콘텐츠 노출 및 클릭이 정상적으로 동작하기 위해서 cleartext 네트워크 설정 필요`

```xml
<application android:usesCleartextTraffic="true" />
```	

#### 더 안전한 구성요소 내보내기 설정 (targetSdkVersion 31 이상)
`intent-filter를 사용하는 활동을 포함하는 경우 android:exported 속성 설정 필요 (MAIN/LAUNCHER activity는 ture 설정 필수)`

```xml
<activity android:exported="true" />
``` 

### proguard 설정 nyoot SDK 포함된 Class는 난독화 시키지 않도록 주의
```clojure
proguard-rules.pro ::
-keep class kr.co.nyoot.sdk.core.Nyoot {
    public static *** init(***);
    public static *** setListener(***);
    public static *** setUserInfo(***, ***);
    public static *** showOfferWall(***);
}
-keep interface kr.co.nyoot.sdk.core.Nyoot$NyootListener {*;}

```

### AndroidX 사용하는 경우
```xml
gradle.properties ::
 * android.useAndroidX=true
 * android.enableJetifier=true
```

- 참고 : https://developer.android.com/jetpack/androidx/migrate

# 2. NYOOT 연동하기

NYOOT 을 삽입하고 싶은 위치에 소스를 삽입

#### `JAVA` [자세한 내용은 'NyootSample' 참조]
``` java

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // 1. 초기화 (디바이스 정보 가져오기)
    Nyoot.init(this);

    // 2. Nyoot 사용자 설정
    Nyoot.setUserInfo("publisher_id", "publisher_user_id");

    // 3. Nyoot 바인딩 및 처리 결과 통지 받을 리스너 등록
    Nyoot.setListener(new Nyoot.NyootListener() {

        // 적립이 완료된 경우 호출된다.
        @Override
        public void onAccumulateSuccess() {
            Log.d("nyoot QA", "onAccumulateSuccess");
        }

        // nyoot 페이지가 닫힌 경우 호출된다.
        @Override
        public void onCloseOfferwall() {
            Log.d("nyoot QA", "onCloseOfferwall");
            Toast.makeText(MainActivity.this, "onCloseOfferwall", Toast.LENGTH_SHORT).show();
        }

        // nyoot 상세 페이지에 진입한 경우 호출된다.
        @Override
        public void onLoadedOfferwall() {
            Log.d("nyoot QA", "onLoadedOfferwall");
        }

        // nyoot 페이지가 오픈된 경우 호출된다.
        @Override
        public void onStartedOfferwall() {
            Log.d("nyoot QA", "onStartedOfferwall");
        }

        // nyoot 페이지 로딩이 실패한 경우 호출된다.
        @Override
        public void onLoadedError(int errorCode, String errorMsg) {
            Log.d("nyoot QA", "failed to load. errorCode: " + errorCode + " errorMsg: " + errorMsg);
        }
    });

    // 4. Nyoot 로드
    Nyoot.showOfferWall(this);
}

```


### `NYOOT 설정방법`

parameter|설 명
---|---
publisher_id|APP 등록 후 APP 소유자에게 발급되는 고유 ID입니다. 필수 값이며 발급 관련한 자세한 내용은 <nyoot@doohub.co.kr>로 문의바랍니다.
publisher_user_id|APP 사용자 식별 ID입니다. 필수 값이며 만약, 설정하지 않으면 Nyoot 페이지가 표시되지 않습니다.



# 3. Class Reference

Nyoot||
---|---
init(Context)   |Nyoot 설정 초기화
setUserInfo(String publisher_id, String publisher_user_id)  |Nyoot 사용자 설정
setListener(NyootListener)  |NyootListener 지정
showOfferWall(Context)  |Nyoot 페이지 오픈

NyootListener||
---|---
onStartedOfferwall()    |Nyoot 페이지 오픈 시 호출됨.
onLoadedOfferwall() |Nyoot 상세 페이지 진입 시 호출됨.
onCloseOfferwall()  |Nyoot 페이지를 닫았을 때 호출됨.
onAccumulateSuccess()   |적립 완료시 호출됨.
onLoadedError(int errorCode, String errorMsg)   |페이지 로딩 실패시 호출됨.

> 늇 SDK 설치 관련하여 문의 사항은 **1544-8867**
> 또는 nyoot@doohub.co.kr 로 문의주시기 바랍니다.
