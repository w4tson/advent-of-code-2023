package com.aoc.day10

import Util.Companion.readInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test



class Day10Test {

    val input = readInput("/day10/day10.txt")
    val testInput = readInput("/day10/day10-test.txt")

    val testStr = """
        -L|F7
        7S-7|
        L|7||
        -L-J|
        L|-JF
    """.trimIndent()

    val testStr2 = """
        ..F7.
        .FJ|.
        SJ.L7
        |F--J
        LJ...
    """.trimIndent()

    val testStr3 = """
        .F----7F7F7F7F-7....
        .|F--7||||||||FJ....
        .||.FJ||||||||L7....
        FJL7L7LJLJ||LJ.L-7..
        L--J.L7...LJS7F-7L7.
        ....F-J..F7FJ|L7L7L7
        ....L7.F7||L7|.L7L7|
        .....|FJLJ|FJ|F7|.LJ
        ....FJL-7.||.||||...
        ....L---J.LJ.LJLJ...
    """.trimIndent()

    @Test
    fun test1() {
        assertEquals(4, part1(testStr))
        assertEquals(8, part1(testStr2))
    }

    @Test
    fun testOne() {
        part1(input)
    }

    @Test
    fun testTwo() {
        val result = part2(input)
        println("result = ${result}")
    }
}

