package com.sgn.apps.calculatorapp

import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {
    private var lastResult: Int = 0
    private var isOperationButtonSelected: Boolean = false
    private var isEditTextEmpty: Boolean = true
    private lateinit var operationType: Operations

    fun getLastResult(): Int {
        return lastResult
    }

    fun setLastResult(lastResult: Int) {
        this.lastResult = lastResult
    }

    fun getIsOperationButtonSelected(): Boolean {
        return isOperationButtonSelected
    }

    fun setOperationButtonSelected(isOperationButtonSelected: Boolean) {
        this.isOperationButtonSelected = isOperationButtonSelected

    }

    fun getIsEditTextEmpty(): Boolean {
        return isEditTextEmpty
    }

    fun setIsEditTextEmpty(isEditTextEmpty: Boolean) {
        this.isEditTextEmpty = isEditTextEmpty

    }

    fun getOperationType(): Operations {
        return operationType
    }

    fun setOperationType(operationType: Operations) {
        this.operationType = operationType

    }
}