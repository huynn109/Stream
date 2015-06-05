package dev.hw.app.streaming.model;

/**
 * Subscriber model
 *
 * Created by huyuit on 5/12/2015.
 */
public class Subscriber {
    private String pk;
    private String mobile;
    private String password;
    private String user_name;
    private String sub_type;
    private String fee_amt;
    private String active_yn;
    private String crt_dt;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public String getFee_amt() {
        return fee_amt;
    }

    public void setFee_amt(String fee_amt) {
        this.fee_amt = fee_amt;
    }

    public String getActive_yn() {
        return active_yn;
    }

    public void setActive_yn(String active_yn) {
        this.active_yn = active_yn;
    }

    public String getCrt_dt() {
        return crt_dt;
    }

    public void setCrt_dt(String crt_dt) {
        this.crt_dt = crt_dt;
    }
}
