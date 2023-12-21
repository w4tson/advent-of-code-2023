package com.aoc.day06

import kotlin.math.floor

data class Strategy(val hold: Long, val distance: Long)

data class Race(val time: Long, val recordDistance: Long) {

    fun strategies() : List<Strategy> = (0..time).map { hold -> Strategy(hold, (time - hold) * hold) }

    fun winningStrategies() : List<Strategy> = strategies().filter { it.distance > recordDistance }

    fun maxHold() : Long = Math.ceil(time.toDouble() / 2).toLong()
    fun max() : Long = (time - maxHold()) * maxHold()

    // find the max time you need to hold. Then do a binary search between 0 and that
    // (because its symmetrical)
    fun findThreshold() : Long {
        var left = 0L
        var right = maxHold()
        while (left <= right) {
            val middle = floor(((left+right)/2).toDouble()).toLong()
            if (isThreshold(middle)) {
                return middle
            } else if (distanceFor(middle) < recordDistance) {
                left = middle+1
            } else {
                right = middle -1
            }
        }
        return -1
    }

    fun howManyWays() : Long = time - ((findThreshold() - 1) * 2) -1

    fun distanceFor(hold: Long) : Long = (time - hold) * hold

    fun isThreshold(n : Long) : Boolean = (distanceFor(n)) > recordDistance && distanceFor(n-1) <= recordDistance

}

fun String.toRaces() : List<Race> {
    val (times, distances) = this.lines().map { it.split("\\s+".toRegex()).drop(1).map { it.toLong() } }
    return times.zip(distances).map { (time,distance) -> Race(time, distance) }
}

fun String.toOneBigRace() : Race {
    val (time, distance) = this.lines().map { it.split(": ")[1].replace(" ", "").toLong() }
    return Race(time, distance)
}



fun part1(input : String) {
    println(input.toRaces())
    val res = input.toRaces().map { it.winningStrategies().count() }.fold(1, Long::times)
    input.toRaces().get(2).strategies().forEach { println("${it.distance}") }
    println(res)
}

fun part2(input: String) {
    val race = input.toOneBigRace()
    val howManyWays = race.howManyWays()
    println("howManyWays = ${howManyWays}")
}