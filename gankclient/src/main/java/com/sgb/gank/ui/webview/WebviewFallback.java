// Copyright 2015 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.sgb.gank.ui.webview;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.sgb.gank.util.ActivityUtils;

/**
 * A Fallback that opens a Webview when Custom Tabs is not available
 */
public class WebviewFallback implements CustomTabActivityHelper.CustomTabFallback {
    @Override
    public void openUri(Activity activity, Uri uri) {
        Bundle bundle = new Bundle();
        bundle.putString(WebViewActivity.EXTRA_URL, uri.toString());
        ActivityUtils.startActivity(activity, WebViewActivity.class, bundle);
    }
}
