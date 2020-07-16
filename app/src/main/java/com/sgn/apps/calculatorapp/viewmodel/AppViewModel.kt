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
    private var saveUndoStack: Stack<RecyclerViewItem> = Stack()
    private var type: String = ""


    fun getType(): String {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }

    init {
        redoStack = Stack()
        undoStack = Stack()
    }

    fun getResultStack(): Stack<RecyclerViewItem> {
        return saveUndoStack
    }

    fun getRedoStack(): Stack<RecyclerViewItem> {
        return redoStack
    }

    fun setRedoStack(stack: Stack<RecyclerViewItem>) {
        this.redoStack = stack
    }

    fun getUndoStack(): Stack<RecyclerViewItem> {
        return undoStack
    }

    fun setUndoStack(stack: Stack<RecyclerViewItem>) {
        this.undoStack = stack
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

    fun setOperationType(operationType: OperationsEnum) {
        this.operationType = operationType

    }
}