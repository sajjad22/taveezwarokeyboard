package inc.flide.vim8.ime.layout.models

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class DirectionSpec : FunSpec({
    context("convert a Direction to a FingerPosition") {
        withData(
            nameFn = { "${it.first} -> ${it.second}" },
            (Direction.RIGHT to FingerPosition.RIGHT),
            (Direction.TOP_RIGHT to FingerPosition.TOP_RIGHT),
            (Direction.TOP_LEFT to FingerPosition.TOP_LEFT),
            (Direction.BOTTOM_LEFT to FingerPosition.BOTTOM_LEFT),
            (Direction.BOTTOM_RIGHT to FingerPosition.BOTTOM_RIGHT)
        ) { (direction, fingerPosition) ->
            direction.toFingerPosition() shouldBe fingerPosition
        }
    }
    context("get the opposite direction") {
        withData(
            nameFn = { "${it.first} -> ${it.second}" },
            (Direction.RIGHT to Direction.TOP_LEFT),
            (Direction.TOP_RIGHT to Direction.BOTTOM_LEFT),
            (Direction.TOP_LEFT to Direction.BOTTOM_RIGHT),
            (Direction.BOTTOM_LEFT to Direction.TOP_RIGHT)
        ) { (direction, opposite) ->
            direction.opposite() shouldBe opposite
        }
    }

    context("get a quadrant from an int") {
        withData(
            nameFn = { "${it.first} -> ${it.second}" },
            (0 to Direction.RIGHT),
            (1 to Direction.TOP_RIGHT),
            (2 to Direction.TOP_LEFT),
            (3 to Direction.BOTTOM_LEFT),
            (4 to Direction.BOTTOM_RIGHT),
            (5 to Direction.RIGHT)
        ) { (value, quadrant) ->
            Direction.baseQuadrant(value) shouldBe quadrant
        }
    }
})
