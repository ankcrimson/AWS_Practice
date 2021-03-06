package com.sqsexample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

public class SQSCRUD {

	public static final String queueName = "sampleQueueFromJava";
	static String queueUrl = "";
	static Message message;
	public static void createQueue(AmazonSQSClient sqsClient) {
		try{
			CreateQueueResult createQueueResult = sqsClient.createQueue(queueName);
			queueUrl = createQueueResult.getQueueUrl();
			System.out.printf("Queue %s created",queueUrl);
		}catch(Exception ex){ex.printStackTrace();}
	}
	public static void deleteQueue(AmazonSQSClient sqsClient) {
		try{
			sqsClient.deleteQueue(queueUrl);
			System.out.printf("Queue %s deleted\n",queueUrl);
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	public static String sendMessage(AmazonSQSClient sqsClient, String message) {
		String messageDetails="";
		try{
			SendMessageResult sendMessageResult = sqsClient.sendMessage(queueUrl, message);
			messageDetails=sendMessageResult.getMessageId();
			System.out.printf("Message with messageid=%s  created\n",messageDetails);
		}catch(Exception ex){ex.printStackTrace();}
		return messageDetails;
	}
	
	public static void printMessages(AmazonSQSClient sqsClient) {
		try{
			List<Message> messages = sqsClient.receiveMessage(queueUrl).getMessages();
			System.out.println("Printing Messages:");
			for(Message msg:messages) {
				message = msg;
				System.out.println(msg.getMessageId()+" - "+msg.getBody());
			}
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	public static List<Message> getMessages(AmazonSQSClient sqsClient, String message) {
		try{
			List<Message> messages = sqsClient.receiveMessage(queueUrl).getMessages();
			return messages;
		}catch(Exception ex){ex.printStackTrace();}
		return null;
	}
	
	public static void deleteMessage(AmazonSQSClient sqsClient, Message message) {
		try{
			sqsClient.deleteMessage(queueUrl, message.getReceiptHandle());
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	public static void main(String[] args) throws Exception {

	AmazonSQSClient sqsClient = new AmazonSQSClient();
	sqsClient.setRegion(Region.getRegion(Regions.US_WEST_2));
	 InputStreamReader inputStreamReader = new InputStreamReader(System.in);
	 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	
	String menu="0 : Create Queue\n1 : Send Message \n2 : List Messages\n3: Delete Message\n4: Delete Queue\n-1: Quit";
	int opt=0;
	System.out.println(menu);
	while((opt=Integer.parseInt(bufferedReader.readLine()))!=-1) {
		
		switch(opt) {
		case 0: createQueue(sqsClient); break;
		case 1: 
				System.out.println("Enter Message < 256kb");
				String msg = bufferedReader.readLine();
				sendMessage(sqsClient, msg); break;
		case 2: printMessages(sqsClient); break;
		case 3: deleteMessage(sqsClient, message); break;
		case 4: deleteQueue(sqsClient);
		}
		
		System.out.println(menu);
	}
	
		
	bufferedReader.close();
	inputStreamReader.close();
	}
}
