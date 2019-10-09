package nl.stokpop.event.wiremock;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WiremockClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(8568).httpsPort(8569));

    private static final String MESSAGE = "{\n"+
            "    \"request\": {\n"+
            "        \"method\": \"GET\",\n"+
            "        \"url\": \"/delay\"\n"+
            "    },\n"+
            "    \"response\": {\n"+
            "        \"status\": 200,\n"+
            "        \"body\": \"Hello world! from Wiremock, with delay :-)\",\n"+
            "        \"fixedDelayMilliseconds\": ${delay},\n"+
            "\t\t\"headers\": {\n"+
            "            \"Content-Type\": \"text/plain\"\n"+
            "        }\n"+
            "    }\n"+
            "}";

    @Test
    public void uploadFileWithReplacements() {
        WiremockClient client = new WiremockClient("http://localhost:8568");

        Map<String,String> replacements = new HashMap<>();
        replacements.put("delay", "2000");
        client.uploadFileWithReplacements(MESSAGE, replacements);
    }
}