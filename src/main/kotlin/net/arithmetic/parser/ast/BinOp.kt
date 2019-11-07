package net.arithmetic.parser.ast

import net.arithmetic.lexer.Token.Operator

data class BinOp(
    val left: AST,
    val operator: Operator,
    val right: AST
) : AST