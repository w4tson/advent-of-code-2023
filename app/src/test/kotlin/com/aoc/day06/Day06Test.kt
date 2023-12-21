package com.aoc.day06

import Util.Companion.readInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test



class Day06Test {

    val input = readInput("/day06/day06.txt")
    val testInput = readInput("/day06/day06-test.txt")

    val testStr = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()

    val actualStr = """
        Time:        42     68     69     85
        Distance:   284   1005   1122   1341
    """.trimIndent()



    @Test
    fun test1() {
        part1(actualStr)
    }

    @Test
    fun test2() {
        part2(actualStr)
    }
}

