package org.hybridai.refund;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

public class EmptyChatMemory implements ChatMemoryProvider {

    private final ChatMemory empty = new MessageWindowChatMemory.Builder().maxMessages(1).id(-1).build();

    @Override
    public ChatMemory get(Object o) {
        return empty;
    }

    public static class Supplier implements java.util.function.Supplier<ChatMemoryProvider> {

        private static final ChatMemoryProvider INSTANCE = new EmptyChatMemory();

        @Override
        public ChatMemoryProvider get() {
            return INSTANCE;
        }
    }
}
