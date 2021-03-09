package com.example.android.webtoon.view.view_categorization

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.model.remote.ListItem
import com.example.android.webtoon.view.adapter.WebtoonsByDayAdapter
import com.example.android.webtoon.view.deepWebtoonActivity
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Tuesday : Fragment() {
    val WEEKDAY : String = "화"

    private lateinit var rv_tuesday : RecyclerView
    private lateinit var rvManager: RecyclerView.LayoutManager
    val webtoonList : ArrayList<ListItem> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView =  inflater.inflate(R.layout.fragment_tuesday, container, false)

        //초기화
        rv_tuesday = rootView.findViewById(R.id.rv_tuesday)

        // 요일별로 나누어 웹툰 목록 가져오기
        getListOfWebtoonsByDay()
        // 가져온 웹툰 목록 UI에 세팅하기
        setListOfWebtoonsByDay()

        return rootView
    }

    private fun getListOfWebtoonsByDay() {
        doAsync {
            val document = Jsoup.connect("https://comic.naver.com/webtoon/weekdayList.nhn?week=tue").get()

            //실시간 요일 가져오기
            var currentTime : Date = Calendar.getInstance().time
            var currentWeekDay : String = SimpleDateFormat("EE", Locale.getDefault()).format(currentTime)
            var isToday : Boolean
            if(currentWeekDay == WEEKDAY){
                isToday = true
            }else{
                isToday = false
            }

            var webtoons : Elements = document.select("ul.img_list li")

            for(webtoon in webtoons){
                var thumbnail = webtoon.select("div.thumb a img").attr("src")
                var title = webtoon.select("div.thumb a img").attr("title")
                var rating = webtoon.select("div.rating_type strong").text()
                var upOrPause = webtoon.select("em.ico_break").text()
                var author = webtoon.select("dd.desc a").text()
                var new = webtoon.select("span.ico_new2").text()
                var cutToon = webtoon.select("span.ico_cut").text()
                var episodeLink = webtoon.select("div.thumb a").attr("href")
                webtoonList.add(ListItem(thumbnail, title, rating, upOrPause, author, new, isToday, cutToon, episodeLink))
            }

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                // 가져온 웹툰 목록 UI에 세팅하기
                setListOfWebtoonsByDay()
            }, 0)
        }
    }

    private fun setListOfWebtoonsByDay(){
        val rvAdapter = WebtoonsByDayAdapter(context, webtoonList) { ListItem ->
            /* 아이템 클릭하면 웹툰 회차 나옴 */
            activity?.let {
                // let() 함수는 자신을 호출한 객체를 매개변수로 전달받은 람다 함수에 매개 변수로 전달하는 함수입니다.
                // let() 함수를 사용하면 불필요한 변수 선언을 방지할 수 있습니다.
                val intent = Intent(context, deepWebtoonActivity::class.java)
                intent.putExtra("episodeLink", ListItem.tv_episodeLink)
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        rvManager = GridLayoutManager(context, 3)

        rv_tuesday.apply {
            // apply 의 블록에서는 오직 프로퍼티 만 사용합니다!
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = rvManager

            // specify an viewAdapter (see also next example)
            adapter = rvAdapter

            addItemDecoration(DividerItemDecoration(context, GridLayoutManager.VERTICAL))
            addItemDecoration(DividerItemDecoration(context, GridLayoutManager.HORIZONTAL))
        }
    }
}