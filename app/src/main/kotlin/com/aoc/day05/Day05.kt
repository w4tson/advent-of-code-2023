package com.aoc.day05

fun part1(input : String) : Long = toAlmanac(input).seeds.map { toAlmanac(input).exec(it) }.min()

fun toAlmanac(str: String) : Almanac {
    val seeds = str.lines()[0].split(": ")[1].split(" ").map { it.toLong() }
    val mappings = str.split("\n\n").drop(1).map { Mapping(it) }
    return Almanac(seeds = seeds, mappings)
}

data class Almanac(val seeds : List<Long>, val mappings : List<Mapping>) {
    fun exec(seed : Long) : Long{
        return mappings.fold(seed) { acc, item ->
            item.map(acc)
        }
    }
}

data class Range(val source: Long, val target : Long, val length: Long)

class Mapping(str : String) {

    var mappings : List<Range>
    var name : String;
    init {
        name = str.lines()[0].split(" ")[0]
        mappings = str.lines().drop(1).map {
            val line = it.split(" ")
            Range(line[1].toLong(), line[0].toLong(), line[2].toLong())
        }
    }

    fun map(from : Long) : Long {
        mappings.find { (it.source..(it.source+it.length)).contains(from) }
            ?.let{
                return it.target + from-(it.source)
            }
        return from
    }

    override fun toString(): String {
        return "Mapping(mappings=$mappings, name='$name')"
    }
}

