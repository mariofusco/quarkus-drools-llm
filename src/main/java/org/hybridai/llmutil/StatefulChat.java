package org.hybridai.llmutil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StatefulChat implements ChatMemoryProvider {

    private final Map<Object, ChatMemory> memories = new ConcurrentHashMap<>();

    @Override
    public ChatMemory get(Object memoryId) {
        return memories.computeIfAbsent(memoryId, id -> MessageWindowChatMemory.builder()
                .maxMessages(20)
                .id(memoryId)
                .build());
    }

    public void clear(Object sessionId) {
        memories.remove(sessionId);
    }

    public static class MemorySupplier implements Supplier<ChatMemoryProvider> {

        private static final ChatMemoryProvider INSTANCE = new StatefulChat();

        @Override
        public ChatMemoryProvider get() {
            return INSTANCE;
        }
    }
}
