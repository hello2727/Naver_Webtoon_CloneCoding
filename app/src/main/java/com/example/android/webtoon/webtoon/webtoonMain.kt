package com.example.android.webtoon.webtoon

import android.content.Intent
import android.os.Bundle
import android.view.Gravity.apply
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat.apply
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.android.webtoon.R
import com.example.android.webtoon.webtoon.Interface.Interaction
import com.example.android.webtoon.webtoon.adapter.ViewPagerAdapter
import com.example.android.webtoon.webtoon.data.RecommendedItem
import kotlinx.android.synthetic.main.fragment_webtoon_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val NUM_PAGES = 5

class webtoonMain : Fragment(), View.OnClickListener, Interaction {

    private lateinit var vp_recommendedWebtoon: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewModel: webtoonMainViewModel
    private var isRunning = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.fragment_webtoon_main, container, false)

        viewModel = ViewModelProvider(this).get(webtoonMainViewModel::class.java)
        viewModel.setRecommendedItems(
            listOf(
                RecommendedItem(R.drawable.sample1),
                RecommendedItem(R.drawable.sample2),
                RecommendedItem(R.drawable.sample3),
                RecommendedItem(R.drawable.sample4),
                RecommendedItem(R.drawable.sample5),
                RecommendedItem(R.drawable.sample6),
                RecommendedItem(R.drawable.sample7),
                RecommendedItem(R.drawable.sample8),
                RecommendedItem(R.drawable.sample9),
                RecommendedItem(R.drawable.sample10)
            )
        )

        vp_recommendedWebtoon = rootView.findViewById(R.id.vp_recommendedWebtoon)

        initViewPager2()
        subscribeObservers()
        autoScrollViewPager()

        return rootView
    }

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
}