package nl.stokpop.event.wiremock;

import nl.stokpop.eventscheduler.api.*;

public class WiremockEventFactory implements EventFactory {
    @Override
    public Event create(String eventName, TestContext testContext, EventProperties eventProperties, EventLogger logger) {
        return new WiremockEvent(eventName, testContext, eventProperties, logger);
    }
}
