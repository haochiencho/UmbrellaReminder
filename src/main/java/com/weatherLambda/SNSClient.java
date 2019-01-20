package com.weatherLambda;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;

/**
 * Created by briancho on 1/19/19.
 */
public class SNSClient {
    public SNSClient() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(System.getenv("ACCESS_KEY_ID"), System.getenv("SECRET_ACCESS_KEY"));

        snsClient = new AmazonSNSClient(credentials).withRegion(Region.getRegion(Regions.US_WEST_2));
    }

    public void publish (String msg) {
        snsClient.publish(System.getenv("TOPIC_ARN"), msg);
    }

    private AmazonSNSClient snsClient;
}
