package net.arithmetic.parser

import net.arithmetic.lexer.Lexer
import net.arithmetic.lexer.Token.Integer
import net.arithmetic.lexer.Token.Operator.Div
import net.arithmetic.lexer.Token.Operator.Minus
import net.arithmetic.lexer.Token.Operator.Mul
import net.arithmetic.lexer.Token.Operator.Plus
import net.arithmetic.parser.ast.BinOp
import net.arithmetic.parser.ast.Num
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ParserTest {

    @Test
    internal fun `test can parse expression`() {
        val expression = "5 + 2 * 3"
        val parser = Parser(Lexer.tokenize(expression))

        val ast = parser.buildAST()

        val expected = BinOp(
            left = Num(Integer(5)),
            operator = Plus,
            right = BinOp(
                left = Num(Integer(2)),
                operator = Mul,
                right = Num(Integer(3))
            )
        )

        assertEquals(expected, ast)
    }


    @Test
    internal fun `test can parse expression with parenthesis`() {
        val expression = "(8 + (5 - 2) * 6) / 2"
        val parser = Parser(Lexer.tokenize(expression))

        val ast = parser.buildAST()

        val expected = BinOp(
            left = BinOp(
                left = Num(Integer(8)),
                operator = Plus,
                right = BinOp(
                    left = BinOp(
                        left = Num(Integer(5)),
                        operator = Minus,
                        right = Num(Integer(2))
                    ),
                    operator = Mul,
                    right = Num(Integer(6))
                )
            ),
            operator = Div,
            right = Num(Integer(2))
        )

        assertEquals(expected, ast)
    }

    @Test
    internal fun `test cannot interpret broken expression`() {
        val expression = "8 - * 2 + 2"
        val parser = Parser(Lexer.tokenize(expression))

        assertThrows<IllegalArgumentException> { parser.buildAST() }
    }
}