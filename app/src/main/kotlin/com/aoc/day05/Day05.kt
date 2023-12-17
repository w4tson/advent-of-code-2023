package com.aoc.day05

fun part1(input : String) : Long = toAlmanac(input).seeds.map { toAlmanac(input).exec(it) }.min()

fun part2(input: String): Long = toAlmanac(input).lowestLocation()

fun toAlmanac(str: String) : Almanac {
    val seeds = str.lines()[0].split(": ")[1].split(" ").map { it.toLong() }
    val mappings = str.split("\n\n").drop(1).map { Mapping(it) }
    return Almanac(seeds, mappings)
}

data class Almanac(val seeds : List<Long>, val mappings : List<Mapping>) {
    fun exec(seed : Long) : Long = mappings.fold(seed) { acc, item ->
        item.map(acc)
    }

    fun exec_reverse(location : Long) : Long =
        mappings.reversed().fold(location) { acc, item ->
            item.map_reverse(acc)
        }

    fun isSeed(potentialSeed: Long): Boolean =
        seeds.windowed(2, step = 2)
            .map { (a,b) -> (a..(a+b))}
            .any { it.contains(potentialSeed) }

    fun lowestLocation() : Long = (0..Long.MAX_VALUE).first {isSeed(exec_reverse(it)) }
}

data class Range(val source: Long, val target : Long, val length: Long)

class Mapping(str : String) {

    var ranges : List<Range>
    var name : String;
    init {
        name = str.lines()[0].split(" ")[0]
        ranges = str.lines().drop(1).map {
            val line = it.split(" ")
            Range(line[1].toLong(), line[0].toLong(), line[2].toLong())
        }
    }

    fun map(from : Long) : Long {
        ranges.find { (it.source..(it.source+it.length)).contains(from) }
            ?.let{
                return it.target + from-(it.source)
            }
        return from
    }

    fun map_reverse(to : Long) : Long {
        ranges.find { (it.target..(it.target+it.length)).contains(to) }
            ?.let{
                return it.source + to-it.target
            }
        return to
    }
}