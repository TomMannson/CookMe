package com.tommannson.convention.config

data class TestConfig(
    val engine: TestingEngine = TestingEngine.JUNIT4
)

enum class TestingEngine {
    JUNIT4,
    JUNIT_PLATFORM
}
