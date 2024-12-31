## 字体变色思路

- 系统提供的只能够显示一种颜色，需要自定View

- 自定义View继承自谁？
  - `extends View` :  `onMeasure()`  `onDraw()`
  - `extends TextView`  :  `onMeasure()` 不需要实现   `textColor`颜色  `textSize`字体大小



- 一个文字两种颜色:  两个画笔去画 , 用的是裁剪
- 能够从左到右，从右到左
- 整合到ViewPager