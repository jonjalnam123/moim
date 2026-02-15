package com.inst.project.utill;

import lombok.Data;

@Data
public class Pager {

    /** 검색 컬럼 */
    private String kind;

    /** 검색어 */
    private String search;

    /** 현재 페이지 번호 */
    private Integer pageNum;

    /** 페이지당 글의 수 */
    private Long perPage = 10L;

    /** DB 조회 시작 행 */
    private Long startRow;

    /** 페이지 시작 번호 */
    private Long startNum;

    /** 페이지 끝 번호 */
    private Long lastNum;

    /** 마지막 블럭 여부 */
    private boolean lastCheck;

    /* ================================
       기본값 처리 Getter
    ================================ */

    public String getSearch() {
        if (this.search == null) {
            this.search = "";
        }
        return this.search;
    }

    public Integer getPageNum() {
        if (this.pageNum == null || this.pageNum < 1) {
            this.pageNum = 1;
        }
        return this.pageNum;
    }

    public Long getPerPage() {
        if (this.perPage == null || this.perPage < 1) {
            this.perPage = 10L;
        }
        return this.perPage;
    }

    /* ================================
       시작 Row 계산
    ================================ */
    public void makeRow() {
        this.startRow = (this.getPageNum() - 1) * this.getPerPage();
    }

    /* ================================
       페이지 번호 계산
    ================================ */
    public void makeNum(Long totalCount) {

        // 전체 페이지 수
        Long totalPage = totalCount / this.getPerPage();
        if (totalCount % this.getPerPage() != 0) {
            totalPage++;
        }

        // 한 블럭당 페이지 수
        Long perBlock = 5L;

        // 전체 블럭 수
        Long totalBlock = totalPage / perBlock;
        if (totalPage % perBlock != 0) {
            totalBlock++;
        }

        // 현재 블럭 번호
        Long curBlock = this.getPageNum() / perBlock;
        if (this.getPageNum() % perBlock != 0) {
            curBlock++;
        }

        // 블럭 시작/끝 번호
        this.startNum = (curBlock - 1) * perBlock + 1;
        this.lastNum = curBlock * perBlock;

        // 마지막 블럭일 경우
        if (curBlock.equals(totalBlock)) {
            this.lastCheck = true;
            this.lastNum = totalPage;
        }
    }
}