package com.amazonaws.samples;

import java.util.ArrayList;
import java.util.Iterator;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

public class DynamoDBSampleDelete {

	public static void main(String[] args) throws Exception {
		AmazonDynamoDBClient amazonDynamoDBClient=new AmazonDynamoDBClient();
		Region region=Region.getRegion(Regions.US_WEST_2);
		amazonDynamoDBClient.setRegion(region);
		
		DynamoDB dynamoDB=new DynamoDB(amazonDynamoDBClient); //create a dynamodb instance
		
		//Listing Logic
		TableCollection<ListTablesResult> tables = dynamoDB.listTables();
		System.out.println("Available Tables:");
		Iterator<Table> tableIterator=tables.iterator();
		System.out.println("List of tables: ");
		while(tableIterator.hasNext()) {
			Table currentTable=tableIterator.next();
			System.out.println("=================");
			System.out.println(currentTable.getTableName());
			System.out.println(currentTable.describe());
			System.out.println("=================");
		}
		
		//Deletion Logic
		String tableName="MyFirstTable";
		Table table=dynamoDB.getTable(tableName);
		table.delete();
		System.out.println("Delete table executed and waiting for completion for : "+tableName);
		table.waitForDelete();
		System.out.println("Table Deleted");
		
	}

}
