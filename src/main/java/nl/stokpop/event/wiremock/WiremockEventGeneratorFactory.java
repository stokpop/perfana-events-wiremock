package nl.stokpop.event.wiremock;

import nl.stokpop.eventscheduler.api.*;

public class WiremockEventGeneratorFactory implements EventGeneratorFactory {

    @Override
    public EventGenerator create(TestContext testContext, EventGeneratorProperties eventGeneratorProperties, EventLogger logger) {
        return new WiremockEventGenerator(testContext, eventGeneratorProperties, logger);
    }
}
