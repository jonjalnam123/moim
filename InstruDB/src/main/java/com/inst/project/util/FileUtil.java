package com.inst.project.util;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

	/**
	* @methodName	: saveFile
	* @author				: 최정석
	* @date            	: 2026. 1. 6.
	* @description		: 범용 파일 저장 로직
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.       최정석             최초 생성
	*/
    public static AdminFileDTO saveFile(MultipartFile file, String basePath) throws Exception {

        if (file == null || file.isEmpty()) {
        	return null;
        }

        String orgName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(orgName);
        ext = (ext == null) ? "" : ext.toLowerCase();

        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String uploadPath = basePath + "/" + datePath;

        File dir = new File(uploadPath);
        if (!dir.exists()) {
        	dir.mkdirs();
        }

        String saveName = UUID.randomUUID().toString().replace("-", "");
        if (!"".equals(ext)) {
        	saveName += "." + ext;
        }

        File saveFile = new File(uploadPath + "/" + saveName);
        file.transferTo(saveFile);

        AdminFileDTO dto = new AdminFileDTO();
        dto.setFileOrgNm(orgName);
        dto.setFileNm(saveName);
        dto.setFilePath(uploadPath);
        dto.setFileSize(file.getSize());
        dto.setFileExt(ext);
        dto.setFileThumbNm(null);
        dto.setFileThumbPath(null);

        return dto;
    }

	/**
	* @methodName	: createImageThumbnail
	* @author				: 최정석
	* @date            	: 2026. 3. 17.
	* @description		: 이미지 썸네일 생성 로직
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 3. 17.      최정석             최초 생성
	*/
    public static void createImageThumbnail(AdminFileDTO dto) throws Exception {

    	if (dto == null) {
    		return;
    	}

    	String filePath = dto.getFilePath();
    	String fileNm = dto.getFileNm();

    	if (filePath == null || fileNm == null) {
    		return;
    	}

        File sourceFile = new File(filePath + "/" + fileNm);
        if (!sourceFile.exists()) {
        	throw new RuntimeException("원본 파일이 존재하지 않습니다.");
        }

        String thumbName = "thumb_" + fileNm;
        File thumbFile = new File(filePath + "/" + thumbName);

        Thumbnails.of(sourceFile)
                .size(300, 300)
                .toFile(thumbFile);

        dto.setFileThumbNm(thumbName);
        dto.setFileThumbPath(filePath);
    }

	/**
	* @methodName	: deleteFile
	* @author				: 최정석
	* @date            	: 2026. 1. 6.
	* @description		: 파일 삭제 로직
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.       최정석             최초 생성
	*/
    public static boolean deleteFile(String path, String fileNm) {
    	if (path == null || path.trim().isEmpty() || fileNm == null || fileNm.trim().isEmpty()) {
    		return false;
    	}

    	boolean fileDeleted = true;
    	boolean thumbDeleted = true;

    	File file = new File(path, fileNm);
    	if (file.exists()) {
    		fileDeleted = file.delete();
    	}

    	File thumb = new File(path, "thumb_" + fileNm);
    	if (thumb.exists()) {
    		thumbDeleted = thumb.delete();
    	}

    	return fileDeleted && thumbDeleted;
    }

	/**
	* @methodName	: downloadFile
	* @author				: 최정석
	* @date            	: 2026. 1. 6.
	* @description		: 파일 다운로드 로직
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.       최정석             최초 생성
	*/
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

	/**
	* @methodName	: getPreviewUrl
	* @author				: 최정석
	* @date            	: 2026. 1. 6.
	* @description		: 파일 썸네일 경로 구하는 로직
	* ===================================
	* DATE              AUTHOR             NOTE
	* ===================================
	* 2026. 1. 6.       최정석             최초 생성
	*/
    public static String getPreviewUrl(String filePath, String fileNm) {
        return "/upload/" + filePath.substring(filePath.indexOf("upload")) + "/thumb_" + fileNm;
    }
}