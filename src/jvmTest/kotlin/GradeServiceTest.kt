import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isA
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class GradeServiceTest {
    @ParameterizedTest
    @CsvSource(value = [
        "''",
        "foobar",
        "/foo",
    ])
    internal fun `returns error if uri is invalid`(uri: String) {
        val result = GradeService.create().grade(uri)
        assertThat(result, isA<BadGradeRequest>())
    }

    @Test
    internal fun `serialization works both ways`() {
        assertThat(
            Json.decodeFromString<GradeResult>(
                Json.encodeToString(
                    GradeError("foobar") as GradeResult
                )
            ),
            equalTo(GradeError("foobar")
            )
        )
    }
}