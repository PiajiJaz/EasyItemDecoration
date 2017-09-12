# EasyItemDecoration
[![](https://jitpack.io/v/PiajiJaz/EasyItemDecoration.svg)](https://jitpack.io/#PiajiJaz/EasyItemDecoration)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)

A flexible Item Decoration written by Kotlin.

## Add
Step 1. Add the JitPack repository to your build file
```
allprojects {
    repositories {
      ...
      maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency
```
dependencies {
    compile 'com.github.PiajiJaz:EasyItemDecoration:1.0.1'
}
```

## Use
### In Kotlin
```kotlin
val easyItemDecoration = EasyItemDecoration.Builder()
        .setDrawDivider(true)
        .setDividerWidth(1f)
        .setDrawOutFrame(true)
        .setLeft(10f)
        .setRight(10f)
        .setTop(10f)
        .setBottom(10f)
        .setDividerColor(R.color.colorDivider)
        .setDrawStartDivider(false)
        .setDrawEndDivider(false)
        .build()
recycler.layoutManager = LinearLayoutManager(baseContext, OrientationHelper.VERTICAL, false)
recycler.adapter = NormalAdapter(getItemLayout(), dataList)
recycler.addItemDecoration(easyItemDecoration)
```

### In Java
```java
EasyItemDecoration itemDecoration = builder.setDrawDivider(true)
        .setDividerWidth(0.5f)
        .setRight(10f)
        .setLeft(10f)
        .setDividerColor(ContextCompat.getColor(this, R.color.colorPrimaryLight))
        .build();
RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
recycler.setLayoutManager(new LinearLayoutManager(this));
recycler.setAdapter(new JavaAdapter(dataList));
EasyItemDecoration.Builder builder = new EasyItemDecoration.Builder();
recycler.addItemDecoration(itemDecoration);
```

* NOTE：Must Set LayoutManager Before add ItemDecoration！

#### DrawOver
You can implement `CustomItemDrawOver` to set `onDrawOver` if you want, just like this
```kotlin
val builder = EasyItemDecoration.Builder()
        .setCustomItemDrawOver(object : CustomItemDrawOver {
            override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                val linearGradient = LinearGradient(0f, 0f, 0f, 100f, intArrayOf(Color.WHITE, 0), null,
                                Shader.TileMode.CLAMP)
                val paint = Paint()
                paint.shader = linearGradient
                c.drawRect(0f, 0f, parent.right.toFloat(), 100f, paint)
            }
        })
```
