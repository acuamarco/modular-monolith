package com.example

import spock.lang.Specification

class FlakyTestSpec extends Specification {
    void "test flaky monolith behavior"() {
        given:
        def random = new Random()
        def shouldFail = random.nextBoolean()

        expect:
        if (shouldFail) {
            println "💥 Flaky test failed (simulated random failure)"
            assert false // simulate failure
        } else {
            println "✅ Flaky test passed"
            assert true
        }
    }
}
