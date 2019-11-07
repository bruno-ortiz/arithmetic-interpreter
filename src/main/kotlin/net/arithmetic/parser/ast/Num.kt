package net.arithmetic.parser.ast

import net.arithmetic.lexer.Token.Integer

data class Num(val token: Integer) : AST