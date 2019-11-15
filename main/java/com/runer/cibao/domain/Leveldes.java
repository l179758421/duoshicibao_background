package com.runer.cibao.domain;

import com.runer.cibao.base.BaseBean;

import javax.persistence.*;

/**
 * @Author szhua
 * @Description:cibao==
 * @Date 2018/11/14
 **/
@Entity
@Table(name = "LevelDes")
public class Leveldes extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id ;


    long  l1_10 ;
    long  l11_20 ;
    long  l21_30 ;
    long  l31_40 ;
    long  l41_50 ;
    long  l51_up ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getL1_10() {
        return l1_10;
    }

    public void setL1_10(long l1_10) {
        this.l1_10 = l1_10;
    }

    public long getL11_20() {
        return l11_20;
    }

    public void setL11_20(long l11_20) {
        this.l11_20 = l11_20;
    }

    public long getL21_30() {
        return l21_30;
    }

    public void setL21_30(long l21_30) {
        this.l21_30 = l21_30;
    }

    public long getL31_40() {
        return l31_40;
    }

    public void setL31_40(long l31_40) {
        this.l31_40 = l31_40;
    }

    public long getL41_50() {
        return l41_50;
    }

    public void setL41_50(long l41_50) {
        this.l41_50 = l41_50;
    }

    public long getL51_up() {
        return l51_up;
    }

    public void setL51_up(long l51_up) {
        this.l51_up = l51_up;
    }
}
