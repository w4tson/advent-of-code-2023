package com.aoc.day09

import Util.Companion.readInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test



class Day09Test {

    val input = readInput("/day09/day09.txt")
    val testInput = readInput("/day09/day09-test.txt")

    val testStr = """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
    """.trimIndent()

    @Test
    fun test1() {
        assertEquals(114, part1(testStr))
    }

    @Test
    fun test2() {
        assertEquals(2, part2(testStr))
    }

    @Test
    fun testpartTwo() {
        println(part2(input))
    }

}

