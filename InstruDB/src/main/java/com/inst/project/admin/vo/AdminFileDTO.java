package com.inst.project.admin.vo;

import lombok.Data;

@Data
public class AdminFileDTO {
	
	private String fileId;
	private String fileOrgNm;
	private String fileNm;
	private String filePath;
	private long fileSize;
	private String fileExt;
	private String refType;
	private String refId;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;

}
