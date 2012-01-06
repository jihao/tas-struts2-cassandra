package com.haojii.prototype.tas.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.log4j.Logger;

import com.haojii.prototype.tas.model.Tweet;
import com.haojii.prototype.tas.model.User;

public class TweetDAOCassandraImpl implements TweetDAO {
	private static Logger logger = Logger.getLogger(TweetDAOCassandraImpl.class);
	
	private static final String CF_NAME = "Tweets";
	private static final String KS_NAME = "tas_struts2_cassandra";
	private static final String HOST_PORT = "localhost:9160";
	
	private static final String CN_MESSAGE = "message";
	private static final String CN_USERNAME = "username";
	
	public TweetDAOCassandraImpl() {	
		Cluster cluster = HFactory.getOrCreateCluster("MyCluster", HOST_PORT);
		keyspace = HFactory.createKeyspace(KS_NAME, cluster);
	}

	private Keyspace keyspace;

	@Override
	public boolean insertTweet(Tweet tweet) {
		ColumnFamilyTemplate<UUID, String> template = new ThriftColumnFamilyTemplate<UUID, String>(keyspace, CF_NAME, UUIDSerializer.get(), StringSerializer.get());
		UUID key = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
		
		ColumnFamilyUpdater<UUID, String> updater = template.createUpdater(key);
		updater.setString(CN_USERNAME, tweet.getUser().getUsername());
		updater.setString(CN_MESSAGE, tweet.getMessage());
		
		template.update(updater);
		
		tweet.setId_key(key.toString());
		return true;
	}

	@Override
	public boolean deleteTweet(String key) {
		ColumnFamilyTemplate<UUID, String> template = new ThriftColumnFamilyTemplate<UUID, String>(keyspace, CF_NAME, UUIDSerializer.get(), StringSerializer.get());
		template.deleteRow(UUID.fromString(key)); 
		
		return true;
	}

	@Override
	public Tweet findTweet(String key) {
		ColumnFamilyTemplate<UUID, String> template = new ThriftColumnFamilyTemplate<UUID, String>(keyspace, CF_NAME, UUIDSerializer.get(), StringSerializer.get());
		ColumnFamilyResult<UUID, String> result = template.queryColumns(UUID.fromString(key));
	    if(result.hasResults()) {
	    	String username = result.getString(CN_USERNAME);
	    	String message = result.getString(CN_MESSAGE);
	    	
	    	User user = findUser(username);
	    	
	    	Tweet tweet = new Tweet();
	    	tweet.setId_key(key);
	    	tweet.setMessage(message);
	    	tweet.setUser(user);
	    	
	    	return tweet;
	    } else {
	    	return null;
	    }
	}
	
	private User findUser(String username) {
		User user = new User();
		user.setUsername(username);
		return user;
	}

	@Override
	public boolean updateTweet(Tweet tweet) {
		ColumnFamilyTemplate<UUID, String> template = new ThriftColumnFamilyTemplate<UUID, String>(keyspace, CF_NAME, UUIDSerializer.get(), StringSerializer.get());
		String key = tweet.getId_key();
		
		ColumnFamilyUpdater<UUID, String> updater = template.createUpdater(UUID.fromString(key));
		updater.setString(CN_USERNAME, tweet.getUser().getUsername());
		updater.setString(CN_MESSAGE, tweet.getMessage());
		
		template.update(updater);

		tweet.setId_key(key);
		return true;
	}

	@Override
	public Collection<Tweet> listTweets() {
		CqlQuery<UUID,String,String> cqlQuery = new CqlQuery<UUID,String,String>(keyspace, UUIDSerializer.get(), StringSerializer.get(), StringSerializer.get());
		
		cqlQuery.setQuery("select * from " + CF_NAME);
		QueryResult<CqlRows<UUID,String,String>> result = cqlQuery.execute();
		CqlRows<UUID,String,String> rows = result.get();
		
		List<Tweet> tweetList = new ArrayList<Tweet>();
		if( rows!=null ) 
		{
			Iterator<Row<UUID, String, String>>  itr = rows.iterator();
			
			while(itr.hasNext()) {
				Row<UUID, String, String> row = itr.next();
				ColumnSlice<String, String> cs = row.getColumnSlice();
				String username = cs.getColumnByName(CN_USERNAME).getValue();
				User user = findUser(username);
				
				String message = cs.getColumnByName(CN_MESSAGE).getValue();
				UUID key = row.getKey();
				Tweet tweet = new Tweet();
				tweet.setUser(user);
				tweet.setMessage(message);
				tweet.setId_key(key.toString());
				tweetList.add(tweet);
			}
		}
		return tweetList;
	}

	@Override
	public int countTweets() {
		CqlQuery<UUID,String,String> cqlQuery = new CqlQuery<UUID,String,String>(keyspace, UUIDSerializer.get(), StringSerializer.get(), StringSerializer.get());
		cqlQuery.setQuery("SELECT COUNT(*) FROM " + CF_NAME);
		QueryResult<CqlRows<UUID,String,String>> result = cqlQuery.execute();
		return result.get().getCount();
	}

}
