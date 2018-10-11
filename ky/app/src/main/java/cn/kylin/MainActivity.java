package cn.kylin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import cn.kylin.system.SysRotationActivity;


public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.text).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text:
                SysRotationActivity.actionStart(this);
                break;
        }
    }


}










