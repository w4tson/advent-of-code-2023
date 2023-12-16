package com.aoc.day04

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day04KtTest {

    @Test
    fun foo() {
        val card = to_card("Card  20:  4 96 82 86 40 24 99 72  6 50 |  6 56 29 77 76 18 48 20 45 80 46 60 81  5 19 55 94 27  9 64 53 12 82 79 83\n")
        println(card.value())
    }

    @Test
    fun day4() {
        day04()
    }

    @Test
    fun part2() {
        day04Part2()
    }
}