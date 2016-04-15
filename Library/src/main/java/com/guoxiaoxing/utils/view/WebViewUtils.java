/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guoxiaoxing.utils.view;

import android.annotation.SuppressLint;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * WebView管理器，提供常用设置
 */
public class WebViewUtils {

	private WebView webView;
	private WebSettings webSettings;
	
	public WebViewUtils(WebView webView){
        this.webView = webView;
        webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
	}

    /**
     * 开启自适应功能
     */
    public WebViewUtils enableAdaptive(){
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        return this;
    }

    /**
     * 禁用自适应功能
     */
    public WebViewUtils disableAdaptive(){
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        return this;
    }

    /**
     * 开启缩放功能
     */
    public WebViewUtils enableZoom(){
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        return this;
    }

    /**
     * 禁用缩放功能
     */
    public WebViewUtils disableZoom(){
        webSettings.setSupportZoom(false);
        webSettings.setUseWideViewPort(false);
        webSettings.setBuiltInZoomControls(false);
        return this;
    }

    /**
     * 开启JavaScript
     */
    @SuppressLint("SetJavaScriptEnabled")
    public WebViewUtils enableJavaScript(){
        webSettings.setJavaScriptEnabled(true);
        return this;
    }

    /**
     * 禁用JavaScript
     */
    public WebViewUtils disableJavaScript(){
        webSettings.setJavaScriptEnabled(false);
        return this;
    }
    
    /**
     * 开启JavaScript自动弹窗
     */
    public WebViewUtils enableJavaScriptOpenWindowsAutomatically(){
    	webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    	return this;
    }
    
    /**
     * 禁用JavaScript自动弹窗
     */
    public WebViewUtils disableJavaScriptOpenWindowsAutomatically(){
    	webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
    	return this;
    }

    /**
     * 返回
     * @return true：已经返回，false：到头了没法返回了
     */
	public boolean goBack(){
		if(webView.canGoBack()){
			webView.goBack();
			return true;
		}else{
			return false;
		}
	}
}