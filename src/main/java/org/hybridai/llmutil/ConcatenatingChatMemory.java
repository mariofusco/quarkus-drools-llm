package org.hybridai.llmutil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.inject.Singleton;

@Singleton
public class ConcatenatingChatMemory {
    private final Map<Object, StringBuilder> memories = new ConcurrentHashMap<>();

    public String append(Object sessionId, String message) {
        return memories.computeIfAbsent(sessionId, id -> new StringBuilder()).append(message).append(". ").toString();
    }

    public void clear(Object sessionId) {
        memories.remove(sessionId);
    }

}
