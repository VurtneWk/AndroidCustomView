package com.vurtnewk.chapter05

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vurtnewk.chapter05.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mActivityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mActivityMainBinding.root)
        mActivityMainBinding.apply {
            progressBar.maxProgress = 4000
            val animator = ObjectAnimator.ofFloat(0F, 4000F)
            animator.setDuration(4000)
            animator.addUpdateListener {
                val progress = it.getAnimatedValue() as Float
                progressBar.currentProgress = progress.toInt()
            }
            animator.start()



            btnExchange.setOnClickListener {
                shapeView.exchange()
            }

        }
    }
}