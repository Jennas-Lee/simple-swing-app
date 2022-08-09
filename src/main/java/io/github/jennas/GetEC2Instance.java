package io.github.jennas;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;

import java.util.ArrayList;

public class GetEC2Instance {
    private final AmazonEC2 client;

    GetEC2Instance(String accessKey, String secretAccessKey, String region) {
        try {
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretAccessKey);
            this.client = AmazonEC2ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .withRegion(region)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ArrayList<String[]> getEC2List() {
        ArrayList<String[]> ec2List = new ArrayList<>();
        boolean done = false;
        DescribeInstancesRequest request = new DescribeInstancesRequest();

        while (!done) {
            DescribeInstancesResult response = client.describeInstances(request);

            for (Reservation reservation : response.getReservations()) {
                for (Instance instance : reservation.getInstances()) {
                    String id;
                    String name;
                    String az;
                    String type;
                    String publicIp;
                    String privateIp;

                    id = instance.getInstanceId();
                    name = instance.getState().getName();
                    az = client.describeSubnets(
                                    new DescribeSubnetsRequest()
                                            .withSubnetIds(
                                                    instance
                                                            .getSubnetId()
                                            )
                            )
                            .getSubnets()
                            .get(0)
                            .getAvailabilityZone();
                    type = instance.getInstanceType();
                    publicIp = instance.getPublicIpAddress();
                    privateIp = instance.getPrivateIpAddress();
                    String[] instanceInfo = {
                            id,
                            name,
                            az,
                            type,
                            publicIp,
                            privateIp
                    };
                    ec2List.add(instanceInfo);
                }
            }

            request.setNextToken(response.getNextToken());

            if (response.getNextToken() == null) {
                done = true;
            }
        }

        return ec2List;
    }
}
