import org.jsoup.Jsoup
import org.jsoup.nodes.Node
import org.jsoup.select.NodeVisitor

object TagsUsedGrader : Grader {
    override fun grade(html: String): Grade {
        val document = Jsoup.parse(html)
        val usedTags = mutableSetOf<String>()
        document.traverse(object : NodeVisitor {
            override fun head(node: Node, depth: Int) {
                if (node.nodeName() in allTags) {
                    usedTags.add(node.nodeName())
                }
            }

            override fun tail(node: Node, depth: Int) {
                // nop
            }
        })

        val unusedTags = allTags - usedTags
        val learnNextSuggestion = "Consider learning about these next: ${unusedTags.shuffled().take(5).joinToString()}"

        val score = Score(usedTags.size, allTags.size)
        val decimal = score.percent()

        return Grade(
            name = Rubric.TAGS_USED.dimension,
            description = Rubric.TAGS_USED.description,
            score = score,
            note = when {
                decimal < 0.1 -> "Looks like you're just getting started. $learnNextSuggestion"
                decimal < 0.2 -> "Keep up the hard work. $learnNextSuggestion"
                decimal < 0.3 -> "There are still a lot of new tags to learn. $learnNextSuggestion"
                decimal < 0.4 -> "Your hard work and dedication will pay off. $learnNextSuggestion"
                decimal < 0.5 -> "You've used about half of all the tags. Keep it up!. $learnNextSuggestion"
                decimal < 0.6 -> "You've used more tags than you haven't. $learnNextSuggestion"
                decimal < 0.7 -> "It's inspiring to watch you learn. $learnNextSuggestion"
                decimal < 0.8 -> "At this point you're very fluent in HTML. $learnNextSuggestion"
                decimal < 0.9 -> "Just a few more tags remain. $learnNextSuggestion"
                decimal < 1.0 -> "Incredible! You've nearly used every single tag.. $learnNextSuggestion"
                else -> "You've managed to use every possible tag.  Amazing!"
            }
        )
    }
}

private val allTags = setOf(
    "#doctype", "#comment", "a", "abbr", "address", "area", "article", "aside", "audio", "b", "base", "bdi", "bdo", "blockquote", "body", "br", "button",
    "canvas", "caption", "cite", "code", "col", "colgroup", "data", "datalist", "dd", "del", "details", "dfn", "dialog", "div", "dl", "dt", "em", "embed",
    "fieldset", "figcaption", "figure", "footer", "form", "h1", "head", "header", "hr", "html", "i", "iframe", "img", "input", "ins", "kbd", "label", "legend",
    "li", "link", "main", "map", "mark", "meta", "meter", "nav", "noscript", "object", "ol", "optgroup", "option", "output", "p", "param", "picture", "pre",
    "progress", "q", "rp", "rt", "ruby", "s", "samp", "script", "section", "select", "small", "source", "span", "strong", "style", "sub", "summary", "sup",
    "svg", "table", "tbody", "td", "template", "textarea", "tfoot", "th", "thead", "time", "title", "tr", "track", "u", "ul", "var", "video", "wbr",
)
