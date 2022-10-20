package com.calculator.ui.fragment.calculator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.calculator.databinding.FragmentCalculatorBinding
import com.calculator.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Fedotov Yakov
 */
@AndroidEntryPoint
class CalculatorFragment :
    BaseFragment<FragmentCalculatorBinding>(FragmentCalculatorBinding::inflate) {

    private val viewModel: CalculatorViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runBinding {
            keyboard.operationListener = {
                /* no-op */
            }
            keyboard.numberListenerListener = {
                result.text = it.toString()
            }
            keyboard.resultListener = {
                result.text = it.toString()
            }
        }
    }


}