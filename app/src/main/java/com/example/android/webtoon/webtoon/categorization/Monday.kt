package com.example.android.webtoon.webtoon.categorization

import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_monday, container, false)

        val rvAdapter = CategoryAdapter(context, webtoonList)
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

        return rootView
    }

}