package net.arithmetic.lexer

sealed class Token {

    data class Integer(
        val value: Int
    ) : Token()

    data class Operator(
        val value: Char
    ) : Token() {
        companion object {
            @JvmStatic
            val OPERATOR_LIST = listOf('+', '-', '*', '/')
        }
    }

    object LeftParenthesis : Token() {
        const val value = '('
    }

    object RightParenthesis : Token() {
        const val value = ')'
    }
}