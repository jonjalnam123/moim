package com.inst.project.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.inst.project.admin.vo.AdminFileDTO;

import net.coobird.thumbnailator.Thumbnails;

public class FileUtil {

    private static final List<String> ALLOW_EXT =
            Arrays.asList("jpg","jpeg","png","gif","webp","bmp");

    private static final List<String> ALLOW_MIME =
            Arrays.asList("image/jpeg","image/png","image/gif","image/webp","image/bmp");

    public static AdminFileDTO saveImageFile(MultipartFile file, String basePath) throws Exception {

        if (file == null || file.isEmpty()) return null;

        String orgName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(orgName).toLowerCase();

        if (!ALLOW_EXT.contains(ext)) {
            throw new RuntimeException("허용되지 않은 파일 확장자");
        }

        String mime = file.getContentType();
        if (!ALLOW_MIME.contains(mime)) {
            throw new RuntimeException("이미지 파일만 업로드 가능합니다.");
        }

        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String uploadPath = basePath + "/" + datePath;

        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();

        String saveName = UUID.randomUUID().toString().replace("-", "") + "." + ext;
        File saveFile = new File(uploadPath + "/" + saveName);

        file.transferTo(saveFile);

        String thumbName = "thumb_" + saveName;
        File thumbFile = new File(uploadPath + "/" + thumbName);
        Thumbnails.of(saveFile).size(300, 300).toFile(thumbFile);

        AdminFileDTO dto = new AdminFileDTO();
        dto.setFileOrgNm(orgName);
        dto.setFileNm(saveName);
        dto.setFilePath(uploadPath);
        dto.setFileSize(file.getSize());
        dto.setFileExt(ext);
        dto.setFileThumbNm(thumbName);
        dto.setFileThumbPath(uploadPath);

        return dto;
    }

    public static boolean deleteFile(String path, String fileNm) {

        boolean result = false;

        File file = new File(path + "/" + fileNm);
        if (file.exists()) {
            result = file.delete();
        }

        File thumb = new File(path + "/thumb_" + fileNm);
        if (thumb.exists()) {
            thumb.delete();
        }

        return result;
    }

    public static ResponseEntity<FileSystemResource> downloadFile(String path, String fileNm, String orgNm) throws Exception {

        File file = new File(path + "/" + fileNm);

        if (!file.exists()) {
            throw new RuntimeException("파일이 존재하지 않습니다.");
        }

        FileSystemResource resource = new FileSystemResource(file);

        String contentDisposition = ContentDisposition.attachment()
                .filename(orgNm, StandardCharsets.UTF_8)
                .build()
                .toString();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(Files.size(file.toPath())))
                .body(resource);
    }

    public static String getPreviewUrl(String filePath, String fileNm) {
        return "/upload/" + filePath.substring(filePath.indexOf("upload")) + "/thumb_" + fileNm;
    }
}