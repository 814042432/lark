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
package com.lark.http.cache;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.lark.http.SDKVersionUtil;
/**
 * using LruCache for internet image dowloading
 * @author YangHaibo
 *
 */
public class BitmapImageCache implements ImageCache {
	private LruCache<String, Bitmap> mMemoryCache;
	private static final float DEFAULT_MEM_PERCENT = 0.25f;

	private BitmapImageCache() {
	}

	private BitmapImageCache(int memCacheSize) {
		init(memCacheSize);
	}

	private void init(int memCacheSize) {
		mMemoryCache = new LruCache<String, Bitmap>(memCacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				final int bitmapSize = getBitmapSize(bitmap) / 1024;
				return bitmapSize == 0 ? 1 : bitmapSize;
			}

			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				super.entryRemoved(evicted, key, oldValue, newValue);
			}
		};
	}

	@SuppressLint("NewApi")
	public static int getBitmapSize(Bitmap bitmap) {
		if (SDKVersionUtil.hasKitKat()) {
			return bitmap.getAllocationByteCount();
		}

		if (SDKVersionUtil.hasHoneycombMR1()) {
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	@Override
	public Bitmap getBitmap(String url) {
		if (!TextUtils.isEmpty(url)) {
			synchronized (mMemoryCache) {
				final Bitmap map = mMemoryCache.get(url);
				if(map != null){
					return map;
				}
			}
		}
		return null;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		if(TextUtils.isEmpty(url) || bitmap == null){
			return;
		}
		synchronized (mMemoryCache) {
			mMemoryCache.put(url, bitmap);
		}
	}
	
    public void clearCache() {
        if (mMemoryCache != null) {
            mMemoryCache.evictAll();
        }
    }

	public static int calculateMemCacheSize(float percent) {
		if (percent < 0.05f || percent > 0.8f) {
			throw new IllegalArgumentException(
					"setMemCacheSizePercent - percent must be "
							+ "between 0.05 and 0.8 (inclusive)");
		}
		return Math.round(percent * Runtime.getRuntime().maxMemory() / 1024);
	}

	public static BitmapImageCache getInstance() {
		BitmapImageCache bitmapImageCache = null;
		if (bitmapImageCache == null) {
			bitmapImageCache = new BitmapImageCache(calculateMemCacheSize(DEFAULT_MEM_PERCENT));
		}
		return bitmapImageCache;
	}
}
