package com.vurtnewk.chapter01

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * createTime:  2024/12/29 15:49
 * author:      vurtnewk
 * description:
 */
class MyTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        //attrs 从 xml里 获取属性数据
        //defStyleAttr 从xml里的style获取数据
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView, defStyleAttr, 0)
        val mText = typedArray.getString(R.styleable.MyTextView_text)

        typedArray.recycle()
    }

    //自定义View的测量方法
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //布局的测量都是用这个方法指定
        //获取宽高的模式
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        when (widthMode) {
            //warp_content
            MeasureSpec.AT_MOST -> {}
            //确切的值，比如 20dp,或者match_parent
            MeasureSpec.EXACTLY -> {}
            //尽可能的大,很少能用到
            MeasureSpec.UNSPECIFIED -> {}
            else -> {}
        }

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
    }

    /**
     * 绘制View
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        canvas.drawText("") //文本
//        canvas.drawArc() //圆弧
//        canvas.drawCircle() //圆
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            //手指按下
            MotionEvent.ACTION_MOVE -> {}
            //移动
            MotionEvent.ACTION_DOWN -> {}
            //抬起
            MotionEvent.ACTION_UP -> {}
            else -> {}
        }
        return super.onTouchEvent(event)
    }
}