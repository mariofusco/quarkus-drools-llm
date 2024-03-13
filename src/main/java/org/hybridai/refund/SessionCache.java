package org.hybridai.refund;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.inject.Singleton;

@Singleton
public class SessionCache {
    private final Map<Object, SessionData> data = new ConcurrentHashMap<>();

    public SessionData getSessionData(Object sessionId) {
        return data.computeIfAbsent(sessionId, id -> new SessionData());
    }

    public void clear(Object sessionId) {
        data.remove(sessionId);
    }
}
