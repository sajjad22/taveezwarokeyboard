package inc.flide.vim8.ime.layout.models

import arrow.core.getOrElse
import arrow.core.lastOrNone
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.KeyDeserializer
import com.fasterxml.jackson.databind.SerializerProvider

typealias MovementSequence = List<FingerPosition>

class MovementSequenceSerializer : JsonSerializer<MovementSequence>() {
    override fun serialize(
        value: MovementSequence,
        gen: JsonGenerator,
        serializers: SerializerProvider
    ) {
        gen.writeFieldName(value.joinToString(","))
    }
}

class MovementSequenceDeserializer : KeyDeserializer() {
    override fun deserializeKey(key: String, ctxt: DeserializationContext): MovementSequence =
        key.split(",").map { FingerPosition.valueOf(it) }
}

enum class FingerPosition {
    NO_TOUCH,
    INSIDE_CIRCLE,
    RIGHT,        // Sector 0 (0°)
    TOP_RIGHT,    // Sector 1 (72°)
    TOP_LEFT,     // Sector 2 (144°)
    BOTTOM_LEFT,  // Sector 3 (216°)
    BOTTOM_RIGHT, // Sector 4 (288°)
    TOP,          // 4-axis legacy
    LEFT,         // 4-axis legacy
    BOTTOM,       // 4-axis legacy
    LONG_PRESS,
    LONG_PRESS_END;

    companion object {
        val sectors5 = listOf(RIGHT, TOP_RIGHT, TOP_LEFT, BOTTOM_LEFT, BOTTOM_RIGHT)

        fun computeMovementSequence(
            layer: LayerLevel,
            quadrant: Quadrant,
            position: CharacterPosition
        ): MovementSequence {
            return when (layer) {
                LayerLevel.HIDDEN -> emptyList()
                else -> {
                    val movementSequencesForDefaultLayer =
                        movementSequencesForLayer(layer, quadrant, position)
                    if (movementSequencesForDefaultLayer.isEmpty()) {
                        emptyList()
                    } else {
                        movementSequencesForDefaultLayer + INSIDE_CIRCLE
                    }
                }
            }
        }

        fun computeQuickMovementSequence(
            layer: LayerLevel,
            quadrant: Quadrant,
            position: CharacterPosition
        ): MovementSequence {
            return when (layer) {
                LayerLevel.HIDDEN -> emptyList()
                else -> {
                    val movementSequenceForExtraLayer =
                        movementSequenceForExtraLayer(layer, quadrant, position)
                    listOf(INSIDE_CIRCLE) + movementSequenceForExtraLayer + listOf(INSIDE_CIRCLE)
                }
            }
        }

        private fun movementSequenceForExtraLayer(
            layer: LayerLevel,
            quadrant: Quadrant,
            position: CharacterPosition
        ): MovementSequence {
            val oppositeQuadrant = quadrant.opposite(position)
            val maxMovements = position.ordinal
            val baseMovementSequence: MovementSequence =
                (0..maxMovements).fold(emptyList()) { acc, _ ->
                    val lastPosition = acc.lastOrNone().getOrElse { INSIDE_CIRCLE }
                    val nextPosition = getNextPosition(quadrant, lastPosition)
                    acc + nextPosition
                }
            return (LayerLevel.SECOND.toInt()..layer.toInt())
                .fold(baseMovementSequence) { acc, _ ->
                    val lastPosition = acc.lastOrNone().getOrElse { INSIDE_CIRCLE }
                    val nextPosition = getNextPosition(oppositeQuadrant, lastPosition)
                    acc + nextPosition
                }
        }

        private fun movementSequencesForLayer(
            layer: LayerLevel,
            quadrant: Quadrant,
            position: CharacterPosition
        ): MovementSequence {
            return when (layer) {
                LayerLevel.FUNCTIONS, LayerLevel.HIDDEN -> emptyList()
                else -> {
                    val maxMovements = position.ordinal
                    val movementSequence =
                        LayerLevel.MovementSequences[layer].orEmpty() + INSIDE_CIRCLE
                    (0..maxMovements).fold(movementSequence) { acc, _ ->
                        val lastPosition = acc.last()
                        val nextPosition = getNextPosition(quadrant, lastPosition)
                        acc + nextPosition
                    }
                }
            }
        }

        private fun getNextPosition(
            quadrant: Quadrant,
            lastPosition: FingerPosition
        ): FingerPosition {
            val sectorIdx = quadrant.sector.toSectorIndex()
            val isCounterClockwise = quadrant.part == Direction.TOP || quadrant.part == Direction.LEFT || quadrant.part == Direction.TOP_LEFT
            
            if (lastPosition == INSIDE_CIRCLE) {
                return sectors5[sectorIdx]
            }
            val currentIdx = sectors5.indexOf(lastPosition).let { if (it == -1) sectorIdx else it }
            val nextIdx = if (isCounterClockwise) {
                (currentIdx + 1) % 5
            } else {
                (currentIdx - 1 + 5) % 5
            }
            return sectors5[nextIdx]
        }
    }
}
