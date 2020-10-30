package com.example.android.webtoon.webtoon

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.android.webtoon.R
import com.example.android.webtoon.webtoon.Interface.Interaction
import com.example.android.webtoon.webtoon.adapter.ViewPagerAdapter
import com.example.android.webtoon.webtoon.categorization.Monday
import com.example.android.webtoon.webtoon.data.RecommendedItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_webtoon_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.lang.Exception

private const val NUM_PAGES = 5

class webtoonMain : Fragment(), View.OnClickListener, Interaction {
    private lateinit var callback: OnBackPressedCallback

    private lateinit var vp_recommendedWebtoon: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewModel: webtoonMainViewModel
    private var isRunning = true

    private var webtoonUrl = "https://comic.naver.com/index.nhn";
    private lateinit var elements: Elements
    private var imgPath = arrayOfNulls<String>(10)

    private lateinit var tab_week: TabLayout
    private lateinit var vp_webtoonOfWeek: ViewPager2
    private lateinit var ViewPagerAdapterOfList: ViewPagerAdapter2
    private var tab_weekTextArray = arrayOf("신작","월","화","수","목","금","토","일","완결")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_webtoon_main, container, false)

//        setWebtoonItem()
        viewModel = ViewModelProvider(this).get(webtoonMainViewModel::class.java)
        viewModel.setRecommendedItems(
            listOf(
                RecommendedItem("https://image-comic.pstatic.net/webtoon/641253/thumbnail/thumbnail_IMAG02_e046a3f5-9825-495b-a61c-fc8162fa6da4.jpg"),
                RecommendedItem("https://image-comic.pstatic.net/webtoon/641253/thumbnail/thumbnail_IMAG02_e046a3f5-9825-495b-a61c-fc8162fa6da4.jpg"),
                RecommendedItem("https://image-comic.pstatic.net/webtoon/641253/thumbnail/thumbnail_IMAG02_e046a3f5-9825-495b-a61c-fc8162fa6da4.jpg"),
                RecommendedItem("https://image-comic.pstatic.net/webtoon/641253/thumbnail/thumbnail_IMAG02_e046a3f5-9825-495b-a61c-fc8162fa6da4.jpg"),
                RecommendedItem("https://image-comic.pstatic.net/webtoon/641253/thumbnail/thumbnail_IMAG02_e046a3f5-9825-495b-a61c-fc8162fa6da4.jpg"),
                RecommendedItem("https://image-comic.pstatic.net/webtoon/641253/thumbnail/thumbnail_IMAG02_e046a3f5-9825-495b-a61c-fc8162fa6da4.jpg"),
                RecommendedItem("https://image-comic.pstatic.net/webtoon/641253/thumbnail/thumbnail_IMAG02_e046a3f5-9825-495b-a61c-fc8162fa6da4.jpg"),
                RecommendedItem("https://image-comic.pstatic.net/webtoon/641253/thumbnail/thumbnail_IMAG02_e046a3f5-9825-495b-a61c-fc8162fa6da4.jpg"),
                RecommendedItem("https://image-comic.pstatic.net/webtoon/641253/thumbnail/thumbnail_IMAG02_e046a3f5-9825-495b-a61c-fc8162fa6da4.jpg"),
                RecommendedItem("https://image-comic.pstatic.net/webtoon/641253/thumbnail/thumbnail_IMAG02_e046a3f5-9825-495b-a61c-fc8162fa6da4.jpg")

//                RecommendedItem(imgPath[1]),
//                RecommendedItem(imgPath[2]),
//                RecommendedItem(imgPath[3]),
//                RecommendedItem(imgPath[4]),
//                RecommendedItem(imgPath[5]),
//                RecommendedItem(imgPath[6]),
//                RecommendedItem(imgPath[7]),
//                RecommendedItem(imgPath[8]),
//                RecommendedItem(imgPath[9])
            )
        )

        vp_recommendedWebtoon = rootView.findViewById(R.id.vp_recommendedWebtoon)

        initViewPager2()
        subscribeObservers()
        autoScrollViewPager()

        tab_week = rootView.findViewById(R.id.tab_week)
        ViewPagerAdapterOfList = ViewPagerAdapter2(this)
        vp_webtoonOfWeek = rootView.findViewById(R.id.vp_webtoonOfWeek)
        vp_webtoonOfWeek.adapter = ViewPagerAdapterOfList

        initViewPagerOfList()

        return rootView
    }

    private fun setWebtoonItem() {
        lifecycleScope.launch {
            var idx: Int = 0
            try{
                var rawData = Jsoup.connect(webtoonUrl).get();
                elements = rawData.select("html body div#wrap.end_page div.mainTopArea div#mainNavi.main_spot div.webtoon_area div.webtoon_lst div.lst_area ul#comicList li a.item span.bigimg");
                for(e in elements){
                    if(idx > 9) {
                        break
                   }

                    Log.d("확인", e.text())

                    imgPath[idx] = e.text()
                    idx++
                }

//                var doc = Jsoup.connect(webtoonUrl).get()
//                elements = doc.select("div.lst_area ul li a span.bigimg")
//                for(e in elements) {
//                    if(idx > 9) {
//                        break
//                    }
//
//                    imgPath[idx] = e.text()
//                    idx++
//                }
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    /*추천웹툰 아이템*/
    private fun initViewPager2() {
        vp_recommendedWebtoon.apply {
            viewPagerAdapter = ViewPagerAdapter(this@webtoonMain)
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    isRunning = true
                    tv_currentPageNumber.text = "${position+1}"

                    //직접 유저가 스크롤했을 때
                    viewModel.setCurrentPosition(position)
                }
            })

        }
    }

    private fun subscribeObservers() {
        viewModel.recommendedItemList.observe(viewLifecycleOwner, Observer { recommendedItemList ->
            viewPagerAdapter.submitList(recommendedItemList)
        })
        viewModel.currentPosition.observe(viewLifecycleOwner, Observer { currentPosition ->
            vp_recommendedWebtoon.currentItem = currentPosition
        })
    }

    private fun autoScrollViewPager() {
        lifecycleScope.launch {
            whenResumed {
                while (isRunning) {
                    delay(3000)
                    viewModel.getcurrentPosition()?.let {
                        viewModel.setCurrentPosition((it.plus(1)) % 10)
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        isRunning = false
    }

    override fun onResume() {
        super.onResume()
        isRunning = true
    }

    //추천 웹툰 클릭했을 때
    override fun onRecommendedItemClicked(recommendedItem: RecommendedItem) {
        TODO("Not yet implemented")
    }

    override fun onClick(p0: View?) {

    }

    /*항목별 웹툰 리스트*/
    private fun initViewPagerOfList() {
        TabLayoutMediator(tab_week, vp_webtoonOfWeek) { tab, position ->
            tab.text = tab_weekTextArray[position]
        }.attach()
    }

    /* 뒤로가기 버튼 이벤트 처리 */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}

class ViewPagerAdapter2(fa: Fragment):FragmentStateAdapter(fa){

    override fun getItemCount(): Int = 9

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1 -> Monday()
            else -> Monday()
        }
    }
}