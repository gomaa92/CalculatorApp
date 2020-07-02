package com.sgn.apps.calculatorapp.viewmodel

import androidx.lifecycle.ViewModel
import com.sgn.apps.calculatorapp.utils.OperationsEnum

class AppViewModel : ViewModel() {
    private var lastResult: Int = 0
    private var isOperationButtonSelected: Boolean = false
    private var isEditTextEmpty: Boolean = true
    private lateinit var operationType: OperationsEnum

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

    fun getOperationType(): OperationsEnum {
        return operationType
    }

    fun setOperationType(operationType: OperationsEnum) {
        this.operationType = operationType

    }
}