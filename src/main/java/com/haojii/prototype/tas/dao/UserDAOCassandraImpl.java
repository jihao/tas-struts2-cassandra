package com.haojii.prototype.tas.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import me.prettyprint.cassandra.dao.SimpleCassandraDao;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.haojii.prototype.tas.model.User;

/**
 * the cassandra APIs will throw runtime exception HectorException
 * 
 * @author hao
 *
 */
public class UserDAOCassandraImpl implements UserDAO {
	private static Logger logger = Logger.getLogger(UserDAOCassandraImpl.class);
	
	private static final String CF_NAME = "Users";
	private static final String KS_NAME = "tas_struts2_cassandra";
	private static final String HOST_PORT = "localhost:9170";
	
	private static final String CN_PASSWORD = "password";
	private static final String CN_CREATED_AT = "created_at";
	
	public UserDAOCassandraImpl() {
		cassandraDao = new SimpleCassandraDao();
		Cluster cluster = HFactory.getOrCreateCluster("MyCluster", HOST_PORT);
		keyspace = HFactory.createKeyspace(KS_NAME, cluster);
		
		cassandraDao.setKeyspace(keyspace);
		cassandraDao.setColumnFamilyName(CF_NAME);
	}

	private SimpleCassandraDao cassandraDao;
	private Keyspace keyspace;
	
	@Override
	public boolean insertUser(User user) {
		String key = user.getUsername();

		String md5Password = DigestUtils.md5Hex(user.getPassword());
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateFormatted = formatter.format(user.getCreated_at());
		
		cassandraDao.insert(key, CN_PASSWORD, md5Password);
		cassandraDao.insert(key, CN_CREATED_AT, dateFormatted);
		
		return true;
	}

	@Override
	public boolean deleteUser(String username) {
		//seems this is not an easy API to delete one single row
		//cassandraDao.delete(columnName, keys)
		//
		//try another way
		ColumnFamilyTemplate<String, String> template =
                new ThriftColumnFamilyTemplate<String, String>(keyspace, CF_NAME, StringSerializer.get(), StringSerializer.get());
		template.deleteRow(username);
		return true;
	}

	@Override
	public User findUser(String username) {
		User user = new User();
		user.setUsername(username);
		
		String md5password = cassandraDao.get(username, CN_PASSWORD);
		user.setPassword(md5password);
		
		String dateStr = cassandraDao.get(username, CN_CREATED_AT);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = formatter.parse(dateStr);
			user.setCreated_at(date);
		} catch (ParseException e) {
			logger.debug(e.getMessage());
		}
		
		return user;
	}

	@Override
	public boolean updateUser(User user) {
		String key = user.getUsername();

		String md5Password = DigestUtils.md5Hex(user.getPassword());
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateFormatted = formatter.format(user.getCreated_at());
		
		cassandraDao.insert(key, CN_PASSWORD, md5Password);
		cassandraDao.insert(key, CN_CREATED_AT, dateFormatted);
		return true;
	}

	@Override
	public Collection<User> listUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
