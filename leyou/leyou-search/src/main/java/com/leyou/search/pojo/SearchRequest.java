package com.leyou.search.pojo;

import java.util.Map;

/**
 * @author 要子康
 * @description SearchRequest
 * @since 2020/7/11 15:31
 */
public class SearchRequest {

    private String key;

    private Integer page;

    private String sortBy;

    private Boolean descending;

    private Map<String, Object> filter;

    private static final Integer DEFAULT_SIZE = 20;

    private static final Integer DEFAULT_PAGE = 1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if (page == null){
            return DEFAULT_PAGE;
        }
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize(){
        return DEFAULT_SIZE;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public Map<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }
}
