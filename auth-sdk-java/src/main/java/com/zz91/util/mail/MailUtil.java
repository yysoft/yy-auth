package com.zz91.util.mail;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;

import com.zz91.util.datetime.DateUtil;
import com.zz91.util.file.FileUtils;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;

/**
 * <br />
 * MailUtil 邮件发送工具包 使用方法： <br />
 * 
 * 第一步：在配置文件中配置邮件api host，默认：mailclient.properties <br />
 * 例：mail.host = http://admin.zz9l.com/zz91-mail <br />
 * 
 * 第二步：初始化工具 <br />
 * MailUtil.getInstance().init("web.properties"); <br />
 * 或 MailUtil.getInstance().init(); 表示初始化mailclient.properties <br />
 * 
 * 第三步：准备数据 <br />
 * sender：发送者email，如果不填，则使用邮件系统默认账号 <br />
 * receiver：邮件接收者，可以用,隔开，表示发送给多个账号，必填 <br />
 * emailTitle：邮件标题，必填 <br />
 * templateId：模板ID，即模板代号，必填 <br />
 * emailParameter：邮件正文数据模型 <br />
 * gmtPost：定时发送时间，不填则放入发送队列后发送邮件 <br />
 * isImmediate： <br />
 * username:邮件服务器账号，不填则使用sender的账号 password:邮件发送账号密码
 * accountCode:accountCode对应的随机账号
 * 
 * 邮件发送账号选择优先级：accountCode > username > 用户自己的账号密码 > 系统默认账号
 * 
 * 第四步：选择邮件调用方法，发送邮件 MailUtil.getInstance().sendMail(xxx,xxx,xxx,xxx,xxx);
 * 
 * @author Leon
 * 
 */
public class MailUtil {

    private static Logger LOG = Logger.getLogger(MailUtil.class);
    private static MailUtil _instances = null;
    // private static MailClient client = new MailClient();
    private static String MAIL_CONFIG = "mailclient.properties";
    private static String API_HOST = "http://apps.zz91.com/zz91-mail";

    public final static int PRIORITY_HEIGHT = 0;
    public final static int PRIORITY_DEFAULT = 1;
    public final static int PRIORITY_TASK = 10;

    // private static boolean FLAG = false;

    /**
     * 获取LogUtil实例
     * 
     * @return
     */
    public synchronized static MailUtil getInstance() {
        if (_instances == null) {
            _instances = new MailUtil();
        }
        return _instances;
    }

    /**
     * 初始化方法
     * 
     * @param properties
     */
    public void init() {
        init(MAIL_CONFIG);
    }

    public void init(String properties) {
        if (StringUtils.isEmpty(properties)) {
            properties = MAIL_CONFIG;
        }
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = FileUtils.readPropertyFile(properties, HttpUtils.CHARSET_UTF8);
            API_HOST = (String) map.get("mail.host");
        } catch (IOException e) {
            LOG.error("An error occurred when load mail properties:" + properties, e);
        }
    }

    /**
     * 邮件发送(参数最多)
     * 
     * @param username
     *            //smtp登录帐号
     * @param password
     *            //smtp登录密码
     * @param title
     *            //邮件标题
     * @param isImmediate
     *            //是否立即发送
     * @param receiveMail
     *            //接收方email
     * @param sender
     *            //发送方email
     * @param gmtPost
     *            //发送日期
     * @param accountCode
     *            //帐号编号
     * @param templateCode
     *            //模板编号
     * @param map
     *            //邮件模板替换参数
     * @return
     */
    public boolean sendMail(String sendName, String senderPassword, String title, String receiver, String sender, Date gmtPost,
            String accountCode, String templateId, Map<String, Object> emailParameter, Integer priority) {
        if (priority == null) {
            priority = PRIORITY_DEFAULT;
        }
        try {
            JSONObject js = JSONObject.fromObject(emailParameter);
            NameValuePair[] data = { new NameValuePair("sender", sender), new NameValuePair("receiver", receiver),
                    new NameValuePair("sendName", sendName), new NameValuePair("senderPassword", senderPassword),
                    new NameValuePair("emailTitle", title), new NameValuePair("templateId", templateId),
                    new NameValuePair("gmtPostStr", DateUtil.toString(gmtPost, "yyyy-MM-dd HH:mm:ss")),
                    new NameValuePair("accountCode", accountCode), new NameValuePair("dataMap", js.toString()),
                    new NameValuePair("priority", priority.toString()) };
            // NameValuePair[] data = { new NameValuePair("email",
            // jto.toString()) };
            HttpUtils.getInstance().httpPost(API_HOST + "/email/send.htm", data, HttpUtils.CHARSET_UTF8);
            return true;
        } catch (IOException e) {
        }
        return false;
    }

    /**
     * 定时邮件发送 注:sender与accountCode必有一不为空
     * 
     * @param title
     *            //标题
     * @param isImmediate
     *            //是否立即发送
     * @param receiveMail
     *            //接收方email
     * @param sender
     *            //发送方email
     * @param gmtPost
     *            //邮件发送日期(定时发送)
     * @param accountCode
     *            //帐号编号
     * @param templateCode
     *            //模板编号
     * @param map
     *            //邮件模板替换参数
     * @return
     */
    public boolean sendMail(String title, String receiveMail, String sender, Date gmtPost, String accountCode, String templateCode,
            Map<String, Object> map, Integer priority) {
        return sendMail(null, null, title, receiveMail, sender, gmtPost, accountCode, templateCode, map, priority);
    }

    /**
     * 根据账户编号选择发送方email发送邮件
     * 
     * @param username
     *            //smtp登录帐号
     * @param password
     *            //smtp登录密码
     * @param title
     *            //标题
     * @param receiveMail
     *            //接收方email
     * @param templateCode
     *            //模板编号
     * @param map
     *            //邮件模板替换参数
     * @return
     */
    public boolean sendMail(String username, String password, String title, String receiveMail, String templateCode,
            Map<String, Object> map, Integer priority) {
        return sendMail(username, password, title, receiveMail, null, null, null, templateCode, map, priority);
    }

    /**
     * 根据账户编号选择发送方email发送邮件
     * 
     * @param title
     *            //标题
     * @param receiveMail
     *            //接收方email
     * @param acountCode
     *            //用户编号
     * @param templateCode
     *            //模板编号
     * @param map
     *            //邮件模板替换参数
     * @return
     */
    public boolean sendMail(String title, String receiveMail, String accountCode, String templateCode, Map<String, Object> map,
            Integer priority) {
        return sendMail(null, null, title, receiveMail, null, null, accountCode, templateCode, map, priority);
    }

    /**
     * 默认帐号发送邮件
     * 
     * @param title
     *            //标题
     * @param receiveMail
     *            //接收方email
     * @param templateCode
     *            //模板编号
     * @param map
     *            //邮件模板替换参数
     * @return
     */
    public boolean sendMail(String title, String receiveMail, String templateCode, Map<String, Object> map, Integer priority) {
        return sendMail(null, null, title, receiveMail, null, null, null, templateCode, map, priority);
    }

    public static void main(String[] args) {

        MailUtil.API_HOST = "http://localhost:8077/zzmail";
//        Date date = null;
//        try {
//            date = DateUtil.getDate("2011-12-13 11:30:00", "yyyy-mm-dd HH:mm:ss");
//        } catch (ParseException e) {
//        }

//        Map<String, Object> map = new HashMap<String, Object>();
//        List<MailInfoDomain> objList=new ArrayList<MailInfoDomain>();
//        MailInfoDomain obj=new MailInfoDomain();
//        obj.setUsername("测试用户名");
//        objList.add(obj);
//        MailInfoDomain obj1=new MailInfoDomain();
//        obj1.setUsername("测试用户名不");
//        objList.add(obj1);
//        MailInfoDomain obj2=new MailInfoDomain();
//        obj2.setUsername("测试用户名c");
//        objList.add(obj2);
//        List<String> list =new ArrayList<String>();
//        list.add("d1");
//        list.add("d2");
//        list.add("d3");
//        list.add("d4");
//        list.add("好东西");
//        map.put("list", list);
//        map.put("objList", objList);
//        map.put("username", "testaccount1");
//        map.put("repwdurl", "http://www.zz91.com/?account=testaccount1");
//        MailUtil.getInstance().sendMail("邮件测试from 163", "mays@zz91.net", null, null, "test", "test", map, null);
        MailUtil.getInstance().sendMail("邮件测试from 163-maillist", "analysis@asto.mail", null, null, "test", "test_maillist", null, null);

//        map.put("username", "testaccount1");
//        map.put("repwdurl", "http://www.zz91.com/?account=testaccount1");
//        MailUtil.getInstance().sendMail("邮件测试from zz91", "kongsj@zz91.net", null, date, "zz91", "test", map, null);
    }
}
