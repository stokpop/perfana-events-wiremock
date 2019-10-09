package nl.stokpop.event.wiremock;

import nl.stokpop.eventscheduler.api.TestContext;
import nl.stokpop.eventscheduler.event.EventAdapter;
import nl.stokpop.eventscheduler.event.EventProperties;
import nl.stokpop.eventscheduler.event.ScheduleEvent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

public class WiremockEvent extends EventAdapter {

    private final static String PERFANA_EVENT = "WiremockEvent";
    private static final String WIREMOCK_FILES_DIR = "wiremockFilesDir";
    private static final String WIREMOCK_URL = "wiremockUrl";

    public static boolean isDebugEnabled = false;

    private List<WiremockClient> clients;
    private File rootDir;

    static {
        sayStatic("class loaded");
    }

    public WiremockEvent() {
        sayDebug("Default constructor called.");
    }

    @Override
    public String getName() {
        return PERFANA_EVENT;
    }

    @Override
    public void beforeTest(TestContext context, EventProperties properties) {
        sayInfo("Hello before test [" + context.getTestRunId() + "]");

        String filesDir = properties.getProperty(WIREMOCK_FILES_DIR);
        if (filesDir == null) {
            throw new WiremockEventException(String.format("property %s is not set", WIREMOCK_FILES_DIR));
        }
        rootDir = new File(filesDir);
        if (!rootDir.exists()) {
            throw new WiremockEventException(String.format("directory not found: %s", rootDir));
        }

        String wiremockUrl = properties.getProperty(WIREMOCK_URL);
        if (wiremockUrl == null) {
            throw new WiremockEventException(String.format("property %s is not set", WIREMOCK_URL));
        }
        clients = Arrays.stream(wiremockUrl.split(","))
                .map(WiremockClient::new)
                .collect(collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    private void importAllWiremockFiles(WiremockClient client, File[] files, Map<String, String> replacements) {
        Arrays.stream(files)
                .peek(file -> sayInfo("check " + file))
                .filter(file -> !file.isDirectory())
                .filter(File::canRead)
                .peek(file -> sayInfo("import " + file))
                .map(this::readContents)
                .filter(Objects::nonNull)
                .forEach(fileContents -> client.uploadFileWithReplacements(fileContents, replacements));
    }

    private String readContents(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            sayError("reading file: " + file);
            return null;
        }
    }

    @Override
    public void customEvent(TestContext context, EventProperties properties, ScheduleEvent scheduleEvent) {

        String eventName = scheduleEvent.getName();
        
        if ("wiremock-change-delay".equalsIgnoreCase(eventName)) {
            injectDelayFromSettings(context, properties, scheduleEvent);
        }
        else {
            sayDebug("ignoring unknown event [" + eventName + "]");
        }
    }

    private void injectDelayFromSettings(TestContext context, EventProperties properties, ScheduleEvent scheduleEvent) {
        Map<String, String> replacements = parseSettings(scheduleEvent.getSettings());
        if (rootDir != null && clients != null) {
            clients.forEach(client -> importAllWiremockFiles(client, rootDir.listFiles(), replacements));
        }
    }

    static Map<String, String> parseSettings(String eventSettings) {
        if (eventSettings == null || eventSettings.trim().length() == 0) {
            return Collections.emptyMap();
        }
        return Arrays.stream(eventSettings.split(";"))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(k -> k[0], v -> v.length == 2 ? v[1] : ""));
    }

    private void sayInfo(String something) {
        System.out.println(String.format("[INFO] [%s] %s", getName(), something));
    }

    private void sayError(String something) {
        System.out.println(String.format("[ERROR] [%s] %s", getName(), something));
    }

    private void sayDebug(String something) {
        if (isDebugEnabled) {
            System.out.println(String.format("[DEBUG] [%s] %s", getName(), something));
        }
    }

    private static void sayStatic(String something) {
        System.out.println(String.format("[INFO] [%s] %s", PERFANA_EVENT, something));
    }
}
