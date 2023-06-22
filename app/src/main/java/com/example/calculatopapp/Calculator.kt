package com.example.calculatopapp

import android.media.VolumeShaper.Operation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Calculator {
    val calculatorFlow = MutableStateFlow(CalculatorModule())
    fun enterNumber(number: String) {
        val currentCalculate: CalculatorModule = calculatorFlow.value
        if (currentCalculate.operation.isEmpty()) {
            val newModel = currentCalculate.copy(
                numberFirst = currentCalculate.numberFirst + number
            )
            calculatorFlow.tryEmit(newModel)
            return
        }
        val newModel = currentCalculate.copy(
            numberSecond = currentCalculate.numberSecond + number
        )
        calculatorFlow.tryEmit(newModel)

    }

    fun enterOperation(operation: String) {
        val currentCalculate: CalculatorModule = calculatorFlow.value
        if (currentCalculate.numberFirst.isNotEmpty() &&
            currentCalculate.numberFirst.lastOrNull() != '.'
        ) {
            val newModel = currentCalculate.copy(
                operation = operation
            )
            calculatorFlow.tryEmit(newModel)
        }
    }

    fun enterCalculate() {
        val currentCalculate: CalculatorModule = calculatorFlow.value
        if (currentCalculate.numberSecond.lastOrNull() == '.')return
        val numberFirst = currentCalculate.numberFirst.toDoubleOrNull() ?: return
        val numberSecond = currentCalculate.numberSecond.toDoubleOrNull() ?: return
        val result = when (currentCalculate.operation) {
            "+" -> numberFirst + numberSecond
            "-" -> numberFirst - numberSecond
            "x" -> numberFirst * numberSecond
            "/" -> numberFirst / numberSecond
            else -> 0.0
        }
        calculatorFlow.tryEmit(
            currentCalculate.copy(
                numberFirst = result.toString(),
                numberSecond = "",
                operation = ""
            )
        )
    }

    fun enterDelete() {
        val currentCalculate: CalculatorModule = calculatorFlow.value
        when {
            currentCalculate.numberSecond.isNotEmpty() -> {
                val numberSecond = currentCalculate.numberSecond.dropLast(1)
                calculatorFlow.tryEmit(
                    currentCalculate.copy(
                        numberFirst = numberSecond
                    )
                )
            }

            currentCalculate.operation.isNotEmpty() -> {
                calculatorFlow.tryEmit(
                    currentCalculate.copy(
                        operation = ""
                    )
                )
            }

            currentCalculate.numberFirst.isNotEmpty() -> {
                val numberFirst = currentCalculate.numberFirst.dropLast(1)
                calculatorFlow.tryEmit(
                    currentCalculate.copy(
                        numberFirst = numberFirst
                    )
                )
            }
        }
    }

    fun enterClear() {
        calculatorFlow.tryEmit(CalculatorModule())

    }

    fun enterDecimal() {
        val currentCalculate: CalculatorModule = calculatorFlow.value

        if (currentCalculate.operation.isEmpty()
            && !currentCalculate.numberFirst.contains(".")
            && currentCalculate.numberFirst.isNotEmpty()
        ) {
            calculatorFlow.tryEmit(
                currentCalculate.copy(
                    numberFirst = currentCalculate.numberFirst + "."
                )
            )
            return
        }
        if (!currentCalculate.numberSecond.contains(".")
            && currentCalculate.numberSecond.isNotEmpty()
        ) {
            calculatorFlow.tryEmit(
                currentCalculate.copy(
                    numberSecond = currentCalculate.numberSecond + "."
                )
            )
        }
    }
}