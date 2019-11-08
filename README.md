# Backdrop Layout

<!--[![Version](https://img.shields.io/badge/Version-1.0.0-brightgreen.svg?style=flat)](https://github.com/yusufpats/backdrop)-->
[ ![Download](https://api.bintray.com/packages/yusufpats/BackdropLayout/backdropLayout/images/download.svg) ](https://bintray.com/yusufpats/BackdropLayout/backdropLayout/_latestVersion)
[![License Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue)](https://opensource.org/licenses/Apache-2.0)
[![Android min SDK](https://img.shields.io/badge/Android%20Min%20SDK-14-brightgreen)](https://bintray.com/yusufpats/BackdropLayout/backdropLayout/_latestVersion)
<!--[![HitCount](http://hits.dwyl.io/yusufpats/yusufpats/backdrop.svg)](http://hits.dwyl.io/yusufpats/yusufpats/backdrop)-->

An easy to implement Layout based on the Backdrop component from Material Design

## Setup
Add this dependency to your app `build.gradle`:
```bash
compile 'com.yusufpats.backdroplayout:backdropLayout:1.0.0'
```

## How to use?
### XML
Add the `BackdropLayout` to your activity `.xml` file:
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.yusufpats.backdroplayout.BackdropLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/backdrop_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/colorPrimary">

    <!-- APPBAR LAYOUT -->
    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/appbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:elevation="0dp">

        <androidx.appcompat.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@color/colorPrimary"
            app:title="Backdrop"
            app:titleTextColor="@color/white" />
    </com.google.android.material.appbar.AppBarLayout>

    <!-- BACK LAYER -->
    <FrameLayout
        android:id="@+id/back_layer"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="?actionBarSize" />

    <!-- FRONT LAYER -->
    <androidx.cardview.widget.CardView
        android:id="@+id/front_layer"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="?actionBarSize"
        app:cardBackgroundColor="#FAF8ED"
        app:cardCornerRadius="16dp"
        app:cardElevation="16dp" />
</com.yusufpats.backdroplayout.BackdropLayout>
```
### Code (Kotlin)
```kotlin        
// let BackdropLayout know about the front layer
backdropLayout.frontSheet = frontLayer
        
// The triggerView can be any view that should trigger the backdrop toggle()
// Toggle backdrop on trigger view click
triggerView.setOnClickListener {
    backdropLayout.toggleBackdrop()
}
```

### Aditional customizations (Kotlin)
| Function | Description                    |
| ------------- | ------------------------------ |
| `frontSheet` | **(Required)** set the front layer of the BackdropLayout     |
| `duration` | duration of toggle animation     |
| `revealHeight` | height of the `frontSheet` that should be visible when backdrop is open     |
| `peakHeight` | height of the `BackLayer` that should be visible when backdrop is open     |
| `interpolator` | animation interpolator     |
##### (NOTE: If Reveal height and Peak Height both are set, the reveal height will take precedence)

#### You can also set the trigger `ImageView` and let the backdrop layout do the magic for you
```kotlin
// `openIcon` - Icon to be used for the trigger view when backdrop is not open
// `closeIcon` - Icon to be used for the trigger view when backdrop is open

fun setTriggerView(triggerView: ImageView, openIcon: Drawable, closeIcon: Drawable);
```


## Author    ![GitHub followers](https://img.shields.io/github/followers/yusufpats?label=Follow&logo=github&style=social)
Yusuf I Patrawala <br>
Github - @yusufpats

## License
```
Copyright 2019 Yusuf Patrawala

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
