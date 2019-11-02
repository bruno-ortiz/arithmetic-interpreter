package net.parenthesis

import net.arithmetic.Lexer
import net.arithmetic.Token
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class LexerTest {

    @Test
    internal fun `test can tokenize simple expression`() {
        val expression = "7 + 3 * 4"

        val tokenSequence = Lexer.tokenize(expression).toList()

        assertEquals(5, tokenSequence.size)
        assertEquals(Token.Integer(7), tokenSequence[0])
        assertEquals(Token.Operator('+'), tokenSequence[1])
        assertEquals(Token.Integer(3), tokenSequence[2])
        assertEquals(Token.Operator('*'), tokenSequence[3])
        assertEquals(Token.Integer(4), tokenSequence[4])
    }

    @Test
    internal fun `test can tokenize expression with parenthesis`() {
        val expression = "(7 + 3) * 4"

        val tokenSequence = Lexer.tokenize(expression).toList()

        assertEquals(7, tokenSequence.size)
        assertEquals(Token.LeftParenthesis, tokenSequence[0])
        assertEquals(Token.Integer(7), tokenSequence[1])
        assertEquals(Token.Operator('+'), tokenSequence[2])
        assertEquals(Token.Integer(3), tokenSequence[3])
        assertEquals(Token.RightParenthesis, tokenSequence[4])
        assertEquals(Token.Operator('*'), tokenSequence[5])
        assertEquals(Token.Integer(4), tokenSequence[6])
    }

    @Test
    internal fun `test throws on unknown token`() {
        val expression = "(7 + 3) * 4 > 3"

        assertThrows(IllegalArgumentException::class.java) {
            Lexer.tokenize(expression).toList()
        }
    }
}