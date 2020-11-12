package com.example.android.webtoon.webtoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.webtoon.adapter.EpisodeAdapter
import com.example.android.webtoon.webtoon.adapter.deepWebtoonAdapter
import com.example.android.webtoon.webtoon.data.EpisodeList
import com.example.android.webtoon.webtoon.data.contentUnit

class EpisodeActivity : AppCompatActivity() {
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

        rvManager = LinearLayoutManager(this)
        val rvAdapter = EpisodeAdapter(this, episodeUnit) { //ListItem ->
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

    /* 뒤로가기 버튼 이벤트 */
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}