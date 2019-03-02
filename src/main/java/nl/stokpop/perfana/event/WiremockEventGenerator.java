package nl.stokpop.perfana.event;

import io.perfana.client.api.TestContext;
import io.perfana.event.EventScheduleGenerator;
import io.perfana.event.ScheduleEvent;
import io.perfana.event.generator.GeneratorProperties;

import java.util.Collections;
import java.util.List;

public class WiremockEventGenerator implements EventScheduleGenerator {

    @Override
    public List<ScheduleEvent> generateEvents(TestContext context, GeneratorProperties props) {
        System.out.println("WiremockEventGenerator: sorry, not implemented yet.");
        return Collections.emptyList();
    }
}
