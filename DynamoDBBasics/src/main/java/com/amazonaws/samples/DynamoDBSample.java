package com.amazonaws.samples;

import java.util.ArrayList;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

public class DynamoDBSample {

	public static void main(String[] args) throws Exception {
		AmazonDynamoDBClient amazonDynamoDBClient=new AmazonDynamoDBClient();
		Region region=Region.getRegion(Regions.US_WEST_2);
		amazonDynamoDBClient.setRegion(region);
		
		DynamoDB dynamoDB=new DynamoDB(amazonDynamoDBClient); //create a dynamodb instance
		
		ArrayList<AttributeDefinition> attributes=new ArrayList<AttributeDefinition>();
		attributes.add(new AttributeDefinition().withAttributeName("id").withAttributeType(ScalarAttributeType.N));//numeric id field
		attributes.add(new AttributeDefinition().withAttributeName("name").withAttributeType(ScalarAttributeType.S));//string name field
		
		ArrayList<KeySchemaElement> keys=new ArrayList<KeySchemaElement>();
		keys.add(new KeySchemaElement().withAttributeName("id").withKeyType(KeyType.HASH));
		keys.add(new KeySchemaElement().withAttributeName("name").withKeyType(KeyType.RANGE));
		
		CreateTableRequest createTableRequest =new CreateTableRequest()
				.withTableName("MyFirstTable")
				.withAttributeDefinitions(attributes)
				.withKeySchema(keys)
				.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));
		
		Table table=dynamoDB.createTable(createTableRequest);
		
		System.out.println("Table created, waiting for it to become active");
		
		table.waitForActive();
		
		System.out.println("Table Active");
		
		TableDescription tableDescription = table.getDescription();
		System.out.printf("%s: %s, having %d writers and %d readers\n",tableDescription.getTableName(),tableDescription.getTableStatus(),tableDescription.getProvisionedThroughput().getWriteCapacityUnits(),tableDescription.getProvisionedThroughput().getReadCapacityUnits());
		
	}

}
