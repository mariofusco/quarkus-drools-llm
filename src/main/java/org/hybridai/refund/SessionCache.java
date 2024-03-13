package org.hybridai.refund;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.inject.Singleton;
import org.hybridai.refund.model.SessionData;

@Singleton
public class SessionCache {
    private final Map<String, SessionData> data = new ConcurrentHashMap<>();

    public SessionData getSessionData(String sessionId) {
        return data.computeIfAbsent(sessionId, SessionData::new);
    }

    public void clear(String sessionId) {
        data.remove(sessionId);
    }
}
