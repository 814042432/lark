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

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.android.volley.Request;
import com.lark.http.annotation.Param;

/**
 * set http request params through this object(including header and body param).
 * @author YangHaibo
 * 
 */
public class HttpParams {
	private final String TAG = HttpParams.this.getClass().getSimpleName();
	private HttpParamModel mModel;
	private Map<String, String> mHeaders = new HashMap<String, String>();
	private Map<String, String> mParams = new HashMap<String, String>();
	
	/**
	 * convert model to header/body params according to annotation of {@code com.lark.http.annotation.Param}.
	 * @param model
	 */
	public void setHttpParamModel(HttpParamModel model) {
		this.mModel = model;
		mModel.convert2Param(model);
	}

	/**
	 * add header params to the request
	 * 
	 * @param headers
	 */
	public void setHeaders(Map<String, String> headers) {
		mHeaders.putAll(headers);
	}

	public Map<String, String> getHeaders() {
		if(mModel != null){
			Map<String, String> fromModel = mModel.getHeaders();
			if(fromModel != null){
				mHeaders.putAll(fromModel);
			}
		}
		return mHeaders;
	}

	/**
	 * add POST/PUT/PATCH request params
	 * 
	 * @param params
	 */
	public void setParams(Map<String, String> params) {
		mParams.putAll(params);
	}

	public Map<String, String> getParams() {
		if(mModel != null){
			Map<String, String> fromModel = mModel.getParams();
			if(fromModel != null){
				mParams.putAll(fromModel);
			}
		}
		return mParams;
	}
	
	public class Type{
		public static final String TYPE_HEADER = "header";
		public static final String TYPE_POST = "post";
	}

}
