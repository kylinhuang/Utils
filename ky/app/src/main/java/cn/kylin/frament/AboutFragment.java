package cn.kylin.frament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.kylin.R;
import cn.kylin.base.BaseLazyFragment;

public class AboutFragment extends BaseLazyFragment {


    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void initData(@Nullable Bundle bundle) {}

    @Override
    public int bindLayout() {
        return R.layout.fragment_about;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void doBusiness() {
    }



    @Override
    public void onWidgetClick(View view) { }


    @Override
    public void doLazyBusiness() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.empty_hint_add:
//                ActivitySetupSnap.actionStart(getActivity());
//                break;
//            case R.id.titleBar_right_layout:
//                ActivityMore.actionStart(getActivity());
//                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }



}
