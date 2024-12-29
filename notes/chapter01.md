## 简介

自定义View可以认为继承自View，系统没有的效果（ImageView,TextView,Button）

- extends View 
- extends ViewGroup

## 关于View的构造函数的调用时机

```kotlin
class MyTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)
```

### 第一个参数Context

```kotlin
val myTextView = MyTextView(this)
```

### 第两个参数AttributeSet

```xml
<!--attrs.xml-->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="MyTextView">
        <attr name="text" format="string" />
        <attr name="textColor" format="color" />
        <attr name="textSize" format="dimension" />
        <attr name="maxLength" format="integer" />
        <attr name="background" format="reference|color" />
        <!-- 枚举 -->
        <attr name="inputType">
            <enum name="number" value="1" />
            <enum name="text" value="2" />
            <enum name="password" value="3" />
        </attr>
    </declare-styleable>
</resources>
```

```xml
 <!--使用MyTextView时-->
<com.vurtnewk.chapter01.MyTextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:text="@string/app_name"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"  />
```

`MyTextView.kt`

```kotlin
//MyTextView
init {
  val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView, defStyleAttr, 0)
  val mText = typedArray.getString(R.styleable.MyTextView_text)

  typedArray.recycle()
}
```

### 第三个参数defStyleAttr

`themes.xml`

定义style

```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <style name="DefaultMyTextView">
        <item name="inputType">number</item>
    </style>
</resources>
```

`activity_main.xml`

添加了 `style="@style/DefaultMyTextView"`

```xml
    <com.vurtnewk.chapter01.MyTextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:text="@string/app_name"
        style="@style/DefaultMyTextView"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"  />
```

`MyTextView`里直接使用

```kotlin
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView, defStyleAttr, 0)
        val mText = typedArray.getString(R.styleable.MyTextView_text)

        typedArray.recycle()
    }
```



## onMeasure

自定义View的测量方法

```kotlin
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
            MeasureSpec.UNSPECIFIED->{}
            else -> {}
        }

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
    }
```

## 为什么ScrollView嵌套ListView显示不全？

### 原因：

```java
public class ScrollView extends FrameLayout
```

在`ScrollView`的`onMeasure`方法

```java
//ScrollView
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
  super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  //...
}
```

所以调用到了`FrameLayout`的`onMeasure`，在这里调用`measureChildWithMargins`，而`ScrollView`重写了`measureChildWithMargins`，

```java
//ScrollView
@Override
protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,
                                       int parentHeightMeasureSpec, int heightUsed) {
  //...
  // 给子View传递了MeasureSpec.UNSPECIFIED
  final int childHeightMeasureSpec = MeasureSpec.makeSafeMeasureSpec(
    Math.max(0, MeasureSpec.getSize(parentHeightMeasureSpec) - usedTotal),
    MeasureSpec.UNSPECIFIED);

  child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
}
```

ListView(子View)里的`onMeasure`时

```java
//ListView
@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
  //..
  if (heightMode == MeasureSpec.UNSPECIFIED) {
    heightSize = mListPadding.top + mListPadding.bottom + childHeight +
      getVerticalFadingEdgeLength() * 2;
  }
}
```

所以当`UNSPECIFIED`时，只会显示一个 `childHeight` 的高度

### 解决：

```kotlin
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
```

## onDraw

```kotlin
/**
     * 绘制View
     */
override fun onDraw(canvas: Canvas) {
  super.onDraw(canvas)
  canvas.drawText("") //文本
  canvas.drawArc() //圆弧
  canvas.drawCircle() //圆 
}
```

## onTouchEvent

```kotlin
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
```

