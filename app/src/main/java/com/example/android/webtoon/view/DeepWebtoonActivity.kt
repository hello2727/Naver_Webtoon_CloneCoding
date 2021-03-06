package com.example.android.webtoon.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.view.adapter.deepWebtoonAdapter
import com.example.android.webtoon.model.remote.EpisodeList
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class deepWebtoonActivity : AppCompatActivity() {
    val URL : String = "https://comic.naver.com"

    private lateinit var rv_listOfEpisode : RecyclerView
    private lateinit var rvManager: RecyclerView.LayoutManager
    private lateinit var episodeLink : String

    var deepwebtoonList : ArrayList<EpisodeList> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_webtoon)

        // 초기화
        rv_listOfEpisode = findViewById(R.id.rv_listOfEpisode)

        // 요일별 웹툰목록 보여주는 액티비티에서 받은 데이터 값 가져오기
        getWebtoonInfo()
        // 클릭한 웹툰의 에피소드 목록 가져오기
        getEpisodesOfTheWebtoon()
        // 가져온 에피소드 목록 UI에 세팅하기 + 클릭 웹툰 에피소드 내용 보는 액티비티 전환 이벤트 설정
        setEpisodesOfTheWebtoon()
    }

    private fun getWebtoonInfo(){
        if(intent.hasExtra("episodeLink")){
            episodeLink = intent.getStringExtra("episodeLink")
            Log.d("에피소드 링크", "$episodeLink")
        }else{
            Log.d("에피소드 링크", "전달된 데이터가 없습니다.")
        }
    }

    private fun getEpisodesOfTheWebtoon(){
        doAsync {
            var pageCnt = 1
            while(true){
                val document = Jsoup.connect(URL+episodeLink+"&page="+pageCnt).get()

                var episodes : Elements = document.select("table.viewList tr:gt(0)")
                var theEnd = false
                for(episode in episodes){
                    var img = episode.select("td a img").attr("src")
                    var title = episode.select("td.title a").text()
                    var rating = episode.select("div.rating_type strong").text()
                    var updateDay = episode.select("td.num").text()
                    var link = episode.select("td a").attr("href")

                    if(deepwebtoonList.contains(EpisodeList(img, title, rating, updateDay, link))){
                        theEnd = true
                        break
                    }

                    deepwebtoonList.add(EpisodeList(img, title, rating, updateDay, link))

                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        // 에피소드 리스트 갱신
                        setRenewEpisodesOfTheWebtoon()
                    }, 0)
                }

                if(theEnd) break

                pageCnt++
            }
        }
    }

    private fun setEpisodesOfTheWebtoon(){
        val rvAdapter = deepWebtoonAdapter(this, deepwebtoonList) { ListItem ->
            val intent = Intent(this, EpisodeActivity::class.java)
            intent.putExtra("episodeContent", ListItem.tv_link)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        rvManager = LinearLayoutManager(this)

        rv_listOfEpisode.apply {
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

    private fun setRenewEpisodesOfTheWebtoon(){
        val rvAdapter = deepWebtoonAdapter(applicationContext, deepwebtoonList){ListItem ->
            val intent = Intent(this, EpisodeActivity::class.java)
            intent.putExtra("episodeContent", ListItem.tv_link)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        
        rv_listOfEpisode.apply {
            adapter = rvAdapter
        }
    }

    /* 뒤로가기 버튼 이벤트 */
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
