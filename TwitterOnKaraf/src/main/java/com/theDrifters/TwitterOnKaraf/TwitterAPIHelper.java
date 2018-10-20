package com.theDrifters.TwitterOnKaraf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * Helper Class for invoking the Twitter APIs using signpost to make the http calls.
 * 
 * @author Mukesh R Sahay, Muttu Kumar Sukumaran, Paramdeep Saini
 *
 */
@SuppressWarnings({ "deprecation", "resource" })
public class TwitterAPIHelper {

	/**
	 * Loads the properties file with the twitter tokens and APIs URL
	 * 
	 * @return Property File
	 * @throws IOException
	 * @author Mukesh R Sahay
	 */
	private Properties getProperties() throws IOException {
		InputStream input = this.getClass().getResourceAsStream("/properties/TwitterConfig.properties");
		Properties apiProperties = new Properties();
		apiProperties.load(input);
		return apiProperties;
	}

	/**
	 * Creates OAuthConsumer for invoking the Twitter APIs
	 * 
	 * @return OAuthConsumer
	 * @throws IOException
	 * @author Mukesh R Sahay
	 */
	public OAuthConsumer getOAuthConsumer() throws IOException {
		Properties apiProperties = getProperties();
		String consumerKey = apiProperties.getProperty("twitter.consumer.key");
		String consumerSecret = apiProperties.getProperty("twitter.consumet.secret");
		String accessToken = apiProperties.getProperty("twitter.access.token");
		String accessTokenSecret = apiProperties.getProperty("twitter.access.secret");
		OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
		oAuthConsumer.setTokenWithSecret(accessToken, accessTokenSecret);
		return oAuthConsumer;
	}

	/**
	 * Execute the Http Post calls for invoking the Post APIs
	 * 
	 * @param apiUrl
	 * @return HttpResponse
	 * @throws OAuthMessageSignerException
	 * @throws OAuthExpectationFailedException
	 * @throws OAuthCommunicationException
	 * @throws IOException
	 * @author Mukesh R Sahay
	 */
	private HttpResponse executeHttpPost(String apiUrl) throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException, IOException {
		HttpPost httprequest = new HttpPost(apiUrl);
		getOAuthConsumer().sign(httprequest);
		HttpClient client = new DefaultHttpClient();
		HttpResponse httpresponse = client.execute(httprequest);
		int responseStatusCode = httpresponse.getStatusLine().getStatusCode();
		System.out.println(responseStatusCode + ":" + httpresponse.getStatusLine().getReasonPhrase());
		return httpresponse;
	}

	/**
	 * Execute the HTTP Get calls for invoking the Get APIs
	 * 
	 * @param apiUrl
	 * @return HttpResponse
	 * @throws OAuthMessageSignerException
	 * @throws OAuthExpectationFailedException
	 * @throws OAuthCommunicationException
	 * @throws IOException
	 * @author Mukesh R Sahay
	 */
	private HttpResponse executeHttpGet(String apiUrl) throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException, IOException {
		HttpGet httprequest = new HttpGet(apiUrl);
		getOAuthConsumer().sign(httprequest);
		HttpClient client = new DefaultHttpClient();
		HttpResponse httpresponse = client.execute(httprequest);
		int responseStatusCode = httpresponse.getStatusLine().getStatusCode();
		System.out.println(responseStatusCode + ":" + httpresponse.getStatusLine().getReasonPhrase());
		return httpresponse;
	}

	/**
	 * Implement Twitter API for searching the tweets based on HashTag
	 * 
	 * @param lookupHashTag
	 * @return List of tweets (Max Count = 15)
	 * @author Mukesh R Sahay
	 */
	public List<String> getTweetsWithHashTag(String lookupHashTag) {
		List<String> listOfTweets = new ArrayList<String>();
		try {
			Properties apiProperties = getProperties();
			String apiUrl = apiProperties.getProperty("twitter.api.search.tweets");
			// Add HasTag to search in the URL
			apiUrl = apiUrl + "?count=15&q=%23" + lookupHashTag;

			HttpResponse apiResponse = executeHttpGet(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {
				// Get the response JSON
				JSONObject jsonobject = new JSONObject(EntityUtils.toString(apiResponse.getEntity()));
				
				JSONArray jsonArray = (JSONArray) jsonobject.get("statuses");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					JSONObject userObject = (JSONObject) jsonObject.get("user");

					// Create tweet with the user's screen name and the tweet text
					String tweet = (String) userObject.get("screen_name") + " : "
							+ (String) jsonObject.get("text");
					listOfTweets.add(tweet);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfTweets;
	}

	/**
	 * Implement Twitter API to follow the specified user.
	 * 
	 * @param screen_name
	 * @return Message stating the status of the request.
	 * @author Muttu Kumar Sukumaran
	 */
	public List<String> followUser(String screen_name) {
		List<String> twitterResponseList = new ArrayList<String>();
		try {
			Properties apiProperties = getProperties();
			String apiUrl = apiProperties.getProperty("twitter.api.followUser");
			// Add screen_name to follow in the URL
			apiUrl = apiUrl + "?screen_name=" + screen_name;

			HttpResponse apiResponse = executeHttpPost(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {
				twitterResponseList.add("Started following the User: "+ screen_name);
			} else {
				twitterResponseList.add("API call was Unsuccessful..!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return twitterResponseList;
	}

	/**
	 * Implement Twitter API to post a tweet on the timeline.
	 * 
	 * @param textToBeTweeted
	 * @return Message stating the status of the request.
	 * @author Muttu Kumar Sukumaran
	 */
	public List<String> postTheTweet(String textToBeTweeted) {
		List<String> twitterResponseList = new ArrayList<String>();
		try {
			Properties apiProperties = getProperties();
			String apiUrl = apiProperties.getProperty("twitter.api.status.postTheTweet");
			// Add text to be tweeted in the URL
			apiUrl = apiUrl + "?status=" + textToBeTweeted.replace(" ", "%20");

			HttpResponse apiResponse = executeHttpPost(apiUrl);
			if (200 == apiResponse.getStatusLine().getStatusCode()) {
				twitterResponseList.add("Tweeted: "+ textToBeTweeted );
			} else {
				twitterResponseList.add("API call was Unsuccessful..!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return twitterResponseList;
	}
	
	/**
	 * Implement Twitter API to get the trending topics and its location
	 * @return Returns the trending topic and its location
	 * @author Paramdeep Saini
	 */

	public List<String> getTrendsLocation() {
		List<String> twitterResponseList = new ArrayList<String>();
		try {
			Properties apiProperties = getProperties();
			String apiUrl = apiProperties.getProperty("twitter.api.trends.location");

			HttpResponse apiResponse = executeHttpGet(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {
				JSONArray jsonArray = new JSONArray(EntityUtils.toString(apiResponse.getEntity()));
				for (int i = 1; i < jsonArray.length() && i <= 15; i++) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					String displayText = (String) object.get("country") + " -> " + (String) object.get("name");
					twitterResponseList.add(displayText);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return twitterResponseList;
	}
	
	/**
	 * Implement Twitter API to display list of supported languages by Twitter
	 * 
	 * @return List of supported languages by Twitter
	 * @author Paramdeep Saini
	 */
	public List<String> getSupportedLanguages() {
		List<String> twitterResponseList = new ArrayList<String>();
		try {
			Properties apiProperties = getProperties();
			String apiUrl = apiProperties.getProperty("twitter.api.supportedLanguage");

			HttpResponse apiResponse = executeHttpGet(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {
				JSONArray jsonArray = new JSONArray(EntityUtils.toString(apiResponse.getEntity()));
				for (int i = 0; i < jsonArray.length() && i < 15; i++) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					String languageName = (String) object.get("name");
					twitterResponseList.add(languageName);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return twitterResponseList;
	}

	/**
	 * Implemen Twitter API to display the tweets from the timeline
	 * 
	 * @return List of Tweets from the timeline (Max count = 15)
	 * @author Paramdeep Saini
	 */
	public List<String> getTweetsFromTimeline() {
		List<String> twitterResponseList = new ArrayList<String>();
		try {
			Properties apiProperties = getProperties();
			// Add number of tweets to be fetched from the timeline
			String apiUrl = apiProperties.getProperty("twitter.api.timeline.tweets") + "?count=15";

			HttpResponse apiResponse = executeHttpGet(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {
				JSONArray jsonArray = new JSONArray(EntityUtils.toString(apiResponse.getEntity()));
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					JSONObject userObject = (JSONObject) jsonObject.get("user");
					String tweetInfo = (String) userObject.get("screen_name") + " : "
							+ (String) jsonObject.get("text");
					// Display the information as screen_name : tweet format
					twitterResponseList.add(tweetInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return twitterResponseList;

	}
		
	/**
	 * Implement Twitter API to get the list of followers for the specified user.
	 * 
	 * @param screen_name
	 * @return Returns the list users following the specified user.
	 * @author Muttu Kumar Sukumaran
	 */

	public List<String> getFollowersList(String screen_name) {
		List<String> listOfFollowers = new ArrayList<String>();
		try {
			Properties apiProperties = getProperties();
			String apiUrl = apiProperties.getProperty("twitter.api.follower.list");
			// Add screen_name to follow in the URL
			apiUrl = apiUrl + "?screen_name=" + screen_name;

			HttpResponse apiResponse = executeHttpGet(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {
				JSONObject jsonobject = new JSONObject(EntityUtils.toString(apiResponse.getEntity()));
				JSONArray jsonArray = (JSONArray) jsonobject.get("users");
				for (int i = 0; i < jsonArray.length() && i < 20; i++) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					String followerInfo = (String) object.get("name") + " : " + (String) object.get("screen_name");
					listOfFollowers.add(followerInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfFollowers;
	}
	
	/**
	 * Implement Twitter API to get the list of most recent tweets liked by the specified user
	 * @return Returns the 20 most recent Tweets liked by the specified user
	 * @author Mukesh R Sahay
	 */

	public List<String> getTweetsLikedByUser(String screen_name) {
		List<String> listOfTweets = new ArrayList<String>();
		try {
			Properties apiProperties = getProperties();
			String apiUrl = apiProperties.getProperty("twitter.api.favorites.tweets");
			// Add screen_name and number of tweets in the URL
			apiUrl = apiUrl + "?count=20&screen_name=" + screen_name;
			
			HttpResponse apiResponse = executeHttpGet(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {
				JSONArray jsonArray = new JSONArray(EntityUtils.toString(apiResponse.getEntity()));
				for (int i = 0; i < jsonArray.length() && i < 20; i++) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					String favoriteTweet = (String) object.get("text");
					listOfTweets.add(favoriteTweet);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfTweets;
	}
}
