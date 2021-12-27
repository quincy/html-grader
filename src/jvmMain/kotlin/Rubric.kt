
enum class Rubric(
    val dimension: String,
    val description: String,
    val grader: Grader,
) {
    TAGS_USED(
        dimension = "Tags Used",
        description = "Checks your familiarity with a wide range of tags. The score you receive is the percentage of all valid HTML5 tags you actually used.",
        grader = TagsUsedGrader
    ),
    ;
}

interface Grader {
    fun grade(html: String): Grade
}
