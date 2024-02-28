package org.hybridai.mortgage;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hybridai")
public class MortgageEndpoint {

    /*
     Mario the firstborn of the Fusco family is born the 18th day of March 1974 Nowadays he works as software engineer and earns a quarter million a year

     Sofia the daughter of Mario Fusco is born on the 26th day of the ninth month of 2011 She is a very smart student
    */

    @Inject
    PersonExtractor personExtractor;

    @Inject
    DroolsMortgageCalculator mortgageCalculator;

    @POST
    @Path("/mortgage")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String mortgage(String message) {
        long start = System.currentTimeMillis();
        Person person = personExtractor.extractPersonFrom(message);
        long llmResponse = System.currentTimeMillis();
        System.out.println( "LLM processing time: " + (llmResponse - start) + " msecs" );
        String response = mortgageCalculator.grantMortgage(person);
        long rulesResponse = System.currentTimeMillis();
        System.out.println( "Rule engine processing time: " + (rulesResponse - llmResponse) + " msecs" );
        return response;
    }
}
