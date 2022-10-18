package com.calculator.ui.fragment.calculator

import com.calculator.ui.fragment.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Fedotov Yakov
 */
@HiltViewModel
class CalculatorViewModel @Inject constructor(): BaseViewModel() {
    override fun onStart() {
        /* no-op */
    }
}