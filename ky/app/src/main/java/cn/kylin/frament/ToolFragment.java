package cn.kylin.frament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.kylin.R;
import cn.kylin.adapter.ButtonGridViewAdapter;
import cn.kylin.adapter.OnItemClickListener;
import cn.kylin.base.BaseLazyFragment;
import cn.kylin.common.CoinUtils;
import cn.kylin.activity.tools.RedEnvelopeActivity;
import cn.kylin.activity.tools.RemindActivity;

public class ToolFragment extends BaseLazyFragment {

    private RecyclerView mRecyclerView;

    public static ToolFragment newInstance() {
        return new ToolFragment();
    }

    @Override
    public void initData(@Nullable Bundle bundle) {}

    @Override
    public int bindLayout() {
        return R.layout.fragment_tool;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //实例化Adapter并且给RecyclerView设上
        final ButtonGridViewAdapter adapter = new ButtonGridViewAdapter(mActivity);
        mRecyclerView.setAdapter(adapter);

        adapter.update(CoinUtils.tools);

        // 如果我们想要一个GridView形式的RecyclerView，那么在LayoutManager上我们就要使用GridLayoutManager
        // 实例化一个GridLayoutManager，列数为3
        final GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 3);
        //把LayoutManager设置给RecyclerView
        mRecyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                switch (position){
                    case 0:
                        RemindActivity.actionStart(mActivity);
                        break;
                    case 1:
                        RedEnvelopeActivity.actionStart(mActivity);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void onLongClick(int position) { }
        });
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
