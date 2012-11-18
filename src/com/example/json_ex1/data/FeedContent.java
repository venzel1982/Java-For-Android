package com.example.json_ex1.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.util.Log;

public class FeedContent {

	/*
	 * создадим класс FeedItem как хранилище данных при создании через
	 * конструктор загоним данные
	 */

	public static class FeedItem {
		public String id;
		public String title;
		public String date;
		public String content;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public FeedItem(String id, String title, String date, String content) {
			setId(id);
			setTitle(title);
			setDate(date);
			setContent(content);
		}

		@Override
		public String toString() {
			// вывод для заголовка поста
			String dateOfPost = getDate();
			String titleOfPost = getTitle();
			return date + " " + title;
		}
	}

	// скажем что у нас есть масив хешей
	public static List<FeedItem> ITEMS = new ArrayList<FeedItem>();
	public static Map<String, FeedItem> ITEM_MAP = new HashMap<String, FeedItem>();
	public static Handler itemsListChangedHandler = null;

	static {

		new Thread(new Runnable() {
			public void run() {
				// получим данные
				String feedConent = readFeed();
				try {
					JSONObject rootItem = new JSONObject(feedConent);

					JSONObject feed = rootItem.getJSONObject("feed");

					JSONArray posts = feed.getJSONArray("entry");

					for (int i = 0; i < posts.length(); i++) {
						JSONObject curPost = posts.getJSONObject(i);

						JSONObject idObj = curPost.getJSONObject("id");
						String id = idObj.optString("$t");
						JSONObject titleObj = curPost.getJSONObject("title");
						String title = titleObj.optString("$t");
						JSONObject dateObj = curPost.getJSONObject("published");
						String date = dateObj.optString("$t");
						JSONObject contentObj = curPost
								.getJSONObject("content");
						String content = contentObj.optString("$t");

						FeedItem curFeedItem = new FeedItem(id, title, date,
								content);
						addItem(curFeedItem);
					}

					FeedContent.itemsListChangedHandler.sendEmptyMessage(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private static void addItem(FeedItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	private static String readFeed() {
		/*
		 * пшлем запрос и получим ответ в зависимости от ответа добавляем
		 * построчно либопишем в лог о ошибке
		 */

		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://android-developers.blogspot.com/feeds/posts/default?alt=json");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e("Feed reading", "Failed to download feed");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}
