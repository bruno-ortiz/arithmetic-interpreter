package net.arithmetic.lexer

sealed class Token {

    sealed class Operator : Token() {
        abstract val repr: Char

        object Plus : Operator() {
            override val repr = '+'
        }

        object Minus : Operator() {
            override val repr = '-'
        }

        object Mul : Operator() {
            override val repr = '*'
        }

        object Div : Operator() {
            override val repr = '/'
        }

        override fun toString() = "$repr"

        companion object {
            @JvmStatic
            val OPERATOR_LIST: List<Char> = listOf(Plus.repr, Minus.repr, Mul.repr, Div.repr)

            @JvmStatic
            fun from(char: Char): Operator {
                return when (char) {
                    Plus.repr -> Plus
                    Minus.repr -> Minus
                    Mul.repr -> Mul
                    Div.repr -> Div
                    else -> throw IllegalArgumentException("$char is not on accepted operator list $OPERATOR_LIST")
                }
            }
        }
    }

    data class Integer(
        val value: Int
    ) : Token()

    object LeftParenthesis : Token() {
        const val repr = '('
    }

    object RightParenthesis : Token() {
        const val repr = ')'
    }
}
