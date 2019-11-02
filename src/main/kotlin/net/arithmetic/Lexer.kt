package net.arithmetic

import net.arithmetic.Token.LeftParenthesis
import net.arithmetic.Token.Operator
import net.arithmetic.Token.Operator.Companion.OPERATOR_LIST
import net.arithmetic.Token.RightParenthesis

object Lexer {

    fun tokenize(text: String): Sequence<Token> {
        var position = 0
        var currentChar: Char? = text[position]

        fun advance() {
            position += 1
            currentChar = when {
                position >= text.length -> null
                else -> text[position]
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
                currentChar!! in OPERATOR_LIST -> Operator(currentChar!!).also { advance() }
                currentChar!! == LeftParenthesis.value -> LeftParenthesis.also { advance() }
                currentChar!! == RightParenthesis.value -> RightParenthesis.also { advance() }
                else -> throw IllegalArgumentException("Unknown token $currentChar.")
            }
        }
    }
}
