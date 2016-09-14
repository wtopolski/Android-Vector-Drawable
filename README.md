# Android Vector Drawable

## Step 1: Traditional solution for drawables

Density:
- **ldpi**	Resources for low-density (ldpi) screens (~120dpi).
- **mdpi**	Resources for medium-density (mdpi) screens (~160dpi). (This is the baseline density.)
- **hdpi**	Resources for high-density (hdpi) screens (~240dpi).
- **xhdpi**	Resources for extra-high-density (xhdpi) screens (~320dpi).
- **xxhdpi**	Resources for extra-extra-high-density (xxhdpi) screens (~480dpi).
- **xxxhdpi**	Resources for extra-extra-extra-high-density (xxxhdpi) uses (~640dpi).

![alt text](https://github.com/wtopolski/android-vector-drawable/blob/master/docs/image1_0.png)

Every time when we want to change colors of app we need to generate all images again.

New colors:
~~~
<resources>
    <color name="colorPrimary">#5E20E5</color>
    <color name="colorPrimaryDark">#2A2333</color>
    <color name="colorAccent">#1BFBC9</color>
    <color name="colorBackground">#ffffff</color>
</resources>
~~~

Before             |  After
:-------------------------:|:-------------------------:
![](https://github.com/wtopolski/android-vector-drawable/blob/master/docs/image1_1.png)  |  ![](https://github.com/wtopolski/android-vector-drawable/blob/master/docs/image1_2.png)

## Step 2: The simplest way of vector drawable

Let's use vectors instead of PNG files. Android Studio will help us in generating vector files by option `File -> New -> Vector Asset`. We can choose image from material design collection or convert SVG from local storage.

Example:
~~~
<vector xmlns:android="http://schemas.android.com/apk/res/android"
        android:width="24dp"
        android:height="24dp"
        android:viewportWidth="24.0"
        android:viewportHeight="24.0">
    <path
        android:fillColor="#1BFBC9"
        android:pathData="M22,9.24l-7.19,-0.62L12,2 9.19,8.63 2,
        9.24l5.46,4.73L5.82,21 12,17.27 18.18,21l-1.63,-7.03L22,
        9.24zM12,15.4l-3.76,2.27 1,-4.28 -3.32,-2.88 4.38,
        -0.38L12,6.1l1.71,4.04 4.38,0.38 -3.32,2.88 1,4.28L12,15.4z"/>
</vector>
~~~

![alt text](https://github.com/wtopolski/android-vector-drawable/blob/master/docs/image2_1.png)

Screen after updates:

![alt text](https://github.com/wtopolski/android-vector-drawable/blob/master/docs/image2_0.png)

It is worth highlight that Git works better with text files then with binary file.

Problems:
- We need to explicitly set color of path `android:fillColor="#ff000"`. Expresions like `android:fillColor="@color/something"` works only on Lollipop and above.
- Gradle still generates set of drawables for different DPI screens.
- Cannot use vector file in expressions like `Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_custom);`. We still need PNG files for this operation, otherwise exception will be thrown.

## Step 3: Complex way for vector drawables

Available since `buildToolsVersion "23.4.0"`+

**build.gradle**
~~~
android {
    compileSdkVersion 24
    buildToolsVersion "24.0.2"

    defaultConfig {
        ...
        vectorDrawables.useSupportLibrary = true
    }
    ...
}
~~~

**MainActivity.java**
~~~
public class MainActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    ...
~~~

**selector_hello_world.xml**
~~~
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/ic_hello_world"/>
</selector>
~~~

**activity_main.xml** - Use selector instead of simple drawable.
~~~
<TextView
    ...
    android:drawableLeft="@drawable/selector_hello_world" />
~~~

**activity_main.xml** - Use `app:srcCompat` instead of `android:src`. `setImageResource` works as always.
~~~
<ImageView
    ...
    app:srcCompat="@drawable/ic_image" />
~~~

### Problem

We need to explicitly set color of path `android:fillColor="#ff000"`. Expresions like `android:fillColor="@color/something"` works only on Lollipop and above.

**Solution**

Now we are able to use color resources: `android:fillColor="@color/colorAccent"` 

### Problem

Gradle still generates set of drawables for different DPI screens.

**Solution**

No PNG files are generated at all.

### Problem

Cannot use vector file in expressions like `Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_custom);`. We still need PNG files for this operation, otherwise exception will be thrown.

**Solution**

New tools are available: VectorDrawableCompat (API 7+) 
~~~
VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(), R.drawable.ic_custom, getTheme());
Drawable vectorDrawable = vectorDrawableCompat.getCurrent();
~~~

Since `buildToolsVersion "24.0.2"` it is even simpler:
~~~
Drawable vectorDrawable = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_custom);
~~~

After above changes, let's again change color of app:
~~~
<resources>
    <color name="colorPrimary">#455A64</color>
    <color name="colorPrimaryDark">#263238</color>
    <color name="colorAccent">#FF9800</color>
    <color name="colorBackground">#9E9E9E</color>
</resources>
~~~

Before             |  After
:-------------------------:|:-------------------------:
![](https://github.com/wtopolski/android-vector-drawable/blob/master/docs/image2_0.png)  |  ![](https://github.com/wtopolski/android-vector-drawable/blob/master/docs/image3_0.png)


## Appendix: Complex vector file

~~~
<vector xmlns:android="http://schemas.android.com/apk/res/android"
        android:width="134dp"
        android:height="194dp"
        android:viewportWidth="67.8"
        android:viewportHeight="97.1">
    <path
        android:fillColor="@color/colorPrimaryDark"
        android:pathData="M51.9,36.4v48.3H15.7V36.4H51.9M54.9,33.4h-3H15.7h-3v3v48.3v3h3h36.2h3v-3V36.4V33.4L54.9,33.4z"/>
    <path
        android:fillColor="@color/colorPrimary"
        android:pathData="M34,79.8c-0.8,0 -1.5,0.7 -1.5,1.5s0.7,1.5 1.5,1.5s1.5,-0.7 1.5,-1.5S34.9,79.8 34,79.8L34,79.8z"/>
    <path
        android:fillColor="@color/colorAccent"
        android:pathData="M38.9,55.3C38.9,55.3 38.9,55.3 38.9,55.3c0.4,0 0.6,0.3 0.5,0.6c0,0.3 -0.3,0.5 -0.5,0.5c0,0 -0.1,0 -0.1,0c-0.3,0 -0.5,-0.3 -0.4,-0.6C38.4,55.4 38.7,55.3 38.9,55.3M38.9,52.3c-1.7,0 -3.2,1.3 -3.5,3c-0.3,1.9 1.1,3.7 3,4c0.2,0 0.3,0 0.5,0c1.7,0 3.2,-1.3 3.5,-3c0.3,-1.9 -1.1,-3.7 -3,-4C39.2,52.3 39,52.3 38.9,52.3L38.9,52.3z"/>
    <path
        android:fillColor="@color/colorAccent"
        android:pathData="M29.4,51.4c-1.1,0 -2,0.8 -2.1,1.9c-0.2,1.2 0.7,2.3 1.8,2.4c0.1,0 0.2,0 0.3,0c1.1,0 2,-0.8 2.1,-1.9c0.2,-1.2 -0.7,-2.3 -1.8,-2.4C29.6,51.4 29.5,51.4 29.4,51.4L29.4,51.4z"/>
    <path
        android:fillColor="@color/colorPrimary"
        android:pathData="M49.4,78.9H18.6V38.5h30.8V78.9zM21.6,75.9h24.8V41.5H21.6V75.9z"/>
    <path
        android:fillAlpha="0.6"
        android:fillColor="@color/colorAccent"
        android:pathData="M46.3,26.5l-2,-6.1l4.1,-1.2l-1.3,-4.5l4.6,-1.4l-1.4,-4.6l5.7,-1.7l0.8,2.9l-2.7,0.8l1.3,4.7l-4.6,1.3l1.3,4.6l-4,1.1l1.1,3.2z"/>
    <path
        android:fillAlpha="0.4"
        android:fillColor="@color/colorAccent"
        android:pathData="M36.4,25.1l-5.3,-3.6l2.5,-3.4l-3.8,-2.9l2.9,-3.8l-3.9,-2.9l3.5,-4.7l2.4,1.8l-1.7,2.3l3.9,2.9l-2.9,3.8l3.8,2.8l-2.5,3.4l2.8,1.9z"/>
    <path
        android:fillAlpha="0.8"
        android:fillColor="@color/colorAccent"
        android:pathData="M25.4,27.5l-6.4,-0.3l0.4,-4.3l-4.7,-0.4l0.4,-4.8l-4.8,-0.5l0.6,-5.8l2.9,0.3l-0.2,2.8l4.8,0.5l-0.5,4.7l4.7,0.5l-0.3,4.1l3.3,0.2z"/>
    <path
        android:fillColor="@color/colorAccent"
        android:pathData="M39.1,67.8h-3c0,-1.1 -0.9,-2 -2,-2s-2,0.9 -2,2h-3c0,-2.8 2.3,-5 5,-5S39.1,65 39.1,67.8z"/>
</vector>
~~~

![](https://github.com/wtopolski/android-vector-drawable/blob/master/docs/image4_0.png)

## Links
- [Supporting Multiple Screens](https://developer.android.com/guide/practices/screens_support.html)
- [AppCompat — Age of the vectors by Chris Banes](https://medium.com/@chrisbanes/appcompat-v23-2-age-of-the-vectors-91cbafa87c88#.aaokp0c47)
- [VectorDrawables – Part 1](https://blog.stylingandroid.com/vectordrawables-part-1/)
- [VectorDrawables – Part 2](https://blog.stylingandroid.com/vectordrawables-part-2/)
- [VectorDrawables – Part 3](https://blog.stylingandroid.com/vectordrawables-part-3/)
- [VectorDrawables – Part 4](https://blog.stylingandroid.com/vectordrawables-part-4/)
