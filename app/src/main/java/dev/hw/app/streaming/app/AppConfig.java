package dev.hw.app.streaming.app;

/**
 * Declare urls
 * <p/>
 * Created by huyuit on 5/11/2015.
 */
public class AppConfig {
    public static final String YOUTUBE_ANDROID_KEY = "AIzaSyBH5wyans3zG3WIjuu1w5u7CUhMhclOjvc";
    public static String URL_LOGIN = "http://thegioinguoidep.vn/api/user/login-auth/format/json";
    //  public static String URL_RTMP_PATH_HOME = "rtmp://192.168.1.115:1935/live/abc";
//    public static String URL_RTMP_PATH_HOME = "rtmp://123.30.191.207/antv/live?e=1431504003&st=xGMlVi-l1gE1ZDsYLje2_g";
    public static String URL_RTMP_PATH_HOME = "rtmp://123.30.191.207/vtc3/live?e=1432216803&st=hw16eOoeCmNkljo2Peq2kQ";
    public static String URL_RTMP_PATH_ONE = "rtmp://loveshop.vn:1935/live/myStream";
    public static String URL_RTMP_PATH_TWO = "rtmp://123.30.191.207/antv/live?e=1432713603&st=uzls0jGnx_r5sGPOyzrcqA";
    public static String URL_RTMP_PATH_THREE = "rtmp://123.30.191.207/vtc1/live?e=1432713603&st=HwNSX5Pf_Wt1iIEGZ_LQ_w";
    public static String URL_RTMP_PATH_FOUR = "rtmp://123.30.191.207/vtc3/live?e=1432713603&st=AOgyF9Sfy9fLikvA-JCMZQ";
    //    public static String URL_RTMP_PATH_HOME = "rtmp://123.30.191.207/vtv3/live?e=1432011603&st=zjJjONMFRbOlROp2JE1ROg";
    public static String URL_WEB_SOCKET_CHAT = "ws://103.1.209.134:9000/websocket/server.php";
    public static String[] ARR_COLOR = {"007AFF", "FF7000", "FF7000", "15E25F", "CFC700", "CFC700", "CF1100", "CF00BE", "E86256"};
    public static String URL_NEW_CLIP = "http://thegioinguoidep.vn/api/video/get_new_video/format/json";
    public static String URL_RANDOM_CLIP = "http://thegioinguoidep.vn/api/video/get_video_by_random/format/json";
}
