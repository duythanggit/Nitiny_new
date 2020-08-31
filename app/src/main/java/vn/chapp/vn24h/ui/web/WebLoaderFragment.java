package vn.chapp.vn24h.ui.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.chapp.vn24h.base.BaseFragment;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.di.component.ActivityComponent;
import vn.chapp.vn24h.models.guide.GuideResponse;
import vn.chapp.vn24h.ui.main.BackableActivity;

public class WebLoaderFragment extends BaseFragment implements WebLoaderFrMvpView {

    public static final String TAG = WebLoaderFragment.class.getCanonicalName();
    public static final String ARG_URL = "ARG_URL";

    @Inject
    WebLoaderFrPresenter<WebLoaderFrMvpView> presenter;
    @BindView(R.id.webLoader)
    WebView webLoader;

    private String url;

    public static WebLoaderFragment newInstance(String url) {
        WebLoaderFragment fragment = new WebLoaderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL,url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_web_loader;
    }

    @Override
    protected void init(View v) {
        url = getArguments().getString(ARG_URL,"");
        WebSettings webSettings = webLoader.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webLoader.setWebViewClient(new WebLoaderClient());
        webLoader.setWebChromeClient(new WebChromeClient());

        webLoader.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                // TODO show you progress image
                presenter.getMvpView().showLoading();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                // TODO hide your progress image
                presenter.getMvpView().hideLoading();
                super.onPageFinished(view, url);
            }
        });

        if (!TextUtils.isEmpty(url)) {
            webLoader.loadUrl(url);
        } else {
            presenter.getGuide();
        }

    }

    @Override
    protected void initCreatedView(View v) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, v));
            presenter.onAttach(this);
        }

    }

    @Override
    public void parseContent(GuideResponse content) {
        ((BackableActivity)getActivity()).setToolbarState(true,content.getTitle());
        webLoader.loadData(content.getContent(), "text/html", "UTF-8");
    }

    class WebLoaderClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            try {
                if (!view.getUrl().startsWith("http://") && !view.getUrl().startsWith("https://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(view.getUrl()));
                    startActivity(intent);
                    return true;
                }
            } catch (android.content.ActivityNotFoundException e) {
            }
            return false;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                if (!view.getUrl().startsWith("http://") && !view.getUrl().startsWith("https://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(view.getUrl()));
                    startActivity(intent);
                    return true;
                }
            } catch (android.content.ActivityNotFoundException e) {
            }
            return false;
        }
    }
}
