/*
 * Copyright (C) 2021 Peter Paul Bakker, Stokpop Software Solutions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.stokpop.event.wiremock;

import nl.stokpop.eventscheduler.api.config.EventConfig;
import nl.stokpop.eventscheduler.api.config.TestContext;

public class WiremockEventConfig extends EventConfig {
    private String wiremockFilesDir;
    private String wiremockUrl;
    private boolean useProxy = false;

    public void setWiremockFilesDir(String wiremockFilesDir) {
        this.wiremockFilesDir = wiremockFilesDir;
    }

    public void setWiremockUrl(String wiremockUrl) {
        this.wiremockUrl = wiremockUrl;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    @Override
    public WiremockEventContext toContext() {
        return new WiremockEventContext(super.toContext(), wiremockFilesDir, wiremockUrl, useProxy);
    }

    @Override
    public WiremockEventContext toContext(TestContext override) {
        return new WiremockEventContext(super.toContext(override), wiremockFilesDir, wiremockUrl, useProxy);
    }
    @Override
    public String toString() {
        return "WiremockEventConfig{" +
            "wiremockFilesDir='" + wiremockFilesDir + '\'' +
            ", wiremockUrl='" + wiremockUrl + '\'' +
            ", useProxy=" + useProxy +
            "} " + super.toString();
    }
}
