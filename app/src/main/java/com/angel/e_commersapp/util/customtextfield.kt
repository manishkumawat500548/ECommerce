package com.angel.e_commersapp.util

import android.annotation.SuppressLint
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeHolder: @Composable (() -> Unit)? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier  = Modifier,
    visualTransformation : VisualTransformation = VisualTransformation.None,
    textColor: Color = Color.Unspecified,
    placeholderColor: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    cursorColor: Color = Color.Unspecified,
    focusedIndicatorColor : Color = Color.Unspecified,
    unfocusedIndicatorColor: Color = Color.Unspecified ,
    disabledIndicatorColor : Color = Color.Unspecified,
    singleLine: Boolean
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        placeholder = placeHolder,
        modifier = modifier,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,

            focusedPlaceholderColor = placeholderColor,
            unfocusedPlaceholderColor = placeholderColor,

            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,

            focusedIndicatorColor = focusedIndicatorColor,
            unfocusedIndicatorColor = unfocusedIndicatorColor,
            disabledIndicatorColor = disabledIndicatorColor,

            cursorColor = cursorColor,

        ),
        singleLine = singleLine

    )
}