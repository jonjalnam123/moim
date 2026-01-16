package com.inst.project.admin.vo;

import java.util.List;

import lombok.Data;

@Data
public class AdminMenuDTO {
	
	private String menuId;
	private String menuPId;
	private String menuNm;
	private String menuUrl;
	private String menuUseYn;
	private String menuDelYn;
	private String menuCn;
	private int menuLvl;
	private int menuSort;
	private String menuDeptCd;
	private String menuTeamCd;
	private String menuPositionCd;
	private String menuIcon;
	private String regId;
	private String regDt;
	private String updId;
	private String updDt;
	
	private List<AdminUnitDTO> menuPositionCdList;


}
