/**
 * HttpRequestUtil.java
 * Copyright 2017 TShangHai ERPLUS Technology Co.,Ltd. 
 * All rights reserved. 
 * Created on 2017年4月24日 上午11:21:42
 */
package com.com.df.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * .
 * <p><br>
 * @author 魏正钦 2017年4月24日 上午11:21:42
 * @version 1.0.0
 */

/**
 * Http请求工具类
 * 
 * @author snowfigure
 * @since 2014-8-24 13:30:56
 * @version v1.0.1
 */
public class HttpRequestUtil {
	static boolean proxySet = false;
	static String proxyHost = "http://192.168.0.104/";
	static int proxyPort = 8080;

	/**
	 * 编码
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncode(String source, String encode) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "0";
		}
		return result;
	}

	public static String urlEncodeGBK(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "0";
		}
		return result;
	}

	/**
	 * 发起http请求获取返回结果
	 * 
	 * @param req_url
	 *            请求地址
	 * @return
	 */
	public static String httpRequest(String req_url) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(req_url);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return "-9";
		}
		return buffer.toString();
	}
	
	/**
	 * 发送http请求取得返回的输入流
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @return InputStream
	 */
	public static InputStream httpRequestIO(String requestUrl) {
		InputStream inputStream = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			// 获得返回的输入流
			inputStream = httpUrlConn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param isproxy
	 *            是否使用代理模式
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param, boolean isproxy) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = null;
			if (isproxy) {// 使用代理模式
				@SuppressWarnings("static-access")
				Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP,
						new InetSocketAddress(proxyHost, proxyPort));
				conn = (HttpURLConnection) realUrl.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) realUrl.openConnection();
			}
			// 打开和URL之间的连接
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST"); // POST方法

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.connect();
			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			// 发送请求参数
			out.write(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 * @return
	 */
	public static String httpRequest(String requestUrl, String requestMethod) {
		StringBuffer result = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod(requestMethod);
			con.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			con.connect();
			BufferedReader inn = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String value = inn.readLine().trim();
			while (value != null) {
				if (!"".equals(value)) {
					result.append(value.trim() + "\n");
				}
				value = inn.readLine();
			}
			inn.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	public static String parseURLPair(Object o) throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> c = o.getClass();
		Field[] fields = c.getDeclaredFields();
		Map<String, Object> map = new TreeMap<String, Object>();
		for (Field field : fields) {
			field.setAccessible(true);
			String name = field.getName();
			Object value = field.get(o);
			if(value != null)
				map.put(name, value);
		}
		Set<Entry<String, Object>> set = map.entrySet();
		Iterator<Entry<String, Object>> it = set.iterator();
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			Entry<String, Object> e = it.next();
			sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	/**
	 * 获取Ip地址
	 * @author  作者 : Jesse
	 * @version 创建时间：2019年4月18日 下午5:54:56
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader(" x-forwarded-for ");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" WL-Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static void main(String[] args) {
		// demo:代理访问
		String url = "http://api.adf.ly/api.php";
		String para = "key=youkeyid&youuid=uid&advert_type=int&domain=adf.ly&url=http://somewebsite.com";

		String sr = HttpRequestUtil.sendPost(url, para, true);
		System.out.println(sr);
	}

}