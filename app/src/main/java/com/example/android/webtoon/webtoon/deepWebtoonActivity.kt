package com.example.android.webtoon.webtoon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.webtoon.adapter.deepWebtoonAdapter
import com.example.android.webtoon.webtoon.data.EpisodeList

class deepWebtoonActivity : AppCompatActivity() {
    private lateinit var rv_listOfEpisode : RecyclerView
    private lateinit var rvManager: RecyclerView.LayoutManager

    var deepwebtoonList = arrayListOf<EpisodeList>(
        EpisodeList("", "아침을 건강하게", "9.97", "20.10.28"),
        EpisodeList("", "퇴근시간 교대역 환승하기", "9.97", "20.10.28"),
        EpisodeList("", "게스트하우스", "9.97", "20.10.28"),
        EpisodeList("", "공간활용", "9.97", "20.10.28"),
        EpisodeList("", "아빠의 생일", "9.97", "20.10.28"),
        EpisodeList("", "반찬", "9.97", "20.10.28"),
        EpisodeList("", "시내버스 적응기", "9.97", "20.10.28")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_webtoon)

        rvManager = LinearLayoutManager(this)
        val rvAdapter = deepWebtoonAdapter(this, deepwebtoonList) { ListItem ->
            val intent = Intent(this, EpisodeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        rv_listOfEpisode = findViewById<RecyclerView>(R.id.rv_listOfEpisode).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = rvManager

            // specify an viewAdapter (see also next example)
            adapter = rvAdapter

            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    /* 뒤로가기 버튼 이벤트 */
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}