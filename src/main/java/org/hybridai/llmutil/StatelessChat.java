package org.hybridai.llmutil;

import java.util.function.Supplier;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

public class StatelessChat implements ChatMemoryProvider {

    private final ChatMemory empty = new MessageWindowChatMemory.Builder().maxMessages(1).id(-1).build();

    @Override
    public ChatMemory get(Object o) {
        return empty;
    }

    public static class MemorySupplier implements Supplier<ChatMemoryProvider> {

        private static final ChatMemoryProvider INSTANCE = new StatelessChat();

        @Override
        public ChatMemoryProvider get() {
            return INSTANCE;
        }
    }
}
