package com.example.android.webtoon.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
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
import java.text.SimpleDateFormat
import java.util.*

class initScreenFragment : Fragment(), View.OnClickListener, Interaction {
    private lateinit var vp_recommendedWebtoon: ViewPager2
    private lateinit var webtoonAdvertisementViewPagerAdapter: WebtoonAdvertisementViewPagerAdapter
    private lateinit var webtoonAdvertisementViewModel: WebtoonAdvertisement
    private var isRunning = true

    private lateinit var tab_week: TabLayout
    private lateinit var vp_webtoonOfWeek: ViewPager2
    private lateinit var ViewPagerAdapter_webtoonByDay: ViewPagerAdapter2
    private var tab_weekArray = arrayOf("신작","월","화","수","목","금","토","일","완결")

    private lateinit var callback: OnBackPressedCallback

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
        //실시간 요일 가져오기
        var currentTime : Date = Calendar.getInstance().time
        var weekDay : String = SimpleDateFormat("EE", Locale.getDefault()).format(currentTime)

        var idx = tab_weekArray.indexOf(weekDay)

        tab_week.selectTab(tab_week.getTabAt(idx))
    }

    // 웹툰 썸네일 배너 클릭했을 때
    override fun onRecommendedItemClicked(recommendedItem: RecommendedItem) {
        TODO("Not yet implemented")
    }

    override fun onClick(p0: View?) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 뒤로가기
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onResume() {
        super.onResume()
        isRunning = true
    }

    override fun onPause() {
        super.onPause()
        isRunning = false
    }

    override fun onDetach() {
        super.onDetach()
        // 뒤로가기
        callback.remove()
    }
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