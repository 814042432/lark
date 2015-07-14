## Lark ##

**Lark** is http library base on Google **Volley** open source project.It provides easy API for user.The library is not intrude into volley,so you can use volley feature as normal.

### Features ###

- Support METHOD Type - GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, PATCH
- Support **Https** request
- All request base on **LarkStringRequest** which treat every request as raw String.Also you can transfer the raw string to object if it json formated.Or you can intercept the raw response string deliver if you consume the result.
- Provide **LarkImageLoaderUtil** to load image from network nuch more easy and effecient using LruCache.Before setting bitmap to you view,you can clip the bitmap with different sharp(e.g:round corner.The class **RoundCliper** do the this.).
- Much more convenient to set header and post/patch/put parameters.
##Usage##
- Basic **Get** Request:

		String url = "http://www.abc.com";
		LarkHttp.build(getApplicationContext())
				.setBeforeReponseCallback(new BeforeReponseCallback() {
					
					@Override
					public boolean beforeReponse(String rawResponse) {
						
						//return false will be intercept the response deliver to RequestCallback 
						return false;
					}

					@Override
					public void onError(String errorMsg, Throwable tr) {
						
					}
				})
				.get(url);

- **Get** Request(Parase raw string to object using **Google Gson**,the default parser)

    	class Person{
    		public int age;
    		public String name;
    		public String sex;
    	}

		LarkHttp.build(getApplicationContext())
				.setJsonParser(new GsonImpl())//default option,not necessary.
				.setRequestCallback(new RequestCallback() {
					
					@Override
					public void onSuccess(Object object) {
						//parsed object
					}
					
					@Override
					public void onStart() {
						//When start http request
					}
					
					@Override
					public void onError(String errorMsg, Throwable tr) {
						//if error occur
					}
				})
				.mappingTo(Person.class)
				.get(url);


- **Post** request with header and post data:
		 
		There are two method to set request parameters(Of course, can be used in combination).
		STEP 1:

		Method 1:
   		public class PostAndHeaderData extends HttpParamModel<PostAndHeaderData> {
	    	/**
	    	 * server will accept key p1, p2 and h1's value
	    	 */
	    	@Param(type=HttpParams.Type.TYPE_POST,key="p1")
	    	public String poststr1;
	    	
	    	@Param(type=HttpParams.Type.TYPE_POST,key="p2")
	    	public String poststr2;
	    	
	    	@Param(type=HttpParams.Type.TYPE_POST,key="h1")
	    	public String headerstr1;
    	}
		//request bean
   		PostAndHeaderData data = new PostAndHeaderData();
    	data.poststr1 = "Test Post Str1";
    	data.poststr2 = "Test Post Str2";
    	data.headerstr1 = "Test Header Str1";

		HttpParams p = new HttpParams();
		p.setHttpParamModel(data);

		Method 2:
		HttpParams p = new HttpParams();
		Map<String,String> post = new HashMap<String,String>();
		post.put("p1","post-1");
		post.put("p2","post-2");

		Map<String,String> header = new HashMap<String,String>();
		header.put("h1","header-1");
		header.put("h2","header-2");
		
		p.setHeaders(header);
		p.setParams(post);
		
		STEP 2:

		LarkHttp.build(getApplicationContext())
				.setHttpParams(p)
				//optional
				.setBeforeReponseCallback(new BeforeReponseCallback() {
					
					@Override
					public boolean beforeReponse(String rawResponse) {
						//raw response string,not intercept
						return true;
					}

					@Override
					public void onError(String errorMsg, Throwable tr) {
						
					}
				})
				.setRequestCallback(new RequestCallback() {
					
					@Override
					public void onSuccess(Object object) {
						 //parsed object
					}
					
					@Override
					public void onStart() {
						//When start http request
					}
					
					@Override
					public void onError(String errorMsg, Throwable tr) {
						
					}
				})
				.mappingTo(Person.class)
				.post(url);


- **LarkImageLoaderUtil** load image from netwotk:

				String url = "http://download.easyicon.net/png/1063968/128/";
				HttpImageConfig config = new HttpImageConfig();
				config.setDefaultErrorBitmapResId(R.drawable.ic_launcher);
				config.setDefaultLoadingBitmapResId(R.drawable.ic_launcher);
				config.setMaxHeight(200);//allow max height
				config.setMaxWidth(200);//allow max width
				//optional,the following code do cut bitmap to round corner
				config.setClippingBitmapListener(new ClippingBitmapListener() {
					
					@Override
					public Bitmap clip(Bitmap bitmap) {
						RoundCliper cliper = new RoundCliper(10);
						return cliper.clip(bitmap);
					}
				});

				LarkImageLoaderUtil.newInstance(getApplicationContext())
				.get(url, mImageView, config);




## Lark ##

**Lark** 是一个基于谷歌Volley开源库的http库。它对外提供了友好的API，没有侵入Volley源码，都是从Volley扩展出来的，因此，你也可以使用Voley那样使用此库。

### Features ###

- 支持HTTP请求为：GET/POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
- 支持HTTPS请求
- 所有的请求都是基于**LarkStringRequest**这个类，http请求的本质都是String的原始串（除了二进制流需要转码等）。所以，你可以灵活处理返回String。也可以拦截默认处理方式。
- **LarkImageLoaderUtil** 是从网络下载图片的库，它提供了基于LruCache的缓存。你也可以对图片做剪裁操作在设置到View之前。
- 提供了更加方便的api设置http header和post参数（post/patch/put）

##使用方式 ##

- 基本的 **Get** 请求:

		String url = "http://www.abc.com";
		LarkHttp.build(getApplicationContext())
				.setBeforeReponseCallback(new BeforeReponseCallback() {
					
					@Override
					public boolean beforeReponse(String rawResponse) {
						
						//返回true，转发事件会传递，会回调RequestCallback；返回false，转发事件就不会传递
						return false;
					}

					@Override
					public void onError(String errorMsg, Throwable tr) {
						
					}
				})
				.get(url);

- **Get** 请求，转换原始字符串（json）为对象，默认的转换器为GSON

    	class Person{
    		public int age;
    		public String name;
    		public String sex;
    	}

		LarkHttp.build(getApplicationContext())
				.setJsonParser(new GsonImpl())//设置转换器，默认是gson，可选项
				.setRequestCallback(new RequestCallback() {
					
					@Override
					public void onSuccess(Object object) {
						//转换之后的对象
					}
					
					@Override
					public void onStart() {
						//开始请求
					}
					
					@Override
					public void onError(String errorMsg, Throwable tr) {
						//发生错误
					}
				})
				.mappingTo(Person.class)//映射对象，json string转换成的对象
				.get(url);


- 带header参数和post参数的**Post**请求:
		 
		设置参数有两种方式，一种是通过参数对象，一种是通过api设置，也可以组合使用
		第一步 1:

		方法一 1:参数对象
   		public class PostAndHeaderData extends HttpParamModel<PostAndHeaderData> {
	    	/**
	    	 * server will accept key p1, p2 and h1's value
	    	 */
	    	@Param(type=HttpParams.Type.TYPE_POST,key="p1")
	    	public String poststr1;
	    	
	    	@Param(type=HttpParams.Type.TYPE_POST,key="p2")
	    	public String poststr2;
	    	
	    	@Param(type=HttpParams.Type.TYPE_POST,key="h1")
	    	public String headerstr1;
    	}
		//request bean
   		PostAndHeaderData data = new PostAndHeaderData();
    	data.poststr1 = "Test Post Str1";
    	data.poststr2 = "Test Post Str2";
    	data.headerstr1 = "Test Header Str1";

		HttpParams p = new HttpParams();
		p.setHttpParamModel(data);

		方法 2:api设置
		HttpParams p = new HttpParams();
		Map<String,String> post = new HashMap<String,String>();
		post.put("p1","post-1");
		post.put("p2","post-2");

		Map<String,String> header = new HashMap<String,String>();
		header.put("h1","header-1");
		header.put("h2","header-2");
		
		p.setHeaders(header);
		p.setParams(post);
		
		第二步 2:

		LarkHttp.build(getApplicationContext())
				.setHttpParams(p)
				//optional
				.setBeforeReponseCallback(new BeforeReponseCallback() {
					
					@Override
					public boolean beforeReponse(String rawResponse) {
						//raw response string,not intercept
						return true;
					}

					@Override
					public void onError(String errorMsg, Throwable tr) {
						
					}
				})
				.setRequestCallback(new RequestCallback() {
					
					@Override
					public void onSuccess(Object object) {
						 //parsed object
					}
					
					@Override
					public void onStart() {
						//When start http request
					}
					
					@Override
					public void onError(String errorMsg, Throwable tr) {
						
					}
				})
				.mappingTo(Person.class)
				.post(url);


- 下载网络图片**LarkImageLoaderUtil**:

				String url = "http://download.easyicon.net/png/1063968/128/";
				HttpImageConfig config = new HttpImageConfig();
				config.setDefaultErrorBitmapResId(R.drawable.ic_launcher);
				config.setDefaultLoadingBitmapResId(R.drawable.ic_launcher);
				config.setMaxHeight(200);//allow max height
				config.setMaxWidth(200);//allow max width
				//可选项，这里把图片剪裁为圆角，不阻塞主线程
				config.setClippingBitmapListener(new ClippingBitmapListener() {
					
					@Override
					public Bitmap clip(Bitmap bitmap) {
						RoundCliper cliper = new RoundCliper(10);
						return cliper.clip(bitmap);
					}
				});

				LarkImageLoaderUtil.newInstance(getApplicationContext())
				.get(url, mImageView, config);