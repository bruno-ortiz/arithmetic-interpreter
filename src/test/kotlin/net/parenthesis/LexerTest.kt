package net.parenthesis

import net.arithmetic.Lexer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LexerTest {

    @Test
    internal fun `test can tokenize simple expression`() {
        val expression = "7 + 3 * 4"

        val lexer = Lexer()

        val tokenSequence = lexer.tokenize(expression).toList()

        assertEquals(5, tokenSequence.size)
    }
}