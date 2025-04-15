package com.aws.Controller;

import com.aws.Entity.FileData;
import com.aws.Payload.ApiResponse;
import com.aws.Repository.FileDataRepository;
import com.aws.Service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/aws/file")
@AllArgsConstructor
public class FileController {

    private FileService service;

    private FileDataRepository repository;

    //  URL:http://localhost:8080/aws/file/upload
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<?>> uploadImageToBucket(@RequestParam MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            ApiResponse<Object> response = new ApiResponse<>(false, "Request must contain file", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
        }
        String filePath = service.uploadImageToBucket(file);
        ApiResponse<Object> response = new ApiResponse<>(true, "File Uploaded successfully", filePath);
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);

    }

    //  URL:http://localhost:8080/aws/file/getfileUrl/{filename}
    @GetMapping("/getfileUrl/{filename}")
    public ResponseEntity<ApiResponse<?>> getFileByName(@PathVariable String filename) {
        String imageByName = service.getImageByName(filename);
        if (imageByName != null) {
            ApiResponse<String> response = new ApiResponse<>(true, "file Found", imageByName);
            return ResponseEntity.ok().body(response);
        }
        ApiResponse<String> response = new ApiResponse<>(false, "file not Found", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //  URL:http://localhost:8080/aws/file/getPreSignUrl/{filename}
    @GetMapping("/getPreSignUrl/{filename}")
    public ResponseEntity<ApiResponse<?>> getPreSignUrl(@PathVariable String filename) {
        String preSignUrl = service.getPreSignUrl(filename);
        ApiResponse<String> response = new ApiResponse<>(true, "file Found", preSignUrl);
        return ResponseEntity.ok().body(response);
    }

    //  URL:http://localhost:8080/aws/file/image
    @GetMapping("/image")
    public ResponseEntity<ApiResponse<List<FileData>>> getAllImage() {
        List<FileData> allImageFile = service.getAllFile();
        ApiResponse<List<FileData>> fileFound = new ApiResponse<>(true, "file Found", allImageFile);
        return ResponseEntity.status(HttpStatus.OK).body(fileFound);

    }

    //  URL:http://localhost:8080/aws/file/image/{id}
    @DeleteMapping("/image/{id}")
    public ResponseEntity<ApiResponse<?>> deleteById(@PathVariable Long id) {
        boolean status = service.deleteByFileId(id);
        if (status) {
            ApiResponse<Object> fileDeleted = new ApiResponse<>(true, "File Deleted", null);
            return ResponseEntity.status(HttpStatus.FOUND).body(fileDeleted);
        }
        ApiResponse<Object> fileDeleted = new ApiResponse<>(false, "File Not Found", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fileDeleted);
    }

    //  URL:http://localhost:8080/aws/file/image/{id}
    @PutMapping("/image/{id}")
    public ResponseEntity<ApiResponse<?>> UpdateById(@PathVariable Long id, @RequestParam MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            ApiResponse<Object> response = new ApiResponse<>(false, "Request must contain file", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
        }

        String updateById = service.updateById(id, file);
        if (updateById != null) {
            ApiResponse<String> response = new ApiResponse<>(true, "File updated", updateById);
            return ResponseEntity.ok().body(response);
        }
        ApiResponse<String> response = new ApiResponse<>(false, "File Not Found", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

}
