package VO;

import java.util.Date;

public class ADUserVo {
    private String company;
    private String banner;
    private Date start_AD;
    private Date end_AD;
    private int AD_Views;
    private int view_Price;
    private int company_maony;
    public ADUserVo(String banner) {
        this.banner = banner;
    }

    public ADUserVo(String company, String banner, Date start_AD, Date end_AD, int AD_Views, int view_Price, int company_maony) {
        this.company = company;
        this.banner = banner;
        this.start_AD = start_AD;
        this.end_AD = end_AD;
        this.AD_Views = AD_Views;
        this.view_Price = view_Price;
        this.company_maony = company_maony;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Date getStart_AD() {
        return start_AD;
    }

    public void setStart_AD(Date start_AD) {
        this.start_AD = start_AD;
    }

    public Date getEnd_AD() {
        return end_AD;
    }

    public void setEnd_AD(Date end_AD) {
        this.end_AD = end_AD;
    }

    public int getAD_Views() {
        return AD_Views;
    }

    public void setAD_Views(int AD_Views) {
        this.AD_Views = AD_Views;
    }

    public int getView_Price() {
        return view_Price;
    }

    public void setView_Price(int view_Price) {
        this.view_Price = view_Price;
    }

    public int getCompany_maony() {
        return company_maony;
    }

    public void setCompany_maony(int company_maony) {
        this.company_maony = company_maony;
    }
}