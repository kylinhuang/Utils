package cn.kylin.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;

import java.util.Locale;

public class TextToSpeechHelper {

    private static TextToSpeechHelper mInstance;
    private final Context mContext;
    private final TextToSpeech mTextToSpeech;




    public static TextToSpeechHelper getInstance() {
        if (mInstance == null) {
            mInstance = new TextToSpeechHelper(Utils.getContext());
        }
        return mInstance;
    }


    private TextToSpeechHelper(Context context) {
        mContext = context;
        //创建tts对象
        mTextToSpeech = new TextToSpeech(mContext, mOnInitListener);
    }


    private final TextToSpeech.OnInitListener mOnInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            //判断tts回调是否成功
            if (status == TextToSpeech.SUCCESS) {
                int result1 = mTextToSpeech.setLanguage(Locale.US);
                int result2 = mTextToSpeech.setLanguage(Locale.CHINESE);
                if (result1 == TextToSpeech.LANG_MISSING_DATA || result1 == TextToSpeech.LANG_NOT_SUPPORTED
                        || result2 == TextToSpeech.LANG_MISSING_DATA || result2 == TextToSpeech.LANG_NOT_SUPPORTED){
                    ToastUtils.showShort("数据丢失或不支持");
                }
            }
        }
    };



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void speak(String str) {
        if (!TextUtils.isEmpty(str)){
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            mTextToSpeech.setPitch(1.0f);
            // 设置语速
            mTextToSpeech.setSpeechRate(1.0f);
            //播放语音
            mTextToSpeech.speak(str, TextToSpeech.QUEUE_ADD, null,null);
        }
    }

    protected void onDestroy() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
    }
}
