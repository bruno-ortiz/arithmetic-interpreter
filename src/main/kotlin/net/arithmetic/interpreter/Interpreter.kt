package net.arithmetic.interpreter

import net.arithmetic.lexer.Lexer
import net.arithmetic.lexer.Token
import net.arithmetic.lexer.Token.Operator.Div
import net.arithmetic.lexer.Token.Operator.Minus
import net.arithmetic.lexer.Token.Operator.Mul
import net.arithmetic.lexer.Token.Operator.Plus
import net.arithmetic.lexer.Token.RightParenthesis

class Interpreter {
    private lateinit var iterator: Iterator<Token>
    private var currentToken: Token? = null

    fun interpret(expression: String): Int {
        init(Lexer.tokenize(expression))

        return expr()
    }

    private fun expr(): Int {
        var result = term()

        val allowedOperators = listOf(Plus, Minus)
        while (currentToken in allowedOperators) {
            result = if (currentToken is Plus) {
                eat()
                result + term()
            } else {
                eat()
                result - term()
            }
        }
        return result
    }

    private fun term(): Int {
        var result = factor()

        val allowedOperators = listOf(Mul, Div)
        while (currentToken in allowedOperators) {
            result = if (currentToken is Mul) {
                eat()
                result * factor()
            } else {
                eat()
                result / factor()
            }
        }
        return result
    }

    private fun factor(): Int {
        val token = currentToken
        return if (token is Token.Integer) {
            eat()
            token.value
        } else {
            eat()
            val result = expr()
            eat { currentToken is RightParenthesis }
            result
        }
    }

    private fun eat(tokenTypeFunc: () -> Boolean = { true }) {
        require(tokenTypeFunc()) { "Parsing error: illegal token position $currentToken" }
        currentToken = iterator.nextOrNull()
    }

    private fun init(tokenSequence: Sequence<Token>) {
        iterator = tokenSequence.iterator()
        currentToken = if (iterator.hasNext()) {
            iterator.next()
        } else throw IllegalStateException("Cannot interpret empty expression")
    }

    private fun <T> Iterator<T>.nextOrNull(): T? = if (hasNext()) next() else null
}