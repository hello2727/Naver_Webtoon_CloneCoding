package com.example.android.webtoon.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.android.webtoon.R
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class EpisodeActivity : AppCompatActivity() {
    val URL : String = "https://comic.naver.com"

    lateinit var roundN : CustomView_webtoonRoundN

    lateinit var link : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)

        // 초기화
        init()
        // 에피소드 회차 링크 가져오기
        getEpisodeInfo()
        // 웹툰뷰어에 에피소드 회차 사진 목록 넘겨주기
        giveWebtoonCuts()
    }

    private fun init(){
        roundN = CustomView_webtoonRoundN(this);
        setContentView(roundN)
    }

    private fun getEpisodeInfo(){
        if(intent.hasExtra("episodeContent")){
            link = intent.getStringExtra("episodeContent")
            Log.d("에피소드 링크", "$link")
        }else{
            Log.d("에피소드 링크", "전달된 데이터가 없습니다.")
        }
    }

    private fun giveWebtoonCuts(){
        doAsync {
            val document = Jsoup.connect(URL+link).get()

            var cuts : Elements = document.select("div.wt_viewer img")
            for(img in cuts){
                var cut = img.attr("src")

                // 이미지 소스 커스텀뷰에 넘기기
                roundN.setCuts(cut)
                roundN.invalidate()
//                var handler = Handler(Looper.getMainLooper())
//                handler.postDelayed({
//                    roundN.renewRV(cut)
//                }, 0)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}