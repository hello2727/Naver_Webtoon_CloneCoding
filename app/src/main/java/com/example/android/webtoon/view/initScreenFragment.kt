package com.example.android.webtoon.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.android.webtoon.R
import com.example.android.webtoon.model.remote.RecommendedItem
import com.example.android.webtoon.view.adapter.Interface.Interaction
import com.example.android.webtoon.view.adapter.WebtoonAdvertisementViewPagerAdapter
import com.example.android.webtoon.view.view_categorization.Monday
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_init_screen.*
import kotlinx.android.synthetic.main.item_layout_recommended.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.text.SimpleDateFormat
import java.util.*

class initScreenFragment : Fragment(), View.OnClickListener, Interaction {
//    private lateinit var callback: OnBackPressedCallback

    private lateinit var vp_recommendedWebtoon: ViewPager2
    private lateinit var webtoonAdvertisementViewPagerAdapter: WebtoonAdvertisementViewPagerAdapter
    private lateinit var webtoonAdvertisementViewModel: WebtoonAdvertisement
    private var isRunning = true

    private lateinit var tab_week: TabLayout
    private lateinit var vp_webtoonOfWeek: ViewPager2
    private lateinit var ViewPagerAdapter_webtoonByDay: ViewPagerAdapter2
    private var tab_weekArray = arrayOf("신작","월","화","수","목","금","토","일","완결")

    //    private var webtoonUrl = "https://comic.naver.com/index.nhn";
    //    private lateinit var elements: Elements
    //    private var imgPath = arrayOfNulls<String>(10)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var rootView = inflater.inflate(R.layout.fragment_init_screen, container, false)

        // 초기화
        vp_recommendedWebtoon = rootView.findViewById(R.id.vp_recommendedWebtoon)
        tab_week = rootView.findViewById(R.id.tab_week)
        vp_webtoonOfWeek = rootView.findViewById(R.id.vp_webtoonOfWeek)

        // 웹툰 썸네일 배너 세팅
        setThumbNailBanner()
        // 웹툰을 요일별로 나누기
        divideWebtoonByDay()
////        setWebtoonItem()

        return rootView
    }

    private fun setThumbNailBanner(){
        //웹툰 썸네일 이미지 보안정책 때문에 서버에서 직접 가져올 수 없어서 이미지를 drawable폴더에 저장하여 웹툰광고배너 이미지뷰에 로드함.
        webtoonAdvertisementViewModel = ViewModelProvider(this).get(WebtoonAdvertisement::class.java)
        webtoonAdvertisementViewModel.setRecommendedItems(
            listOf(
                RecommendedItem(R.drawable.thumbnail01),
                RecommendedItem(R.drawable.thumbnail02),
                RecommendedItem(R.drawable.thumbnail03),
                RecommendedItem(R.drawable.thumbnail04),
                RecommendedItem(R.drawable.thumbnail05),
                RecommendedItem(R.drawable.thumbnail06),
                RecommendedItem(R.drawable.thumbnail07),
                RecommendedItem(R.drawable.thumbnail08),
                RecommendedItem(R.drawable.thumbnail09),
                RecommendedItem(R.drawable.thumbnail10)
            )
        )

        // 웹툰광고배너 설정
        initWebtoonAdvertisementViewPager2()
        // 뷰페이저 어댑터에 웹툰광고배너 리스트 넘기고 세팅하기
        subscribeObservers()
        // 웹툰광고배너 뷰페이저 자동스크롤 세팅하기
        autoScrollViewPager()
    }

    private fun initWebtoonAdvertisementViewPager2() {
        vp_recommendedWebtoon.apply {
            webtoonAdvertisementViewPagerAdapter = WebtoonAdvertisementViewPagerAdapter(this@initScreenFragment)
            adapter = webtoonAdvertisementViewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    isRunning = true
                    tv_currentPageNumber.text = "${position+1}"

                    //직접 유저가 스크롤했을 때
                    webtoonAdvertisementViewModel.setCurrentPosition(position)
                }
            })
        }
    }

    private fun subscribeObservers() {
        webtoonAdvertisementViewModel.recommendedItemList.observe(viewLifecycleOwner, Observer { recommendedItemList ->
            webtoonAdvertisementViewPagerAdapter.submitList(recommendedItemList)
        })
        webtoonAdvertisementViewModel.currentPosition.observe(viewLifecycleOwner, Observer { currentPosition ->
            vp_recommendedWebtoon.currentItem = currentPosition
        })
    }

    private fun autoScrollViewPager() {
        lifecycleScope.launch {
            whenResumed {
                while (isRunning) {
                    delay(3000)
                    webtoonAdvertisementViewModel.getcurrentPosition()?.let {
                        webtoonAdvertisementViewModel.setCurrentPosition((it.plus(1)) % 10)
                    }
                }
            }
        }
    }

    private fun divideWebtoonByDay(){
        ViewPagerAdapter_webtoonByDay = ViewPagerAdapter2(this)
        vp_webtoonOfWeek.adapter = ViewPagerAdapter_webtoonByDay

        // 웹툰을 요일별로 나눌 탭 세팅
        setWeekDayTab()
        // 1.네이버웹툰 페이지에서 요일 가져오기 2.현재요일에 맞게 탭에 고정
        getDaysAndFixedTodayTab()
    }

    private fun setWeekDayTab() {
        TabLayoutMediator(tab_week, vp_webtoonOfWeek) { tab, position ->
            tab.text = tab_weekArray[position]
        }.attach()
    }

    private fun getDaysAndFixedTodayTab(){
        doAsync {
            val document = Jsoup.connect("https://comic.naver.com/webtoon/weekday.nhn").get()

            var days : Elements = document.select("div.col_inner h4 span")
            val handler : Handler = Handler(Looper.getMainLooper())
            for(e in days){
                var day = e.toString().substring(6, 7) //요일 추출
                //실시간 요일 가져오기
                var currentTime : Date = Calendar.getInstance().time
                var weekDay : String = SimpleDateFormat("EE", Locale.getDefault()).format(currentTime)

                //앱 실행시 현재 요일 탭 선택
                if(weekDay.equals(day)){
                    var idx = tab_weekArray.indexOf(weekDay)

                    handler.postDelayed({
                        tab_week.setScrollPosition(0, idx.toFloat(), true, true)
                    }, 0)

                    Log.d("tabSetting", "success $idx")
                    break
                }
                Log.d("!요일", "$day $weekDay")
            }
            ViewPagerAdapter_webtoonByDay
        }
    }

//    private fun setWebtoonItem() {
//        lifecycleScope.launch {
//            var idx: Int = 0
//            try{
//                var rawData = Jsoup.connect(webtoonUrl).get();
//                elements = rawData.select("html body div#wrap.end_page div.mainTopArea div#mainNavi.main_spot div.webtoon_area div.webtoon_lst div.lst_area ul#comicList li a.item span.bigimg");
//                for(e in elements){
//                    if(idx > 9) {
//                        break
//                   }
//
//                    Log.d("확인", e.text())
//
//                    imgPath[idx] = e.text()
//                    idx++
//                }
//
////                var doc = Jsoup.connect(webtoonUrl).get()
////                elements = doc.select("div.lst_area ul li a span.bigimg")
////                for(e in elements) {
////                    if(idx > 9) {
////                        break
////                    }
////
////                    imgPath[idx] = e.text()
////                    idx++
////                }
//            } catch (e : Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

    // 웹툰 썸네일 배너 클릭했을 때
    override fun onRecommendedItemClicked(recommendedItem: RecommendedItem) {
        TODO("Not yet implemented")
    }

    override fun onClick(p0: View?) {

    }

    override fun onResume() {
        super.onResume()
        isRunning = true
    }

    override fun onPause() {
        super.onPause()
        isRunning = false
    }

//    /* 뒤로가기 버튼 이벤트 처리 */
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        callback = object : OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//    }
//    override fun onDetach() {
//        super.onDetach()
//        callback.remove()
//    }
}

class ViewPagerAdapter2(fa: Fragment): FragmentStateAdapter(fa){
    override fun getItemCount(): Int = 9
    override fun createFragment(position: Int): Fragment {
        return when(position){
            1 -> Monday()
            else -> Monday()
        }
    }
}