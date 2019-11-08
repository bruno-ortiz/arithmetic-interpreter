package net.arithmetic.interpreter

import net.arithmetic.lexer.Token.Operator.Div
import net.arithmetic.lexer.Token.Operator.Minus
import net.arithmetic.lexer.Token.Operator.Mul
import net.arithmetic.lexer.Token.Operator.Plus
import net.arithmetic.parser.ast.AST
import net.arithmetic.parser.ast.BinOp
import net.arithmetic.parser.ast.Num

object NodeVisitor {

    fun visit(node: AST): Int {
        return when (node) {
            is BinOp -> visitBinOp(node)
            is Num -> visitNum(node)
            else -> throw IllegalStateException("Unknown ast node of type ${node::class.simpleName}")
        }
    }

    private fun visitBinOp(node: BinOp): Int {
        return when (node.operator) {
            Plus -> visit(node.left) + visit(node.right)
            Minus -> visit(node.left) - visit(node.right)
            Mul -> visit(node.left) * visit(node.right)
            Div -> visit(node.left) / visit(node.right)
        }
    }

    private fun visitNum(node: Num): Int {
        return node.token.value
    }
}