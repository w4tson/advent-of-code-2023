import Compass.*
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.IllegalStateException
import kotlin.math.absoluteValue
import kotlin.streams.toList

class Util {

    companion object {
        fun readInput(fileName: String): String =
            this::class.java.getResource(fileName).readText(StandardCharsets.UTF_8)
    }
}

fun repeatRange(repeat : Int) : Sequence<Int> {
    return generateSequence(0, { (it+1) % repeat })
}

fun <T> List<T>.cycle() : Sequence<T> {
    return repeatRange(this.size).map { this[it] }
}

fun naturalNumbers() : Sequence<Int> = (0..Int.MAX_VALUE).asSequence()

fun <T> Sequence<T>.takeWhileInclusive(predicate : (T) -> Boolean) : Sequence<T> {
    var keepGoing: Boolean
    var prevPred = true
    return this.takeWhile{
        keepGoing = prevPred
        prevPred = predicate(it)
        keepGoing
    }
}

fun <T> List<T>.takeWhileInclusive(predicate : (T) -> Boolean) : List<T> {
    var keepGoing: Boolean
    var prevPred = true
    return this.takeWhile{
        keepGoing = prevPred
        prevPred = predicate(it)
        keepGoing
    }
}

fun Sequence<Long>.multiply() : Long = this.reduce { acc, i -> acc * i}
fun Iterable<Long>.multiply() : Long = this.reduce { acc, i -> acc * i}


fun <T> permutations(list : List<T>) : Sequence<List<T>> {
    return sequence {
        if (list.size <= 1) yield(list) else {
            list.indices.forEach { i ->
                permutations(list.slice(0 until i) + list.slice(i+1 until list.size)).forEach { p ->
                    yield(listOf(list[i]) +p)
                }
            }
        }
    }
}

fun <T> List<T>.permutations(): List<List<T>> = if(isEmpty()) listOf(emptyList()) else  mutableListOf<List<T>>().also{result ->
    for(i in this.indices){
        (this - this[i]).permutations().forEach{
            result.add(it + this[i])
        }
    }
}

fun <T> generatePermutations(elementsList: List<T>, onNextPermutation: (List<T>) -> Unit) {
    if (elementsList.isEmpty()) {
        onNextPermutation(emptyList())
        return
    }
    val elementCounts = LinkedHashMap<T, Int>() // We need to remember order in which the elements were added to map
    elementsList.forEach {
        elementCounts[it] = 1 + (elementCounts[it] ?: 0) // Count our elements
    }
    val differentElements = elementCounts.keys

    val totalPermutationsCount = elementCounts.values.fold(1) { a, b -> a * b }

    // Next 3 collections are reused through generator loop for the sake of performance

    val takenEntryNumbers = LinkedHashMap<T, Int>() // Number of entry of each element we will take to next permutation
    differentElements.forEach { takenEntryNumbers[it] = 0 }

    val entriesOfElementViewed = HashMap<T, Int>() // Count of entries of each element we already viewed while iterating elementsList
    differentElements.forEach { entriesOfElementViewed[it] = 0 }

    val currentPermutation = ArrayList<T>() // Mutable list which we will use to write permutations in
    repeat(differentElements.size) { currentPermutation.add(elementsList[0]) } // Just fill it to needed size

    repeat(totalPermutationsCount) { // Generate next permutation
        var entriesTaken = 0 // Total count of entries taken in this permutation
        for (element in elementsList) { // Generate current permutation
            if (entriesOfElementViewed[element] == takenEntryNumbers[element]) {
                currentPermutation[entriesTaken++] = element
            }
            entriesOfElementViewed[element] = 1 + (entriesOfElementViewed[element] ?: 0)
        }
        onNextPermutation(currentPermutation)
        // Update collections to start next permutation
        differentElements.forEach { entriesOfElementViewed[it] = 0 }
        // Generate next permutation of entry numbers, where each entry number is less than element's total count
        for (element in differentElements) {
            if (1 + (takenEntryNumbers[element] ?: 0) == elementCounts[element]) {
                takenEntryNumbers[element] = 0
            }
            else {
                takenEntryNumbers[element] = 1 + (takenEntryNumbers[element] ?: 0)
                break
            }
        }

    }

}

fun String.charList() : List<Char> = this.toCharArray().toList()

fun <T> List<List<T>>.contentDeepEquals(other: List<List<T>>) : Boolean {
    this.indices.forEach {
        val a = this[it]
        val b = other[it]
        a.indices.forEach {
            if (a[it] == b[it]) return false
        }
    }
    return true
}

fun <T> List<T>.toDeque() :Deque<T> {
    val d = ArrayDeque<T>()
    this.forEach { d.addLast(it) }
    return d
}

fun String.splitIntoTwo() : Pair<String, String> {
    val halfWay = this.length /2
    return Pair(this.substring(0,halfWay),this.substring(halfWay,this.length))
}

fun String.toSetOfAscii() : Set<Int> = this.chars().toList().toSet()

fun ClosedRange<Int>.fullyContains(other: ClosedRange<Int>) : Boolean {
    return other.start >= this.start && other.endInclusive <= this.endInclusive
}

fun ClosedRange<Int>.hasOverlapWith(other: ClosedRange<Int>) : Boolean {
    return (other.start >= this.start && other.start <= this.endInclusive) ||
            (other.endInclusive >= this.start && other.endInclusive <= this.endInclusive) ||
            (this.start >= other.start && this.start <= other.endInclusive) ||
            (this.endInclusive >= other.start && this.endInclusive <= other.endInclusive)
}

fun <T> kotlin.collections.ArrayDeque<T>.removeLast(n : Int) : List<T> {
    return (0 until n ).map { this.removeLast() }.toList().reversed()
}

class Coord(val x: Int, val y : Int) {

    companion object {
        val origin = Coord(0, 0)
    }

    fun getAdjacent(width: Int = Int.MAX_VALUE, height: Int = Int.MAX_VALUE, allowNegative: Boolean = true) : List<Coord> {
        return listOf(Coord(x,y-1),Coord(x+1,y),Coord(x,y+1),Coord(x-1,y))
            .filter { it.x < width && it.y < height && (allowNegative || (it.x >= 0 && it.y >= 0)) }
    }

    fun isOrthogonalTo(other : Coord) : Boolean {
        val xDist = (x - other.x).absoluteValue
        val yDist = (y - other.y).absoluteValue
        val manhattanDistance = xDist + yDist
        return this == other || manhattanDistance == 1
    }

    fun isOneSquareAway(other: Coord) : Boolean {
        val xDist = (x - other.x).absoluteValue
        val yDist = (y - other.y).absoluteValue
        return isOrthogonalTo(other) || (xDist == 1 && yDist == 1)
    }

    fun moveByChar(ch : Char) : Coord = when (ch) {
        'U' -> Coord(x,y-1)
        'D' -> Coord(x,y+1)
        'L' -> Coord(x-1,y)
        'R' -> Coord(x+1,y)
        else -> throw IllegalStateException("Can't move in direction $ch")
    }

    operator fun plus(other : Coord) : Coord = Coord(x + other.x, y + other.y)
    operator fun minus(other : Coord) : Coord = Coord(x - other.x, y - other.y)
    operator fun component1() : Int = x
    operator fun component2() : Int = y

    fun directionTo(other: Coord): Compass {
        val v = other - this
        return when  {
            v.x == 0 && v.y < 0 -> NORTH
            v.x == 0 && v.y > 0 -> SOUTH
            v.x > 0 && v.y == 0 -> EAST
            v.x < 0 && v.y == 0 -> WEST
            v.x > 0 && v.y < 0 -> NORTH_EAST
            v.x > 0 && v.y > 0 -> SOUTH_EAST
            v.x < 0 && v.y > 0 -> SOUTH_WEST
            v.x < 0 && v.y < 0 -> NORTH_WEST
            else -> throw IllegalStateException("that's gone wrong")
        }
    }

    fun north() : Coord = moveBy(NORTH)
    fun east() : Coord = moveBy(EAST)
    fun west() : Coord = moveBy(WEST)
    fun south() : Coord = moveBy(SOUTH)
    fun southEast() : Coord = moveBy(SOUTH_EAST)
    fun southWest() : Coord = moveBy(SOUTH_WEST)

    fun moveBy(compass : Compass) : Coord {
        return when(compass) {
            NORTH -> this + Coord(0,-1)
            SOUTH -> this + Coord(0,1)
            EAST -> this + Coord(1,0)
            WEST -> this + Coord(-1,0)
            NORTH_EAST -> this + Coord(1,-1)
            SOUTH_EAST -> this + Coord(1,1)
            SOUTH_WEST -> this + Coord(-1,1)
            NORTH_WEST -> this + Coord(-1,-1)
        }
    }

    fun follow(other: Coord) : Coord {
        return if (isOneSquareAway(other)) {
            this;
        } else {
            val vector = when (directionTo(other)) {
                NORTH -> Coord(0,-1)
                SOUTH -> Coord(0,1)
                EAST -> Coord(1,0)
                WEST -> Coord(-1,0)
                NORTH_EAST -> Coord(1,-1)
                SOUTH_EAST -> Coord(1,1)
                SOUTH_WEST -> Coord(-1,1)
                NORTH_WEST -> Coord(-1,-1)
            }

            this + vector
        }
    }

    fun adjacentCoords() : List<Coord> {
        return listOf(north(), east(), south(), west())
    }

    fun manhattenDistanceTo(other : Coord) : Int {
        val xDist = (x - other.x).absoluteValue
        val yDist = (y - other.y).absoluteValue
        return xDist + yDist
    }

    fun allCoordsWithinARadius(r : Int, y: Int) : Pair<Coord, Coord> {
        val distanceToY = (this.y - y).absoluteValue
        val minx = x - r
        val maxx = x + r

        val start = Coord((minx + distanceToY), y)
        val end = Coord((maxx - distanceToY), y)

        return Pair(start, end)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coord

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "Coord(x=$x, y=$y)"
    }
}

enum class Compass {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NORTH_EAST,
    NORTH_WEST,
    SOUTH_EAST,
    SOUTH_WEST


}

fun fourCompassPoints() : List<Compass> {
    return listOf(NORTH, EAST, SOUTH, WEST)
}

fun String.chunkLinesBy(p : (s : String) -> Boolean) : List<String> {
    val initial = listOf<MutableList<String>>(mutableListOf())
    val result = this.lines().fold(initial) { acc, line ->
        if (p(line)) {
            acc + listOf(mutableListOf())
        } else {
            acc.last().add(line)
            acc
        }
    }

    return result.map { it.joinToString("\n") }
}

fun String.chunkByEmptyLine() = this.chunkLinesBy { it.trim().isEmpty() }

fun String.stripAndLastToInt() : Int = split(" ").last().toInt()
