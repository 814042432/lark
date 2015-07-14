/*
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lark.http;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.lark.http.LarkImageRequest.ClippingBitmapListener;
import com.lark.http.cache.BitmapImageCache;
import com.lark.http.param.HttpImageConfig;

/**
 * Get image from internet.It will be cached base on LruCache.
 * 
 * @author YangHaibo
 * 
 */
public class LarkImageLoaderUtil {
	private final String TAG = LarkImageLoaderUtil.this.getClass().getName();
	private RequestQueue mRequestQueue;

	// private HttpImageConfig config;

	// public LarkImageLoaderUtil setHttpImageConfig(HttpImageConfig config) {
	// this.config = config;
	// return this;
	// }

	private LarkImageLoaderUtil() {
	}

	private LarkImageLoaderUtil(Context context) {
		mRequestQueue = VolleyPlus.newRequestQueue(context);
	}

	public static LarkImageLoaderUtil newInstance(Context context) {
		return new LarkImageLoaderUtil(context);
	}

	private void getBitmap(final String url, ImageView imageView, int maxWidth,
			int maxHeight, ScaleType scaleType, int defaultLoadingBitmapResId,
			int defaultErrorBitmapResId, ImageListener listener,
			ClippingBitmapListener clippingBitmapListener) {

		LarkImageLoader imageLoader = new LarkImageLoader(mRequestQueue,
				BitmapImageCache.getInstance());
		if (clippingBitmapListener != null) {
			imageLoader.setClippingBitmapListener(clippingBitmapListener);
		}
		ImageListener imageListener = null;
		if (listener != null) {
			imageListener = listener;
		} else {
			if (imageView != null) {
				imageListener = ImageLoader.getImageListener(imageView,
						defaultLoadingBitmapResId, defaultErrorBitmapResId);
			} else {
				Log.e(TAG,
						"Invalid request(No ImageListener and ImageView).Url:"
								+ url);
			}
		}
		if(scaleType == null){
			scaleType = ScaleType.CENTER_INSIDE;
		}
		imageLoader.get(url, imageListener, maxWidth, maxHeight, scaleType);
	}

	/**
	 * Issues a bitmap request with the given URL.
	 * 
	 * @param url
	 * @param imageView
	 * @param config
	 *            cusomize the image and set params through this object
	 */
	public void get(final String url, ImageView imageView,
			HttpImageConfig config) {
		if (config != null) {
			getBitmap(url, imageView, config.getMaxWidth(),
					config.getMaxHeight(), config.getScaleType(),
					config.getDefaultLoadingBitmapResId(),
					config.getDefaultErrorBitmapResId(), null,config.getClippingBitmapListener());
		} else {
			getBitmap(url, imageView, 0, 0, ScaleType.CENTER_INSIDE, 0, 0, null,null);
		}
	}

	/**
	 * image dealing by yourself according to listener
	 * 
	 * @param url
	 * @param listener
	 */
	public void get(final String url, ImageListener listener) {
		if (listener != null) {
			getBitmap(url, null, 0, 0, ScaleType.CENTER_INSIDE, 0, 0, listener,null);
		}
	}

	/**
	 * Issues a bitmap request with the given URL if that image is not available
	 * in the cache, and returns a bitmap container that contains all of the
	 * data relating to the request (as well as the default image if the
	 * requested image is not available).
	 * 
	 * @param url
	 *            The url of the remote image
	 * @param maxWidth
	 *            The maximum width of the returned image.
	 * @param maxHeight
	 *            The maximum height of the returned image.
	 * @param scaleType
	 *            The ImageViews ScaleType used to calculate the needed image
	 *            size.
	 * @param listener
	 *            The listener to call when the remote image is loaded
	 */
	public void get(final String url, int maxWidth, int maxHeight,
			ScaleType scaleType, ImageListener listener) {
		getBitmap(url, null, maxWidth, maxHeight, scaleType, 0, 0, listener,null);
	}
}
