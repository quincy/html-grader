import kotlinx.serialization.Serializable

@Serializable
data class Score(val points: Int, val possible: Int) {
    fun percent(): Double = points.toDouble() / possible
}