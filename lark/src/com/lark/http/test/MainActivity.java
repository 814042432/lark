package com.lark.http.test;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lark.http.LarkHttp;
import com.lark.http.LarkHttp.BeforeReponseCallback;
import com.lark.http.LarkHttp.RequestCallback;
import com.lark.http.LarkImageLoaderUtil;
import com.lark.http.LarkImageRequest.ClippingBitmapListener;
import com.lark.http.R;
import com.lark.http.draw.RoundCliper;
import com.lark.http.param.HttpImageConfig;
import com.lark.http.param.HttpParams;

public class MainActivity extends Activity {
	private TextView tv_show;
	private Button btn_raw,btn_json,btn_image;
	private ImageView iv_show;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_show = (TextView) findViewById(R.id.tv_show);
		btn_raw = (Button) findViewById(R.id.btn_raw);
		btn_json = (Button) findViewById(R.id.btn_json);
		btn_image = (Button) findViewById(R.id.btn_image);
		iv_show = (ImageView) findViewById(R.id.iv_image);
		btn_raw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv_show.setText("");
				String url = "http://www.baidu.com";
				LarkHttp.build(getApplicationContext())
				.setBeforeReponseCallback(new BeforeReponseCallback() {
					@Override
					public void onError(String errorMsg, Throwable tr) {
						tv_show.setText("Error Occurs:["+errorMsg+":"+tr.getMessage()+"]");
					}
					
					@Override
					public boolean beforeReponse(String rawResponse) {
						tv_show.setText("Raw Response String:["+rawResponse+"]");
						return false;
					}
				})
				.get(url);
			}
		});
		
		btn_json.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv_show.setText("");
				String url = "http://testserver.coding.io/test";
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
						tv_show.setText("Reponse From Server[Raw String]:"+rawResponse);
						return true;
					}

					@Override
					public void onError(String errorMsg, Throwable tr) {
						tv_show.setText("Error Occurs:["+errorMsg+":"+tr.getMessage()+"]");
					}
				})
				.setRequestCallback(new RequestCallback() {
					
					@Override
					public void onSuccess(Object object) {
						Person p = (Person) object;
						String v1 = tv_show.getText().toString();
						tv_show.setText(v1+"\nConvert to Object:"+p.toString());
					}
					
					@Override
					public void onStart() {
						Log.e("tag","start request");
					}
					
					@Override
					public void onError(String errorMsg, Throwable tr) {
						tv_show.setText("Error Occurs:["+errorMsg+":"+tr.getMessage()+"]");
					}
				})
				.mappingTo(Person.class)
				.post(url);
			}
		});
		
		btn_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String imageurl = "http://download.easyicon.net/png/1063968/128/";
				HttpImageConfig config = new HttpImageConfig();
				config.setDefaultErrorBitmapResId(R.drawable.ic_launcher);
				config.setDefaultLoadingBitmapResId(R.drawable.ic_launcher);
				config.setMaxHeight(200);
				config.setMaxWidth(200);
				config.setClippingBitmapListener(new ClippingBitmapListener() {
					@Override
					public Bitmap clip(Bitmap bitmap) {
						RoundCliper cliper = new RoundCliper(10);
						return cliper.clip(bitmap);
					}
				});
				LarkImageLoaderUtil.newInstance(getApplicationContext())
				.get(imageurl, iv_show, config);
			}
		});
	}
	
	class Person{
		//{"name":"YangHaibo","sex":"ÄÐ","age":27,"money":17.5,"list":[{"book":"C expert","price":90.0},{"book":"C#","price":90.0}]} 
		public String name;
		public String sex;
		public int age;
		public float money;
		public List<llist> list;
//		public llist list;
		@Override
		public String toString() {
			return "Person [name=" + name + ", sex=" + sex + ", age=" + age
					+ ", money=" + money + "]"+list.toString();
		}
		public class llist{
			public String book;
			public double price;
			@Override
			public String toString() {
				return "llist [book=" + book + ", price=" + price + "]";
			}
			
		}
	}
	
	
}
