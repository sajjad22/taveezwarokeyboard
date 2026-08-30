package inc.flide.vim8.ime.layout.models

import arrow.optics.optics

const val NUMBER_OF_SECTORS = 5

@optics
data class Quadrant(val sector: Direction, val part: Direction) {
    companion object
}

fun Quadrant.characterIndexInString(characterPosition: CharacterPosition): Int {
    val sectorIdx = sector.toSectorIndex()
    val isClockwise = part == Direction.BOTTOM || part == Direction.RIGHT || part == Direction.BOTTOM_RIGHT
    val spokeOffset = if (isClockwise) 1 else 0
    val spokeIndex = sectorIdx * 2 + spokeOffset
    return spokeIndex * 5 + characterPosition.ordinal
}

fun Quadrant.opposite(position: CharacterPosition): Quadrant {
    return Quadrant(sector.opposite(), part.opposite())
}
