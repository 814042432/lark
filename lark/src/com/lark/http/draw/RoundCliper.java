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
package com.lark.http.draw;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

/**
 * clip bitmap to round cornor
 * 
 * @author YangHaibo
 * 
 */
public class RoundCliper extends Cliper {
	private int cornerRadius;

	public RoundCliper(int cornerRadius) {
		this.cornerRadius = cornerRadius;
	}

	@Override
	public Bitmap clip(Bitmap bitmap) {
		return RoundCornor.createRoundConerImage(bitmap, cornerRadius);
	}

	public static class RoundCornor {
		public static Bitmap createRoundConerImage(Bitmap source, float radious) {
			final Paint paint = new Paint();
			paint.setAntiAlias(true);
			Bitmap target = Bitmap.createBitmap(source.getWidth(),
					source.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(target);
			RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
			canvas.drawRoundRect(rect, radious, radious, paint);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawBitmap(source, 0, 0, paint);
			return target;
		}
	}
}
