package com.aoc.day11

import Util.Companion.readInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test



class Day11Test {

    val input = readInput("/day11/day11.txt")
    val testInput = readInput("/day11/day11-test.txt")

    val testStr = """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....
    """.trimIndent()

    @Test
    fun test1() {
        part1(input)
    }

}

