package com.aoc.day04

import Util.Companion.readInput


val card = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"

data class Card(val id: Int, val winners: List<Int>, val numbers: List<Int>) {
    fun value() : Int {
        return winners.toSet().intersect(numbers).fold(0, {acc ,item -> if (acc ==0) 1 else 2*acc})
    }

    fun matching(): Int = winners.toSet().intersect(numbers).count()
    fun matchingIds(): List<Int> = (id+1 ..(id + matching())).toList()
}
fun to_card(card : String) : Card {
    val (first, second) = card.split(" | ")
    println(first)
    val winners = first.split(": ")[1].trim().split("\\s+".toRegex()).map { it.trim().toInt() }
    val numbers = second.trim().split("\\s+".toRegex()).map { it.toInt() }
    val id = first.split(":")[0].substring(5).trim().toInt()
    return Card(id=id, winners=winners, numbers= numbers)
}

fun day04() {
    val input = readInput("day04/day04.txt")
    println(input.lines().map { to_card(it).value() }.sum())
}

fun day04Part2() {
    val input = readInput("day04/day04.txt")
    val pack = input.lines().map { to_card(it) }

    val counts = IntArray(pack.size+1) { _ -> 1 }
    val totals = IntArray(pack.size+1)
    totals[0] = 0

    for (card in pack) {
        totals[card.id] = counts[card.id]
        card.matchingIds().forEach { counts[it] += counts[card.id]  }
    }

    println(totals.sum())
}