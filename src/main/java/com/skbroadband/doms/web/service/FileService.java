package com.skbroadband.doms.web.service;

import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.global.exception.GlobalException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.service
 * @File : FileService
 * @Program :
 * @Date : 2023-02-01
 * @Comment :
 */
@Service
@Slf4j
public class FileService {

    @SneakyThrows
    public String fileUpload(MultipartFile multipartFile, String folder) {
        if(Objects.isNull(multipartFile)) {
            throw new BadRequestException("업로드할 파일이 없습니다.");
        }

        String fileName = UUID.randomUUID().toString().replace("-", "").toLowerCase()
                + "."
                + getExtension(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        String path = folder
                + File.separator
                + fileName;

        Path serverPath = Paths.get(path);

        try {
            if (!Files.exists(serverPath.getParent())) {
                Files.createDirectories(serverPath.getParent());
            }
            Files.copy(multipartFile.getInputStream(), serverPath);
        } catch (IOException e) {
            throw new GlobalException("파일 저장에 실패했습니다.");
        }

        return fileName;
    }

    public String fileCaptureUpload(String base64Str, String folder) {
        String fileName = UUID.randomUUID().toString().replace("-", "").toLowerCase()
                + ".png";

        String path = folder
                + File.separator
                + fileName;

        Path serverPath = Paths.get(path);

        try {
            if (!Files.exists(serverPath.getParent())) {
                Files.createDirectories(serverPath.getParent());
            }

            File file = new File(path);

            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decodeBytes = decoder.decode(base64Str.getBytes());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(decodeBytes);
            fileOutputStream.close();
        } catch(IOException e) {
            log.error(e.getMessage());
            throw new GlobalException("파일 저장에 실패했습니다.");
        }

        return fileName;
    }

    public Resource download(Path path) throws IOException {
        return  new InputStreamResource(Files.newInputStream(path));
    }

    public static String getExtension(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos + 1);
    }

    public void deleteFile(Path path) {
        try {
            if(Files.exists(path)) {
            Files.delete(path);
            }
        } catch (IOException ignored) {}
    }
}
