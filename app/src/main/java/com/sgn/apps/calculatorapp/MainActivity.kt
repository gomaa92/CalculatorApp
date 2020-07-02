package com.sgn.apps.calculatorapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    /*    private var isOperationButtonSelected: Boolean = false
        private var isEditTextEmpty: Boolean = true
        private lateinit var operationType: Operations
        private val lastResult: Int = 0*/
    private lateinit var mViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        bindEditTextStatus()

        equalButtonClicked()

    }

    private fun equalButtonClicked() {
        equal_btn.setOnClickListener {
            if (mViewModel.getOperationType() == Operations.ADDITION) {
                mViewModel.setLastResult(
                    mViewModel.getLastResult() +
                            (second_operand_edit_text.text.toString()).toInt()
                )


            } else if (mViewModel.getOperationType() == Operations.SUBTRACTION) {
                mViewModel.setLastResult(
                    mViewModel.getLastResult() -
                            (second_operand_edit_text.text.toString()).toInt()
                )
            } else if (mViewModel.getOperationType() == Operations.MULTIPLICATION) {
                mViewModel.setLastResult(
                    mViewModel.getLastResult() *
                            (second_operand_edit_text.text.toString()).toInt()
                )
            } else if (mViewModel.getOperationType() == Operations.DIVISION) {
                mViewModel.setLastResult(
                    mViewModel.getLastResult() /
                            (second_operand_edit_text.text.toString()).toInt()
                )
            }
            result_value_text_view.setText(mViewModel.getLastResult().toString())
            second_operand_edit_text.setText("")
            handelEditText(false)
        }

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
                mViewModel.setIsEditTextEmpty(second_operand_edit_text.text.toString().isEmpty())
                handelEqualButton(mViewModel.getIsEditTextEmpty())
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
            second_operand_edit_text_layout.setHint(getString(R.string.please_enter_second_operand))
        }

        second_operand_edit_text.isEnabled = isOperationButtonSelected
    }

    fun subtractionBtnClicked(view: View) {
        mViewModel.setOperationButtonSelected(true)
        handelEditText(mViewModel.getIsOperationButtonSelected())
        mViewModel.setOperationType(Operations.SUBTRACTION)
    }

    fun additionBtnClicked(view: View) {
        mViewModel.setOperationButtonSelected(true)
        handelEditText(mViewModel.getIsOperationButtonSelected())
        mViewModel.setOperationType(Operations.ADDITION)
    }

    fun multiplicationBtnClicked(view: View) {
        mViewModel.setOperationButtonSelected(true)
        handelEditText(mViewModel.getIsOperationButtonSelected())
        mViewModel.setOperationType(Operations.MULTIPLICATION)
    }

    fun divisionBtnClicked(view: View) {
        mViewModel.setOperationButtonSelected(true)
        handelEditText(mViewModel.getIsOperationButtonSelected())
        mViewModel.setOperationType(Operations.DIVISION)
    }


}