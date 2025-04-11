package com.jininsadaecheonmyeong.starthubserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class StartHubServerApplication

fun main(args: Array<String>) {
    runApplication<StartHubServerApplication>(*args)
}
