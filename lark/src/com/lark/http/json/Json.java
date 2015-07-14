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

/**
 * 
 * @author YangHaibo
 * 
 */
public abstract class Json {
	/**
	 * convert object to json string
	 * 
	 * @param src
	 *            object to be converted
	 * @return json string
	 * @throws JsonConvertFailException
	 */
	public abstract String toJson(Object src) throws JsonConvertFailException;

	/**
	 * convert json string to object
	 * 
	 * @param json
	 *            json string
	 * @param clazz
	 *            target of convert to
	 * @return
	 * @throws JsonConvertFailException
	 */
	public abstract <T> T toObject(String json, Class<T> clazz)
			throws JsonConvertFailException;

	/**
	 * convert json string to object
	 * 
	 * @param json
	 *            json string
	 * @param clazz
	 *            target of convert to
	 * @return
	 * @throws JsonConvertFailException
	 */
	public abstract <T> T toObject(String json, Type clazz)
			throws JsonConvertFailException;

	/**
	 * convert json string to object
	 * 
	 * @param bytes
	 *            bytes of json string
	 * @param clazz
	 *            target of convert to
	 * @return
	 * @throws JsonConvertFailException
	 */
	public abstract <T> T toObject(byte[] bytes, Class<T> clazz)
			throws JsonConvertFailException;
}
