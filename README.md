개인 프로젝트
==============
>네이버 웹툰 클론코딩

결과물
-----------------
### 하단 '웹툰' 탭 화면
- 백버튼 눌렀을 때
<div>
  <img width="200", src="https://user-images.githubusercontent.com/43267195/98893510-e1d6e600-24e5-11eb-82a1-2e771bf2309f.jpg">
</div>
- 앱 실행시 스플래시 화면을 제외하고 처음으로 보이는 화면
<div>
  <img width="200", src="https://user-images.githubusercontent.com/43267195/87907401-864c9c80-ca9f-11ea-8145-bd987212c473.gif">
  <img width="200", src="https://user-images.githubusercontent.com/43267195/98892963-d0d9a500-24e4-11eb-8769-25b10e869ffc.jpg">
</div>
- 요일별 카테고리 웹툰 목록을 눌렀을 때 보여지는 화면(고른 웹툰의 목차리스트)
<div>
  <img width="200", src="https://user-images.githubusercontent.com/43267195/98894323-e8fef380-24e7-11eb-914d-e8c68aaed5ad.gif">
  <img width="200", src="https://user-images.githubusercontent.com/43267195/98893008-e8b12900-24e4-11eb-9565-23c31f7becf8.jpg">
</div>
- 선택한 웹툰 회차의 컨텐츠 내용 보기
<div>
  <img width="200", src="https://user-images.githubusercontent.com/43267195/98894323-e8fef380-24e7-11eb-914d-e8c68aaed5ad.gif">
  <img width="200", src="https://user-images.githubusercontent.com/43267195/98893008-e8b12900-24e4-11eb-9565-23c31f7becf8.jpg">
</div>

 
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
