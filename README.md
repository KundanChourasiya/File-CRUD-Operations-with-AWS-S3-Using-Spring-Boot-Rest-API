# File-CRUD-Operations-with-AWS-S3-Using-Spring-Boot-Rest-API

> [!WARNING]
> # AWS S3 Bucket Prerequisite
> ### AWS S3 Bucket Configuration
> * Login with root user in Amazon console.
> * Search S3 bucket in AWS search bar.
> *	Click to create bucket.
> *	Enter Bucket name.
> *	Click to select button, block all public access and click to select button Turning off block all public access might result in this bucket and the objects within becoming public.
> *	Now click the create bucket button to create the s3 bucket.
> *	Upload the file inside the bucket.
> *	Click to uploaded file to open the file properties, then copy the Object URL and paste in the browser search bar to access the file.
> **Note: we get failed to access the file, because we not assign the bucket policy, provides access to the objects stored in the bucket.**
> *	Click the created bucket name then go to permission option then click edit button of Bucket Policy to write the below code to provide access to the objects stored in the bucket.
> *	Mention the bucket name in Resource line and click to save change button.
> 
> ```
> {
> "Version":"2012-10-17",
>   "Statement":[
>         {
>             "Sid":"PublicRead",
>             "Effect":"Allow",
>             "Principal":"*",
>             "Action":["s3:GetObject"],
>             "Resource":["arn:aws:s3:::examplebucket/*"]
>         }
>         ]
> }
> ```
> 
> *	Now again Click to uploaded file to open the file properties, then copy the Object URL and paste in the browser search bar to access the file successfully.
>
> ### 1.	I AM USERS Configuration
> *	Open the browser with AWS console and Search I AM in AWS search bar to Manage user access to AWS resources.
>
>  **-	User Groups**
> *	User group it is a collection of IAM users. Use groups to specify permissions for a collection of users.
> *	Click to User groups option then click the create group button.
> *	Enter the user groups name.
> *	Select the AmazonS3FullAccess policy in Attach permissions policies option to All the users in this group will have permissions that are defined in the selected policies.
> *	Now click to create user groups button option to create user groups successfully.
> 
> **-	Users**
> * user is an identity with long-term credentials that is used to interact with AWS in an account.
> * Click to User option then click the create user button.
> * Enter the user name.
> * Click to select the Provide user access to the AWS Management Console.
> * Click to select the I want to create an IAM user.
> * Enter the custom password and click the next button.
> * Click to select the created user groups to Set permissions then click the next button.
> * Now we can view and download the user's password below or email users instructions for signing in to the AWS Management Console.
> * Click to download .csv button to download user instructions for signing then again click to return user list button to return the users page.
>
> **-	Access Key**
> *	Avoid using long-term credentials like access keys to improve your security.
> *	Click to created user to access the user properties.
> *	Click the Create access key option.
> *	Now click to select the Local code option to use this access key to enable application code in a local development environment to access your AWS account.
> *	Click then next button and again click the create access key button.
> *	Click the Download .csv file button to download the user access key and secret access key to access the AWS service from application development environment.
>   
> ### 2.	Sign with AWS Console using I AM USER option to access the AWS S3 services.
> * Open the user credentials.csv file and copy the Console sign-in URL.
> * Now open the browser and paste the copied URL.
> * Now enter the Account id, I AM Username and password and click the sign in button.
> * Search the S3 inside the AWS search bar.
> * Now we can see created user are access AWS S3 service access successfully.


> [!NOTE]
> ### In this Api we upload and download image/ pdf files from Aws S3 Bucket.
> 1. Create Aws account/ Create Bucket/ Create User and Access key
> 1. Postman for testing endpoint
> 2. For Database we will use Mysql
> 3. Good interet connection to build project faster

## Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- lombok
- MySQL
- Amazon S3 Bucket
- Postman

## Modules
* File Upload Module/ download/ delete/ update / preSign Url etc....

## Installation & Run
Before running the API server, you should update the database config inside the application.properties file.
Update the port number, username and password as per your local database config and storage file path configuration.
    
```
# MySql Configuration
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/myDb
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# image/pdf size and path configuration
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB

#AWS S3 Configuration
aws.accessKeyId=****************************
aws.secretAccessKey=****************************
aws.region=****************************
aws.s3.bucket=****************************
```

## API Root Endpoint
```
https://localhost:8080/
user this data for checking purpose.
```
## Step To Be Followed
> 1. Create Rest Api will return to FileData Details.
>    
>    **Project Documentation**
>    - **Entity** - FileData (class)
>    - **Payload** - ApiResponse (class)
>    - **Repository** - FileDataRepository (interface)
>    - **Service** - FileService (interface), FileServiceImpl (class)
>    - **Controller** - FileController (Class)
>    - **Global Exception** - GlobalException(class)
>    - **Config** - AWSS3Config (class)
>
> 2. Add Aws-java-sdk-s3 dependency in pom.xml file.
> 3. configure Mysql configuration and AmazonS3 accesskey, secret key, region and bucket name in applcation.properties file
> 4. Create AWSS3Config class to configure AmazonS3 client Credentials.
> 5. Create File service class to use File Operations using Amazon S3 client properties.
>      * Upload file to Amazon s3 bucket
>      * get File by using file name
>      * Create presigned URL uses security credentials to grant time-limited permission to download objects.
>      * get all image url using database
>      * delete database and Awss3 object using by id
>      * update database path and s3 object using by id
> 6. Create File Controller to use upload file and download file.
> 7. Create GlobalException class to handle all runtime exception.

## Important Dependency to be used
```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
	<groupId>com.mysql</groupId>
	<artifactId>mysql-connector-j</artifactId>
	<scope>runtime</scope>
    </dependency>
    
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

<!-- https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-s3 -->
  <dependency>
       <groupId>com.amazonaws</groupId>
       <artifactId>aws-java-sdk-s3</artifactId>
			    <version>1.12.345</version>
		</dependency>
```

## configure Mysql configuration and AmazonS3 accesskey, secret key, region and bucket name in applcation.properties file.
```
# MySql Configuration
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/awsdb
spring.datasource.username=root
spring.datasource.password=test

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# image/pdf size and path configuration
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB

#AWS S3 Configuration
aws.accessKeyId=****************************
aws.secretAccessKey=****************************
aws.region=****************************
aws.s3.bucket=****************************

```

## Create AWSS3Config class to configure AmazonS3 client Credentials.
```
@Configuration
public class AWSS3Config {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.region}")
    private String awsRegion;

    // verify the amazonS3 credential
    @Bean
    public AmazonS3 s3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(awsRegion)) // Use the region from properties
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
```

## Create FileService interface and FileServiceImpl class in Service package.

### *FileService*
```
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
```

### *FileServiceImpl*
```
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

        // generated Presigned Url Request
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
```

### *Create FileController class inside the Controller Package.* 

```
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
```

### Following pictures will help to understand flow of API

### *PostMan Test Cases*

Url - http://localhost:8080/aws/file/upload

![image](https://github.com/user-attachments/assets/62418ace-2152-4d13-89c1-ac09a326de7d)

Url - http://localhost:8080/aws/file/getfileUrl/{fileName}

![image](https://github.com/user-attachments/assets/e37a31b2-5538-49ae-b64d-da1df4b8b390)

Url - http://localhost:8080/aws/file/getPreSignUrl/{fileName}

![image](https://github.com/user-attachments/assets/ae44c7f0-7c39-47a9-94e8-ca87347f8d62)

Url - http://localhost:8080/aws/file/image

![image](https://github.com/user-attachments/assets/dc89fbca-887c-419e-a21f-69d546083744)

Url - http://localhost:8080/aws/file/image/{id}

![image](https://github.com/user-attachments/assets/dd9a691f-016e-47bd-bbd4-28d55d51750f)


Url - http://localhost:8080/aws/file/image/{id}

![image](https://github.com/user-attachments/assets/556b2085-ea79-4fd1-bc8a-aeb7f3f5b79f)






