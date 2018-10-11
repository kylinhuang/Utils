package cn.kylin.base;

import android.util.Log;

public abstract class BaseLazyFragment extends BaseFragment {

    private static final String TAG = "BaseLazyFragment";


    public abstract void doLazyBusiness();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(TAG, "setUserVisibleHint: " + isVisibleToUser  +  " class " +getClass().getSimpleName());
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mContentView != null) {
            show();
        }
    }

    public void show() {

    }
}