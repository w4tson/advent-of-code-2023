package com.aoc.day10

import Compass
import Compass.*
import Coord
import Coord.Companion.origin
import charList
import fourCompassPoints
import kotlin.math.absoluteValue

val RED = "\u001b[31m"
val YELLOW = "\u001b[33m"
val BLUE = "\u001b[34m"

// Resets previous color codes
val RESET = "\u001b[0m"

typealias Route = List<Coord>

data class Pipes(val m: MutableMap<Coord, Char>, val start: Coord, val width: Int, val height: Int) {
    fun isValidMove(coord: Coord, compass : Compass) : Boolean {
        return when(compass) {
            NORTH -> "|F7S".contains(get(coord.moveBy(NORTH))) && isInsideMap(coord.moveBy(NORTH)) && "|LJS".contains(get(coord))
            EAST -> "-7JS".contains(get(coord.moveBy(EAST))) && isInsideMap(coord.moveBy(EAST)) && "-LFS".contains(get(coord))
            WEST -> "-LFS".contains(get(coord.moveBy(WEST))) && isInsideMap(coord.moveBy(WEST)) && "-7JS".contains(get(coord))
            SOUTH -> "|LJS".contains(get(coord.moveBy(SOUTH))) && isInsideMap(coord.moveBy(SOUTH)) && "|F7S".contains(get(coord))
            else -> throw IllegalStateException("nope")
        }
    }

    fun isInsideMap(coord: Coord) : Boolean {
        return coord.x in 0 until width && coord.y >=0 && coord.y < height
    }

    fun nextAvailableMoves(coord: Coord) : List<Coord> {
        return fourCompassPoints().filter { isValidMove(coord, it) }.map{ coord.moveBy(it) }
    }

    fun get(coord : Coord) : Char = m.getOrDefault(coord, '.')

    fun areaOfRoute() : Int {
        val route = findPipeRoute()
        val routeWrapped = route + route[0]
        val area = routeWrapped.windowed(2).map { (a,b) ->
            (a.x * b.y) - (b.x * a.y)
        }.sum() / 2
        return area
    }

    // https://en.wikipedia.org/wiki/Shoelace_formula to calc area
    // then https://en.wikipedia.org/wiki/Pick%27s_theorem
    // 🙄
    fun solve2(): Int {
        val route = findPipeRoute()
        val area = areaOfRoute().absoluteValue
        return Math.ceil(((2 * (area + 1) - route.size).toDouble() / 2)).toInt()
    }

    fun solve(): Int = (findPipeRoute().size - 1) / 2

    fun findPipeRoute() : List<Coord> {
        var routes = listOf(listOf(start))
        var circularRouteFound = false
        while (!circularRouteFound) {

            routes = routes.map { route ->
                nextAvailableMoves(route.last())
                    .filter { (it == start && route.size > 2) || !route.contains(it) }
                    .map { route + it }
            }
                .map { it.getOrNull(0) }
                .filterNotNull()

            circularRouteFound = routes.any { it.last() == start }
            if (circularRouteFound) {
               return routes[0]
            }
        }

        return listOf()
    }

    fun display(route : List<Coord> = listOf(), filled: Route = listOf()) {
        (0..height-1).forEach { y ->
            (0..width-1).forEach { x ->
                val c = Coord(x,y)
                if (route.contains(c)) print("$YELLOW${m[c]}$RESET")
                else if (filled.contains(c)) print(" ")
                else print("$BLUE${m.get(c)}$RESET")
            }
            println()
        }
    }
}

fun part1(input : String) : Int = input.toPipes().solve()
fun part2(input : String) : Int = input.toPipes().solve2()

fun String.toPipes() : Pipes {
    val m = mutableMapOf<Coord, Char>()
    var start = origin
    val width = this.lines()[0].length
    val height = this.lines().count()
    this.lines().forEachIndexed { y, line ->
        line.charList().forEachIndexed { x, it ->
            val coord = Coord(x, y)
            m.put(coord, it)
            if (it == 'S') start = coord
        }
    }
    println("start = ${start}")

    return Pipes(m, start, width, height)
}