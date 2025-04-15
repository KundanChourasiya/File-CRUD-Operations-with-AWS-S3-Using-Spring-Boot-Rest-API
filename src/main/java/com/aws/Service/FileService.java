package com.aws.Service;

import com.aws.Entity.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    // Upload file to Amazon s3 bucket
    String uploadImageToBucket(MultipartFile file) throws IOException;

    // get File by using file name
    String getImageByName(String fileName);

    // Create presigned URL uses security credentials to grant time-limited permission to download objects.
    String getPreSignUrl(String fileName);

    // get all image url using database
    List<FileData> getAllFile();

    // delete database and Awss3 object using by id
    boolean deleteByFileId(Long Id);

    // update database path and s3 object using by id
    String updateById(Long Id,MultipartFile file) throws IOException;
}
