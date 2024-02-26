package org.hybridai.refund;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;

@RegisterAiService(chatMemoryProviderSupplier = EmptyChatMemory.Supplier.class)
@Singleton
public interface CustomerExtractor {

    @UserMessage("Extract information about a customer from {text}. The response must contain only the JSON with customer's data and without any other sentence.")
    Customer extractCustomerFrom(String text);
}

