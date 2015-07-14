package com.lark.http;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lark.http.LarkHttp.BeforeReponseCallback;
import com.lark.http.LarkHttp.RequestCallback;
import com.lark.http.param.HttpParams;
import com.lark.http.test.PostAndHeaderData;

public class MainActivity extends Activity {
	private TextView tv_show;
	private Button btn_https;
	private ImageView iv_show;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_show = (TextView) findViewById(R.id.tv_show);
		btn_https = (Button) findViewById(R.id.btn_https);
		iv_show = (ImageView) findViewById(R.id.iv_image);
		btn_https.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PostAndHeaderData data = new PostAndHeaderData();
				data.poststr1 = "Yang";
				data.poststr2 = "Haibo";
				data.headerstr1 = "Me";
				
				HttpParams p = new HttpParams();
				p.setHttpParamModel(data);
				
				LarkHttp.build(getApplicationContext())
				.setHttpParams(p)
				.setBeforeReponseCallback(new BeforeReponseCallback() {
					
					@Override
					public boolean beforeReponse(String rawResponse) {
						Log.e("yhb","raw string->"+rawResponse);
						tv_show.setText(rawResponse);
						return true;
					}

					@Override
					public void onError(String errorMsg, Throwable tr) {
						
					}
				})
				.setRequestCallback(new RequestCallback() {
					
					@Override
					public void onSuccess(Object object) {
						Person p = (Person) object;
						String v1 = tv_show.getText().toString();
						tv_show.setText(v1+"------"+p.toString());
					}
					
					@Override
					public void onStart() {
						Log.e("yhb","onStart");
					}
					
					@Override
					public void onError(String errorMsg, Throwable tr) {
						
					}
				})
				.mappingTo(Person.class)
				.post("http://192.168.1.104:8080/test/first");
				
				
				
//				for test image loader	
//				String url = "http://download.easyicon.net/png/1063968/128/";
//				HttpImageConfig config = new HttpImageConfig();
//				config.setDefaultErrorBitmapResId(R.drawable.ic_launcher);
//				config.setDefaultLoadingBitmapResId(R.drawable.ic_launcher);
//				config.setMaxHeight(200);
//				config.setMaxWidth(200);
//				config.setClippingBitmapListener(new ClippingBitmapListener() {
//					
//					@Override
//					public Bitmap clip(Bitmap bitmap) {
//						RoundCliper cliper = new RoundCliper(10);
//						return cliper.clip(bitmap);
//					}
//				});
//				LarkImageLoaderUtil.newInstance(getApplicationContext())
//				.get(url, iv_show, config);
				
				
			}
		});
	}
	
	class Person{
		//{"name":"YangHaibo","sex":"ÄÐ","age":27,"money":17.5,"list":[{"book":"C expert","price":90.0},{"book":"C#","price":90.0}]} 
		public String name;
		public String sex;
		public int age;
		public float money;
//		public List<llist> list;
		public llist list;
		@Override
		public String toString() {
			return "Person [name=" + name + ", sex=" + sex + ", age=" + age
					+ ", money=" + money + "]"+list.toString();
		}
		public class llist{
			public String book;
			public double price;
		}
	}
	
	
}
