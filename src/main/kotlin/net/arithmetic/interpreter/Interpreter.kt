package net.arithmetic.interpreter

import net.arithmetic.lexer.Lexer
import net.arithmetic.parser.Parser

class Interpreter {

    fun interpret(expression: String): Int {
        val tokenSequence = Lexer.tokenize(expression)

        val parser = Parser(tokenSequence)

        val ast = parser.buildAST()

        return 0
    }

}