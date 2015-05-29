package com.example.foodieandroidforsale;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.StrictMode;

public class SeatToServer {
		public static final String serviceAddr = "http://101.200.174.49:3000";
		
		public String testURLConn1(String urlAdd) throws Exception{

			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
			.detectDiskReads().detectDiskWrites().detectNetwork()
			.penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
			.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
			.penaltyLog().penaltyDeath().build());

			URL url=new URL(serviceAddr+urlAdd);
			System.out.println("url-----------"+url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");	
			conn.setInstanceFollowRedirects(false);		
			System.out.println("Status:"+conn.getResponseCode());
			if (conn.getResponseCode() != 200)
				throw new RuntimeException("失败");
			InputStream is = conn.getInputStream();
			String result=readData(is, "UTF-8");

			conn.disconnect();
			return result;

		}
		
		public static String readData(InputStream inSream, String charsetName)
				throws Exception {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = inSream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			byte[] data = outStream.toByteArray();
			outStream.close();
			inSream.close();
			return new String(data, charsetName);
		}
		
		public  String Get(String url) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());

			String result = null;
	        BufferedReader reader = null;
	        try {
	            HttpClient client = new DefaultHttpClient();
	            HttpGet request = new HttpGet();
	            request.setURI(new URI(serviceAddr + url));
	            System.out.println(serviceAddr + url);
	            HttpResponse response = client.execute(request);
	            System.out.println("2222222");
	            reader = new BufferedReader(new InputStreamReader(response
	                    .getEntity().getContent()));
	 
	            StringBuffer strBuffer = new StringBuffer("");
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                strBuffer.append(line);
	            }
	            result = strBuffer.toString();
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                    reader = null;
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	 
	        return result;
			
		}

		public String Post(String url,List<NameValuePair> postParameters) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());
			
			System.out.println("url-------"+serviceAddr + url);

			String result = null;
			BufferedReader reader = null;
			try {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost();
				request.setURI(new URI(serviceAddr + url));
				

				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters,HTTP.UTF_8);
				request.setEntity(formEntity);

				System.out.println("url-------"+request.toString());
				HttpResponse response = client.execute(request);
				reader = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));

				StringBuffer strBuffer = new StringBuffer("");
				String line = null;
				while ((line = reader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
						reader = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return result;
		}
		
		public static void post2(){
			NameValuePair pair1 = new BasicNameValuePair("name", "test");

	        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
	        pairList.add(pair1);

	        try
	        {
	            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
	                    pairList);
	            // URL使用基本URL即可，其中不需要加参数
	            HttpPost httpPost = new HttpPost("http://10.0.2.2:3000/service/moment/addMoment");
	            // 将请求体内容加入请求中
	            httpPost.setEntity(requestHttpEntity);
	            // 需要客户端对象来发送请求
	            HttpClient httpClient = new DefaultHttpClient();
	            // 发送请求
	            HttpResponse response = httpClient.execute(httpPost);
	            // 显示响应
	            //showResponseResult(response);
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
		}
		
		public static void post3() throws JSONException, ClientProtocolException, IOException{
			HttpPost request = new HttpPost("http://10.0.2.2:3000/service/moment/addMoment");
			// 先封装一个 JSON 对象
			JSONObject param = new JSONObject();
			param.put("name", "cmm3");
			param.put("password", "123456");
			// 绑定到请求 Entry
			StringEntity se = new StringEntity(param.toString());
			request.setEntity(se);
			// 发送请求
			HttpResponse httpResponse = new DefaultHttpClient().execute(request);
			// 得到应答的字符串，这也是一个 JSON 格式保存的数据
			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			// 生成 JSON 对象
			//JSONObject result = new JSONObject( retSrc);
			//String token = result.get("token");
		}
		
		public static void post4(){
		    HttpClient httpClient = new DefaultHttpClient();  
	        try {  
	            HttpPost httpPost = new HttpPost("http://10.0.2.2:3000/service/moment/addMoment");  
	            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();  
	            JSONObject jsonObject = new JSONObject();  
	            jsonObject.put("uemail", "cmm4");  
	            nameValuePair.add(new BasicNameValuePair("jsonString", jsonObject  
	                    .toString()));  
	            //Log.i("lifeweeker", jsonObject2.toString());  
	            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
	        }catch (Exception e){
	        	
	        }
		}
		
		

}