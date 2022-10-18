package com.calculator.ui.fragment.calculator

import androidx.fragment.app.viewModels
import com.calculator.databinding.FragmentCalculatorBinding
import com.calculator.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Fedotov Yakov
 */
@AndroidEntryPoint
class CalculatorFragment: BaseFragment<FragmentCalculatorBinding>(FragmentCalculatorBinding::inflate) {

    private val viewModel: CalculatorViewModel by viewModels()


}