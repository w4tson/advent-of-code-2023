package com.aoc.day07

import Util.Companion.readInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test



class Day07Test {

    val input = readInput("/day07/day07.txt")
    val testInput = readInput("/day07/day07-test.txt")

    val testStr = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()

    @Test
    fun test1() {
        part1(input)
    }

    @Test
    fun name() {
        val handType = "JJJJJ".toHand().handType()
        println("handType = ${handType}")
    }
}

