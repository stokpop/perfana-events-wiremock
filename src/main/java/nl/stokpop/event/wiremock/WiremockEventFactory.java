package nl.stokpop.event.wiremock;

import nl.stokpop.eventscheduler.api.Event;
import nl.stokpop.eventscheduler.api.EventFactory;
import nl.stokpop.eventscheduler.api.EventProperties;
import nl.stokpop.eventscheduler.api.TestContext;

public class WiremockEventFactory implements EventFactory {
    @Override
    public Event create(String eventName, TestContext testContext, EventProperties eventProperties) {
        return new WiremockEvent(eventName, testContext, eventProperties);
    }
}
