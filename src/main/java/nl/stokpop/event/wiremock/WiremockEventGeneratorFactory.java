package nl.stokpop.event.wiremock;

import nl.stokpop.eventscheduler.api.EventGenerator;
import nl.stokpop.eventscheduler.api.EventGeneratorFactory;
import nl.stokpop.eventscheduler.api.EventGeneratorProperties;
import nl.stokpop.eventscheduler.api.TestContext;

public class WiremockEventGeneratorFactory implements EventGeneratorFactory {

    @Override
    public EventGenerator create(TestContext testContext, EventGeneratorProperties eventGeneratorProperties) {
        return new WiremockEventGenerator(testContext, eventGeneratorProperties);
    }
}
