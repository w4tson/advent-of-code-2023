package com.aoc.day07

import com.aoc.day07.HandType.*
import takeWhileInclusive

enum class Card {
    CARD_2,
    CARD_3,
    CARD_4,
    CARD_5,
    CARD_6,
    CARD_7,
    CARD_8,
    CARD_9,
    CARD_T,
    CARD_J,
    CARD_Q,
    CARD_K,
    CARD_A;

    companion object {
        fun fromChar(c : Char) : Card {
            return when (c) {
                '2' -> CARD_2
                '3' -> CARD_3
                '4' -> CARD_4
                '5' -> CARD_5
                '6' -> CARD_6
                '7' -> CARD_7
                '8' -> CARD_8
                '9' -> CARD_9
                'T' -> CARD_T
                'J' -> CARD_J
                'Q' -> CARD_Q
                'K' -> CARD_K
                'A' -> CARD_A
                else -> throw Exception()
            }
        }
    }
}

enum class HandType {
    HighCard,
    OnePair,
    TwoPair,
    ThreeofAKind,
    FullHouse,
    FourOfAKind,
    FiveOfAKind
}

fun part1(input : String) {
    val hands = input.lines().map {
        val (hand, bid) = it.trim().split(' ')
        Pair(hand.toHand(), bid.toInt())
    }
    val sum = hands.sortedBy { it.first }.mapIndexed { i, (_, bid) -> (i + 1) * bid }.sum()
    println("sum = ${sum}")

}

data class Hand(val cards: List<Card>) : Comparable<Hand> {
    fun handType() : HandType {
        val groupBy = cards.groupBy { it }.map { (k,v) -> Pair(k, v.size) }.toMap()

        return if (groupBy.values.contains(5)) {
            FiveOfAKind
        } else if (groupBy.values.contains(4)) {
            FourOfAKind
        } else if (groupBy.values.contains(3) && groupBy.values.contains(2)) {
            FullHouse
        } else if (groupBy.values.contains(3)) {
            ThreeofAKind
        } else if (groupBy.values.filter { it == 2 }.count() == 2) {
            TwoPair
        } else if (groupBy.values.contains(2)) {
            OnePair
        } else {
            HighCard
        }
    }

    override fun compareTo(other: Hand): Int {
        if (this.handType() == other.handType()) {
            //compare the ordering
            val res = this.cards.zip(other.cards).map { (a,b) ->
                a.ordinal - b.ordinal
            }.takeWhileInclusive { it == 0 }

            return res.last()
        } else {
            return this.handType().ordinal - other.handType().ordinal
        }
    }
}

fun String.toHand() : Hand = Hand(toCharArray().map { Card.fromChar(it) })
