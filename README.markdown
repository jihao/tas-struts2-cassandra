# About #

## How to Run ##
 * Eclipse: Run As-> Maven Build ->Goals jetty:run
 * CLI: mvn jetty:run
 
 in a browser go to <http://localhost:8080/tas-struts2-cassandra/>

## Start Cassandra ##
	sudo mkdir -p /var/log/cassandra
	sudo chown -R `whoami` /var/log/cassandra
	sudo mkdir -p /var/lib/cassandra
	sudo chown -R `whoami` /var/lib/cassandra

	./bin/cassandra -f

## init cassandra schema ##
	./bin/cassandra-cli --host localhost
	[default@unknown] create keyspace tas_struts2_cassandra;
	[default@unknown] use tas_struts2_cassandra;
	Authenticated to keyspace: tas_struts2_cassandra
	[default@tas_struts2_cassandra] create column family Users with comparator=UTF8Type and default_validation_class=UTF8Type and key_validation_class=UTF8Type;
	[default@tas_struts2_cassandra] create column family Tweets with comparator=UTF8Type and default_validation_class=UTF8Type and key_validation_class=DateType;
	[default@tas_struts2_cassandra] create column family Userline with comparator=UTF8Type and default_validation_class=UTF8Type and key_validation_class=UTF8Type;
	[default@tas_struts2_cassandra] create column family Timeline with comparator=UTF8Type and default_validation_class=UTF8Type and key_validation_class=DateType;


## Schema ##
### ColumnFamily Users

	Users = {
	  'testuser1':{
	  	{"name":"username","value":"testuser1","timestamp":""},
	  	{"name":"password","value":"MD5(testuser1)","timestamp":""},          
	  },
	  'testuser2':{
	  	{"name":"username","value":"testuser2","timestamp":""},
	  	{"name":"password","value":"MD5(testuser2)","timestamp":""},          
	  },
	}

### ColumnFamily Tweets

	Tweets = {
	  'timeuuid_1':{
	  	{"name":"username","value":"testuser1","timestamp":""},
	  	{"name":"message","value":"this is a tweet message posted by testuser1","timestamp":""},
	  },
	  'timeuuid_2':{
	  	{"name":"username","value":"testuser2","timestamp":""},
	  	{"name":"message","value":"this is a tweet message posted by testuser2","timestamp":""},
	  },
	}

### ColumnFamily Userline, (SuperColumnFamily)

	Row Key => row key of ColumnFamily User 
	SuperColumn name: TimeUUIDType 
	Access: get timeline for a user 
 
	Userline = {
	  'testuser1': { // row key of CF User
	    timeuuid_1 : { // SC Name
	      {"name":"timestamp","value":"timestamp of tweet","timestamp":""},
	  	  {"name":"tweet_id","value":"uuid of tweet","timestamp":""},
	  	},
	  	... // more timeline for testuser1
	  	// latest tweet last 
	  	timeuuid_2 : { // SC Name
	      {"name":"timestamp","value":"timestamp of tweet","timestamp":""},
	  	  {"name":"tweet_id","value":"uuid of tweet","timestamp":""},
	  	},
	  },
	}

### ColumnFamily Timeline

	Timeline = {
	    timeuuid_1 : { 
	      {"name":"timestamp","value":"timestamp of tweet","timestamp":""},
	  	  {"name":"tweet_id","value":"uuid of tweet","timestamp":""},
	  	  {"name":"username","value":"testuser1","timestamp":""},
	  	},
	  	... 
	  	// latest tweet last 
	  	timeuuid_2 : { 
	      {"name":"timestamp","value":"timestamp of tweet","timestamp":""},
	  	  {"name":"tweet_id","value":"uuid of tweet","timestamp":""},
	  	  {"name":"username","value":"testuser2","timestamp":""},
	  	},
	}
