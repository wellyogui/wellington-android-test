package com.wellignton.androidtest.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.wellignton.androidtest.databinding.ViewGenericErrorBinding

/**
 * Created by well_ on 09/04/2023 for Android test.
 */
class GenericErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    var action: (() -> Unit)? = null
) : LinearLayout(context, attrs, defStyleAttr) {

    private var _binding: ViewGenericErrorBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = ViewGenericErrorBinding.inflate(LayoutInflater.from(context), this, true)
        refreshClickButton()
    }

    private fun refreshClickButton() {
        binding.tryAgainView.setOnClickListener { _ ->
            action?.invoke() ?: run {
                throw NotImplementedError("You must set the genericErrorView.action val. Context: ${context.javaClass.simpleName}")
            }
        }
    }
}

interface OnGenericErrorViewListener {
    fun requestError()
}
