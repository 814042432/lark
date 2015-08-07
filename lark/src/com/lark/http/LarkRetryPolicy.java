package com.lark.http;

import com.android.volley.DefaultRetryPolicy;

/**
 * 
 * @author Yang Haibo 
 *
 */
public class LarkRetryPolicy extends DefaultRetryPolicy{
    /** The default socket timeout in milliseconds */
    public static final int DEFAULT_TIMEOUT_MS = 5000;

    /** The default number of retries */
    public static final int DEFAULT_MAX_RETRIES = 3;

    /** The default backoff multiplier */
    public static final float DEFAULT_BACKOFF_MULT = 1f;

	public LarkRetryPolicy(){
		super(DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT);
	}
}
