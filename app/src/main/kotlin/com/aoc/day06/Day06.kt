package com.aoc.day06

data class Strategy(val hold: Int, val distance: Int)

data class Race(val time: Int, val distance: Int) {

    fun strategies() : List<Strategy> {
        return (0..time).map {hold -> Strategy(hold, (time - hold) * hold) }
    }

    fun winningStrategies() : List<Strategy> = strategies().filter { it.distance > distance }

}

fun String.toRaces() : List<Race> {
    val (times, distances) = this.lines().map { it.split("\\s+".toRegex()).drop(1).map { it.toInt() } }
    return times.zip(distances).map { (time,distance) -> Race(time, distance) }
}



fun part1(input : String) {
    println(input.toRaces())
    val res = input.toRaces().map { it.winningStrategies().count() }.fold(1, Int::times)
    input.toRaces().get(2).strategies().forEach { println("${it.distance}") }
    println(res)
}
