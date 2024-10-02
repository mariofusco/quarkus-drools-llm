package org.hybridai.refund.aiservices;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;
import org.hybridai.llmutil.StatelessChat;
import org.hybridai.refund.model.Customer;

@RegisterAiService(chatMemoryProviderSupplier = StatelessChat.MemorySupplier.class)
@Singleton
public interface CustomerExtractor {

    @UserMessage("Extract information about a customer from this text '{text}'. The response must contain only the JSON with customer's data and without any other sentence.")
    Customer extractData(String text);
}

