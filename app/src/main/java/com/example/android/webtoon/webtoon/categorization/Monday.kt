package com.example.android.webtoon.webtoon.categorization

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.webtoon.adapter.CategoryAdapter
import com.example.android.webtoon.webtoon.data.ListItem
import com.example.android.webtoon.webtoon.deepWebtoonActivity
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class Monday : Fragment() {
    private lateinit var rv_listOfWebtoon : RecyclerView
    private lateinit var rvManager: RecyclerView.LayoutManager
    var webtoonList = arrayListOf<ListItem>(
        ListItem("", "소녀의 세계", "9.97", "연재", "모랑지"),
        ListItem("", "유일무이 로맨스", "9.98", "연재", "두부"),
        ListItem("", "인생존망", "9.82", "연재", "박태준/전선욱"),
        ListItem("", "칼가는 소녀", "9.97", "연재", "오리"),
        ListItem("", "백수세끼", "9.86", "연재", "치즈"),
        ListItem("", "인간의 온도", "9.90", "휴재", "이재익/양세준")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_monday, container, false)

        val rvAdapter = CategoryAdapter(context, webtoonList) {ListItem ->
            /* 아이템 클릭하면 웹툰 회차 나옴 */
            activity?.let {
                val intent = Intent(context, deepWebtoonActivity::class.java)
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        rvManager = GridLayoutManager(context, 3)

        rv_listOfWebtoon = rootView.findViewById<RecyclerView>(R.id.rv_listOfWebtoon).apply {
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

        getSource()
        return rootView
    }

    fun getSource(){
        doAsync {
            val document = Jsoup.connect("https://comic.naver.com/webtoon/weekday.nhn").get()

            var days : Elements = document.select("div.col_inner h4 span")
            for(e in days){
                Log.d("요일", "$e")
            }
        }
    }

}