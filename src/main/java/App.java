import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;

public class App {

    public static void main(String[] args) {
        visitS3ByEmrItSelfRole();
//        visitS3ByAssumeRole();
    }

    public static void visitS3ByEmrItSelfRole() {
        System.out.println("pengli");
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new InstanceProfileCredentialsProvider(false))
                .build();
        ListObjectsV2Result result = amazonS3.listObjectsV2("bucket-name", "prefix");
        System.out.println(result.getObjectSummaries().get(0));
    }

    public static void visitS3ByAssumeRole2() {
        STSAssumeRoleSessionCredentialsProvider stsAssumeRoleSessionCredentialsProvider = new STSAssumeRoleSessionCredentialsProvider.Builder(
                "arn:aws:iam::xxxxxxxxx:role/xxxxxxx", "pengli-session-name")
                .withStsClient(AWSSecurityTokenServiceClientBuilder.standard()
                        .withRegion(Regions.fromName("us-east-1"))
                        .withCredentials(new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials("XXXXXXXXXXXX", "XXXXXXZZZXXXXXXXXXXXXXXXXXXXX")))
                        .build()).build();


        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(stsAssumeRoleSessionCredentialsProvider)
                .withRegion(Regions.fromName("us-east-1"))
                .build();

        ListObjectsV2Result result = amazonS3.listObjectsV2("bucket-name", "2021/03/10/00");
        System.out.println(result.getObjectSummaries().get(0));
    }

}
