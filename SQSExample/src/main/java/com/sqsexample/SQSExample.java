package com.sqsexample;

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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AmazonSQSClient sqsClient =new AmazonSQSClient();
		CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
		CreateQueueResult createQueueResult = sqsClient.createQueue(createQueueRequest);
		String queueUrl = createQueueResult.getQueueUrl();
		System.out.println("Queue Created: "+queueName+" with QueueUrl:"+queueUrl);
		System.out.println("Sending Message");
		
		//sending a message
		SendMessageRequest sendMessageRequest = new SendMessageRequest(queueUrl,"Test Message");
		sqsClient.sendMessage(sendMessageRequest);
		
		//sending another message
		sqsClient.sendMessage(queueUrl, "Test Message 2");
		
		//recieveing messages
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
		ReceiveMessageResult receiveMessageResult = sqsClient.receiveMessage(receiveMessageRequest);
		List<Message> messages = receiveMessageResult.getMessages();
		
		//listing messages:
		System.out.printf("Found a total of %d messages\n",messages.size());
		for(Message message: messages) {
			System.out.println(message.getMessageId()+" - "+message.getBody());
			//deleting the messages
			DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(queueUrl, message.getReceiptHandle());
			sqsClient.deleteMessage(deleteMessageRequest);
		}
		
		//deleting queue
		DeleteQueueRequest deleteQueueRequest = new DeleteQueueRequest(queueUrl); 
		sqsClient.deleteQueue(deleteQueueRequest);
	}

}
