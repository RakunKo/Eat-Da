package io.eatda

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class EatDaApplication

fun main(args: Array<String>) {
    runApplication<EatDaApplication>(*args)
}