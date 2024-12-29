package com.vurtnewk.chapter01

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView

/**
 * createTime:  2024/12/29 16:14
 * author:      vurtnewk
 * description: 解决[android.widget.ScrollView] 嵌套ListView的问题
 */
class MyListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ListView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //解决显示不全问题
        //为什么Int.MAX_VALUE shr 2 ？ 因为MeasureSpec的size就是根据32-2 来设计的最大宽高值
        val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2,MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec)
    }

}