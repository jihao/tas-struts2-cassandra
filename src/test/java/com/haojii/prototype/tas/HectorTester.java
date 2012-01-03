package com.haojii.prototype.tas;

import java.util.Arrays;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class HectorTester {

	private static final String TMP_KEYSPACE = "tmp_keyspace";
	private static Logger logger = Logger.getLogger(HectorTester.class);
	
	@BeforeClass
	public static void setUpBeforeClass(){
		HFactory.getOrCreateCluster("single-node-cluster","localhost:9160");
		setUp_createSchema();
	}
	@AfterClass
	public static void tearDownAfterClass(){
		teamDown_dropSchema();
		Cluster cluster = HFactory.getOrCreateCluster("single-node-cluster","localhost:9160");
		HFactory.shutdownCluster(cluster);
	}

	private static void setUp_createSchema() {
		Cluster cluster = HFactory.getOrCreateCluster("single-node-cluster","localhost:9160");
		ColumnFamilyDefinition cfdef = HFactory.createColumnFamilyDefinition(TMP_KEYSPACE, "Users", ComparatorType.UTF8TYPE);

		int replicationFactor = 1;
		KeyspaceDefinition ksdf = HFactory.createKeyspaceDefinition(
				TMP_KEYSPACE, ThriftKsDef.DEF_STRATEGY_CLASS,
				replicationFactor, Arrays.asList(cfdef));

		// Add the schema to the cluster.
		// "true" as the second param means that Hector will block until all
		// nodes see the change.
		cluster.addKeyspace(ksdf, true);
		
		//TODO: what's the difference? seems it can be omitted
		//cluster.addColumnFamily(cfdef, true);
		
		prepareData();
				
	}
	private static void prepareData() {
		Cluster cluster = HFactory.getOrCreateCluster("single-node-cluster","localhost:9160");
		Keyspace ksp = HFactory.createKeyspace(TMP_KEYSPACE, cluster);
		
		ColumnFamilyTemplate<String, String> template =
                new ThriftColumnFamilyTemplate<String, String>(ksp, "Users", StringSerializer.get(), StringSerializer.get());
		
		// <String, String> correspond to key and Column name.
		ColumnFamilyUpdater<String, String> updater = template.createUpdater("steve");
		updater.setString("firstname", "steve");
		updater.setString("lastname", "jobs");
		updater.setInteger("age", 56);
		updater.setLong("timestamp", System.currentTimeMillis());
		
		ColumnFamilyUpdater<String, String> updater2 = template.createUpdater("mark");
		updater2.setString("firstname", "mark");
		updater2.setString("lastname", "zuckerberg");
		updater2.setInteger("age", 27);
		updater2.setLong("timestamp", System.currentTimeMillis());
		
		try {
		    template.update(updater);
		    template.update(updater2);
		} catch (HectorException e) {
		   Assert.fail(e.getMessage());
		}
	}
	
	private static void teamDown_dropSchema() {
		Cluster cluster = HFactory.getOrCreateCluster("single-node-cluster","localhost:9160");
		cluster.dropColumnFamily(TMP_KEYSPACE, "Users", true);
		cluster.dropKeyspace(TMP_KEYSPACE,true);
		
	}

	@Test
	public void addUser() {
		Cluster cluster = HFactory.getOrCreateCluster("single-node-cluster","localhost:9160");
		Keyspace ksp = HFactory.createKeyspace(TMP_KEYSPACE, cluster);
		
		ColumnFamilyTemplate<String, String> template =
                new ThriftColumnFamilyTemplate<String, String>(ksp, "Users", StringSerializer.get(), StringSerializer.get());
		
		// <String, String> correspond to key and Column name.
		ColumnFamilyUpdater<String, String> updater = template.createUpdater("steve2");
		updater.setString("firstname", "steve2");
		updater.setString("lastname", "jobs2");
		updater.setInteger("age", 56);
		updater.setLong("timestamp", System.currentTimeMillis());
		
		try {
		    template.update(updater);
		} catch (HectorException e) {
		   Assert.fail(e.getMessage());
		}
	}
	@Test
	public void getUser() {
		Cluster cluster = HFactory.getOrCreateCluster("single-node-cluster","localhost:9160");
		Keyspace ksp = HFactory.createKeyspace(TMP_KEYSPACE, cluster);
		
		ColumnFamilyTemplate<String, String> template =
                new ThriftColumnFamilyTemplate<String, String>(ksp, "Users", StringSerializer.get(), StringSerializer.get());
		
		try {
		    ColumnFamilyResult<String, String> res = template.queryColumns("steve");
		    String value = res.getString("lastname");
		    Assert.assertEquals("jobs", value);
		} catch (HectorException e) {
			Assert.fail(e.getMessage());
		}
	}
	@Test
	public void deleteUser() {
		Cluster cluster = HFactory.getOrCreateCluster("single-node-cluster","localhost:9160");
		Keyspace ksp = HFactory.createKeyspace(TMP_KEYSPACE, cluster);
		
		ColumnFamilyTemplate<String, String> template =
                new ThriftColumnFamilyTemplate<String, String>(ksp, "Users", StringSerializer.get(), StringSerializer.get());
		
		try {
		    template.deleteColumn("mark", "firstname");
		    ColumnFamilyResult<String, String> res = template.queryColumns("mark");
		    String value = res.getString("firstname");
		    logger.debug(value);
		    Assert.assertNull(value);
		    
		    template.deleteRow("mark");
		    res = template.queryColumns("mark");
		    
		    logger.debug(res.hasResults());
		    Assert.assertFalse(res.hasResults());
		    
		} catch (HectorException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void createKeyspace2times() {
		
		Cluster cluster = HFactory.getOrCreateCluster("single-node-cluster","localhost:9160");
		//create keyspace, it can be created with or without any schema definition
		Keyspace ksp = HFactory.createKeyspace(TMP_KEYSPACE, cluster);
		
		Assert.assertNotNull(ksp);
		
		//this API seems to be make a connection and get a stub object of keyspace
		Keyspace ksp2 = HFactory.createKeyspace(TMP_KEYSPACE, cluster);
		Assert.assertNotNull(ksp2);
	}
	
	
}
