package com.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

public class ApplicationUtil {
	 /**
	     * 通过包名获取应用程序的图标。
	     * @param context
	     *            Context对象。
	     * @param packageName
	     *            包名。
	     * @return 返回包名所对应的应用程序的图标。
	     */
	    public static Drawable getProgramNameByPackageName(Context context,String packageName) {
	        PackageManager pm = context.getPackageManager();
	        Drawable drawable = null;
	        try {
	        	ApplicationInfo  appInfo  = pm.getApplicationInfo(packageName,PackageManager.GET_META_DATA);
	        	drawable  = pm.getApplicationIcon(appInfo);
	        	
	        } catch (NameNotFoundException e) {
	            e.printStackTrace();
	        }
	        return drawable;
	    }
	    
	    
	    
	    /**
	     * 判断一个程序是否显示在前端,根据测试此方法执行效率在11毫秒,无需担心此方法的执行效率
	     * 
	     * @param packageName程序包名
	     * @param context上下文环境
	     * @return true--->在前端,false--->不在前端
	     * 
	     * http://blog.163.com/itcast_android/blog/static/215029084201302611428770/
	     */
	    public static boolean isApplicationShowing(String packageName, Context context) {
	     boolean result = false;
	     
	     ActivityManager am = (ActivityManager) context .getSystemService(Context.ACTIVITY_SERVICE);
	     List<RunningAppProcessInfo> appProcesses = am.getRunningAppProcesses();
	     if (appProcesses != null) {
	      for (RunningAppProcessInfo runningAppProcessInfo : appProcesses) {
	       if (runningAppProcessInfo.processName.equals(packageName)) {
	        int status = runningAppProcessInfo.importance;
	        if (status == RunningAppProcessInfo.IMPORTANCE_VISIBLE  || status == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
	         result = true;
	        }
	       }
	      }
	     }
	     return result;
	    }
	    
	    
	    
	    
		/**
	     * 通过包名获取应用程序的图标。
	     * @param context
	     *            Context对象。
	     * @param packageName
	     *            包名。
	     * @return 返回包名所对应的应用程序的图标。
	     */
	    public static PackageInfo getAppInfoByPackageName(Context context,String packageName) {
	        PackageManager pm = context.getPackageManager();
	        PackageInfo info = null;
	        try {
//	        	ApplicationInfo  appInfo  = pm.getApplicationInfo(packageName,PackageManager.GET_META_DATA);
	        	info = pm.getPackageInfo(packageName, 0);
	        } catch (NameNotFoundException e) {
	            e.printStackTrace();
	        }
	        return info;
	    }

}
