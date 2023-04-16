package com.example.esa

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExposedSpringApplication

fun main(args: Array<String>) {
    runApplication<ExposedSpringApplication>(*args)
}
