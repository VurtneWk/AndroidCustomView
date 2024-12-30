## 自定义属性

### 异常

#### 问题

> A failure occurred while executing com.android.build.gradle.internal.res.ResourceCompilerRunnable
> Resource compilation failed (Failed to compile values resource file /Users/vurtnewk/Documents/data/studyCode/Android/AndroidCustomView/chapter02/build/intermediates/incremental/debug/mergeDebugResources/merged.dir/values/values.xml. Cause: java.lang.IllegalStateException: Can not add resource (com.android.aaptcompiler.ParsedResource@1f0b9380) to table.). Check logs for more details.

#### 原因

系统有的自定义属性，我们是不能重新定义的

#### 解决

改名

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="MyTextView">
        <attr name="customText" format="string" />
        <attr name="customTextColor" format="color" />
        <attr name="customTextSize" format="dimension" />
        <attr name="customMaxLength" format="integer" />
        <attr name="customBackground" format="reference|color" />
        <!-- 枚举 -->
        <attr name="customInputType">
            <enum name="number" value="1" />
            <enum name="text" value="2" />
            <enum name="password" value="3" />
        </attr>
    </declare-styleable>
</resources>
```

```xml
    <com.vurtnewk.chapter02.MyTextView
        android:layout_width="match_parent"
        app:customText="vurtnewk"
        app:customTextColor="#FF0000"
        android:layout_height="wrap_content"/>
```

## 文本内容的BaseLine

![img](https://gitee.com/vurtnewk/typora-image/raw/master/images03/202412292033791.png) 

> [!caution]
>
> (top)     --- 字体顶部的最大距离（包括间距）
>
> (ascent)   --- 可见字符顶部到基线的距离
>
> (baseline)  --- 基线
>
> (descent)   --- 可见字符底部到基线的距离
>
> (bottom)   --- 字体底部的最大距离（包括间距）

**推导**：（先都当做正数） 中线是 y/2 ，中线到BOTTOM的距离是(Descent+Ascent)/2，这个距离又等于Descent+中线到基线的距离，即(Descent+Ascent)/2=基线到中线的距离+Descent ， 所以基线值为 (Descent+Ascent)/2 - Descent

```kotlin
val baseline1 = height / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
val baseline2 = height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2
```

## 字体绘制流程

#### 获取要绘制的内容高宽

```kotlin
//step1
//数据会存储到mTextBounds
 private val mTextBounds = Rect()
 mPaint.getTextBounds(mText, 0, mText.length, mTextBounds)
```

#### 计算文字基准线

```kotlin
val fontMetrics = mPaint.fontMetrics
val baseline = height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2
```

#### 绘制文字

```kotlin
//mText 要绘制的文字 
//paddingLeft.toFloat() x方向起点
//baseline 基准线
//mPaint 画笔
canvas.drawText(mText, paddingLeft.toFloat(), baseline, mPaint)
```

## QA

#### Q：

> 如果让 `MyTextView` 继承于 `LinearLayout` ， 会出来内容吗？

#### A：

> 答案不会。

> [!warning]
>
> 绘制实际是  `draw(Canvas canvas)` 的调用 , 该方法采用了模版设计模式。
>
> - 老版本的控制是在draw方法里的dirtyOpaque
>
> - 新版本是在其它地方进行了优化
>
> 所以当给 `MyTextView` 设置了背景后 就可以正常显示。

> [!caution]
>
> 解决方案：
>
> - `dispatchDraw` 代替 onDraw
> - 设置透明背景
> - 调用 `setWillNotDraw(false)`



