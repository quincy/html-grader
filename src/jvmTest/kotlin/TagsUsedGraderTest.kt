import com.natpryce.hamkrest.allOf
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.has
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test

internal class TagsUsedGraderTest {
    @Test
    internal fun `five tags used`() {
        @Language("HTML")
        val input = """<!doctype html><html lang="en"><head><title>foobar</title></head><body></body></body></html>"""
        val grade = TagsUsedGrader.grade(input)
        assertThat(
            grade,
            allOf(
                    has(Grade::name, equalTo(Rubric.TAGS_USED.dimension)),
                    has(Grade::description, equalTo(Rubric.TAGS_USED.description)),
                    has(Grade::score, equalTo(Score(5, 107))),
                    has(Grade::note, containsSubstring("Looks like you're just getting started. Consider learning about these next: ")),
            )
        )
    }
}