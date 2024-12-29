package com.vurtnewk.chapter02

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.orhanobut.logger.Logger


/**
 * createTime:  2024/12/29 15:49
 * author:      vurtnewk
 * description:
 */
class MyTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mText = ""
    private var mTextSize = 15
    private var mTextColor = Color.BLACK
    private val mTextBounds = Rect()

    init {
        //attrs 从 xml里 获取属性数据
        //defStyleAttr 从xml里的style获取数据
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView, defStyleAttr, 0)
        mText = typedArray.getString(R.styleable.MyTextView_customText).orEmpty()
        mTextColor = typedArray.getColor(R.styleable.MyTextView_customTextColor, mTextColor)
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_customTextSize, sp2px(mTextSize))
        typedArray.recycle()

        with(mPaint) {
            textSize = mTextSize.toFloat()
            color = mTextColor
        }
        //如果继承LinearLayout的情况下，onDraw不调用的解决办法之一
        // setWillNotDraw(false)
    }


    //自定义View的测量方法
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //布局的测量都是用这个方法指定
        //获取宽高的模式
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)


        when (widthMode) {
            //warp_content
            MeasureSpec.AT_MOST -> {
                //计算字体的宽高、多少等有关
                mPaint.getTextBounds(mText, 0, mText.length, mTextBounds)
                widthSize = mTextBounds.width() + paddingLeft + paddingRight
            }
            //确切的值，比如 20dp,或者match_parent
            MeasureSpec.EXACTLY -> {}
            //尽可能的大,很少能用到
            MeasureSpec.UNSPECIFIED -> {}
            else -> {}
        }

        when (heightMode) {
            //warp_content
            MeasureSpec.AT_MOST -> {
                //计算字体的宽高、多少等有关
                mPaint.getTextBounds(mText, 0, mText.length, mTextBounds)
                heightSize = mTextBounds.height() + paddingTop + paddingBottom
            }
            //确切的值，比如 20dp,或者match_parent
            MeasureSpec.EXACTLY -> {}
            //尽可能的大,很少能用到
            MeasureSpec.UNSPECIFIED -> {}
            else -> {}
        }

//        widthSize = resolveSize(widthSize,widthMeasureSpec)
        setMeasuredDimension(widthSize, heightSize)
    }


    /**
     * 绘制View
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制文本
        //x 0 开始位置
        //y 基线 baseline
        val fontMetrics = mPaint.fontMetrics
        //1、3
        val baseline1 = height / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
        Logger.d("baseline1 = $baseline1")
//        val baseLine2 = height / 2 + ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom)
//        Logger.d("baseLine2 = $baseLine2")

//        val baseline3 = height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2
//        Logger.d("baseline3 = $baseline3")

        canvas.drawText(mText, paddingLeft.toFloat(), baseline1, mPaint)
        Logger.d("ascent => ${fontMetrics.ascent} , descent => ${fontMetrics.descent} , top => ${fontMetrics.top} , bottom => ${fontMetrics.bottom}")

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

    private fun sp2px(sp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), resources.displayMetrics).toInt()
    }
}