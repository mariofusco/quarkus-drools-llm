package org.hybridai.refund;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;
import org.hybridai.llmutil.StatelessChat;
import org.hybridai.refund.model.Customer;

@RegisterAiService(chatMemoryProviderSupplier = StatelessChat.MemorySupplier.class)
@Singleton
public interface CustomerExtractor {

    @UserMessage("Extract information about a customer from this text '{text}'. The response must contain only the JSON with customer's data and without any other sentence. Don't make things up: if you cannot recognize any valid customer data answer with an empty first name and last name and a age equal to 0.")
    Customer extractData(String text);
}

