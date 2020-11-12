개인 프로젝트
==============
>네이버 웹툰 클론코딩

결과물
-----------------

<div>
  <img width="200", height="500", src="https://user-images.githubusercontent.com/43267195/87907401-864c9c80-ca9f-11ea-8145-bd987212c473.gif">
  <img width="200", height="500", src="https://user-images.githubusercontent.com/43267195/89756030-4e73ca80-db1c-11ea-81db-7cab4e413ef9.jpg">
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
