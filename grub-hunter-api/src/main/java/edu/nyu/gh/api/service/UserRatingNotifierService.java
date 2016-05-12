package edu.nyu.gh.api.service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

public class UserRatingNotifierService {
	
	private BasicAWSCredentials credentials;
	private AmazonSQS sqs;
	private String queueURL;
	
	public UserRatingNotifierService() {
		this.credentials = new BasicAWSCredentials("AKIAIVPKZMADIZWINZYQ", 
				"em2INqN1wp99dEFfAZhM9IjhXN97w541KMtoACJv");
		this.queueURL = "https://sqs.us-west-2.amazonaws.com/910837675618/UserRating";
		this.sqs = new AmazonSQSClient(this.credentials);
		this.sqs.setEndpoint("https://sqs.us-west-2.amazonaws.com");
	}
	
	public void notifyUserRating(String user){
		SendMessageResult messageResult =  this.sqs.sendMessage(new SendMessageRequest(queueURL, "{\"user\":\""+user+"\"}"));
		System.out.println(messageResult.toString());
	}

}
