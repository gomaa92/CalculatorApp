package com.sgn.apps.calculatorapp

import com.sgn.apps.calculatorapp.model.RecyclerViewItem
import com.sgn.apps.calculatorapp.utils.OperationsEnum
import com.sgn.apps.calculatorapp.viewmodel.AppViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class AppViewModelUnitTest {
    @Mock
    lateinit var appViewModel: AppViewModel


    @Before
    fun setUp() {
        appViewModel = AppViewModel()

    }

    @Test
    fun testAddition() {
        appViewModel.setLastResult(3)
        val expected = 4
        val actual = appViewModel.addition(1)
        assertEquals(expected, actual)
    }

    @Test
    fun testSubtraction() {
        appViewModel.setLastResult(3)
        val expected = 2
        val actual = appViewModel.subtraction(1)
        assertEquals(expected, actual)
    }

    @Test
    fun testMultiplication() {
        appViewModel.setLastResult(3)
        val expected = 6
        val actual = appViewModel.multiplication(2)
        assertEquals(expected, actual)
    }

    @Test
    fun testDivision() {
        appViewModel.setLastResult(3)
        val expected = 1
        val actual = appViewModel.division(3)
        assertEquals(expected, actual)
    }

    @Test
    fun testAllOperationWithInitialZero() {
        // appViewModel.setLastResult(3)
        val expected = 1
        appViewModel.addition(20)
        appViewModel.division(4)
        appViewModel.subtraction(4)
        val actual = appViewModel.multiplication(1)
        assertEquals(expected, actual)
    }

    @Test
    fun testAllOperationWithInitialNotZero() {
        appViewModel.setLastResult(5)
        val expected = 1
        appViewModel.addition(20)
        appViewModel.division(5)
        appViewModel.subtraction(4)
        val actual = appViewModel.multiplication(1)
        assertEquals(expected, actual)
    }

    @Test
    fun testEqualButton() {
        val expected = 3
        appViewModel.equalButton(RecyclerViewItem("", ""))
        appViewModel.equalButton(RecyclerViewItem("", ""))
        val actual = appViewModel.equalButton(RecyclerViewItem("", ""))
        assertEquals(expected, actual)
    }

    @Test
    fun testCalculateLastResultWithEmptyObject() {
        val expected = 0
        val actual = appViewModel.calculateLastResult(RecyclerViewItem("", ""))
        assertEquals(expected, actual)

    }

    @Test
    fun testCalculateUndoORDeleteItemResultAddition() {
        appViewModel.setLastResult(5)
        val expected = 2
        val actual = appViewModel.calculateLastResult(RecyclerViewItem("3", "+"))
        assertEquals(expected, actual)

    }

    @Test
    fun testCalculateUndoORDeleteItemResultSubtraction() {
        appViewModel.setLastResult(5)
        val expected = 8
        val actual = appViewModel.calculateLastResult(RecyclerViewItem("3", "-"))
        assertEquals(expected, actual)

    }

    @Test
    fun testCalculateUndoORDeleteItemResultMultiplication() {
        appViewModel.setLastResult(6)
        val expected = 3
        val actual = appViewModel.calculateLastResult(RecyclerViewItem("2", "*"))
        assertEquals(expected, actual)

    }

    @Test
    fun testCalculateUndoORDeleteItemResultDivision() {
        appViewModel.setLastResult(5)
        val expected = 10
        val actual = appViewModel.calculateLastResult(RecyclerViewItem("2", "/"))
        assertEquals(expected, actual)

    }

    @Test
    fun testCalculateRedoResultAddition() {
        appViewModel.setLastResult(5)
        val expected = 8
        val actual = appViewModel.getRedoResult(RecyclerViewItem("3", "+"))
        assertEquals(expected, actual)

    }

    @Test
    fun testCalculateRedoResultSubtraction() {
        appViewModel.setLastResult(5)
        val expected = 2
        val actual = appViewModel.getRedoResult(RecyclerViewItem("3", "-"))
        assertEquals(expected, actual)

    }

    @Test
    fun testCalculateRedoResultMultiplication() {
        appViewModel.setLastResult(6)
        val expected = 12
        val actual = appViewModel.getRedoResult(RecyclerViewItem("2", "*"))
        assertEquals(expected, actual)

    }

    @Test
    fun testCalculateRedoResultDivision() {
        appViewModel.setLastResult(10)
        val expected = 5
        val actual = appViewModel.getRedoResult(RecyclerViewItem("2", "/"))
        assertEquals(expected, actual)

    }

    @Test
    fun testGetLastResultZero() {
        val expected = 0
        val actual = appViewModel.getLastResult()
        assertEquals(expected, actual)
    }

    @Test
    fun testGetLastResult() {
        appViewModel.setLastResult(5)
        val expected = 5
        val actual = appViewModel.getLastResult()
        assertEquals(expected, actual)
    }

    @Test
    fun undo() {
        val expectedUndoStackSize = 2
        appViewModel.equalButton(RecyclerViewItem("", ""))
        appViewModel.equalButton(RecyclerViewItem("", ""))
        appViewModel.equalButton(RecyclerViewItem("", ""))
        appViewModel.undo()
        appViewModel.redo()
        val actual = appViewModel.undo()
        assertEquals(expectedUndoStackSize, actual)

    }

    @Test
    fun redo() {
        val expectedUndoStackSize = 2
        appViewModel.equalButton(RecyclerViewItem("", ""))
        appViewModel.equalButton(RecyclerViewItem("", ""))
        appViewModel.equalButton(RecyclerViewItem("", ""))
        appViewModel.undo()
        appViewModel.undo()
        appViewModel.undo()
        val actual = appViewModel.redo()
        assertEquals(expectedUndoStackSize, actual)

    }

    @Test
    fun testHandleOperationButtonClicked() {
        val expected = OperationsEnum.ADDITION
        val actual = appViewModel.handleButtonClicked(OperationsEnum.ADDITION)
        assertEquals(expected, actual)
    }

    @Test
    fun testSetOperationEnum() {
        val expected = OperationsEnum.ADDITION
        val actual = appViewModel.setOperationType(OperationsEnum.ADDITION)
        assertEquals(expected, actual)
    }

    @Test
    fun testGetOperationEnum() {
        val expected = OperationsEnum.ADDITION
        appViewModel.setOperationType(OperationsEnum.ADDITION)
        val actual = appViewModel.getOperationType()
        assertEquals(expected, actual)
    }

    @Test
    fun testGetType() {
        val expected = OperationsEnum.ADDITION.type
        appViewModel.setType(OperationsEnum.ADDITION.type)
        val actual = appViewModel.getType()
        assertEquals(expected, actual)
    }

    @Test
    fun testSetType() {
        val expected = true
        val actual = appViewModel.setType(OperationsEnum.ADDITION.type)
        assertEquals(expected, actual)
    }

    @Test
    fun testSetRedoStack() {
        val expected = true
        val actual = appViewModel.setRedoStack(Stack())
        assertEquals(expected, actual)
    }

    @Test
    fun testGetRedoStack() {
        val expected: Stack<RecyclerViewItem> = Stack()
        val actual = appViewModel.getRedoStack()
        assertEquals(expected, actual)
    }

    @Test
    fun testSetUndoStack() {
        val expected = true
        val actual = appViewModel.setUndoStack(Stack())
        assertEquals(expected, actual)
    }

    @Test
    fun testGetUndoStack() {
        val expected: Stack<RecyclerViewItem> = Stack()
        val actual = appViewModel.getUndoStack()
        assertEquals(expected, actual)
    }

    @Test
    fun testGetIsOperationButtonSelected() {
        val expected = true
        appViewModel.setOperationButtonSelected(true)
        val actual = appViewModel.getIsOperationButtonSelected()
        assertEquals(expected, actual)
    }
    @Test
    fun testGetIsOperationButtonNotSelected() {
        val expected = false
        appViewModel.setOperationButtonSelected(false)
        val actual = appViewModel.getIsOperationButtonSelected()
        assertEquals(expected, actual)
    }
    @Test
    fun testGetIsEditTextEmpty() {
        val expected=true
        appViewModel.setIsEditTextEmpty(true)
        val actual = appViewModel.getIsEditTextEmpty()
        assertEquals(expected, actual)
    }
    @Test
    fun testGetIsEditTextNotEmpty() {
        val expected=false
        appViewModel.setIsEditTextEmpty(false)
        val actual = appViewModel.getIsEditTextEmpty()
        assertEquals(expected, actual)
    }

    @Test
    fun testSetIsEditTextEmpty() {
        val expected = true
        val actual = appViewModel.setIsEditTextEmpty(true)
        assertEquals(expected, actual)
    }
}