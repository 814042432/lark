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

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView.ScaleType;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.lark.http.LarkImageRequest.ClippingBitmapListener;
/**
 * extends from {@link ImageLoader}.in order to support customized bitmap effects.
 * @author YangHaibo
 *
 */
public class LarkImageLoader extends ImageLoader {

	private LarkImageRequest larkImageRequest;
	private ClippingBitmapListener clippingBitmapListener;

	public LarkImageLoader(RequestQueue queue, ImageCache imageCache) {
		super(queue, imageCache);
	}
	/**
	 * set bitmap clip listener.This interface allow you cut bitmap by yourself(round cornor or circle).
	 * It MUST BE called before {@link com.android.volley.toolbox.ImageLoader#get()} method.
	 * @param clippingBitmapListener
	 */
	public void setClippingBitmapListener(
			ClippingBitmapListener clippingBitmapListener) {
		this.clippingBitmapListener = clippingBitmapListener;
	}

	@Override
	protected Request<Bitmap> makeImageRequest(String requestUrl, int maxWidth,
			int maxHeight, ScaleType scaleType, final String cacheKey) {
		larkImageRequest = new LarkImageRequest(requestUrl,
				new Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						onGetImageSuccess(cacheKey, response);
					}
				}, maxWidth, maxHeight, scaleType, Config.RGB_565,
				new com.android.volley.Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						onGetImageError(cacheKey, error);
					}

				});
		
		larkImageRequest.setClippingBitmapListener(clippingBitmapListener);

		return larkImageRequest;

	}

}
