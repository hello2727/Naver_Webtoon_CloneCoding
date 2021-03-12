개인 프로젝트
==============
>네이버 웹툰 클론코딩

앱스토어 등록여부
-----------------
* 미등록 (학습목적 프로젝트)

개발환경
-----------------
* Android Kotlin Glide Jsoup

결과물
-----------------
### 하단의 '웹툰' 탭을 눌렀을 때 나타나는 view
- 백버튼 눌렀을 때
<div>
  <img width="200", src="https://user-images.githubusercontent.com/43267195/110272880-9a351680-800e-11eb-954e-2664d85db5bf.PNG">
</div>

- 1)웹툰 썸네일 배너 자동스크롤 및 손으로 스크롤 기능  2)요일별 웹툰 목록 구성(앱 실행시 현재 요일에 맞는 요일탭 고정 + 컷툰/휴재 상태 표시)
<div>
  <img width="200", src="https://user-images.githubusercontent.com/43267195/110273049-0d3e8d00-800f-11eb-9cad-b8f705184187.gif">
  <img width="200", src="https://user-images.githubusercontent.com/43267195/110273300-a53c7680-800f-11eb-9627-901262e9c44d.gif">
</div>

- 요일별 카테고리 웹툰 목록을 눌렀을 때 보여지는 화면(고른 웹툰의 목차리스트)
<div>
  <img width="200", src="https://user-images.githubusercontent.com/43267195/110915675-49e5ed80-835b-11eb-8402-89da3afbf263.gif">
  <img width="200", src="https://user-images.githubusercontent.com/43267195/98893008-e8b12900-24e4-11eb-9565-23c31f7becf8.jpg">
</div>

- 선택한 웹툰 회차의 컨텐츠 내용 보기 
<div>
  <img width="200", src="https://user-images.githubusercontent.com/43267195/98905111-35edc480-24fe-11eb-96e7-76a4c3943532.gif">
  <img width="200", src="https://user-images.githubusercontent.com/43267195/98905148-456d0d80-24fe-11eb-9f0e-eff97c104026.gif">
  <img width="200", src="https://user-images.githubusercontent.com/43267195/99513371-e90e6000-29cd-11eb-8415-fa5f8c22557e.gif">
</div>

## 진행상황 알림
이미지뷰어 설정 및 웹 크롤링 단계입니다. 이후 긁어온 소스코드에서 roomDB 형식으로 서버의 데이터베이스를 다룰 생각입니다. 그렇기 때문에 서버 데이터베이스에서 가지고 온 정보로 UI를 구성할 부분들을 현재는 예제 이미지와 글, arraylist로 대체하여 기능을 구현하였습니다. 
 
환경설정
-----------------
### build.gradle
```
dependencies {
    implementation fileTree(dir: "libs", include: ["*.jar"])
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation 'androidx.core:core-ktx:1.3.0'
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    // 뷰페이저 -> 자동넘김 ui구성
    implementation 'com.google.android.material:material:1.1.0'
    implementation 'androidx.viewpager2:viewpager2:1.0.0'

    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

    def nav_version = "2.3.0-beta01"
    implementation "androidx.navigation:navigation-fragment:$nav_version"
    implementation "androidx.navigation:navigation-ui:$nav_version"
    // Kotlin
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
    // Dynamic Feature Module Support
    implementation "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"
    // Testing Navigation
    androidTestImplementation "androidx.navigation:navigation-testing:$nav_version"

    // livedata의 ViwModelProvider
    def lifecycle_version = "2.2.0"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"

    //Coroutine 사용
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.4'

    //LifecycleScope 사용
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-alpha01'
    
    //jsoup (웹에서 정보 가져오기)
    implementation 'org.jsoup:jsoup:1.13.1'
    //크롤링 비동기 처리
    implementation "org.jetbrains.anko:anko:0.10.8"

    //glide (추천웹툰 이미지 이미지뷰에 로더)
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    kapt "android.arch.lifecycle:compiler:1.0.0"
    kapt 'com.github.bumptech.glide:compiler:4.11.0'
}
```
### build.gradle(Project)
```
allprojects {
        ...
        //Safe Args를 추가
        def nav_version = "2.3.0-alpha06"
        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"
}
```
### manifest
```
  //인터넷 접근
  <uses-permission android:name="android.permission.INTERNET"/> 
```
