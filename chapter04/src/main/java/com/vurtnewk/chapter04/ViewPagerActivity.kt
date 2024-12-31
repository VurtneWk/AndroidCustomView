package com.vurtnewk.chapter04

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.vurtnewk.chapter04.databinding.ActivityViewPagerBinding

/**
 * createTime:  2024/12/31 14:28
 * author:      vurtnewk
 * description:
 */
class ViewPagerActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ViewPagerActivity::class.java)
            context.startActivity(starter)
        }

        private val list = listOf("直播", "推荐", "视频", "图片", "段子", "精华")
    }

    private lateinit var mActivityViewPagerBinding: ActivityViewPagerBinding
    private val tabList = arrayListOf<ColorTrackTextView>()
    private var mTabLayoutMediator: TabLayoutMediator? = null
    private var currentPosition = 0
    private val mOnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            //position 当前位置
            //positionOffset 滚动的百分比 0 - 1
//            Logger.d("position = $position , positionOffset = $positionOffset")

            val left = tabList[position]
            left.currentProgress = (1 - positionOffset)
            left.direction = ColorTrackTextView.Direction.RIGHT_TO_LEFT

            if (position + 1 < tabList.size) {
                val right = tabList[position + 1]
                right.currentProgress = positionOffset
                right.direction = ColorTrackTextView.Direction.LEFT_TO_RIGHT
            }

        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            currentPosition = position
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityViewPagerBinding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(mActivityViewPagerBinding.root)
        initView()
    }

    private fun initView() {
        list.forEachIndexed { index, _ ->
            tabList.add(tabMenu(index))
        }
        mActivityViewPagerBinding.apply {
            viewPager.adapter = object : FragmentStateAdapter(this@ViewPagerActivity) {
                override fun getItemCount(): Int {
                    return list.size
                }

                override fun createFragment(position: Int): Fragment {
                    return ViewPagerFragment.newInstance(list[position])
                }
            }

            mTabLayoutMediator = TabLayoutMediator(
                tabLayout, viewPager,
            ) { tab, position ->
                tab.setCustomView(tabList[position])
            }

            mTabLayoutMediator?.attach()
            viewPager.registerOnPageChangeCallback(mOnPageChangeCallback)
        }

    }

    private fun tabMenu(position: Int): ColorTrackTextView {
        val colorTrackTextView = ColorTrackTextView(this)
        colorTrackTextView.text = list[position]
        return colorTrackTextView
    }

    override fun onDestroy() {
        mTabLayoutMediator?.detach()
        mActivityViewPagerBinding.viewPager.unregisterOnPageChangeCallback(mOnPageChangeCallback)
        super.onDestroy()
    }
}


