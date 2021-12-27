# html-grader
Grades my kids' HTML so they know how will they're doing and where they can improve.

## Usage

## Grading Rubric
The initial URI which is submitted for grading is used as a starting point.  The URI is recorded in a collection of
pages and then the HTML is traversed for links.  Each link is fetched and its HTML is then parsed as above, recording
the URI in the pages collection and then searching for links until no new links are found.

Next, each page is sent to a series of graders which calculate a score for one of the dimensions in the below rubric.

| Dimension                    | Points Possible |
| ---------------------------- | --------------- |
| Number of Distinct Tags Used | 107             |
| Formatting                   | 100             |
| Syntax                       | 100             |
| Total                        | 307             |

### Number of Tags Used
The student is awarded one point for each distinct HTML tag used from the valid HTML 5 tags defined at
https://www.w3schools.com/tags/default.asp.

### Formatting
The assignment is formatted by a pretty printer and the result compared to the original assignment.  For each line
changed the student loses 1 point from a total of 100.

### Syntax
The assignment is checked by a linter.  For each error or warning found the student loses 1 point from a total of 100.

The scores are then combined to calculate a total score for the assignment as a percentage by
`sun of all scores / total possible`.
