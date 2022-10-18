package com.calculator.ui.fragment.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @author Fedotov Yakov
 */
abstract class BaseViewModel : ViewModel() {

    private val _navigation = MutableSharedFlow<NavDirections>()
    val navigation = _navigation.asSharedFlow()

    private var isStarted: Boolean = false

    lateinit var navController: NavController

    protected abstract fun onStart()

    fun start() {
        if (!isStarted) {
            isStarted = true
            onStart()
        }
    }

    fun start(navController: NavController) {
        this.navController = navController
        if (!isStarted) {
            isStarted = true
            onStart()
        }
    }

    protected fun <T> Flow<T>.handleResult(
        onLoading: ((Boolean) -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        onStart { onLoading?.invoke(true) }
            .onEach {
                onSuccess?.invoke(it)
            }
            .catch {
                onError?.invoke(it)
            }
            .onCompletion { onLoading?.invoke(false) }
            .launchIn(viewModelScope)
    }



    protected fun <T> emit(flow: MutableSharedFlow<T>, value: T) {
        viewModelScope.launch {
            flow.emit(value)
        }
    }

    protected fun navigationTo(directions: NavDirections) {
        emit(_navigation, directions)
    }
}