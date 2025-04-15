package com.aws.Service.Impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.aws.Entity.FileData;
import com.aws.Repository.FileDataRepository;
import com.aws.Service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${aws.s3.bucket}")
    private String cloudBucket;

    private final AmazonS3 s3Client;

    private final FileDataRepository repository;

    public FileServiceImpl(AmazonS3 s3Client, FileDataRepository repository) {
        this.s3Client = s3Client;
        this.repository = repository;
    }

    @Override
    public String uploadImageToBucket(MultipartFile file) throws IOException {

        // Get the file name and change original name to unique name
        String originalFilename = file.getOriginalFilename();
        String randomNum = UUID.randomUUID().toString().substring(0, 5);
        String fileName = randomNum.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

        InputStream fileInputStream = file.getInputStream();

        // Upload file to S3
        s3Client.putObject(new PutObjectRequest(cloudBucket, fileName, fileInputStream, null));

        // Generate URL for the uploaded file (public URL)
        URL fileUrl = s3Client.getUrl(cloudBucket, fileName);

        // save File to database
        FileData fileData = FileData.builder()
                .name(fileName)
                .type(file.getContentType())
                .path(fileUrl.toString())
                .build();
        repository.save(fileData);

        // Return the URL of the uploaded file
        return fileUrl.toString();
    }

    @Override
    public String getImageByName(String fileName) {
        String path=null;
        Optional<FileData> byName = repository.findByName(fileName);
        if (byName.isPresent()){
            path = byName.get().getPath();
            return path;
        }
        return null;
    }

    @Override
    public String getPreSignUrl(String fileName) {

        // create expire time for url
        Date expirationDate = new Date();
        long time = expirationDate.getTime();
        int hour=2;
        time=time+hour+60*60*1000;
        expirationDate.setTime(time);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(cloudBucket, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expirationDate);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    @Override
    public List<FileData> getAllFile() {
        List<FileData> fileData = repository.findAll();
        return fileData;
    }

    @Override
    public boolean deleteByFileId(Long Id) {
        Optional<FileData> byId = repository.findById(Id);
        if (byId.isPresent()){
            repository.deleteById(Id);
            s3Client.deleteObject(cloudBucket, byId.get().getName());
            return true;
        }
        return false;
    }

    @Override
    public String updateById(Long Id, MultipartFile file) throws IOException {
        Optional<FileData> byId = repository.findById(Id);
        if (byId.isPresent()){
            // Get the file name and change original name to unique name
            String originalFilename = file.getOriginalFilename();
            String randomNum = UUID.randomUUID().toString().substring(0, 5);
            String fileName = randomNum.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

            InputStream fileInputStream = file.getInputStream();

            // Upload file to S3
            s3Client.putObject(new PutObjectRequest(cloudBucket, fileName, fileInputStream, null));
            // Generate URL for the uploaded file (public URL)
            URL fileUrl = s3Client.getUrl(cloudBucket, fileName);

            FileData fileData = FileData.builder()
                    .id(Id)
                    .name(fileName)
                    .type(file.getContentType())
                    .path(fileUrl.toString())
                    .build();

            repository.save(fileData);
            return fileUrl.toString();
        }
        return null;
    }


}
