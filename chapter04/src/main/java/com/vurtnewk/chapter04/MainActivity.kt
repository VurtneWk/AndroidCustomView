package com.vurtnewk.chapter04

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vurtnewk.chapter04.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var mActivityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mActivityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mActivityMainBinding.apply {
            btnLtr.setOnClickListener {
                colorTrackTextView.direction = ColorTrackTextView.Direction.LEFT_TO_RIGHT
                val animator = ObjectAnimator.ofFloat(0F, 1F)
                animator.setDuration(2000)
                animator.addUpdateListener {
                    colorTrackTextView.currentProgress = it.getAnimatedValue() as Float
                }
                animator.start()
            }

            btnRtl.setOnClickListener {
                colorTrackTextView.direction = ColorTrackTextView.Direction.RIGHT_TO_LEFT
                val animator = ObjectAnimator.ofFloat(0F, 1F)
                animator.setDuration(2000)
                animator.addUpdateListener {
                    colorTrackTextView.currentProgress = it.getAnimatedValue() as Float
                }
                animator.start()
            }

            btnToViewpager.setOnClickListener { ViewPagerActivity.start(this@MainActivity) }
        }


    }

}