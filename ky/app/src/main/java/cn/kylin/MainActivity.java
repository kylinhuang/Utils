package cn.kylin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import cn.kylin.base.BaseActivity;
import cn.kylin.base.BaseLazyFragment;
import cn.kylin.frament.AboutFragment;
import cn.kylin.frament.SystemFragment;
import cn.kylin.frament.ToolFragment;
import cn.kylin.utils.Utils;
import cn.kylin.view.BottomNavigationViewEx;


public class MainActivity extends BaseActivity implements View.OnClickListener {


    private int[] itemIds = new int[]{
            R.id.navigation_snap,
            R.id.navigation_security,
            R.id.navigation_me
    };


    private ViewPager            mVp;
    private BottomNavigationViewEx navigation;
    private ArrayList<BaseLazyFragment> mFragmentList = new ArrayList<>();

    private PagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    };

    @Override
    public void initData(@Nullable Bundle bundle) {
        Utils.init(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

        mVp = findViewById(R.id.vp_status_bar);
        navigation = findViewById(R.id.navigation_status_bar);

        navigation.setItemIconTintList(null);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);

        mFragmentList.add(SystemFragment.newInstance());
        mFragmentList.add(ToolFragment.newInstance());
        mFragmentList.add(AboutFragment.newInstance());

        mVp.setOffscreenPageLimit(3);
        mVp.setAdapter(mAdapter);

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                navigation.setSelectedItemId(itemIds[position]);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()){
//            case R.id.text:
//                SysRotationActivity.actionStart(this);
//                break;
//            case R.id.wifi:
//                WifiActivity.actionStart(this);
//                break;
//            case R.id.alipay:
//                AliPlayActivity.actionStart(this);
//                break;

        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_snap:
                    mVp.setCurrentItem(0);
                    return true;
                case R.id.navigation_security:
                    mVp.setCurrentItem(1);
                    return true;
                case R.id.navigation_me:
                    mVp.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };
}










