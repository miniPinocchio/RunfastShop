package com.example.runfastshop.activity.homepage;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huiliu on 2017/9/3.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class TopImageLinkActivity extends ToolBarActivity {

    @BindView(R.id.web_image_link)
    WebView mWebImageLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_link);
        ButterKnife.bind(this);

        String link = getIntent().getStringExtra("link");
        mWebImageLink.loadUrl(link);
    }
}
