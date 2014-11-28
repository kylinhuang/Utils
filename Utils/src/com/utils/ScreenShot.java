package com.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

/** 
* @ClassName: ScreenShot 
* @Description: 截屏
* @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
* @date 2014-11-17 上午10:55:56  
*/
public class ScreenShot {
	
	 private static Bitmap takeScreenShot(Activity activity) {  
         // View是你需要截图的View  
         View view = activity.getWindow().getDecorView();  
         view.setDrawingCacheEnabled(true);  
         view.buildDrawingCache();  
         Bitmap b1 = view.getDrawingCache();  
   
         // 获取状态栏高度  
         Rect frame = new Rect();  
         activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
         int statusBarHeight = frame.top;  
   
         // 获取屏幕长和高  
         int width  = activity.getWindowManager().getDefaultDisplay().getWidth();  
         int height = activity.getWindowManager().getDefaultDisplay().getHeight();  
         // 去掉标题栏  
         Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);  
         view.destroyDrawingCache();  
         return b;  
     }  
   
     private static void savePic(Bitmap b, File filePath) {  
         FileOutputStream fos = null;  
         try {  
             fos = new FileOutputStream(filePath);  
             if (null != fos) {  
                 b.compress(Bitmap.CompressFormat.PNG, 100, fos);  
                 fos.flush();  
                 fos.close();  
             }  
         } catch (FileNotFoundException e) {  
             // e.printStackTrace();  
         } catch (IOException e) {  
             // e.printStackTrace();  
         }  
     }  
   
     /** 
    * shoot 
    * <ul>
    * <li> 截屏 保存图片至本地  </li>
    * </ul>
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @return void    返回类型 
    * @author <a href="https://github.com/kylinhuang" target="_blank">kylinhuang</a>
    * @date 2014-10-30 上午11:18:08
    * @param a Activity
    * @param filePath
    */
    public static void shoot(Activity a, File filePath) {  
         if (filePath == null) {  
             return;  
         }  
         if (!filePath.getParentFile().exists()) {  
             filePath.getParentFile().mkdirs();  
         }  
         ScreenShot.savePic(ScreenShot.takeScreenShot(a), filePath);  
     }  
 }  