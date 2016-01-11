package com.amazonaws.samples;

import java.io.File;
import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class UploadHTML {

	public static void createBucketIfNotExists(AmazonS3 client, String myBucket) {
		List<Bucket> buckets = client.listBuckets();
		for(Bucket bucket: buckets)
		{
			if(bucket.getName().equals(myBucket))
				return;
		}
		//TODO: add logic to retry as bucket names are shared by all users
		client.createBucket(myBucket);
		
	}
	
	public static void uploadFile(AmazonS3 client, String bucketName,String filePath, String key) {
		
		File htmlFile=new File(filePath);
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, htmlFile);
		client.putObject(putObjectRequest);
	}
	
	public static void makePublic(AmazonS3 client, String bucketName, String key) {
		client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
	}
	
	public static void main(String[] args) throws Exception {
		// Create S3 client
		AmazonS3 client=new AmazonS3Client();
		
		//Set Region
		Region usWest2=Region.getRegion(Regions.US_WEST_2);
		client.setRegion(usWest2);
		
		String bucketName = "ankur-s3-test";
		String filePath = "src/main/resources/test.html";
		String key="test.html";
		//client.createBucket(bucketName);
		createBucketIfNotExists(client, bucketName);
		uploadFile(client,bucketName,filePath,key);
		makePublic(client, bucketName, key);
		
	}

}
