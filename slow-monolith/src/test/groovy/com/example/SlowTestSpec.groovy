package com.example

import spock.lang.Specification

class SlowTestSpec extends Specification {
    void "test slow legacy monolith behavior"() {
        given:
        long start = System.currentTimeMillis()
        println "\nSimulating long-running test in monolith..."
        Thread.sleep(3000) // 🔥 Simulate deep setup, multiple mocks, etc.
        long elapsed = System.currentTimeMillis() - start
        println "Slow test executed in ${elapsed} ms"

        expect:
        true // Doesn't matter — this is about simulating time
    }
}
