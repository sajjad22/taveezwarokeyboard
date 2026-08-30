package inc.flide.vim8.ime.layout.models

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class QuadrantSpec : FunSpec({
    context("get index for a character set string from quadrant when") {
        withData(
            nameFn = { "${it.first.sector}/${it.first.part} should have index: ${it.second}" },
            listOf(
                (Quadrant(Direction.RIGHT, Direction.TOP) to 0),
                (Quadrant(Direction.RIGHT, Direction.BOTTOM) to 5),
                (Quadrant(Direction.TOP_RIGHT, Direction.TOP) to 10),
                (Quadrant(Direction.TOP_RIGHT, Direction.BOTTOM) to 15),
                (Quadrant(Direction.TOP_LEFT, Direction.TOP) to 20),
                (Quadrant(Direction.TOP_LEFT, Direction.BOTTOM) to 25),
                (Quadrant(Direction.BOTTOM_LEFT, Direction.TOP) to 30),
                (Quadrant(Direction.BOTTOM_LEFT, Direction.BOTTOM) to 35),
                (Quadrant(Direction.BOTTOM_RIGHT, Direction.TOP) to 40),
                (Quadrant(Direction.BOTTOM_RIGHT, Direction.BOTTOM) to 45)
            )
        ) { (quadrant, index) ->
            quadrant.characterIndexInString(CharacterPosition.FIRST) shouldBe index
        }
    }

    context("opposite quadrant of RIGHT/BOTTOM when character position") {
        val quadrant = Quadrant(Direction.RIGHT, Direction.BOTTOM)
        withData(
            nameFn = { "${it.first} should be ${it.second.sector}/${it.second.part}" },
            listOf(
                (CharacterPosition.FIRST to Quadrant(Direction.TOP_LEFT, Direction.TOP)),
                (CharacterPosition.SECOND to Quadrant(Direction.TOP_LEFT, Direction.TOP))
            )
        ) { (position, opposite) ->
            quadrant.opposite(position) shouldBe opposite
        }
    }
})
