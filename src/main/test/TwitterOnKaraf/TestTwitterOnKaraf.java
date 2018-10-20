package TwitterOnKaraf;

import com.theDrifters.TwitterOnKaraf.TwitterAPIHelper;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Mukesh R Sahay, Muttu Kumar Sukumaran, Paramdeep Saini
 *
 */
@SuppressWarnings("deprecation")
public class TestTwitterOnKaraf {

	TwitterHelper twitterHelper = new TwitterHelper();
	
	/**
	 * @author Mukesh R Sahay
	 * JUnit test case to search based on hashtag
	 * Success if response is not null
	 */
	@Test
	public void testGetTweetsWithHashTag() {
		List<String> response = twitterHelper.getTweetsWithHashTag("sjsu");
		Assert.assertNotNull(response);
	}
	
	/**
	 * @author Mukesh R Sahay
	 * JUnit test case to check tweets on timeline
	 * Success if response is not null
	 */
	@Test
	public void testGetTweetsFromTimeline() {
		List<String> response = twitterHelper.getTweetsFromTimeline();
		Assert.assertNotNull(response);
	}
	
	/**
	 * @author Mukesh R Sahay
	 * JUnit test case to check fetching the favorite tweets
	 * Success if response is not null
	 */
	@Test
	public void testGetTweetsLikedByUser() {
		List<String> response = twitterHelper.getTweetsLikedByUser("sjsu");
		Assert.assertNotNull(response);
	}
	
	/**
	 * @author Muttu Kumar Sukumaran
	 * JUnit test case to check subscribing a user
	 * Success if response is not null
	 */
	@Test
	public void testGetFollowersList() {
		List<String> response = twitterHelper.getFollowersList("sjsu");
		Assert.assertNotNull(response);
	}
	
	/**
	 * @author Muttu Kumar Sukumaran
	 * JUnit test case to check subscribing a user
	 * Success if response is not null
	 */
	@Test
	public void testFollowUser() {
		List<String> response = twitterHelper.followUser("sjsu");
		Assert.assertNotNull(response);
	}
	
	/**
	 * @author Muttu Kumar Sukumaran
	 * JUnit test case to check posting a tweet
	 * Success if response is not null
	 */
	@Test
	public void testPostTheTweet() {
		List<String> response = twitterHelper.postTheTweet("Test Tweet..!!");
		Assert.assertNotNull(response);
	}
	
	/**
	 * @author Paramdeep Saini
	 * JUnit test case to check getting the location for trending topic
	 * Success if response is not null
	 */
	@Test
	public void testGetTrendsLocation() {
		List<String> response = twitterHelper.getTrendsLocation();
		Assert.assertNotNull(response);
	}
	
	/**
	 * @author Paramdeep Saini
	 * JUnit test case to verify getting the list of supported language by Twitter
	 * Success if response is not null
	 */
	@Test
	public void testGetSupportedLanguages() {
		List<String> response = twitterHelper.getSupportedLanguages();
		Assert.assertNotNull(response);
	}
}
