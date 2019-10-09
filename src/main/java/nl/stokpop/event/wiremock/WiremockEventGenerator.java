package nl.stokpop.event.wiremock;

import nl.stokpop.eventscheduler.api.TestContext;
import nl.stokpop.eventscheduler.event.EventGenerator;
import nl.stokpop.eventscheduler.event.ScheduleEvent;
import nl.stokpop.eventscheduler.generator.EventGeneratorProperties;

import java.util.Collections;
import java.util.List;

public class WiremockEventGenerator implements EventGenerator {

    @Override
    public List<ScheduleEvent> generate(TestContext context, EventGeneratorProperties props) {
        System.out.println("WiremockEventGenerator: sorry, not implemented yet.");
        return Collections.emptyList();
    }

}
