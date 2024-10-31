package org.hybridai.refund.aiservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.message.AiMessage;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrail;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hybridai.refund.model.Customer;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ValidJsonOutputGuardrail implements OutputGuardrail {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Inject
    Logger logger;

    @Override
    public OutputGuardrailResult validate(AiMessage responseFromLLM) {
        String llmResponse = responseFromLLM.text();
        logger.infof("LLM output: %s", llmResponse);

        if (validateJson(llmResponse, Customer.class)) {
            return success();
        }

        String json = trimNonJson(llmResponse);
        if (json != null && validateJson(json, Customer.class)) {
            return successWith(json);
        }

        return reprompt("Invalid JSON",
            "Make sure you return a valid JSON object following "
                + "the specified format");
    }

    private static String trimNonJson(String llmResponse) {
        int jsonStart = llmResponse.indexOf("{");
        int jsonEnd = llmResponse.indexOf("}");
        if (jsonStart >= 0 && jsonEnd >= 0 && jsonStart < jsonEnd) {
            return llmResponse.substring(jsonStart + 1, jsonEnd);
        }
        return null;
    }

    private static boolean validateJson(String json, Class<?> expectedOutputClass) {
        try {
            MAPPER.readValue(json, expectedOutputClass);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
