package com.example.xiaopeng.myapplication.response;

import java.io.Serializable;

/**
 * Data的返回基类
 * @author xiaopeng
 * @date 2018/9/18
 *
 */
public class ResponseData<T> implements Serializable {
    private static final long serialVersionUID = 4013443090078725706L;

    public static final int SUCCESS_CODE = 0;
    public static final int LOGIN_OUT_CODE = -200199;//用户已在其他设备上登录,请重新登录
    public static final int NO_LOGIN_CODE = -200198;//未登录
    public static final int STOP_ASK_CAR_CODE = -400108;//取消订单次数过多，限制叫车
    public static final String NEAR_NO_CAR_CODE = "2003";
    public static final String NET_ERROR = "-1";
    public static final String JSON_ERROR = "-2";
    public static final String FORM_CHECK_ERROR = "-3";
    public static final String LOGIN_ERROR = "-4";
    public static final String CONFIG_ERROR = "-5";
    public static final String DECRYPTION_ERROR = "-6";
    public static final String ENCRYPTION_ERROR = "-7";
    public static final String PARAM_ERROR = "-8";
    public static final String TIME_OUT_ERROR = "-9";
    public static final String UN_KNOWN_ERROR = "-10";//未知错误
    public static final String NET_ERROR_404 = "404";
    public static final String NET_ERROR_501 = "501";//服务器501
    public static final String OK = "0";
    public static final String DEFAULT_ERROR = "-1000";
    public static final String LOGINED_ERROR = "00004";

    private T data;

    private String errMsg;

    private int errCode;

//    public ResponseData(String resCode, String resMsg_cn) {
//        super(resCode, resMsg_cn);
//    }


    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
