package com.theDrifters.TwitterOnKaraf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for implementing the Twitter APIs
 *
 * @author Mukesh R Sahay, Muttu Kumar Sukumaran, Paramdeep Saini
 *
 */
public class TwitterAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * servlet doPost method to implement the Twitter APIs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			TwitterAPIHelper twitterAPIHelper = new TwitterAPIHelper();
			
			String twitterAction = request.getParameter("twitterAction");
			String argumentStr = request.getParameter("argumentStr");
			List<String> twitterResponseList = new ArrayList<String>();

			switch (twitterAction) {
			case "Follow User":
				twitterResponseList = twitterAPIHelper.followUser(argumentStr);
				break;
			case "Tweet":
				twitterResponseList = twitterAPIHelper.postTheTweet(argumentStr);
				break;
			case "Trending From":
				twitterResponseList = twitterAPIHelper.getTrendsLocation();
				break;
			case "Get Tweets with HashTag":
				twitterResponseList = twitterAPIHelper.getTweetsWithHashTag(argumentStr);
				break;
			case "Show Timeline":
				twitterResponseList = twitterAPIHelper.getTweetsFromTimeline();
				break;
			case "Supported Languages":
				twitterResponseList = twitterAPIHelper.getSupportedLanguages();
				break;
			case "Favorite Tweets":
				twitterResponseList = twitterAPIHelper.getTweetsLikedByUser(argumentStr);
				break;
			case "Followers List":
				twitterResponseList = twitterAPIHelper.getFollowersList(argumentStr);
				break;

			default:
				break;
		}
			request.getSession().setAttribute("twitterResponse", twitterResponseList);
			request.getSession().setAttribute("twitterAction", twitterAction);
		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * Servlet doGet method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
		requestDispatcher.forward(request, response);
	}

}
