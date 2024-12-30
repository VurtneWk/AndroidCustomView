package com.vurtnewk.chapter03

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mQQStepView = findViewById<QQStepView>(R.id.QQStepView)
        mQQStepView.mMaxStep = 4000

        val valueAnimator = ObjectAnimator.ofFloat(0F, 3000F)
        valueAnimator.setDuration(3000)
        valueAnimator.addUpdateListener {
            val currentStep = it.getAnimatedValue() as Float
            mQQStepView.mCurrentStep = currentStep.toInt()
        }
        valueAnimator.interpolator = DecelerateInterpolator()

        valueAnimator.start()
    }
}