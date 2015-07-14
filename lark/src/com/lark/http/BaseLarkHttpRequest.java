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

import java.util.Map;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.lark.http.param.HttpParams;

/**
 * 
 * @author YangHaibo
 * 
 */
public abstract class BaseLarkHttpRequest<T> extends Request<T> {

	private HttpParams mHttpParams;

	public BaseLarkHttpRequest(int method, String url,
			ErrorListener errorListener) {
		super(method, url, errorListener);
	}

	public void setHttpParams(HttpParams httpParams) {
		this.mHttpParams = httpParams;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		if (mHttpParams != null) {
			return mHttpParams.getParams();
		}
		return null;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		if (mHttpParams != null) {
			return mHttpParams.getHeaders();
		}
		return super.getHeaders();
	}

}
