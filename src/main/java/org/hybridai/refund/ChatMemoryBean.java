package org.hybridai.refund;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChatMemoryBean implements ChatMemoryProvider {

    private final Map<Object, ChatMemory> memories = new ConcurrentHashMap<>();

    private final Map<Object, SessionData> data = new ConcurrentHashMap<>();

    @Override
    public ChatMemory get(Object memoryId) {
        return memories.computeIfAbsent(memoryId, id -> MessageWindowChatMemory.builder()
                .maxMessages(20)
                .id(memoryId)
                .build());
    }

    public void clear(Object sessionId) {
        memories.remove(sessionId);
        data.remove(sessionId);
    }

    public SessionData getSessionData(Object sessionId) {
        return data.computeIfAbsent(sessionId, id -> new SessionData());
    }
}
