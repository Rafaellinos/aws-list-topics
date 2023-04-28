package org.example;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.ListTopicsRequest;
import software.amazon.awssdk.services.sns.model.ListTopicsResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

public class Main {

    public static void listSNSTopics(SnsClient snsClient) {

        try {
            ListTopicsRequest request = ListTopicsRequest.builder().build();

            ListTopicsResponse result = snsClient.listTopics(request);
            Integer statusCode = result.sdkHttpResponse().statusCode();
            if (statusCode == 200) {
                System.out.println("Topics:");
                result.topics().forEach(topic -> System.out.println(topic.topicArn()));
            } else {
                System.out.println("Error: " + statusCode);
            }
        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }


    public static void main(String[] args) {

        SnsClient snsClient = SnsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        listSNSTopics(snsClient);
    }
}