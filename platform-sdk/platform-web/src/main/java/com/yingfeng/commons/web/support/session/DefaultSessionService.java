package com.yingfeng.commons.web.support.session;

import com.yingfeng.commons.web.exception.SessionTimeOutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Service
public class DefaultSessionService implements SessionService {

	@Autowired
	private SessionRepository sessionRepository;

	@Override
	public String getSessionId() {
		return getCurrentRequestAttributes().getSessionId();
	}

	@Override
	public <T> void setAttribute(String key, T attr) {
		Session session = getCurrentSession();
		if (session != null) {
			session.setAttribute(key, attr);
			sessionRepository.save(session);
		}
	}

	@Override
	public <T> T getAttribute(String key) {
		Session session = getCurrentSession();
		if (session != null) {
			return session.getAttribute(key);
		}
		return null;
	}

	@Override
	public void removeAttribute(String key) {
		Session session = getCurrentSession();
		if (session != null) {
			session.removeAttribute(key);
			sessionRepository.save(session);
		}
	}

	@Override
	public void delete() {
		sessionRepository.delete(getSessionId());
	}

	@Override
	public <T> T getSession(String key) {
		Session session = getCurrentSession();
		if (session != null) {
			return session.getAttribute(key);
		}
		throw new SessionTimeOutException();
	}

	private Session getCurrentSession() {
		return sessionRepository.getSession(getSessionId());
	}

	private RequestAttributes getCurrentRequestAttributes() {
		RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
		Assert.notNull(attributes, "RequestAttributes must not be null");
		return attributes;
	}

}