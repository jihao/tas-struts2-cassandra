package com.haojii.prototype.tas.dao;

import java.util.Collection;

import com.haojii.prototype.tas.model.Tweet;

public interface TweetDAO {
	public boolean insertTweet(Tweet tweet);

	public boolean deleteTweet(String key);

	public Tweet findTweet(String key);

	public boolean updateTweet(Tweet tweet);

	public Collection<Tweet> listTweets();
	
	public int countTweets();
}
