package com.domain;



import java.io.Serializable;

/**
 * 课程薪资实体类,用于表示薪资表
 * @author : zzc
 * @version 1.1.0
 **/
public class Pay implements Serializable {

   private long cid ;  // 课程id
   private double qualified ; //合格员工薪资/人
   private double basic ;   //基础工资

    public Pay() {
    }
    public Pay(long cid) {
        this.cid = cid ;
    }

    public Pay(long cid, double qualified, double basic) {
        this.cid = cid;
        this.qualified = qualified;
        this.basic = basic;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public double getQualified() {
        return qualified;
    }

    public void setQualified(double qualified) {
        this.qualified = qualified;
    }

    public double getBasic() {
        return basic;
    }

    public void setBasic(double basic) {
        this.basic = basic;
    }

    @Override
    public String toString() {
        return "Pay{" +
                "cid=" + cid +
                ", qualified=" + qualified +
                ", basic=" + basic +
                '}';
    }
}
