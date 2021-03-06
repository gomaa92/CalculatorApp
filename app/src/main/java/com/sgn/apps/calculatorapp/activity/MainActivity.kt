package com.sgn.apps.calculatorapp.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sgn.apps.calculatorapp.*
import com.sgn.apps.calculatorapp.adapter.HistoryAdapter
import com.sgn.apps.calculatorapp.listener.ItemClickListener
import com.sgn.apps.calculatorapp.model.RecyclerViewItem
import com.sgn.apps.calculatorapp.utils.OperationsEnum
import com.sgn.apps.calculatorapp.viewmodel.AppViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),
    ItemClickListener {

    private lateinit var mViewModel: AppViewModel
    private lateinit var historyAdapter: HistoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        initRecyclerView()
        bindEditTextStatus()
        equalButtonClicked()
        handelUndoButtonClicked()
        handelRedoButtonClicked()
        //buttonsStatus()
    }

    private fun initRecyclerView() {
        operations_history_recycler_view.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        historyAdapter = HistoryAdapter(this)
        operations_history_recycler_view.adapter = historyAdapter

    }

    // update last result after equal button pressed, clear edit text, clear selected button, add to history
    private fun equalButtonClicked() {
        equal_btn.setOnClickListener {
            when {
                mViewModel.getOperationType() == OperationsEnum.ADDITION -> {

                    mViewModel.setType(OperationsEnum.ADDITION.type)
                    mViewModel.addition(second_operand_edit_text.text.toString().toInt())


                }
                mViewModel.getOperationType() == OperationsEnum.SUBTRACTION -> {
                    mViewModel.setType(OperationsEnum.SUBTRACTION.type)
                    mViewModel.subtraction(second_operand_edit_text.text.toString().toInt())
                }
                mViewModel.getOperationType() == OperationsEnum.MULTIPLICATION -> {
                    mViewModel.setType(OperationsEnum.MULTIPLICATION.type)
                    mViewModel.multiplication(second_operand_edit_text.text.toString().toInt())
                }
                mViewModel.getOperationType() == OperationsEnum.DIVISION -> {
                    mViewModel.setType(OperationsEnum.DIVISION.type)
                    mViewModel.division(second_operand_edit_text.text.toString().toInt())

                }
            }

            updateResult(mViewModel.getLastResult())

            val historyItem = RecyclerViewItem(
                second_operand_edit_text.text.toString(),
                mViewModel.getType()
            )
            clearSelectedBtn()

            historyAdapter.addItem(historyItem)

            historyAdapter.notifyDataSetChanged()

            handelEditText(false)
            second_operand_edit_text.setText("")

            mViewModel.equalButton(historyItem)
            undo_btn.isEnabled = true


        }

    }

    //redo
    private fun handelRedoButtonClicked() {
        redo_btn.setOnClickListener {
            // historyAdapter.addItem(item)
            mViewModel.redo()
            updateResult(mViewModel.getLastResult())
            if (!undo_btn.isEnabled)
                undo_btn.isEnabled = true
            if (mViewModel.getRedoStack().size == 0) {
                redo_btn.isEnabled = false
            }
        }
    }

    //undo
    private fun handelUndoButtonClicked() {
        undo_btn.setOnClickListener {
            redo_btn.isEnabled = true
            mViewModel.undo()
            // historyAdapter.deleteItem(historyAdapter.getLastIndex())
            updateResult(mViewModel.getLastResult())
            if (mViewModel.getUndoStack().size == 0) {
                undo_btn.isEnabled = false
            }

        }
    }

    // show last result
    private fun updateResult(lastResult: Int) {

        result_value_text_view.text = lastResult.toString()
    }

    // change edit text status according to user enter second operand or not
    private fun bindEditTextStatus() {
        second_operand_edit_text.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Fires right as the text is being changed (even supplies the range of text)
                mViewModel.setIsEditTextEmpty(second_operand_edit_text.text.toString().isEmpty())
                handelEqualButton(mViewModel.getIsEditTextEmpty())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Fires right before text is changing

            }

            override fun afterTextChanged(s: Editable) {
                // Fires right after the text has changed
                mViewModel.setIsEditTextEmpty(second_operand_edit_text.text.toString().isEmpty())
                handelEqualButton(mViewModel.getIsEditTextEmpty())

            }
        })
    }

    // make equal button disable until user enter second operand
    private fun handelEqualButton(isEditTextEmpty: Boolean) {
        equal_btn.isEnabled = !isEditTextEmpty
    }

    //handel edit text until user select operation
    private fun handelEditText(isOperationButtonSelected: Boolean) {
        if (isOperationButtonSelected) {
            second_operand_edit_text_layout.hint = getString(R.string.please_enter_second_operand)
        }

        second_operand_edit_text.isEnabled = isOperationButtonSelected
    }

    // handel button selected
    fun operationButtonSelected(view: View) {

        when (view.id) {
            R.id.subtraction_btn -> {
                setButtonPressed(subtraction_btn)
                mViewModel.handleButtonClicked(OperationsEnum.SUBTRACTION)
                handelEditText(mViewModel.getIsOperationButtonSelected())

            }
            R.id.addition_btn -> {
                setButtonPressed(addition_btn)
                mViewModel.handleButtonClicked(OperationsEnum.ADDITION)
                handelEditText(mViewModel.getIsOperationButtonSelected())

            }
            R.id.multiplication_btn -> {
                setButtonPressed(multiplication_btn)
                mViewModel.handleButtonClicked(OperationsEnum.MULTIPLICATION)
                handelEditText(mViewModel.getIsOperationButtonSelected())

            }
            R.id.division_btn -> {
                setButtonPressed(division_btn)
                mViewModel.handleButtonClicked(OperationsEnum.DIVISION)
                handelEditText(mViewModel.getIsOperationButtonSelected())

            }
        }

    }


    // delete item form recycler view after clicked
    override fun onItemClick(item: RecyclerViewItem?, position: Int) {

        if (mViewModel.getUndoStack().size < 0) {
            undo_btn.isEnabled = false
        }
        if (mViewModel.getRedoStack().size < 0) {
            redo_btn.isEnabled = false
        }
        historyAdapter.deleteItem(position)
        mViewModel.calculateLastResult(item)

        updateResult(mViewModel.getLastResult())
        buttonsStatus()

    }


    //set border to selected button
    private fun setButtonPressed(button: Button) {

        when (button) {
            subtraction_btn -> {
                subtraction_btn.setBackgroundResource(R.drawable.selected_btn)
                addition_btn.setBackgroundResource(R.drawable.button_color)
                division_btn.setBackgroundResource(R.drawable.button_color)
                multiplication_btn.setBackgroundResource(R.drawable.button_color)

            }
            addition_btn -> {
                subtraction_btn.setBackgroundResource(R.drawable.button_color)
                addition_btn.setBackgroundResource(R.drawable.selected_btn)
                division_btn.setBackgroundResource(R.drawable.button_color)
                multiplication_btn.setBackgroundResource(R.drawable.button_color)

            }
            division_btn -> {
                subtraction_btn.setBackgroundResource(R.drawable.button_color)
                addition_btn.setBackgroundResource(R.drawable.button_color)
                division_btn.setBackgroundResource(R.drawable.selected_btn)
                multiplication_btn.setBackgroundResource(R.drawable.button_color)

            }
            multiplication_btn -> {
                subtraction_btn.setBackgroundResource(R.drawable.button_color)
                addition_btn.setBackgroundResource(R.drawable.button_color)
                division_btn.setBackgroundResource(R.drawable.button_color)
                multiplication_btn.setBackgroundResource(R.drawable.selected_btn)

            }
        }


    }


    // set default bg to button after equal button clicked
    private fun clearSelectedBtn() {
        subtraction_btn.setBackgroundResource(R.drawable.button_color)
        addition_btn.setBackgroundResource(R.drawable.button_color)
        division_btn.setBackgroundResource(R.drawable.button_color)
        multiplication_btn.setBackgroundResource(R.drawable.button_color)
    }

    private fun buttonsStatus() {
        if (historyAdapter.getListSize() <= 0) {
            redo_btn.isEnabled = false
            undo_btn.isEnabled = false
            result_value_text_view.text = "0"
            mViewModel.setRedoStack(Stack())
            mViewModel.setUndoStack(Stack())
            mViewModel.setLastResult(0)
        }
    }

}