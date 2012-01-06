package com.haojii.prototype.tas.services;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.haojii.prototype.tas.dao.TweetDAO;
import com.haojii.prototype.tas.dao.TweetDAOCassandraImpl;
import com.haojii.prototype.tas.model.Tweet;

public class TweetService {
	private TweetDAO tweetDAO;
	private static Logger logger = Logger.getLogger(TweetService.class);
	
	public TweetService(boolean debug) {
		tweetDAO = (debug? null : (new TweetDAOCassandraImpl()));
	}

	public boolean insertTweet(Tweet tweet) {
		return tweetDAO.insertTweet(tweet);
	}

	public boolean deleteTweet(String key) {
		return tweetDAO.deleteTweet(key);
	}

	public Tweet findTweet(String key) {
		return tweetDAO.findTweet(key);
	}

	public boolean updateTweet(Tweet tweet) {
		return tweetDAO.updateTweet(tweet);
	}

	public Collection<Tweet> listTweets() {
		return tweetDAO.listTweets();
	}
	
	public int countTweets() {
		return tweetDAO.countTweets();
	}

}
