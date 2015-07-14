package com.lark.http.test;

import com.lark.http.annotation.Param;
import com.lark.http.param.HttpParamModel;
import com.lark.http.param.HttpParams;

/**
 *
 *@author YangHaibo
 *@date 2015Äê7ÔÂ10ÈÕ
 *
 */
public class PostAndHeaderData extends HttpParamModel<PostAndHeaderData> {
	/**
	 * server will accept key p1 and p2 for returned value
	 */
	@Param(type=HttpParams.Type.TYPE_POST,key="p1")
	public String poststr1;
	
	@Param(type=HttpParams.Type.TYPE_POST,key="p2")
	public String poststr2;
	
	@Param(type=HttpParams.Type.TYPE_HEADER,key="h1")
	public String headerstr1;
}
