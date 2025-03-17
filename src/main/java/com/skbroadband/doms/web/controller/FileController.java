package com.skbroadband.doms.web.controller;

import com.skbroadband.doms.global.constant.ImageUri;
import com.skbroadband.doms.global.dto.Response;
import com.skbroadband.doms.global.exception.BadRequestException;
import com.skbroadband.doms.web.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.controller
 * @File : FileController
 * @Program :
 * @Date : 2023-02-02
 * @Comment :
 */
@Controller
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    /**
     * 이미지 파일다운로드
     *
     * @param division
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping("/images/{division}/**")
    public ResponseEntity<Resource> downloadImage(@PathVariable("division") String division,
                                            HttpServletRequest request) throws IOException {
        String fileName = request.getRequestURI().split("/"+division+"/")[1];

        Path path = Paths.get(ImageUri.getPath(division) + File.separator + fileName);
        Resource resource = fileService.download(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /**
     * 이미지파일 업로드
     *
     * @param division
     * @param file
     * @return
     */
    @PostMapping("/images/{division}")
    public ResponseEntity<?> uploadImage(@PathVariable("division") String division, @RequestPart MultipartFile file) {
        if(!StringUtils.hasText(ImageUri.getPath(division))) {
            throw new BadRequestException("잘못된 요청입니다.");
        }

        return Response.of(
                Collections.singletonMap("fileNo", fileService.fileUpload(file, ImageUri.getPath(division))));
    }

    /**
     * 이미지파일 삭제
     *
     * @param division
     * @param request
     * @return
     */
    @PostMapping("/images/{division}/**")
    public ResponseEntity<?> deleteImage(@PathVariable("division") String division,
                                         HttpServletRequest request) {
        String fileName = request.getRequestURI().split("/"+division+"/")[1];

        Path path = Paths.get(ImageUri.getPath(division) + File.separator + fileName);
        fileService.deleteFile(path);

        return Response.ok();
    }
}
