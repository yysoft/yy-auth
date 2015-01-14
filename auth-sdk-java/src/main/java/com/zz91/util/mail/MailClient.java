package com.zz91.util.mail;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.NameValuePair;

import com.zz91.util.http.HttpUtils;

@Deprecated
public class MailClient {

    public boolean sendMail(JSONObject sto) throws IOException {
        boolean flag = false;
        String str = sto.toString();
        NameValuePair[] data = { new NameValuePair("email", str) };
        HttpUtils.getInstance().httpPost("http://192.168.2.210:8077/zzmail/emailsender/send.htm", data, "UTF-8");
        return flag;
    }
}
