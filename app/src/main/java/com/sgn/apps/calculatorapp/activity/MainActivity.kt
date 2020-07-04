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

class MainActivity : AppCompatActivity(),
    ItemClickListener {

    private lateinit var mViewModel: AppViewModel
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var type: String
    private var firstRedo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        initRecyclerView()
        bindEditTextStatus()
        equalButtonClicked()
        handelUndoButtonClicked()
        handelRedoButtonClicked()
    }

    private fun initRecyclerView() {
        operations_history_recycler_view.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        historyAdapter = HistoryAdapter(this)
        operations_history_recycler_view.adapter = historyAdapter

    }

    private fun equalButtonClicked() {
        equal_btn.setOnClickListener {
            if (mViewModel.getOperationType() == OperationsEnum.ADDITION) {
                type = OperationsEnum.ADDITION.type
                mViewModel.setLastResult(
                    mViewModel.getLastResult() +
                            (second_operand_edit_text.text.toString()).toInt()
                )


            } else if (mViewModel.getOperationType() == OperationsEnum.SUBTRACTION) {
                type = OperationsEnum.SUBTRACTION.type
                mViewModel.setLastResult(
                    mViewModel.getLastResult() -
                            (second_operand_edit_text.text.toString()).toInt()
                )
            } else if (mViewModel.getOperationType() == OperationsEnum.MULTIPLICATION) {
                type = OperationsEnum.MULTIPLICATION.type
                mViewModel.setLastResult(
                    mViewModel.getLastResult() *
                            (second_operand_edit_text.text.toString()).toInt()
                )
            } else if (mViewModel.getOperationType() == OperationsEnum.DIVISION) {
                type = OperationsEnum.DIVISION.type
                mViewModel.setLastResult(
                    mViewModel.getLastResult() /
                            (second_operand_edit_text.text.toString()).toInt()
                )
            }

            //  result_value_text_view.setText(mViewModel.getLastResult().toString())
            updateResult(mViewModel.getLastResult())
            val historyItem = RecyclerViewItem(
                second_operand_edit_text.text.toString(),
                type
            )
            clearSelectedBtn()
            historyAdapter.addItem(historyItem)
            historyAdapter.notifyDataSetChanged()
            handelEditText(false)
            second_operand_edit_text.setText("")
            //  mViewModel.getRedoStack().push(historyItem)
            mViewModel.getUndoStack().push(historyItem)
            undo_btn.isEnabled = true


        }

    }

    //ctr+y
    private fun handelRedoButtonClicked() {
        redo_btn.setOnClickListener {
            val item: RecyclerViewItem = mViewModel.getRedoStack().pop()
            getRedoResult(item)
            // historyAdapter.addItem(item)
            mViewModel.getUndoStack().push(item)
            updateResult(mViewModel.getLastResult())
            if (!undo_btn.isEnabled)
                undo_btn.isEnabled = true
            if (mViewModel.getRedoStack().size == 0) {
                redo_btn.isEnabled = false
            }
        }
    }

    //ctr+z
    private fun handelUndoButtonClicked() {
        undo_btn.setOnClickListener {
            redo_btn.isEnabled = true
                val item: RecyclerViewItem = mViewModel.getUndoStack().pop()
                getLastResult(item)
                //   historyAdapter.deleteItem(historyAdapter.getLastIndex())
                updateResult(mViewModel.getLastResult())
                mViewModel.getRedoStack().push(item)

            if (mViewModel.getUndoStack().size == 0) {

                undo_btn.isEnabled = false

            }

        }
    }

    private fun updateResult(lastResult: Int) {

        result_value_text_view.setText(lastResult.toString())
    }

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

    private fun handelEqualButton(isEditTextEmpty: Boolean) {
        equal_btn.isEnabled = !isEditTextEmpty
    }

    private fun handelEditText(isOperationButtonSelected: Boolean) {
        if (isOperationButtonSelected) {
            second_operand_edit_text_layout.hint = getString(R.string.please_enter_second_operand)
        }

        second_operand_edit_text.isEnabled = isOperationButtonSelected
    }

    fun subtractionBtnClicked(view: View) {
        setButtonPressed(subtraction_btn)
        mViewModel.setOperationButtonSelected(true)
        handelEditText(mViewModel.getIsOperationButtonSelected())
        mViewModel.setOperationType(OperationsEnum.SUBTRACTION)
    }

    fun additionBtnClicked(view: View) {
        setButtonPressed(addition_btn)
        mViewModel.setOperationButtonSelected(true)
        handelEditText(mViewModel.getIsOperationButtonSelected())
        mViewModel.setOperationType(OperationsEnum.ADDITION)
    }

    fun multiplicationBtnClicked(view: View) {
        setButtonPressed(multiplication_btn)
        mViewModel.setOperationButtonSelected(true)
        handelEditText(mViewModel.getIsOperationButtonSelected())
        mViewModel.setOperationType(OperationsEnum.MULTIPLICATION)
    }

    fun divisionBtnClicked(view: View) {
        setButtonPressed(division_btn)
        mViewModel.setOperationButtonSelected(true)
        handelEditText(mViewModel.getIsOperationButtonSelected())
        mViewModel.setOperationType(OperationsEnum.DIVISION)
    }

    override fun onItemClick(item: RecyclerViewItem?, position: Int) {
        if (mViewModel.getUndoStack().size < 0) {
            undo_btn.isEnabled = false
        }
        if (mViewModel.getRedoStack().size < 0) {
            redo_btn.isEnabled = false
        }
        historyAdapter.deleteItem(position)
        if (historyAdapter.getListSize() == 0) {
            undo_btn.isEnabled = false
        }
        getLastResult(item)

        updateResult(mViewModel.getLastResult())
    }

    private fun getLastResult(item: RecyclerViewItem?) {
        if (item?.operationType.equals(OperationsEnum.ADDITION.type)) {
            mViewModel.setLastResult(mViewModel.getLastResult() - item?.operationValue!!.toInt())

        }
        if (item?.operationType.equals(OperationsEnum.SUBTRACTION.type)) {
            mViewModel.setLastResult(mViewModel.getLastResult() + item?.operationValue!!.toInt())
        }
        if (item?.operationType.equals(OperationsEnum.MULTIPLICATION.type)) {
            mViewModel.setLastResult(mViewModel.getLastResult() / item?.operationValue!!.toInt())
        }
        if (item?.operationType.equals(OperationsEnum.DIVISION.type)) {
            mViewModel.setLastResult(mViewModel.getLastResult() * item?.operationValue!!.toInt())
        }

    }

    private fun getRedoResult(item: RecyclerViewItem?) {
        if (item?.operationType.equals(OperationsEnum.ADDITION.type)) {
            mViewModel.setLastResult(mViewModel.getLastResult() + item?.operationValue!!.toInt())

        }
        if (item?.operationType.equals(OperationsEnum.SUBTRACTION.type)) {
            mViewModel.setLastResult(mViewModel.getLastResult() - item?.operationValue!!.toInt())
        }
        if (item?.operationType.equals(OperationsEnum.MULTIPLICATION.type)) {
            mViewModel.setLastResult(mViewModel.getLastResult() * item?.operationValue!!.toInt())
        }
        if (item?.operationType.equals(OperationsEnum.DIVISION.type)) {
            mViewModel.setLastResult(mViewModel.getLastResult() / item?.operationValue!!.toInt())
        }

    }

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

    private fun clearSelectedBtn() {
        subtraction_btn.setBackgroundResource(R.drawable.button_color)
        addition_btn.setBackgroundResource(R.drawable.button_color)
        division_btn.setBackgroundResource(R.drawable.button_color)
        multiplication_btn.setBackgroundResource(R.drawable.button_color)
    }


}