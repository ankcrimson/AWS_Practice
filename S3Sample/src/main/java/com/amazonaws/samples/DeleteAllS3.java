package com.amazonaws.samples;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class DeleteAllS3 {

	
	public static void main(String[] args) throws Exception {
		// Create S3 client
		AmazonS3 client=new AmazonS3Client();
		
		//Set Region
		Region usWest2=Region.getRegion(Regions.US_WEST_2);
		client.setRegion(usWest2);
		
		List<Bucket> buckets = client.listBuckets();
		for(Bucket bucket:buckets) {
			
			ObjectListing objectListing=client.listObjects(bucket.getName());
			
			
			for(S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
				System.out.println(objectSummary.getKey());
				client.deleteObject(bucket.getName(), objectSummary.getKey());
			}
			
			
			System.out.println("Deleting "+bucket.getName());
			client.deleteBucket(bucket.getName());
		}
		
	}

}
