package com.yingfeng.commons.web.support.session;

/**
 * 会话服务.
 *
 * @author pengc on 2016/11/7.
 */
public interface SessionService {

	String getSessionId();

	<T> void setAttribute(String key, T attr);

	<T> T getAttribute(String key);

	void removeAttribute(String key);

	void delete();

	/**
	 * 获取Session
	 * @param key
	 * @param <T>
	 * @return
	 */
	<T> T getSession(String key);
}
