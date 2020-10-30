package com.example.android.webtoon.webtoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.webtoon.R

class EpisodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)
    }

    /* 뒤로가기 버튼 이벤트 */
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}