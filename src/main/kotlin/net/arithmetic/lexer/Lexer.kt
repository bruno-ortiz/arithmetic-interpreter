package net.arithmetic.lexer

import net.arithmetic.lexer.Token.LeftParenthesis
import net.arithmetic.lexer.Token.Operator
import net.arithmetic.lexer.Token.Operator.Companion.OPERATOR_LIST
import net.arithmetic.lexer.Token.RightParenthesis

object Lexer {

    fun tokenize(expression: String): Sequence<Token> {
        var position = 0
        var currentChar: Char? = expression[position]

        fun advance() {
            position += 1
            currentChar = when {
                position >= expression.length -> null
                else -> expression[position]
            }
        }

        fun skipWhitespaces() {
            while (currentChar != null && currentChar!!.isWhitespace()) {
                advance()
            }
        }

        fun intToken(): Token.Integer {
            val sb = StringBuilder()
            while (currentChar != null && currentChar!!.isDigit()) {
                sb.append(currentChar!!)
                advance()
            }
            return Token.Integer(Integer.valueOf(sb.toString()))
        }

        return generateSequence {
            if (currentChar != null && currentChar!!.isWhitespace()) {
                skipWhitespaces()
            }
            if (currentChar == null) return@generateSequence null

            return@generateSequence when {
                currentChar!!.isDigit() -> intToken()
                currentChar!! in OPERATOR_LIST -> Operator.from(currentChar!!).also { advance() }
                currentChar!! == LeftParenthesis.repr -> LeftParenthesis.also { advance() }
                currentChar!! == RightParenthesis.repr -> RightParenthesis.also { advance() }
                else -> throw IllegalArgumentException("Unknown token $currentChar.")
            }
        }
    }
}
