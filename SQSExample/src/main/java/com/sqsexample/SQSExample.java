package com.sqsexample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;


import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SQSExample {

	public static final String queueName = "sampleQueueFromJava";
	
	public static void main(String[] args) throws Exception {

	 InputStreamReader inputStreamReader = new InputStreamReader(System.in);
	 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	
		AmazonSQSClient sqsClient =new AmazonSQSClient();
		CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
		CreateQueueResult createQueueResult = sqsClient.createQueue(createQueueRequest);
		String queueUrl = createQueueResult.getQueueUrl();
		System.out.println("Queue Created: "+queueName+" with QueueUrl:"+queueUrl);
		System.out.println("PRESS ENTER TO CONTINUE");
		bufferedReader.readLine();
		System.out.println("Sending Messages");
		
		//sending a message
		SendMessageRequest sendMessageRequest = new SendMessageRequest(queueUrl,"Test Message");
		sqsClient.sendMessage(sendMessageRequest);
		
		//sending another message
		sqsClient.sendMessage(queueUrl, "Test Message 2");
		sqsClient.sendMessage(queueUrl, "Test Message 3");
		
		System.out.println("Messages Sent, PRESS ENTER TO CONTINUE");
		bufferedReader.readLine();
		
		//recieveing messages
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
		ReceiveMessageResult receiveMessageResult = sqsClient.receiveMessage(receiveMessageRequest);
		List<Message> messages = receiveMessageResult.getMessages();
		
		//listing messages:
		System.out.printf("Found a total of %d messages\n",messages.size());
		
		System.out.println("PRESS ENTER TO CONTINUE");
		bufferedReader.readLine();
		
		for(Message message: messages) {
			System.out.println(message.getMessageId()+" - "+message.getBody());
			//deleting the messages
			DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(queueUrl, message.getReceiptHandle());
			sqsClient.deleteMessage(deleteMessageRequest);
		}
		System.out.println("Messages Deleted, PRESS ENTER TO CONTINUE");
		bufferedReader.readLine();
		
		//deleting queue
		DeleteQueueRequest deleteQueueRequest = new DeleteQueueRequest(queueUrl); 
		sqsClient.deleteQueue(deleteQueueRequest);
		System.out.println("Queue Deleted, PRESS ENTER TO EXIT");
		bufferedReader.readLine();
		
	bufferedReader.close();
	inputStreamReader.close();
	}
}
