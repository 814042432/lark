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
package com.lark.http.param;

import com.lark.http.LarkImageLoader;
import com.lark.http.LarkImageLoaderUtil;
import com.lark.http.LarkImageRequest;

import android.widget.ImageView.ScaleType;
/**
 * The config object for {@link LarkImageLoader} which packaged by {@link LarkImageLoaderUtil}
 * @author YangHaibo
 *
 */
public class HttpImageConfig {
	private int maxWidth;
	private int maxHeight;
	private int defaultLoadingBitmapResId;
	private int defaultErrorBitmapResId;

	public int getDefaultLoadingBitmapResId() {
		return defaultLoadingBitmapResId;
	}

	/**
	 * 
	 * @param defaultLoadingBitmapResId
	 *            when loading image from network,the default display of image's
	 *            resource ID.
	 */
	public void setDefaultLoadingBitmapResId(int defaultLoadingBitmapResId) {
		this.defaultLoadingBitmapResId = defaultLoadingBitmapResId;
	}

	public int getDefaultErrorBitmapResId() {
		return defaultErrorBitmapResId;
	}

	/**
	 * 
	 * @param defaultErrorBitmapResId
	 *            error occurs when loading image from network ,the default
	 *            display of image's resource ID.
	 */
	public void setDefaultErrorBitmapResId(int defaultErrorBitmapResId) {
		this.defaultErrorBitmapResId = defaultErrorBitmapResId;
	}

	private ScaleType scaleType;
	private LarkImageRequest.ClippingBitmapListener clippingBitmapListener;

	public int getMaxWidth() {
		return maxWidth;
	}

	public LarkImageRequest.ClippingBitmapListener getClippingBitmapListener() {
		return clippingBitmapListener;
	}

	/**
	 * 
	 * @param clippingBitmapListener
	 *            customize the bitmap at here
	 */
	public void setClippingBitmapListener(
			LarkImageRequest.ClippingBitmapListener clippingBitmapListener) {
		this.clippingBitmapListener = clippingBitmapListener;
	}

	/**
	 * 
	 * @param maxWidth
	 *            The maximum width of the returned image.
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	/**
	 * 
	 * @param maxHeight
	 *            The maximum height of the returned image.
	 */
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	public ScaleType getScaleType() {
		return scaleType;
	}

	/**
	 * 
	 * @param scaleType
	 *            The ImageViews ScaleType used to calculate the needed image
	 *            size.
	 */
	public void setScaleType(ScaleType scaleType) {
		this.scaleType = scaleType;
	}

}
