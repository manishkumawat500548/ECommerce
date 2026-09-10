package com.angel.e_commersapp.util

import android.annotation.SuppressLint
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeHolder: @Composable (() -> Unit)? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier  = Modifier,
    visualTransformation : VisualTransformation = VisualTransformation.None
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        placeholder = placeHolder,
        modifier = modifier,
        visualTransformation = visualTransformation

    )
}