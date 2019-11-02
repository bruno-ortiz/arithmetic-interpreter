package net.arithmetic

sealed class Token {

    data class Integer(
        val value: Int
    ) : Token()

    data class Operator(
        val value: Char
    ) : Token()

    object LeftParenthesis : Token()
    object RightParenthesis : Token()
}