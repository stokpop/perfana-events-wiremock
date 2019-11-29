# test-events-wiremock

Events to load and change wiremock stubs during load tests.

Properties:
* `wiremockFilesDir` the directory where to find the wiremock files
* `wiremockUrl` the wiremock urls, comma separated
* `useProxy` on port 8888, for example to use with fiddler

Custom events:
* `wiremock-change-delay` use to change delay of wiremock instances at specific time

Example wiremock response with a dynamic delay:

```json
{
  "request": {
    "method": "GET",
    "url": "/delay"
  },
  "response": {
    "status": 200,
    "body": "Hello world! from Wiremock, with delay :-)",
    "fixedDelayMilliseconds": ${delay},
    "headers": {
      "Content-Type": "text/plain"
    }
  }
}
```
Put this in a file at in the files dir and it gets uploaded with the specific delay.

Define the delays in a eventSchedulerScript, example in use with events-gatling-maven-plugin:

```xml
<eventSchedulerScript>
    PT0S|wiremock-change-delay|delay=400
    PT30S|wiremock-change-delay|delay=4000
    PT1M30S|wiremock-change-delay|delay=8000
</eventSchedulerScript>
```
This means: set delay to 400 milliseconds at the start of the Gatling load test.
Then increase the response time to 4000 milliseconds after 30 seconds.
And increase response tim to 8000 milliseconds after 1 minute and 30 seconds. 


Works with the Stokpop event-scheduler framework: 
* https://github.com/stokpop/event-scheduler
* https://github.com/stokpop/events-gatling-maven-plugin