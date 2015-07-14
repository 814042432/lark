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

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import android.text.TextUtils;
import android.util.Log;

import com.lark.http.annotation.Param;

/**
 * this class convert bean to request params
 * 
 * @author YangHaibo
 * 
 */
public class HttpParamModel<T> {
	private static final String TAG = "com.lark.http.param.HttpParamModel";
	private Class<T> mEntity;

	private Map<String, String> mHeader;
	private Map<String, String> mParams;
	public HttpParamModel(){}
	/**
	 * get the generic type real present type.
	 * 
	 * @return Class
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getEntityClass() {
		mEntity = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return mEntity;
	}

	@SuppressWarnings("rawtypes")
	public void convert2Param(Object mBean){
		Class clazz = getEntityClass();
		if(clazz != null){
			Field[] fs = clazz.getDeclaredFields();
			if(fs != null){
				/*Object mBean = null;
				try {
					mBean = clazz.newInstance();
				} catch (InstantiationException e2) {
					Log.e(TAG, "instantiation object failed! ",e2);
					e2.printStackTrace();
				} catch (IllegalAccessException e2) {
					Log.e(TAG, "instantiation object failed! ",e2);
					e2.printStackTrace();
				}*/
				if(mBean == null){
					return;
				}
				Param p;
				for(Field f : fs){
					p = f.getAnnotation(Param.class);
					if(p != null){
						String type = p.type();
						String key = TextUtils.isEmpty(p.key()) ? "key" : p.key();
						String value="";
						if(!f.isAccessible()){
							f.setAccessible(true);
						}
						try {
							value = (String) f.get(mBean);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
						if(HttpParams.Type.TYPE_HEADER.equals(type)){
							if(mHeader == null){
								mHeader = new HashMap<String, String>();
							}
							mHeader.put(key, value);
						}
						else{
							if(mParams == null){
								mParams = new HashMap<String, String>();
							}
							mParams.put(key, value);
						}
					}
				}
			}
		}
	}
	
	/**
	 * if not set,null will be return
	 * @return map
	 */
	public Map<String, String> getHeaders() {
		return mHeader;
	}
	
	/**
	 * if not set,null will be return
	 * @return map
	 */
	public Map<String, String> getParams() {
		return mParams;
	}

}
