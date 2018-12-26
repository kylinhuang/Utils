package cn.kylin.activity.tools;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.kylin.R;
import cn.kylin.base.BaseActivity;


public class RedEnvelopeActivity extends BaseActivity {

    private AccessibilityManager accessibilityManager;

    private TextView pluginStatusText;
    private ImageView pluginStatusIcon;

    public static void actionStart(Context mContext) {
        Intent intent = new Intent(mContext, RedEnvelopeActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_red_envelope;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

        //监听AccessibilityService 变化
        accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        accessibilityManager.addAccessibilityStateChangeListener(mAccessibilityStateChangeListener);
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

        pluginStatusText = (TextView) findViewById(R.id.layout_control_accessibility_text);
        pluginStatusIcon = (ImageView) findViewById(R.id.layout_control_accessibility_icon);

        findViewById(R.id.layout_control_settings).setOnClickListener(this);
        findViewById(R.id.layout_control_accessibility).setOnClickListener(this);
        updateServiceStatus();

    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.layout_control_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                settingsIntent.putExtra("title", getString(R.string.preference));
                settingsIntent.putExtra("frag_id", "GeneralSettingsFragment");
                startActivity(settingsIntent);

                break;

            case R.id.layout_control_accessibility:
                try {
                    Toast.makeText(this, getString(R.string.turn_on_toast) + pluginStatusText.getText(), Toast.LENGTH_SHORT).show();
                    Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(accessibleIntent);
                } catch (Exception e) {
                    Toast.makeText(this, getString(R.string.turn_on_error_toast), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessibilityManager.removeAccessibilityStateChangeListener(mAccessibilityStateChangeListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * 更新当前 HongbaoService 显示状态
     */
    private void updateServiceStatus() {
        if (isServiceEnabled()) {
            pluginStatusText.setText(R.string.service_off);
            pluginStatusIcon.setBackgroundResource(R.mipmap.ic_stop);
        } else {
            pluginStatusText.setText(R.string.service_on);
            pluginStatusIcon.setBackgroundResource(R.mipmap.ic_start);
        }
    }

    /**
     * 获取 HongbaoService 是否启用状态
     *
     * @return
     */
    private boolean isServiceEnabled() {
        List<AccessibilityServiceInfo> accessibilityServices =
                accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().equals(getPackageName() + "/.services.HongbaoService")) {
                return true;
            }
        }
        return false;
    }


    private AccessibilityManager.AccessibilityStateChangeListener mAccessibilityStateChangeListener = new AccessibilityManager.AccessibilityStateChangeListener() {
        @Override
        public void onAccessibilityStateChanged(boolean enabled) {
            updateServiceStatus();
        }
    };
}










