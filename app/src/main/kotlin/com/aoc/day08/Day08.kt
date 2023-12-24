package com.aoc.day08

import charList
import cycle
import java.lang.Exception
import java.lang.IllegalStateException

fun part1(input : String) {
    val map = input.toDay8Map()
    //TODO i cheated here and pumped the numbers into a least common multiple calculator rather than calc myself
    map.walkAll()
}

data class Day8Map(val m : Map<String, Pair<String, String>>, val instructions: String) {

    var current : String
    var next : String

    init {
        current = "AAA"
        println("init = ${current}")
        next = ""
    }

    fun walkAll() {
        m.keys.filter { it.charList().last()=='A' }.map {
            println(it)
            walk(it)
        }.forEach { println(it) }
    }


    fun walk(init : String= "AAA"): Int {
        current = init
        val instrIter = instructions.charList().cycle().iterator()
        var steps = 0
        while (current.charList().last() != 'Z') {

            val take = instrIter.next()
            val next = when (take) {
                'L' -> m[current]!!.first
                'R' -> m[current]!!.second
                else -> throw IllegalStateException("asdf")
            }
            steps ++
            current = next
        }
        println("steps = ${steps}")
        return steps
    }
}

fun String.toDay8Map() : Day8Map {
    val (instr, mapStr) = this.split("\n\n")
    val r = "(\\w{3}) = \\((\\w{3}), (\\w{3})\\)".toRegex()
    val m = mapStr.lines().map {
        r.matchEntire(it)
        ?.destructured
        ?.let { (from, to1, to2) ->
          Pair(from, Pair(to1,to2))

        } ?: throw IllegalArgumentException("Bad input")
    }.toMap()


    return Day8Map(m, instr)
}