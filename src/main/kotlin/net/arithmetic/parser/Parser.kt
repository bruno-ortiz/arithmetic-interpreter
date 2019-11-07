package net.arithmetic.parser

import net.arithmetic.lexer.Token
import net.arithmetic.lexer.Token.Operator
import net.arithmetic.lexer.Token.Operator.Div
import net.arithmetic.lexer.Token.Operator.Minus
import net.arithmetic.lexer.Token.Operator.Mul
import net.arithmetic.lexer.Token.Operator.Plus
import net.arithmetic.parser.ast.AST
import net.arithmetic.parser.ast.BinOp
import net.arithmetic.parser.ast.Num

class Parser(tokenSequence: Sequence<Token>) {
    private val iterator: Iterator<Token> = tokenSequence.iterator()
    private var currentToken: Token? = if (iterator.hasNext()) {
        iterator.next()
    } else throw IllegalStateException("Cannot interpret empty expression")


    fun buildAST() = expr()

    private fun expr(): AST {
        var result = term()

        while (currentToken in PLUS_MINUS) {
            val token = currentToken as Operator
            if (currentToken is Plus) {
                eat { currentToken is Plus }
            } else {
                eat { currentToken is Minus }
            }
            result = BinOp(result, token, term())
        }
        return result
    }

    private fun term(): AST {
        var result = factor()

        while (currentToken in MUL_DIV) {
            val token = currentToken as Operator
            if (currentToken is Mul) {
                eat { currentToken is Mul }
            } else {
                eat { currentToken is Div }
            }
            result = BinOp(result, token, factor())
        }
        return result
    }

    private fun factor(): AST {
        val token = currentToken
        return if (token is Token.Integer) {
            eat()
            Num(token)
        } else {
            eat { currentToken is Token.LeftParenthesis }
            val result = expr()
            eat { currentToken is Token.RightParenthesis }
            result
        }
    }

    private fun eat(tokenTypeFunc: () -> Boolean = { true }) {
        require(tokenTypeFunc()) { "Parsing error: illegal token position $currentToken" }
        currentToken = iterator.nextOrNull()
    }

    private fun <T> Iterator<T>.nextOrNull(): T? = if (hasNext()) next() else null

    companion object {
        private val PLUS_MINUS = listOf(Operator.Plus, Operator.Minus)
        private val MUL_DIV = listOf(Operator.Mul, Operator.Div)
    }
}