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
package com.lark.http.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;

/**
 * for gson
 *@author YangHaibo
 *
 */
public class GsonImpl extends Json {
	private Gson mGson = new Gson();
	@Override
	public String toJson(Object src) throws JsonConvertFailException {
		try {
			return mGson.toJson(src);
		} catch (Exception e) {
			throw new JsonConvertFailException(e);
		}
	}

	@Override
	public <T> T toObject(String json, Class<T> clazz) throws JsonConvertFailException {
		try {
			return mGson.fromJson(json, clazz);
		} catch (Exception e) {
			throw new JsonConvertFailException(e);
		}
		
	}

	@Override
	public <T> T toObject(String json, Type clazz) throws JsonConvertFailException {
		try {
			return mGson.fromJson(json, clazz);
		} catch (Exception e) {
			throw new JsonConvertFailException(e);
		}
		
	}

	@Override
	public <T> T toObject(byte[] bytes, Class<T> clazz) throws JsonConvertFailException {
		try {
			return mGson.fromJson(new String(bytes), clazz);
		} catch (Exception e) {
			throw new JsonConvertFailException(e);
		}
		
	}

}
