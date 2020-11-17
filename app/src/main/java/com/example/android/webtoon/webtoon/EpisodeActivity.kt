package com.example.android.webtoon.webtoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.webtoon.adapter.EpisodeAdapter
import com.example.android.webtoon.webtoon.data.contentUnit

class EpisodeActivity : AppCompatActivity() {
    var doubleClickFlag = 0
    var CLICK_DELAY = 200L;

    private lateinit var rv_listOfcontent : RecyclerView
    private lateinit var rvManager: RecyclerView.LayoutManager

    var episodeUnit = arrayListOf<contentUnit>(
        contentUnit(""),
        contentUnit(""),
        contentUnit(""),
        contentUnit(""),
        contentUnit(""),
        contentUnit("")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)

        /* jpg 이어붙여 한 회차 만들기 */
        rvManager = LinearLayoutManager(this)
        val rvAdapter = EpisodeAdapter(this, episodeUnit) { contentUnit ->
            /* 더블클릭 이벤트 */
            DoubleClick()
//            ListItem ->
//            val intent = Intent(this, EpisodeActivity::class.java)
//            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        rv_listOfcontent = findViewById<RecyclerView>(R.id.rv_contents).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = rvManager

            // specify an viewAdapter (see also next example)
            adapter = rvAdapter
        }
    }

    /* 화면 더블클릭 이벤트(원본에서 더블클릭하면 확대화면, 확대화면에서 더블클릭하면 원본 화면으로 변환) */
    fun DoubleClick(){
        doubleClickFlag++
        var clickRunnable = object : Runnable {
            override fun run() {
                doubleClickFlag = 0
                //클릭 이벤트
            }
        }
        if(doubleClickFlag == 1){
            Handler().postDelayed(clickRunnable, CLICK_DELAY)
        }else if(doubleClickFlag == 2){
            doubleClickFlag = 0
            //더블클릭 이벤트
            Toast.makeText(this, "더블클릭", Toast.LENGTH_SHORT).show()
        }
    }

    /* 뒤로가기 버튼 이벤트 */
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}