package com.kyunam.aws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
public class S3Manager {
	private static final String amazonPath = "https://s3.ap-northeast-2.amazonaws.com/kyubucket/";
	private static AmazonS3 s3Cliient = new AmazonS3Client(new ProfileCredentialsProvider());
	private static ObjectMetadata meta;
	public String saveUploadedFiles(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			System.out.println("file not found");
		}
		byte[] bytes = file.getBytes();
		String randomFileName = UUID.randomUUID() + file.getOriginalFilename();
		sendFiles(randomFileName, bytes);
		return amazonPath + randomFileName;
	}
	public static boolean sendFiles(String name, byte[] file) {
		InputStream is = new ByteArrayInputStream(file);
		try {
			s3Cliient.putObject(makeRequest(is, name));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static PutObjectRequest makeRequest(InputStream is, String fileName) {
		ObjectMetadata uploadMetaData = new ObjectMetadata();
		return new PutObjectRequest("kyubucket", fileName, is, uploadMetaData).withCannedAcl(CannedAccessControlList.PublicRead);
	}
}