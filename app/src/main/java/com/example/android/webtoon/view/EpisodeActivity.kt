package com.example.android.webtoon.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.view.adapter.EpisodeAdapter
import com.example.android.webtoon.model.contentUnit

class EpisodeActivity : AppCompatActivity() {
    lateinit var roundN : CustomView_webtoonRoundN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)

        // 초기화
        init()
    }

    private fun init(){
        roundN = CustomView_webtoonRoundN(this);
        setContentView(roundN)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}