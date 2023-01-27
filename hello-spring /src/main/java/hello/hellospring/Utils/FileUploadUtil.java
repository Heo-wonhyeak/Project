package hello.hellospring.Utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static String saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPathToShow = Paths.get("/Library/WebServer/Documents/front-demo/img/upload-files");
        Path uploadPathToDownload = Paths.get("Files-Upload");

        // 업로드 패스에 파일이 없으면
        if (!Files.exists(uploadPathToShow)) {
            Files.createDirectories(uploadPathToShow);
        }

        if (!Files.exists(uploadPathToDownload)) {
            Files.createDirectories(uploadPathToDownload);
        }

        // 파일코드 영문 숫자 조합 8자리
        String fileCode = RandomStringUtils.randomAlphanumeric(8);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            // 경로 조합하기 (고정 루트경로 + 부분 경로)
            Path filePathToShow = uploadPathToShow.resolve(fileCode + "-" + fileName);
            Path filePathToDownload = uploadPathToDownload.resolve(fileCode + "-" + fileName);
            // 복사할 경로 , 타겟 파일 경로 , 존재하더라도 덮어씀
            Files.copy(inputStream, filePathToShow, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(inputStream, filePathToDownload, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("파일이 저장되지 않았습니다" + fileName, e);
        }

        return fileCode;
    }
}
