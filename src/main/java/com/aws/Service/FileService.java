package com.aws.Service;

import com.aws.Entity.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    String uploadImageToBucket(MultipartFile file) throws IOException;

    String getImageByName(String fileName);

    String getPreSignUrl(String fileName);

    List<FileData> getAllFile();

    boolean deleteByFileId(Long Id);

    String updateById(Long Id,MultipartFile file) throws IOException;
}
