# 개인 프로젝트
>네이버 웹툰 클론코딩

결과물
==============

<div>
  <img width="300", src="https://user-images.githubusercontent.com/43267195/87907401-864c9c80-ca9f-11ea-8145-bd987212c473.gif">
</div>

 
환경설정
==============
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
