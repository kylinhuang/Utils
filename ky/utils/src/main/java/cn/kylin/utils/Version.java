package cn.kylin.utils;

import android.text.TextUtils;

public class Version {

    public static boolean isBigger(String currentVersion , String tagVersion) {
        try {
            if (TextUtils.isEmpty(currentVersion)) return  false ;
            if (TextUtils.isEmpty(tagVersion) ) return  false ;

            String[] currentV = currentVersion.trim().split("\\.");
            String[] tagV = tagVersion.trim().split("\\.");


            int currentVSize = currentV.length ;
            int tagVSize = tagV.length ;

            int size =  (currentVSize > tagVSize) ? currentVSize : tagVSize ; //取最长的

            for (int i = 0; i < size; i++) {
                int curV = 0;
                int serV = 0;
                if (i < currentVSize){
                    try {
                        curV = Integer.parseInt(currentV[i]);
                    }catch (NumberFormatException e){
                        return false ;
                    }
                }

                if (i < tagVSize){
                    try {
                        serV = Integer.parseInt(tagV[i]);
                    }catch (NumberFormatException e){
                        return false ;
                    }
                }

                if (curV > serV) { //big
                    return true;
                }else if(curV < serV ){ //
                    return false ;
                }
            }
            return false ;

        } catch (Exception e) {
            e.printStackTrace();
            return false ;
        }
    }
}
