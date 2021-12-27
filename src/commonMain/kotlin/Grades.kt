import kotlinx.serialization.Serializable

const val gradeResource = "/grade"

@Serializable
sealed class GradeResult

@Serializable
data class Grades(val totalScore: Score, val scores: Map<String, Grade>) : GradeResult()

@Serializable
data class Grade(
    val name: String,
    val description: String,
    val score: Score,
    val note: String,
)

@Serializable
data class BadGradeRequest(val error: String): GradeResult()

@Serializable
data class GradeError(val error: String) : GradeResult()

@Serializable
data class Submission(val uri: String)
