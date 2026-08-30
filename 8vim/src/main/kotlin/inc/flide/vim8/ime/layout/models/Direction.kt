package inc.flide.vim8.ime.layout.models

import kotlin.math.abs

enum class Direction {
    RIGHT,        // Sector 0 (0° / East)
    TOP_RIGHT,    // Sector 1 (72° / North-East)
    TOP_LEFT,     // Sector 2 (144° / North-West)
    BOTTOM_LEFT,  // Sector 3 (216° / South-West)
    BOTTOM_RIGHT, // Sector 4 (288° / South-East)
    TOP,          // 4-axis legacy / top spoke
    LEFT,         // 4-axis legacy / left spoke
    BOTTOM,       // 4-axis legacy / bottom spoke
    SECTOR_1,
    SECTOR_2,
    SECTOR_3,
    SECTOR_4,
    SECTOR_5;

    companion object {
        val sectors5 = listOf(RIGHT, TOP_RIGHT, TOP_LEFT, BOTTOM_LEFT, BOTTOM_RIGHT)

        fun baseQuadrant(continuousQuadrantValue: Int): Direction {
            val result = abs(continuousQuadrantValue % NUMBER_OF_SECTORS)
            return sectors5[result]
        }
    }
}

fun Direction.toSectorIndex(): Int = when (this) {
    Direction.RIGHT, Direction.SECTOR_1 -> 0
    Direction.TOP_RIGHT, Direction.SECTOR_2, Direction.TOP -> 1
    Direction.TOP_LEFT, Direction.SECTOR_3, Direction.LEFT -> 2
    Direction.BOTTOM_LEFT, Direction.SECTOR_4, Direction.BOTTOM -> 3
    Direction.BOTTOM_RIGHT, Direction.SECTOR_5 -> 4
}

fun Direction.toFingerPosition(): FingerPosition = when (this) {
    Direction.RIGHT, Direction.SECTOR_1 -> FingerPosition.RIGHT
    Direction.TOP_RIGHT, Direction.SECTOR_2 -> FingerPosition.TOP_RIGHT
    Direction.TOP_LEFT, Direction.SECTOR_3 -> FingerPosition.TOP_LEFT
    Direction.BOTTOM_LEFT, Direction.SECTOR_4 -> FingerPosition.BOTTOM_LEFT
    Direction.BOTTOM_RIGHT, Direction.SECTOR_5 -> FingerPosition.BOTTOM_RIGHT
    Direction.TOP -> FingerPosition.TOP
    Direction.LEFT -> FingerPosition.LEFT
    Direction.BOTTOM -> FingerPosition.BOTTOM
}

fun Direction.opposite(): Direction = when (this) {
    Direction.RIGHT, Direction.SECTOR_1 -> Direction.TOP_LEFT
    Direction.TOP_RIGHT, Direction.SECTOR_2 -> Direction.BOTTOM_LEFT
    Direction.TOP_LEFT, Direction.SECTOR_3 -> Direction.BOTTOM_RIGHT
    Direction.BOTTOM_LEFT, Direction.SECTOR_4 -> Direction.TOP_RIGHT
    Direction.BOTTOM_RIGHT, Direction.SECTOR_5 -> Direction.RIGHT
    Direction.TOP -> Direction.BOTTOM
    Direction.LEFT -> Direction.RIGHT
    Direction.BOTTOM -> Direction.TOP
}
