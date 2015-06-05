package dev.hw.app.streaming.model;

/**
 * Created by huyuit on 6/2/2015.
 */

public class ClipItemDataResponse {

    private String PK;
    private String TITLE_VN;
    private String YOUTUBE_ID;
    private String URL;
    private String VIDEO_TYPE;
    private String TITLE_ICON;
    private String FILE_SIZE;
    private String TITLE_EN;
    private String TITLE_CN;
    private String TAGS;
    private String SOURCE;
    private String SENSITIVE_YN;
    private String CLICK_CNT;
    private String LOVE;
    private String UNLOVE;
    private String ACTIVE_YN;
    private String USER_PK;
    private String DEL_IF;
    private String CRT_BY;
    private String CRT_DT;
    private String MOD_BY;
    private String MOD_DT;
    private String POST_DATE;
    private String IP_ADDR;
    private String DIRECT_DOWNLOAD;
    private String POST_YMD;

    public ClipItemDataResponse() {
    }

    public ClipItemDataResponse(String PK, String TITLE_VN, String YOUTUBE_ID, String URL,
                                String VIDEO_TYPE, String TITLE_ICON, String FILE_SIZE,
                                String TITLE_EN, String TITLE_CN, String TAGS, String SOURCE,
                                String SENSITIVE_YN, String CLICK_CNT, String LOVE, String UNLOVE,
                                String ACTIVE_YN, String USER_PK, String DEL_IF, String CRT_BY,
                                String CRT_DT, String MOD_BY, String MOD_DT, String POST_DATE,
                                String IP_ADDR, String DIRECT_DOWNLOAD, String POST_YMD) {
        this.PK = PK;
        this.TITLE_VN = TITLE_VN;
        this.YOUTUBE_ID = YOUTUBE_ID;
        this.URL = URL;
        this.VIDEO_TYPE = VIDEO_TYPE;
        this.TITLE_ICON = TITLE_ICON;
        this.FILE_SIZE = FILE_SIZE;
        this.TITLE_EN = TITLE_EN;
        this.TITLE_CN = TITLE_CN;
        this.TAGS = TAGS;
        this.SOURCE = SOURCE;
        this.SENSITIVE_YN = SENSITIVE_YN;
        this.CLICK_CNT = CLICK_CNT;
        this.LOVE = LOVE;
        this.UNLOVE = UNLOVE;
        this.ACTIVE_YN = ACTIVE_YN;
        this.USER_PK = USER_PK;
        this.DEL_IF = DEL_IF;
        this.CRT_BY = CRT_BY;
        this.CRT_DT = CRT_DT;
        this.MOD_BY = MOD_BY;
        this.MOD_DT = MOD_DT;
        this.POST_DATE = POST_DATE;
        this.IP_ADDR = IP_ADDR;
        this.DIRECT_DOWNLOAD = DIRECT_DOWNLOAD;
        this.POST_YMD = POST_YMD;
    }

    public String getPK() {
        return PK;
    }

    public void setPK(String PK) {
        this.PK = PK;
    }

    public String getTITLE_VN() {
        return TITLE_VN;
    }

    public void setTITLE_VN(String TITLE_VN) {
        this.TITLE_VN = TITLE_VN;
    }

    public String getYOUTUBE_ID() {
        return YOUTUBE_ID;
    }

    public void setYOUTUBE_ID(String YOUTUBE_ID) {
        this.YOUTUBE_ID = YOUTUBE_ID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getVIDEO_TYPE() {
        return VIDEO_TYPE;
    }

    public void setVIDEO_TYPE(String VIDEO_TYPE) {
        this.VIDEO_TYPE = VIDEO_TYPE;
    }

    public String getTITLE_ICON() {
        return TITLE_ICON;
    }

    public void setTITLE_ICON(String TITLE_ICON) {
        this.TITLE_ICON = TITLE_ICON;
    }

    public String getFILE_SIZE() {
        return FILE_SIZE;
    }

    public void setFILE_SIZE(String FILE_SIZE) {
        this.FILE_SIZE = FILE_SIZE;
    }

    public String getTITLE_EN() {
        return TITLE_EN;
    }

    public void setTITLE_EN(String TITLE_EN) {
        this.TITLE_EN = TITLE_EN;
    }

    public String getTITLE_CN() {
        return TITLE_CN;
    }

    public void setTITLE_CN(String TITLE_CN) {
        this.TITLE_CN = TITLE_CN;
    }

    public String getTAGS() {
        return TAGS;
    }

    public void setTAGS(String TAGS) {
        this.TAGS = TAGS;
    }

    public String getSOURCE() {
        return SOURCE;
    }

    public void setSOURCE(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    public String getSENSITIVE_YN() {
        return SENSITIVE_YN;
    }

    public void setSENSITIVE_YN(String SENSITIVE_YN) {
        this.SENSITIVE_YN = SENSITIVE_YN;
    }

    public String getCLICK_CNT() {
        return CLICK_CNT;
    }

    public void setCLICK_CNT(String CLICK_CNT) {
        this.CLICK_CNT = CLICK_CNT;
    }

    public String getLOVE() {
        return LOVE;
    }

    public void setLOVE(String LOVE) {
        this.LOVE = LOVE;
    }

    public String getUNLOVE() {
        return UNLOVE;
    }

    public void setUNLOVE(String UNLOVE) {
        this.UNLOVE = UNLOVE;
    }

    public String getACTIVE_YN() {
        return ACTIVE_YN;
    }

    public void setACTIVE_YN(String ACTIVE_YN) {
        this.ACTIVE_YN = ACTIVE_YN;
    }

    public String getUSER_PK() {
        return USER_PK;
    }

    public void setUSER_PK(String USER_PK) {
        this.USER_PK = USER_PK;
    }

    public String getDEL_IF() {
        return DEL_IF;
    }

    public void setDEL_IF(String DEL_IF) {
        this.DEL_IF = DEL_IF;
    }

    public String getCRT_BY() {
        return CRT_BY;
    }

    public void setCRT_BY(String CRT_BY) {
        this.CRT_BY = CRT_BY;
    }

    public String getCRT_DT() {
        return CRT_DT;
    }

    public void setCRT_DT(String CRT_DT) {
        this.CRT_DT = CRT_DT;
    }

    public String getMOD_BY() {
        return MOD_BY;
    }

    public void setMOD_BY(String MOD_BY) {
        this.MOD_BY = MOD_BY;
    }

    public String getMOD_DT() {
        return MOD_DT;
    }

    public void setMOD_DT(String MOD_DT) {
        this.MOD_DT = MOD_DT;
    }

    public String getPOST_DATE() {
        return POST_DATE;
    }

    public void setPOST_DATE(String POST_DATE) {
        this.POST_DATE = POST_DATE;
    }

    public String getIP_ADDR() {
        return IP_ADDR;
    }

    public void setIP_ADDR(String IP_ADDR) {
        this.IP_ADDR = IP_ADDR;
    }

    public String getDIRECT_DOWNLOAD() {
        return DIRECT_DOWNLOAD;
    }

    public void setDIRECT_DOWNLOAD(String DIRECT_DOWNLOAD) {
        this.DIRECT_DOWNLOAD = DIRECT_DOWNLOAD;
    }

    public String getPOST_YMD() {
        return POST_YMD;
    }

    public void setPOST_YMD(String POST_YMD) {
        this.POST_YMD = POST_YMD;
    }
}