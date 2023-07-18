package com.example.calculatorocr.util

enum class Operator(val value: String) {
    PLUS("+"), MINUS("-"), TIMES("*"), DIVIDE_BY("/");

    companion object {
        fun from(value: String): Operator? = Operator.values().firstOrNull { it.value == value }
    }
}

fun findFirstExpression(text: String): String? {
    return Regex("\\d+[-+/*]\\d+").find(text)?.value
}

fun calculateTwoArgumentsOperation(twoArgumentsOperation: String): Float? {
    val splittedExpression: List<String> =
        twoArgumentsOperation.split(Regex("((?<=[-+/*])|(?=[-+/*]))"))

    val argumentOne = splittedExpression[0].toFloat()
    val argumentTwo = splittedExpression[2].toFloat()

    val result: Float? = when (Operator.from(splittedExpression[1])) {
        Operator.PLUS -> argumentOne + argumentTwo
        Operator.MINUS -> argumentOne - argumentTwo
        Operator.TIMES -> argumentOne * argumentTwo
        Operator.DIVIDE_BY -> argumentOne / argumentTwo
        else -> null
    }
    return result
}