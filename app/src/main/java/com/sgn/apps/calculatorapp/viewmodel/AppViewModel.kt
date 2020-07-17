package com.sgn.apps.calculatorapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.sgn.apps.calculatorapp.model.RecyclerViewItem
import com.sgn.apps.calculatorapp.utils.OperationsEnum
import java.util.*
import kotlin.collections.ArrayList

class AppViewModel : ViewModel() {
    private var lastResult: Int = 0
    private var isOperationButtonSelected: Boolean = false
    private var isEditTextEmpty: Boolean = true
    private lateinit var operationType: OperationsEnum
    private var redoStack: Stack<RecyclerViewItem>
    private var undoStack: Stack<RecyclerViewItem>
    private var type: String = ""


    fun getType(): String {
        return type
    }

    fun setType(type: String): Boolean {
        this.type = type
        return true
    }

    init {
        redoStack = Stack()
        undoStack = Stack()
    }

    fun getRedoStack(): Stack<RecyclerViewItem> {
        return redoStack
    }

    fun setRedoStack(stack: Stack<RecyclerViewItem>): Boolean {
        this.redoStack = stack
        return true
    }

    fun getUndoStack(): Stack<RecyclerViewItem> {
        return undoStack
    }

    fun setUndoStack(stack: Stack<RecyclerViewItem>): Boolean {
        this.undoStack = stack
        return true
    }

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

    fun setOperationType(operationType: OperationsEnum): OperationsEnum {
        this.operationType = operationType
        return operationType
    }

    fun addition(secondOperand: Int): Int {

        setLastResult(getLastResult() + secondOperand)
        return getLastResult()
    }

    fun subtraction(secondOperand: Int): Int {
        setLastResult(getLastResult() - secondOperand)
        return getLastResult()
    }

    fun multiplication(secondOperand: Int): Int {
        setLastResult(getLastResult() * secondOperand)
        return getLastResult()
    }

    fun division(secondOperand: Int): Int {
        setLastResult(getLastResult() / secondOperand)
        return getLastResult()
    }

    fun equalButton(historyItem: RecyclerViewItem): Int {
        undoStack.push(historyItem)
        return undoStack.size
    }

    fun calculateLastResult(item: RecyclerViewItem?): Int {

        if (item?.operationType.equals(OperationsEnum.ADDITION.type)) {
            setLastResult(getLastResult() - item?.operationValue!!.toInt())


        }
        if (item?.operationType.equals(OperationsEnum.SUBTRACTION.type)) {
            setLastResult(getLastResult() + item?.operationValue!!.toInt())
        }
        if (item?.operationType.equals(OperationsEnum.MULTIPLICATION.type)) {
            setLastResult(getLastResult() / item?.operationValue!!.toInt())
        }
        if (item?.operationType.equals(OperationsEnum.DIVISION.type)) {
            setLastResult(getLastResult() * item?.operationValue!!.toInt())
        }
        return getLastResult()
    }

    fun getRedoResult(item: RecyclerViewItem?): Int {
        if (item?.operationType.equals(OperationsEnum.ADDITION.type)) {
            setLastResult(getLastResult() + item?.operationValue!!.toInt())

        }
        if (item?.operationType.equals(OperationsEnum.SUBTRACTION.type)) {
            setLastResult(getLastResult() - item?.operationValue!!.toInt())
        }
        if (item?.operationType.equals(OperationsEnum.MULTIPLICATION.type)) {
            setLastResult(getLastResult() * item?.operationValue!!.toInt())
        }
        if (item?.operationType.equals(OperationsEnum.DIVISION.type)) {
            setLastResult(getLastResult() / item?.operationValue!!.toInt())
        }
        return getLastResult()

    }

    fun handleButtonClicked(operationsEnum: OperationsEnum): OperationsEnum {
        setOperationButtonSelected(true)
        setOperationType(operationsEnum)
        return operationsEnum
    }

    fun undo(): Int {
        val item = undoStack.pop()
        calculateLastResult(item)
        redoStack.push(item)
        return undoStack.size
    }

    fun redo(): Int {
        val item = redoStack.pop()
        getRedoResult(item)
        undoStack.push(item)
        return redoStack.size
    }
}