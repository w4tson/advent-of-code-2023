package com.aoc.day11

import Coord
import combinations

data class Universe(val galaxies: List<Coord>, val width: Int, val height: Int) {
    fun expand() : Universe {
        val allX = galaxies.map { it.x }
        val allY = galaxies.map { it.y }
        val blankColumns = (0..width -1).filter{ !allX.contains(it) }
        val blankRows = (0..height -1).filter{ !allY.contains(it) }

        val expandedColumns = blankColumns.reversed().fold(this) { acc, i ->
            val newG = acc.galaxies.map { (x,y) ->
                if (x>i) Coord(x+1,y) else Coord(x,y)
            }
            Universe(newG, acc.width+1, acc.height)
        }

        val fullyExpandedUniverse = blankRows.reversed().fold(expandedColumns) { acc, i ->
            val newG = acc.galaxies.map { (x,y) ->
                if (y>i) Coord(x,y+1) else Coord(x,y)
            }
            Universe(newG, acc.width, acc.height+1)
        }

        return fullyExpandedUniverse
    }

    fun display() {
        (0..height-1).map { y ->
            (0..width-1).map{x->
                if (galaxies.contains(Coord(x,y))) print("#") else print(".")
            }
            println()
        }
    }
}



fun part1(input : String) : Int{
    val sum = input.toUniverse().expand()
        .galaxies.combinations(2)
            .map { (a, b) -> a.manhattenDistanceTo(b) }
            .sum()
    println("sum = ${sum}")
    return sum
}


fun String.toUniverse() : Universe {
    var width = 0
    var height = this.lines().size
    val galaxies = this.lines().flatMapIndexed { y, line ->
        width = line.length
        line.mapIndexed{ x, char -> Pair(Coord(x,y), char)}
    }.filter { (_,b) -> b == '#' }
    .map { (a,_) -> a }

    return Universe(galaxies, width, height)
}
