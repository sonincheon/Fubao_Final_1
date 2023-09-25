package VO;

import java.math.BigDecimal;

public class Like_DisVo {
    private int s_number;
    private BigDecimal s_like;
    private BigDecimal s_dis;
    private int my_like;
    private int my_dis;

    public Like_DisVo(int s_number, BigDecimal s_like, BigDecimal s_dis) {
        this.s_number = s_number;
        this.s_like = s_like;
        this.s_dis = s_dis;
    }

    public int getS_number() {
        return s_number;
    }

    public void setS_number(int s_number) {
        this.s_number = s_number;
    }

    public BigDecimal getS_like() {
        return s_like;
    }

    public void setS_like(BigDecimal s_like) {
        this.s_like = s_like;
    }

    public BigDecimal getS_dis() {
        return s_dis;
    }

    public void setS_dis(BigDecimal s_dis) {
        this.s_dis = s_dis;
    }

    public Like_DisVo(int my_like, int my_dis) {
        this.my_like = my_like;
        this.my_dis = my_dis;
    }

    public int getMy_like() {
        return my_like;
    }

    public void setMy_like(int my_like) {
        this.my_like = my_like;
    }

    public int getMy_dis() {
        return my_dis;
    }

    public void setMy_dis(int my_dis) {
        this.my_dis = my_dis;
    }




}




















