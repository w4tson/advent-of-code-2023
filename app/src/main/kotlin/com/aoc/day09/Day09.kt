package com.aoc.day09

fun String.toDay9() : List<List<Long>> = this.lines().map { it.trim().split(" ").map { it.toLong() } }

fun part1(input : String) : Long = input.toDay9().map { nextInSeq(it) }.sum()

fun part2(input : String) : Long = input.toDay9().map { prevInSeq(it) }.sum()

fun nextInSeq(input: List<Long>) : Long {
    val layers = buildLayers(mutableListOf(input))
    val res = layers.fold(0L) { acc, list ->
        list.last() + acc
    }
    return res
}

fun prevInSeq(input: List<Long>) : Long {
    val layers = buildLayers(mutableListOf(input))

    val res = layers.reversed().fold(0L) { acc, list ->
        list.first() - acc
    }
    return res
}

fun buildLayers(acc: MutableList<List<Long>>) : List<List<Long>> {
    val last = acc.last()
    if (last.all { it == 0L}) return acc
    else {
        acc.add(buildNextLayer(last))
        return buildLayers(acc)
    }
}

fun buildNextLayer(input: List<Long>) : List<Long> = input.windowed(2).map { (a,b) -> b - a }
