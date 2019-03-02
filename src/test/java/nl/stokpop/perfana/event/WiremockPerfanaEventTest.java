package nl.stokpop.perfana.event;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.perfana.client.api.TestContext;
import io.perfana.client.api.TestContextBuilder;
import io.perfana.event.EventProperties;
import io.perfana.event.ScheduleEvent;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.assertEquals;

public class WiremockPerfanaEventTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(8568).httpsPort(8569));

    @Test
    public void runningSomeEvents() {
        Map<String,String> props = new HashMap<>();
        props.put("wiremockFilesDir", new File(".","src/test/resources/wiremock-stubs").getAbsolutePath());
        props.put("wiremockUrl", "http://localhost:8568");

        EventProperties properties = new EventProperties(props);

        TestContext context = new TestContextBuilder()
                .setTestRunId("my-test-run-id")
                .build();
        
        WiremockPerfanaEvent event = new WiremockPerfanaEvent();
        event.beforeTest(context, properties);
        event.keepAlive(context, properties);
        event.customEvent(context, properties, ScheduleEvent.createFromLine("PT3S|wiremock-change-delay|delay=4000"));
        event.customEvent(context, properties, ScheduleEvent.createFromLine("PT1M|wiremock-change-delay|delay=8000"));
        event.afterTest(context, properties);

        // not much to assert really... just look at System.out and
        // check it does not blow with an Exception...

    }

    @Test
    public void parseSettingsZero() {
        Map<String, String> emptyMap = WiremockPerfanaEvent.parseSettings("");
        assertEquals(0, emptyMap.size());
    }

    @Test
    public void parseSettingsOne() {
        Map<String, String> settings = WiremockPerfanaEvent.parseSettings("foo=bar");
        assertEquals(1, settings.size());
        assertEquals("bar", settings.get("foo"));
    }

    @Test
    public void parseSettingsTwo() {
        Map<String, String> settings = WiremockPerfanaEvent.parseSettings("foo=bar;name=stokpop");
        assertEquals(2, settings.size());
        assertEquals("bar", settings.get("foo"));
        assertEquals("stokpop", settings.get("name"));
    }

    @Test
    public void parseSettingsNoValue() {
        Map<String, String> settings = WiremockPerfanaEvent.parseSettings("foo=bar;name");
        assertEquals(2,settings.size());
        assertEquals("bar", settings.get("foo"));
        assertEquals("", settings.get("name"));
    }

    @Test
    public void parseSettingsNoEntry() {
        Map<String, String> settings = WiremockPerfanaEvent.parseSettings("foo=bar;");
        assertEquals(1, settings.size());
        assertEquals("bar", settings.get("foo"));
    }

}