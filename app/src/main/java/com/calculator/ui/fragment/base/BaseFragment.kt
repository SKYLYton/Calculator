package com.calculator.ui.fragment.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * @author Fedotov Yakov
 */
typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<Binding : ViewBinding>(private val inflate: Inflate<Binding>) :
    Fragment() {
    private var binding: Binding? = null

    /**
     * Поле, хранящее значение первый раз ли вызывается onResume
     */
    private var isFirstResume: Boolean = true

    /**
     * Главный контейнер, к элементам которого будет применяться анимация
     */
    protected var container: ViewGroup? = null

    /**
     * Слушатель, который вызывают при нажатии кнопки "назад"
     */
    val onBackNavigationListener: ((View) -> Unit) = {
        requireActivity().onBackPressed()
    }

    private val transition = AutoTransition().apply {
        duration = 200
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflate.invoke(inflater, container, false)
        return binding!!.root
    }

    protected fun navigateSafety(destination: NavDirections) =
        findNavController().currentDestination?.getAction(destination.actionId)?.let {
            findNavController().navigate(destination)
        }

    protected fun navigateToCall(number: String) {
        startActivity(Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$number")
        })
    }

    protected fun <T> subscribe(flow: Flow<T>, action: suspend (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    flow.collect { action.invoke(it) }
                }
            }
        }
    }

    protected fun runBinding(block: Binding.() -> Unit) {
        binding?.let(block)
    }

    protected fun runBindingWithAnim(block: Binding.() -> Unit) {
        container?.let {
            //TransitionManager.endTransitions(it)
            TransitionManager.beginDelayedTransition(it, transition)
        }
        binding?.let(block)
    }

    override fun onResume() {
        super.onResume()
        if (!isFirstResume) {
            onRestart()
        }
        isFirstResume = false
    }

    /**
     * Метод вызывается при перезапуске фрагмента
     */
    open fun onRestart() {
        /* no-op */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}