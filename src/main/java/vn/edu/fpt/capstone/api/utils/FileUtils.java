package vn.edu.fpt.capstone.api.utils;


import com.azure.storage.file.share.ShareDirectoryClient;
import com.azure.storage.file.share.ShareFileClient;
import com.azure.storage.file.share.ShareFileClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
public final class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    private static final String CONNECT_STRING = "DefaultEndpointsProtocol=https;AccountName=fomsv1;AccountKey=1s4YzGb+GMvvkLyTDqz396cWLCFA8hBc8yRN9bo4UjF55PPGvJp0qMAzrzUCWD9OG5D+r2XrurFS+AStpJmEnA==;EndpointSuffix=core.windows.net";

    private static final String TOKEN = "?sv=2021-06-08&ss=bfqt&srt=sco&sp=rwdlacupiytfx&se=2024-08-02T16:42:58Z&st=2022-08-02T08:42:58Z&spr=https,http&sig=cdY8JsIJgIBysjIn7PsUK6TJlGISXQcYSDzxF0NlIUI%3D";

    private static final String SHARE_NAME = "foms-storage";

    public static final String XLSX_EXT = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static final String XLS_EXT = "application/vnd.ms-excel";

    public static final Integer XLS_EXT_LENGTH = 4;

    public static final Integer XLSX_EXT_LENGTH = 5;

    public static final Integer PDF_EXT_LENGTH = 4;

    public static boolean isPdf(MultipartFile file) {
        return MediaType.APPLICATION_PDF_VALUE.equals(file.getContentType());
    }



    public static boolean hasMSExcelFormat(MultipartFile file) {
        return XLSX_EXT.equals(file.getContentType());
    }

    public static boolean hasExcelFormat(MultipartFile file) {
        return XLS_EXT.equals(file.getContentType());
    }

    public static boolean isEmptyName(MultipartFile file) {
        return !StringUtils.hasText(file.getOriginalFilename());
    }

    public static String getFileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        switch (file.getContentType()) {
            case MediaType.APPLICATION_PDF_VALUE:
            case XLS_EXT:
                return fileName.substring(0, fileName.length() - XLS_EXT_LENGTH);
            case XLSX_EXT:
                return fileName.substring(0, fileName.length() - XLSX_EXT_LENGTH);
            default:
                return null;
        }
    }
    public static String generateFileName(String fileName, Long id) {

        return String.format("%s_%s_%s",
                fileName.substring(0, fileName.length() - PDF_EXT_LENGTH),
                id,
                DateUtils.format(DateUtils.now(), DateUtils.DATETIME_FORMAT_CUSTOM)) ;
    }

    public static String generateFileName(String fileName, String ext, Long id) {

        return String.format("%s_%s_%s",
                fileName.replace(ext,""),
                id,
                DateUtils.format(DateUtils.now(), DateUtils.DATETIME_FORMAT_CUSTOM)) ;
    }

    private static void deleteFile(File file) {
        file.delete();
    }

    public static String uploadFile(String fileName, String directory) {
        File file = new File(fileName);
        try {
            ShareDirectoryClient dirClient = new ShareFileClientBuilder()
                    .connectionString(CONNECT_STRING)
                    .shareName(SHARE_NAME)
                    .resourcePath(directory)
                    .buildDirectoryClient();

            ShareFileClient fileClient = dirClient.getFileClient(file.getName());
            fileClient.create(file.length());
            fileClient.uploadFromFile(file.getAbsolutePath());
            deleteFile(file);
            return fileClient.getFileUrl() + TOKEN;
        } catch (Exception e) {
            LOGGER.error("An error occurred while uploading data. Exception: ", e);
            deleteFile(file);
            throw new RuntimeException("Có lỗi khi lưu file lên cloud");
        }
    }

    public static void convertToFile(MultipartFile file, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(file.getBytes());
        fos.close();
    }

}
