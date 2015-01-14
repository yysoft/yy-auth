/**
 * Copyright 2011 ASTO.
 * All right reserved.
 * Created on 2011-10-10
 */
package com.zz91.util.seo;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.zz91.util.lang.StringUtils;

/**
 * @author mays (mays@huanbao.com)
 * 
 *         created on 2011-10-10
 */
public class SeoUtil {

	private static Logger LOG = Logger.getLogger(SeoUtil.class);

	public final static String DEFAULT_PROP = "seo.properties";

	public final static Map<String, String> TEMPLATE = new HashMap<String, String>();

	public static SeoUtil _instance = null;

	public static synchronized SeoUtil getInstance() {
		if (_instance == null) {
			_instance = new SeoUtil();
		}
		return _instance;
	}

	public void init() {
		init(DEFAULT_PROP);
	}

	public void init(String prop) {
		LOG.debug("Initializing seo...");
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
				prop);
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			LOG.error("An error occurred when load search properties:" + prop,
					e);
		}

		String tmp = null;
		for (Object key : p.keySet()) {
			tmp = String.valueOf(key);
			if (tmp != null) {
				TEMPLATE.put(String.valueOf(key), String.valueOf(p.get(key)));
			}
		}

		LOG.debug("Initializing params end...");
	}

	public void buildSeo(String page, String[] title, String[] keywords,
			String[] desc, Map<String, Object> out) {

		String titleTmp = TEMPLATE.get(page + ".title");
		if (StringUtils.isEmpty(titleTmp)) {
			titleTmp = TEMPLATE.get("title");
		}
		if (title != null) {
			MessageFormat titleFormat = new MessageFormat(titleTmp);
			out.put("seoTitle", titleFormat.format(title));
		} else {
			out.put("seoTitle", titleTmp);
		}

		String keywordsTmp = TEMPLATE.get(page + ".keywords");
		if (StringUtils.isEmpty(keywordsTmp)) {
			keywordsTmp = TEMPLATE.get("keywords");
		}
		if (keywords != null) {
			MessageFormat keywordsFormat = new MessageFormat(keywordsTmp);
			out.put("seoKeywords", keywordsFormat.format(keywords));
		} else {
			out.put("seoKeywords", keywordsTmp);
		}

		String descTmp = TEMPLATE.get(page + ".description");
		if (StringUtils.isEmpty(descTmp)) {
			descTmp = TEMPLATE.get("description");
		}
		if (desc != null) {
			MessageFormat descriptionFormat = new MessageFormat(descTmp);
			out.put("seoDescription", descriptionFormat.format(desc));
		} else {
			out.put("seoDescription", descTmp);
		}
	}
	
	public void buildSeo(Map<String, Object> out) {
		buildSeo(null, null, null, null, out);
	}
	
	public void buildSeo(String page, Map<String, Object> out) {
		buildSeo(page, null, null, null, out);
	}

}