package io.github.jennas;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.identitymanagement.model.DeletePolicyRequest;
import com.amazonaws.services.identitymanagement.model.ListPoliciesRequest;
import com.amazonaws.services.identitymanagement.model.Policy;
import com.amazonaws.services.identitymanagement.model.PolicyScopeType;

import java.util.ArrayList;
import java.util.List;

public class IamPolicy {
    private final AmazonIdentityManagement client;

    IamPolicy(String accessKey, String secretAccessKey) {
        try {
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretAccessKey);
            this.client = AmazonIdentityManagementClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .withRegion(Regions.US_EAST_1)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    List<Policy> getIamPolicyList() {
        return client.listPolicies(
                new ListPoliciesRequest().withScope(PolicyScopeType.Local)
        ).getPolicies();
    }

    void deleteIamPolicyList(ArrayList<String> policyArn) {
        for (String arn : policyArn) {
            client.deletePolicy(
                    new DeletePolicyRequest().withPolicyArn(arn)
            );
        }
    }
}
