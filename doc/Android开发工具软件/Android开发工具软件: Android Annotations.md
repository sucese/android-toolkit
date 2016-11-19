
#Android注解

```
dependencies {
    compile 'com.android.support:support-annotations:23.3.0'
}
```

官方文档: https://developer.android.com/studio/write/annotations.html#check-result

#一 Nullness Annotations
Nullness Annotations用来做null的检查。
- @Nullable
Can be null.

- @NonNull
Cannot be null.


```
import android.support.annotation.NonNull;
...

    /** Add support for inflating the <fragment> tag. */
    @NonNull
    @Override
    public View onCreateView(String name, @NonNull Context context,
      @NonNull AttributeSet attrs) {
      ...
      }
...
```

#二 Resource Annotations
Resource Annotations用来指定不同的资源类型。
- @StringRes
References a R.string resource.
- @DrawableRes
References a Drawable resource.
- @ColorRes
References a Color resource.
- @InterpolatorRes
References a Interpolator resource.
- @AnyRes
References any type of R. resource.

```
import android.support.annotation.StringRes;
...
    public abstract void setTitle(@StringRes int resId);
    ...
```

#三 Thread Annotations

Thread Annotations检查当前方法是否在指定的线程类型中被调用。

- @UiThread
- @MainThread
- @WorkerThread
- @BinderThread

#四 Value Constraint Annotations
Value Constraint Annotations用来指定参数的取值范围。

- @IntRange
Checks the range of a int
- @FloatRange
Checks the range of a float
- @Size 
Checks the size of a collection or array, as well as the length of a string.

```
public void setAlpha(@IntRange(from=0,to=255) int alpha) { … }
public void setAlpha(@FloatRange(from=0.0, to=1.0) float alpha) {...}
int[] location = new int[3];
button.getLocationOnScreen(@Size(min=1) location);

```

#五 Permission Annotations
Permission Annotations用来检查操作权限。

- @RequiresPermission 

单个权限
```
@RequiresPermission(Manifest.permission.SET_WALLPAPER)
public abstract void setWallpaper(Bitmap bitmap) throws IOException;
```

多个权限
```
@RequiresPermission(allOf = {
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE})
public static final void copyFile(String dest, String source) {
    ...
}

```

#六 CheckResults Annotations
CheckResults Annotations用来保证当前函数的返回值确定会被使用

- CheckResults

```
@CheckResult(suggest="#enforcePermission(String,int,int,String)")
public abstract int checkPermission(@NonNull String permission, int pid, int uid);
```

#七 CallSuper Annotations
CallSuper Annotations用来保证当前函数会调用父类的实现。

- @CallSuper

```
@CallSuper
protected void onCreate(Bundle savedInstanceState) {
}

```

#八 Enumerated Annotations

Enumerated Annotations可以用来限制函数参数的取值范围。

- @IntDef 
- @StringDef

```
import android.support.annotation.IntDef;
...
public abstract class ActionBar {
    ...
    //Define the list of accepted constants
    @IntDef({NAVIGATION_MODE_STANDARD, NAVIGATION_MODE_LIST, NAVIGATION_MODE_TABS})

    //Tell the compiler not to store annotation data in the .class file
    @Retention(RetentionPolicy.SOURCE)

    //Declare the NavigationMode annotation
    public @interface NavigationMode {}

    //Declare the constants
    public static final int NAVIGATION_MODE_STANDARD = 0;
    public static final int NAVIGATION_MODE_LIST = 1;
    public static final int NAVIGATION_MODE_TABS = 2;

    //Decorate the target methods with the annotation
    @NavigationMode
    public abstract int getNavigationMode();

    //Attach the annotation
    public abstract void setNavigationMode(@NavigationMode int mode);

```


```
import android.support.annotation.IntDef;
...

@IntDef(flag=true, value={
        DISPLAY_USE_LOGO,
        DISPLAY_SHOW_HOME,
        DISPLAY_HOME_AS_UP,
        DISPLAY_SHOW_TITLE,
        DISPLAY_SHOW_CUSTOM
})
@Retention(RetentionPolicy.SOURCE)
public @interface DisplayOptions {}

...
```