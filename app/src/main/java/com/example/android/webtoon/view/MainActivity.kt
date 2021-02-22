package com.example.android.webtoon.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android.webtoon.R
import com.example.android.webtoon.view.view_more.more
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var backBtnTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //하단 버튼에 알맞은 프래그먼트 올리기 ('웹툰' '추천완결' '베스트도전' 'MY' '더보기')
        init()
    }

    private fun init(){
        val initScreenFragment = initScreenFragment()
        val moreScreenFragment = more()

        bottom_nav.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.webtoon -> {
                        // 프래그먼트 올리기 ('웹툰' '추천완결' '베스트도전' 'MY' '더보기')
                        loadFragment(initScreenFragment)

                    }
                    R.id.recommendedCompletion -> {
                    }
                    R.id.bestChallenge -> {
                    }
                    R.id.my -> {
                    }
                    R.id.more -> {
                        // 프래그먼트 올리기 ('웹툰' '추천완결' '베스트도전' 'MY' '더보기')
                        loadFragment(moreScreenFragment)
                    }
                }
                true
            }
            selectedItemId = R.id.webtoon
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerOfWebtoon, fragment)
            .commit()
    }

    override fun onBackPressed() {
        var curTime = System.currentTimeMillis()
        var gapTime = curTime - backBtnTime

        // 두번 눌러 뒤로가기 종료
        if(0 <= gapTime && 2000 >= gapTime){
            super.onBackPressed()
        }else{
            backBtnTime = curTime
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}