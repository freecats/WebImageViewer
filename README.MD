# WebImageViewer
Android WebView Image Viewer Demo

##Preview##
![preview](https://github.com/freecats/WebImageViewer/blob/master/preview.gif)

##Topic##
This demo focus on how to get images information from android WebView, and by click any image, we can launch an activity
to display all those images and provide zoom gesture supported.

##Code Snippet#
```java
    /**
     * inject WebView and get images information after WebView on page load finished
     */
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

    /**
     * images information getter listener
     */
    public class JavascriptInterface {

        //get image information
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

        //click image action 
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
```

##License##


    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
