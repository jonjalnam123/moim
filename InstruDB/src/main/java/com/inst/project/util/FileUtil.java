package com.inst.project.util;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.inst.project.admin.vo.AdminFileDTO;

import net.coobird.thumbnailator.Thumbnails;

public class FileUtil {

    /** 허용 이미지 확장자 */
    private static final List<String> ALLOW_EXT =
            Arrays.asList("jpg","jpeg","png","gif","webp","bmp");

    /** 허용 MIME */
    private static final List<String> ALLOW_MIME =
            Arrays.asList("image/jpeg","image/png","image/gif","image/webp","image/bmp");


    /**
    * @methodName        	: saveImageFile
    * @author            			: 최정석
    * @date              			: 2026. 3. 9.
    * @description       		: 이미지 파일 저장 + 썸네일 생성
    * ===================================
    * DATE              AUTHOR             NOTE
    * ===================================
    * 2026. 3. 9.       최정석             최초 생성
    */
    public static AdminFileDTO saveImageFile(MultipartFile file, String basePath) throws Exception {

        if (file == null || file.isEmpty()) return null;

        String orgName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(orgName).toLowerCase();

        // 확장자 체크
        if (!ALLOW_EXT.contains(ext)) {
            throw new RuntimeException("허용되지 않은 파일 확장자");
        }

        // MIME 체크
        String mime = file.getContentType();
        if (!ALLOW_MIME.contains(mime)) {
            throw new RuntimeException("이미지 파일만 업로드 가능합니다.");
        }

        // 날짜 폴더 생성
        String datePath = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String uploadPath = basePath + "/" + datePath;

        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();

        // UUID 파일명
        String saveName = UUID.randomUUID().toString().replace("-", "") + "." + ext;

        File saveFile = new File(uploadPath + "/" + saveName);

        // 파일 저장
        file.transferTo(saveFile);

        // 썸네일 생성
        String thumbName = "thumb_" + saveName;
        File thumbFile = new File(uploadPath + "/" + thumbName);

        Thumbnails.of(saveFile).size(300, 300).toFile(thumbFile);

        AdminFileDTO dto = new AdminFileDTO();

        dto.setFileOrgNm(orgName);
        dto.setFileNm(saveName);
        dto.setFilePath(uploadPath);
        dto.setFileSize(file.getSize());
        dto.setFileExt(ext);

        return dto;
    }


    /**
    * @methodName        : deleteFile
    * @author            : 최정석
    * @date              : 2026. 3. 9.
    * @description       : 파일 및 썸네일 삭제
    * ===================================
    * DATE              AUTHOR             NOTE
    * ===================================
    * 2026. 3. 9.       최정석             최초 생성
    */
    public static boolean deleteFile(String path, String fileNm) {

        boolean result = false;

        File file = new File(path + "/" + fileNm);
        if (file.exists()) {
            result = file.delete();
        }

        // 썸네일 삭제
        File thumb = new File(path + "/thumb_" + fileNm);
        if (thumb.exists()) {
            thumb.delete();
        }

        return result;
    }


    /**
    * @methodName        : downloadFile
    * @author            : 최정석
    * @date              : 2026. 3. 9.
    * @description       : 파일 다운로드 처리
    * ===================================
    * DATE              AUTHOR             NOTE
    * ===================================
    * 2026. 3. 9.       최정석             최초 생성
    */
    public static ResponseEntity<FileSystemResource> downloadFile(String path, String fileNm, String orgNm) throws Exception {

        File file = new File(path + "/" + fileNm);

        if (!file.exists()) {
            throw new RuntimeException("파일이 존재하지 않습니다.");
        }

        FileSystemResource resource = new FileSystemResource(file);

        String encodeName = URLEncoder.encode(orgNm, "UTF-8").replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + encodeName + "\"")
                .header(HttpHeaders.CONTENT_LENGTH,
                        String.valueOf(Files.size(file.toPath())))
                .body(resource);
    }


    /**
    * @methodName        : getPreviewUrl
    * @author            : 최정석
    * @date              : 2026. 3. 9.
    * @description       : 이미지 미리보기 URL 반환
    * ===================================
    * DATE              AUTHOR             NOTE
    * ===================================
    * 2026. 3. 9.       최정석             최초 생성
    */
    public static String getPreviewUrl(String filePath, String fileNm) {

        return "/upload/" + filePath.substring(filePath.indexOf("upload")) + "/thumb_" + fileNm;
    }

}