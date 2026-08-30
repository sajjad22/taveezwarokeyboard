package inc.flide.vim8.ime.layout.models

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class FingerPositionSpec : FunSpec({
    context("computing movement sequence") {
        val quadrant = Quadrant(Direction.RIGHT, Direction.TOP)
        withData(
            nameFn = { "for the ${it.first} layer at ${it.second} position" },
            Triple(
                LayerLevel.FIRST,
                CharacterPosition.FIRST,
                listOf(
                    FingerPosition.INSIDE_CIRCLE,
                    FingerPosition.RIGHT,
                    FingerPosition.INSIDE_CIRCLE
                )
            ),
            Triple(
                LayerLevel.FIRST,
                CharacterPosition.SECOND,
                listOf(
                    FingerPosition.INSIDE_CIRCLE,
                    FingerPosition.RIGHT,
                    FingerPosition.TOP_RIGHT,
                    FingerPosition.INSIDE_CIRCLE
                )
            ),
            Triple(
                LayerLevel.FIRST,
                CharacterPosition.THIRD,
                listOf(
                    FingerPosition.INSIDE_CIRCLE,
                    FingerPosition.RIGHT,
                    FingerPosition.TOP_RIGHT,
                    FingerPosition.TOP_LEFT,
                    FingerPosition.INSIDE_CIRCLE
                )
            ),
            Triple(
                LayerLevel.FIRST,
                CharacterPosition.FOURTH,
                listOf(
                    FingerPosition.INSIDE_CIRCLE,
                    FingerPosition.RIGHT,
                    FingerPosition.TOP_RIGHT,
                    FingerPosition.TOP_LEFT,
                    FingerPosition.BOTTOM_LEFT,
                    FingerPosition.INSIDE_CIRCLE
                )
            ),
            Triple(
                LayerLevel.FIRST,
                CharacterPosition.FIFTH,
                listOf(
                    FingerPosition.INSIDE_CIRCLE,
                    FingerPosition.RIGHT,
                    FingerPosition.TOP_RIGHT,
                    FingerPosition.TOP_LEFT,
                    FingerPosition.BOTTOM_LEFT,
                    FingerPosition.BOTTOM_RIGHT,
                    FingerPosition.INSIDE_CIRCLE
                )
            )
        ) { (layer, position, result) ->
            FingerPosition.computeMovementSequence(layer, quadrant, position) shouldBe result
        }
    }
})
