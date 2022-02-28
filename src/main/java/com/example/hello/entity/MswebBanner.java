package com.example.hello.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Mapper
public class MswebBanner implements Serializable {

    private int bannerId;

    private String bannerCode;

    private String bannerName;

    private String bannerUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bannerStartTime;

    private int available;

    private int sort;

    private String bannerUrlYaoyao;

    private String bannerUrlWeb;

    private String bannerTargetUrl;

    public int getBannerId() {
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerCode() {
        return bannerCode;
    }

    public void setBannerCode(String bannerCode) {
        this.bannerCode = bannerCode;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Date getBannerStartTime() {
        return bannerStartTime;
    }

    public void setBannerStartTime(Date bannerStartTime) {
        this.bannerStartTime = bannerStartTime;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getBannerUrlYaoyao() {
        return bannerUrlYaoyao;
    }

    public void setBannerUrlYaoyao(String bannerUrlYaoyao) {
        this.bannerUrlYaoyao = bannerUrlYaoyao;
    }

    public String getBannerUrlWeb() {
        return bannerUrlWeb;
    }

    public void setBannerUrlWeb(String bannerUrlWeb) {
        this.bannerUrlWeb = bannerUrlWeb;
    }

    public String getBannerTargetUrl() {
        return bannerTargetUrl;
    }

    public void setBannerTargetUrl(String bannerTargetUrl) {
        this.bannerTargetUrl = bannerTargetUrl;
    }

    @Override
    public String toString() {
        return "MswebBanner{" +
                "bannerId=" + bannerId +
                ", bannerCode='" + bannerCode + '\'' +
                ", bannerName='" + bannerName + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", bannerStartTime=" + bannerStartTime +
                ", available=" + available +
                ", sort=" + sort +
                ", bannerUrlYaoyao='" + bannerUrlYaoyao + '\'' +
                ", bannerUrlWeb='" + bannerUrlWeb + '\'' +
                ", bannerTargetUrl='" + bannerTargetUrl + '\'' +
                '}';
    }
}
