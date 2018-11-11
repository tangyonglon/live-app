package com.douliao.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class UploadTest {
	
	static AmazonS3 s3;
    static String AWS_ACCESS_KEY = "AKIAJF5HEUQ37WEDDNIA"; // 【你的 access_key】
    static String AWS_SECRET_KEY = "MQvT6p4iGSeKkKAlU52ColYczs/zjV8Zdr+taG0u"; // 【你的 aws_secret_key】

    String bucketName = "usa-video"; // 【你 bucket 的名字】 # 首先需要保证 s3 上已经存在该存储桶

    static {
        s3 = new AmazonS3Client(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY));
        s3.setRegion(Region.getRegion(Regions.US_WEST_1)); // 此处根据自己的 s3 地区位置改变
    }
    
    /**
     * 方法重载
     * @param tempFile
     * @param remoteFileName
     * @param bucketName
     * @return
     * @throws IOException
     */
    public String uploadToS3(File tempFile, String remoteFileName,String bucketName) throws IOException {
        try {
//            String bucketPath = bucketName + "/upload" ;
            String bucketPath = bucketName;
            s3.putObject(new PutObjectRequest(bucketPath, remoteFileName, tempFile)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, remoteFileName);
            URL url = s3.generatePresignedUrl(urlRequest);
            String fileUrl = url.toString();
            String[] urlStrArray = fileUrl.split("\\?");
    	    fileUrl = (urlStrArray.length >= 1) ? urlStrArray[0] : url.toString();
            return fileUrl;
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
        } catch (AmazonClientException ace) {
            ace.printStackTrace();
        }
        return null;
    }
    
    
    public String uploadToS3(File tempFile, String remoteFileName) throws IOException {
        try {
//            String bucketPath = bucketName + "/upload" ;
            String bucketPath = bucketName;
            s3.putObject(new PutObjectRequest(bucketPath, remoteFileName, tempFile)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, remoteFileName);
            URL url = s3.generatePresignedUrl(urlRequest);
            String fileUrl = url.toString();
            String[] urlStrArray = fileUrl.split("\\?");
    	    fileUrl = (urlStrArray.length >= 1) ? urlStrArray[0] : url.toString();
            return fileUrl;
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
        } catch (AmazonClientException ace) {
            ace.printStackTrace();
        }
        return null;
    }
    
    
    public static void main(String[] args) throws IOException {
    	UploadTest upload=new UploadTest();
    	File uploadFile = new File("D:\\file\\11\\videos\\7.mp4");
	    String uploadKey = "7";
	    String result=upload.uploadToS3(uploadFile,uploadKey);
	    System.out.println(result);
	    
	    
//	    final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
//        List<Bucket> buckets = s3.listBuckets();
//        System.out.println("Your Amazon S3 buckets are:");
//        for (Bucket b : buckets) {
//            System.out.println("* " + b.getName());
//        }
	}
    
    
//    @Test
//    public void test(){
//        File uploadFile = new File("c:/test.txt");
//        String uploadKey = "test";
//        uploadToS3(uploadFile,uploadKey);
//    }
	
}
