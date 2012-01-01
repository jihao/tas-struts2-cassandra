package com.haojii.prototype.tas.user.actions;

import org.apache.log4j.Logger;

import com.haojii.prototype.tas.model.Tweet;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * the action for user registration
 * 
 * @author ehaojii
 *
 */
public class TweetAction extends ActionSupport {

	private static final Logger logger = Logger.getLogger(TweetAction.class);
	
	private Tweet tweet;
	
	public String tweetUI()  {
		return Action.SUCCESS;
	}
	
	public String tweet() {
		return create();
	}
	
	public String create() {
		logger.debug(tweet.toString());
		//TODO: save tweet to cassandra
		
		return Action.SUCCESS;
	}

	public Tweet getTweet() {
		return tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}
}
