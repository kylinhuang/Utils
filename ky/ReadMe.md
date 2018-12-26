上传中央仓库
https://juejin.im/entry/58a3ca855c497d0062ae7479

在project目录下执行 
./gradlew install 
./gradlew bintrayUpload


# use
```
Utils.init(mContext);
in the application or firist call must be to call Utils.init(mContext)
```

# Details
### Utils
```
void init(@NonNull final Context context)   初始化在第一次调用 或者 application 中 初始化
Context getContext()        获取ApplicationContext
Handler getMainHandler()    获取主线程 Handler
```


# LogUtils
答应log 的堆栈信息 包含log 位置
   
# ScreenUtils
```
int getScreenWidth()    获取屏幕的宽度（单位：px）
int getScreenHeight()   获取屏幕的高度（单位：px）
void setLandscape(Activity activity)        设置屏幕为横屏
void setPortrait(Activity activity)   设置屏幕为竖屏
boolean isLandscape()                       判断是否横屏
boolean isPortrait()                        判断是否竖屏
int getScreenRotation(Activity activity)    获取屏幕旋转角度
Bitmap captureWithStatusBar(Activity activity)  获取当前屏幕截图，包含状态栏
boolean isScreenLock()              判断是否锁屏
void setSleepDuration(int duration) 设置进入休眠时长 <uses-permission android:name="android.permission.WRITE_SETTINGS" />
int getSleepDuration()              获取进入休眠时长
```
