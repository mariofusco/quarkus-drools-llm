package org.mfusco;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChatMemoryBean implements ChatMemoryProvider {

    private final Map<Object, ChatMemory> memories = new ConcurrentHashMap<>();

    private final Map<Object, Person> persons = new ConcurrentHashMap<>();

    @Override
    public ChatMemory get(Object memoryId) {
        return memories.computeIfAbsent(memoryId, id -> MessageWindowChatMemory.builder()
                .maxMessages(20)
                .id(memoryId)
                .build());
    }

    public void clear(Object session) {
        memories.remove(session);
    }

    public void registerPerson(Object id, Person person) {
        persons.put(id, person);
    }

    public Person getPerson(Object id) {
        return persons.get(id);
    }
}
