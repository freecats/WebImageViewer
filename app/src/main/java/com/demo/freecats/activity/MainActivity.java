package com.demo.freecats.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.freecats.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    WebView mWebView;
    EditText mEditTextUrl;
    Button mBtnGo;

    private ArrayList<String> mLists = new ArrayList<>();
    private String url = "http://mp.weixin.qq.com/s?__biz=MzA4NzM5MjYxMw==&mid=2655820996&idx=1&sn=0b98b0bd3a9e57eb50943bc50ce1e419&chksm=8b8214cebcf59dd84c0790512e5abcb4a0e9f9dfe80013e74ac5659e3f84f628a3689090b71a&scene=0#wechat_redirect";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mWebView = (WebView) findViewById(R.id.wv);
        mEditTextUrl = (EditText) findViewById(R.id.et_url);
        mBtnGo = (Button) findViewById(R.id.btn_go);


        mEditTextUrl.setText(url);
        mBtnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickGo();
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 适应内容
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        // 设置是否出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.addJavascriptInterface(new MainActivity.JavascriptInterface(), "imageListener");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLists.clear();
                addImageListener();
            }
        });

        //load web info
        clickGo();
    }

    private void clickGo() {
        if (TextUtils.isEmpty(mEditTextUrl.getText().toString())) {
            Toast.makeText(this, "please input url", Toast.LENGTH_LONG).show();
            return;
        }

        url = mEditTextUrl.getText().toString();
        mWebView.loadUrl(url);
    }

    private void addImageListener() {
        //in some case, src of an img tag might be base64 string but no an url
        //we can get image url by data-src if exists
        if (null != mWebView)
            mWebView.loadUrl("javascript:(function(){ "
                    + " var objs = document.getElementsByTagName(\"img\"); "
                    + " for(var i=0;i<objs.length;i++)  "
                    + " {"
                    + "     window.imageListener.addImage(objs[i].src, objs[i].dataset.src); "
                    + "     objs[i].onclick=function()  "
                    + "    {  "
                    + "      window.imageListener.openImage(this.src, this.dataset.src);  "
                    + "     }  "
                    + " } "
                    + " })()");
    }

    public class JavascriptInterface {

        @android.webkit.JavascriptInterface
        public void addImage(String src, String dataSrc) {
            if (null != mLists) {
                String url = null;
                if (!TextUtils.isEmpty(dataSrc) && (dataSrc.startsWith("http:") || dataSrc.startsWith("https:"))) {
                    url = dataSrc;
                } else if (!TextUtils.isEmpty(src) && (src.startsWith("http:") || src.startsWith("https:"))) {
                    url = src;
                }

                if (!TextUtils.isEmpty(url) &&
                        (null != MainActivity.this.url && !MainActivity.this.url.contains(url)))
                    mLists.add(url);
            }

        }

        @android.webkit.JavascriptInterface
        public void openImage(String url, String dataSrc) {
            if (null != mLists && null != url && (mLists.contains(url) || mLists.contains(dataSrc))) {
                int position = -1 == mLists.indexOf(url) ? mLists.indexOf(dataSrc) : mLists.indexOf(url);
                if (-1 == position) return;
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(ImagesShowActivity.KEY_URLS, mLists);
                bundle.putInt(ImagesShowActivity.KEY_INDEX, position);
                Intent intent = new Intent(MainActivity.this, ImagesShowActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

}
