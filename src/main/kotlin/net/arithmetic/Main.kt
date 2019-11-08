package net.arithmetic

import net.arithmetic.interpreter.Interpreter

fun main(args: Array<String>) {
    val expression = args[0]

    val interpreter = Interpreter()
    val result = interpreter.interpret(expression)

    println("Result: $result")
}