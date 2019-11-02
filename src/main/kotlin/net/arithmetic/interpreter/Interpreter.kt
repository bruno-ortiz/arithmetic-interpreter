package net.arithmetic.interpreter

import net.arithmetic.lexer.Lexer

class Interpreter {

    fun interpret(text: String): Int {
        val tokenSequence = Lexer.tokenize(text)

        return 0
    }
}