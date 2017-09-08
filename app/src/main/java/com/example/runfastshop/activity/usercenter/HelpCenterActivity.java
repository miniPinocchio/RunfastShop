package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 帮助中心
 */
public class HelpCenterActivity extends ToolBarActivity {

    @BindView(R.id.view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        ButterKnife.bind(this);
        webView.loadUrl("http://120.77.70.27/iwapb/home/user/help.html");
    }
}
