package org.hybridai.refund.aiservices;

import io.quarkiverse.langchain4j.guardrails.AbstractJsonExtractorOutputGuardrail;
import jakarta.enterprise.context.ApplicationScoped;
import org.hybridai.refund.model.Customer;

@ApplicationScoped
public class CustomerExtractionOutputGuardrail extends AbstractJsonExtractorOutputGuardrail {

    @Override
    protected Class<?> getOutputClass() {
        return Customer.class;
    }
}
