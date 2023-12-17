package aoc.day05

import Util.Companion.readInput
import com.aoc.day05.Almanac
import com.aoc.day05.part1
import com.aoc.day05.part2
import com.aoc.day05.toAlmanac
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test



class Day05Test {

    val input = readInput("/day05/day05.txt")
    val testInput = readInput("/day05/day05-test.txt")

    val testStr = """
        ...
    """.trimIndent()

    @Test
    fun test1() {
        println(part1(input))
    }

    @Test
    fun test2() {
        println("Seed ${part2(input)}")
    }
}

