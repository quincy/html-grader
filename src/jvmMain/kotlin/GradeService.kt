import mu.KotlinLogging
import java.io.File
import java.net.URI

private val log = KotlinLogging.logger {}

interface GradeService {
    fun grade(uri: String): GradeResult

    companion object {
        fun create(): GradeService = GradeServiceImpl()
    }
}

private class GradeServiceImpl : GradeService {
    override fun grade(uri: String): GradeResult {
        val file = try {
            require(uri.isNotEmpty()) { "Path to assignment cannot be empty." }
            URI(uri).normalize()
                .also { require(it.isAbsolute) { "Path to assignment must be a valid URI." } }
                .let(::File)
                .also { require(it.exists()) }
        } catch (e: RuntimeException) {
            return BadGradeRequest(e.localizedMessage)
        }

        val html = file.readText()

        val scores = Rubric.values()
            .map { it.grader }
            .map { it.grade(html) }

        // TODO
        // send it to the grader which needs to return
        // - the grade details
        // - a list of links for the same host found during parsing

        // send each of the new URIs (from the links) back to the grade service for processing

        // Reduce the results to a single grade
        val total = scores.fold(Score(0, 0)) { acc, grade ->
            Score(
                points = acc.points + grade.score.points,
                possible = acc.possible + grade.score.possible
            )
        }

        return Grades(totalScore = total, scores = scores.associateBy { it.name })
    }
}