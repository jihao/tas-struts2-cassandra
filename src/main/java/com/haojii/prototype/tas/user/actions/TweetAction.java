package com.haojii.prototype.tas.user.actions;

import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.haojii.prototype.tas.model.Tweet;
import com.haojii.prototype.tas.model.User;
import com.haojii.prototype.tas.services.ServiceFactory;
import com.haojii.prototype.tas.services.TweetService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * the action for user registration
 * 
 * @author ehaojii
 *
 */
public class TweetAction extends ActionSupport implements ServletRequestAware, ServletContextAware, SessionAware {

	private static final Logger logger = Logger.getLogger(TweetAction.class);
	private Map<String, Object> session;
	private HttpServletRequest request;
	private ServletContext context;
	private String message;
	
	public String tweetUI()  {
		//list all tweets and add to request
		TweetService tweetService = ServiceFactory.getInstance(context).getTweetService();
		Collection<Tweet> tweetList = tweetService.listTweets();
		
		request.setAttribute("tweetList", tweetList); 
		logger.debug("tweetList size="+tweetList.size());
		
		return Action.SUCCESS;
	}
	
	public String tweet() {
		logger.debug(message);
		//save tweet to cassandra
		User user = (User)session.get("SESSION_CURRENT_USER");
		if (user == null)
		{
			addActionError("not allowed to tweet when not signed in");
			return Action.ERROR;
		}
		Tweet tweet = new Tweet();
		tweet.setMessage(message);
		tweet.setUser(user);
		
		TweetService tweetService = ServiceFactory.getInstance(context).getTweetService();
		tweetService.insertTweet(tweet);
		
		return Action.SUCCESS;
	}
	

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
