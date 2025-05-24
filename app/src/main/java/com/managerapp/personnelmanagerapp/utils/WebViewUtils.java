package com.managerapp.personnelmanagerapp.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebViewUtils {

    public static void configureWebViewForPdf(WebView webView, String pdfUrl) {
        WebSettings webSettings = webView.getSettings();

        // Enable JavaScript and DOM storage
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        // Enable zoom controls
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // Enable mixed content
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // Cache and viewport settings
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfUrl));
            Log.d("Urllll", pdfUrl);
            request.setMimeType(mimetype);
            request.addRequestHeader("User-Agent", userAgent);
            request.setDescription("Đang tải file...");
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));

            DownloadManager dm = (DownloadManager) webView.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            dm.enqueue(request);

            Toast.makeText(webView.getContext(), "Bắt đầu tải...", Toast.LENGTH_LONG).show();
        });


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String css = "body { -webkit-tap-highlight-color: transparent; } " +
                        ".drive-viewer-paginated-scrollable { -webkit-overflow-scrolling: touch; } " +
                        ".drive-viewer-toolstrip { position: fixed; top: 0; z-index: 100; }";
                String script = "var style = document.createElement('style');" +
                        "style.innerHTML = '" + css + "';" +
                        "document.head.appendChild(style);";
                view.evaluateJavascript(script, null);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
    }

    public static void loadPdf(WebView webView, String pdfUrl) throws UnsupportedEncodingException {
        String encodedUrl = URLEncoder.encode(pdfUrl, "UTF-8");
        String pdfViewerUrl = "https://mozilla.github.io/pdf.js/web/viewer.html?file=" + encodedUrl;
        webView.loadUrl(pdfViewerUrl);
    }
}
