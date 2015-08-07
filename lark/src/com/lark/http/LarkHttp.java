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

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.lark.http.json.GsonImpl;
import com.lark.http.json.Json;
import com.lark.http.json.JsonConvertFailException;
import com.lark.http.param.HttpParams;

/**
 * the entrance of http request.Every request is asynchronous.
 * @author YangHaibo
 * 
 */
public class LarkHttp {
	private final String TAG = LarkHttp.this.getClass().getName();
	private Json mJson;
	private HttpParams mHttpParams;
	private BeforeReponseCallback mBeforeReponseCallback;
	private RequestCallback mRequestCallback;
	private Class mTarget;
	private Context mContext;
	private RequestQueue mRequestQueue;
	private String mUrl;
	private int mRequestMethod = Request.Method.GET;
	private LarkStringRequest mLarkStringRequest;

	public static LarkHttp build(Context context){
		return new LarkHttp(context);
	}
	private LarkHttp(){}
	private LarkHttp(Context context) {
		this.mContext = context;
		mRequestQueue = VolleyPlus.newRequestQueue(mContext);
	}
	/**
	 * set customize json parser
	 * @param json
	 * @return
	 */
	public LarkHttp setJsonParser(Json json) {
		this.mJson = json;
		return this;
	}
	/**
	 * POST/PUT/PATCH operation accept body params,GET/DELETE/HEAD will not.All operation accept header params.So using {@code HttpParams} should know it.
	 * @param httpParam
	 * @return
	 */
	public LarkHttp setHttpParams(HttpParams httpParam) {
		this.mHttpParams = httpParam;
		return this;
	}

	public LarkHttp setBeforeReponseCallback(BeforeReponseCallback callback) {
		this.mBeforeReponseCallback = callback;
		return this;
	}

	public LarkHttp mappingTo(Class clazz) {
		this.mTarget = clazz;
		return this;
	}

	public LarkHttp setRequestCallback(RequestCallback callback) {
		this.mRequestCallback = callback;
		return this;
	}
	/**
	 * @return request of LarkStringRequest which extends {@link Request}
	 */
	public Request getRequest(){
		return mLarkStringRequest;
	}
	/**
	 * http delete request,body params will be discard
	 * @param url
	 */
	public void head(final String url){
		this.mUrl = url;
		this.mRequestMethod = Request.Method.HEAD;
		sendRequest();
	}
	/**
	 * http delete request,body params will be discard.
	 * @param url
	 */
	public void delete(final String url){
		this.mUrl = url;
		this.mRequestMethod = Request.Method.DELETE;
		sendRequest();
	}
	/**
	 * http get request,body params will be discard
	 * @param url
	 */
	public void get(final String url){
		this.mUrl = url;
		this.mRequestMethod = Request.Method.GET;
		sendRequest();
	}
	/**
	 * http post request,accept body params.
	 * @param url
	 */
	public void post(final String url){
		this.mUrl = url;
		this.mRequestMethod = Request.Method.POST;
		sendRequest();
	}
	/**
	 * http patch request,accept body params.
	 * @param url
	 */
	public void patch(final String url){
		this.mUrl = url;
		this.mRequestMethod = Request.Method.PATCH;
		sendRequest();
	}
	/**
	 * http put request,accept body params.
	 * @param url
	 */
	public void put(final String url){
		this.mUrl = url;
		this.mRequestMethod = Request.Method.PUT;
		sendRequest();
	}
	
	private void init(){
		mLarkStringRequest = new LarkStringRequest(mRequestMethod, mUrl, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				if(mBeforeReponseCallback != null){
					if(mBeforeReponseCallback.beforeReponse(response)){//custom the response string,then convert it.
						convertAnddispatcherResponse(response);
					}
					else{
						Log.v(TAG, "dispatch reponse was interupted by beforeReponse()<return false>.");
					}
				}
				else{//direct convert the response string
					convertAnddispatcherResponse(response);
				}
			}
		},new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				StringBuilder errorMsg = new StringBuilder("Volley Internal Error:");
				if(error instanceof ServerError){
					int code = error.networkResponse.statusCode;
					String msg = "remote server error";
					errorMsg.append("HTTP Status ").append(code).append("/").append(msg);
				}
				else if(error instanceof NoConnectionError){
					errorMsg.append("No network connection.");
				}
				else if(error instanceof NetworkError){
					errorMsg.append("Network error.");
				}
				else if(error instanceof TimeoutError) {
					errorMsg.append("Request timeout");
				}
				if(mBeforeReponseCallback != null){
					mBeforeReponseCallback.onError(errorMsg.toString(),error);
				}
				if(mRequestCallback != null){
					mRequestCallback.onError(errorMsg.toString(),error);
				}
				else{
					Log.v(TAG, "Not dealing volley error.Error:"+error.getMessage());
				}
			}
		});
		mLarkStringRequest.setRetryPolicy(new LarkRetryPolicy());
	}
	
	private void sendRequest(){
		init();
		mLarkStringRequest.setHttpParams(mHttpParams);
		if(mRequestCallback != null){
			mRequestCallback.onStart();
		}
		mRequestQueue.add(mLarkStringRequest);
	}
	
	@SuppressWarnings("unchecked")
	private Object json2Object(String json) throws JsonConvertFailException{
		if(mJson != null){
			if(mTarget != null){
				return mJson.toObject(json, mTarget);
			}
		}
		else{
			Json defJson = new GsonImpl();
			return defJson.toObject(json, mTarget);
		}
		return null;
	}
	
	private void convertAnddispatcherResponse(String response){
		try {
			Object obj = json2Object(response);
			if(mRequestCallback != null){
				mRequestCallback.onSuccess(obj);
			}
		} catch (JsonConvertFailException e) {
			if(mRequestCallback != null){
				mRequestCallback.onError("Http request success,but convert it from json to object failed!",e);
			}
			e.printStackTrace();
		}
	}
	/**
	 * The interface of http response.
	 * @author YangHaibo
	 */
	public static interface RequestCallback {
		/**
		 * when start send request
		 */
		public void onStart();
		/**
		 * the http request successful response from server will be called
		 * @param object
		 */
		public void onSuccess(Object object);
		/**
		 * the error occur will be called
		 * @param errorMsg
		 * @param tr
		 */
		public void onError(String errorMsg,Throwable tr);
	}
	/**
	 * the interface of customize dealing the http response.
	 * @author YangHaibo
	 *
	 */
	public static interface BeforeReponseCallback {
		/**
		 * you can handle the reponse string before it convert to real object.
		 * 
		 * @param response
		 *            the raw string of http reponse
		 * @return true/fasle If true,{@link RequestCallback} 's {@link RequestCallback#onSuccess(Object object)},{@link RequestCallback#onError(String errorMsg,Throwable tr)}
		 *         will be call.Otherwise the event chain will be interrupt. you
		 *         can deal it in custom way.
		 */
		public boolean beforeReponse(String rawResponse);
		
		/**
		 * the error occur will be called
		 * @param errorMsg
		 * @param tr volley exception
		 */
		public void onError(String errorMsg,Throwable tr);
	}
}
