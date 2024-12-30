## 仿QQ运动步数进度效果

直接看源码

## QA

### Q：为什么不能在子线程更行UI

### A：

子线程更新UI时的报错是 `ViewRootImpl` 的 `checkThread()` 来检测线程的。

```java
    void checkThread() {
        Thread current = Thread.currentThread();
        if (mThread != current) {
            throw new CalledFromWrongThreadException(
                    "Only the original thread that created a view hierarchy can touch its views."
                            + " Expected: " + mThread.getName()
                            + " Calling: " + current.getName());
        }
    }
```

`mThread = Thread.currentThread();` 是在 `ViewRootImpl` 构造函数里初始化。

`ViewRootImpl` 是在在 `ActivityThread` 的 `handleResumeActivity` 时 创建，由 `WindowManagerGlobal.addView()` 里创建。

## invalidate()流程

由子View循环到最外层的ViewGroup => 然后 draw() => dispatchDraw() 一路往下画 最终画到当前调用invaldate的View的onDraw()方法

## 如何避免过度渲染

开发者选项  打开调试GPU过度绘制，不要见红

- 尽量不要嵌套

- 能不设置背景不要设置背景