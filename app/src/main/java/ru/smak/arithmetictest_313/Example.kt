package ru.smak.arithmetictest_313

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

enum class Operator(val symbol: Char){
    PLUS('+'), MINUS('-'), TIMES('×'), DIV('÷')
}

class Example {
    val op1: Int
    val op2: Int
    val op: Operator
    var isCorrect: Boolean? by mutableStateOf(null)
    init{
        op = Operator.entries[Random.nextInt(4)]
        val (op1, op2) = when(op){
            Operator.PLUS,
            Operator.MINUS -> Random.nextInt(1, 100) to Random.nextInt(1, 100)
            Operator.TIMES -> Random.nextInt(-14, 15) to Random.nextInt(1, 15)
            Operator.DIV -> Random.nextInt(1, 15).let { denom ->
                val r = Random.nextInt(-14, 15)
                denom * r to denom
            }
        }
        this.op1 = op1
        this.op2 = op2
    }
    fun check(userResult: Int?): Boolean{
        val result = when(op){
            Operator.PLUS -> op1+op2
            Operator.MINUS -> op1-op2
            Operator.TIMES -> op1*op2
            Operator.DIV -> op1/op2
        }

        isCorrect = result == userResult
        return isCorrect == true
    }
}