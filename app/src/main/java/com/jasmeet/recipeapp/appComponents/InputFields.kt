package com.jasmeet.recipeapp.appComponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.recipeapp.ui.theme.poppins

@Composable
fun InputField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint : String,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Email,
) {
    OutlinedTextField(
        value = value ,
        onValueChange = {
            onValueChange.invoke(it)
        },
        textStyle = TextStyle(
            fontFamily = poppins,
            fontSize = 15.sp,
            fontWeight = FontWeight(600),
            color = Color.Black.copy(alpha = 0.5f),
        ),
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor =  Color(0xff999999),
            unfocusedIndicatorColor = Color(0xffd9d9d9),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor =  Color(0xff999999),
            selectionColors = TextSelectionColors(
                handleColor = Color(0xff999999),
                backgroundColor = Color(0xffd9d9d9),
            )
        ),
        placeholder = {
            Text(
                text = hint,
                style = TextStyle(
                    fontFamily = poppins,
                    fontSize = 15.sp,
                    fontWeight = FontWeight(300),
                    color = Color(0xff666666),
                )
            )
        },
        maxLines = 1,
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
            autoCorrect = false,
            capitalization = KeyboardCapitalization.None
        ),
    )

}

@Composable
fun PasswordFieldComponent(
    modifier: Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Password,

    ) {


    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange.invoke(it)
        },
        modifier = modifier,
        colors =  TextFieldDefaults.colors(
            focusedIndicatorColor =  Color(0xff999999),
            unfocusedIndicatorColor = Color(0xffd9d9d9),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor =  Color(0xff999999),
            selectionColors = TextSelectionColors(
                handleColor = Color(0xff999999),
                backgroundColor = Color(0xffd9d9d9),
            )

        ),
        placeholder = {
            Text(
                text = hint,
                style = TextStyle(
                    fontFamily = poppins,
                    fontSize = 15.sp,
                    fontWeight = FontWeight(300),
                    color = Color(0xff666666),
                )
            )
        },

        textStyle = TextStyle(
            fontFamily = poppins,
            fontSize = 15.sp,
            fontWeight = FontWeight(600),
            color = Color.Black.copy(alpha = 0.5f),
        ),
        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
            autoCorrect = false,
            capitalization = KeyboardCapitalization.None
        ),
        maxLines = 1,
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {

            val iconImage =
                if (passwordVisible.value)
                    Icons.Default.Visibility
                else
                    Icons.Default.VisibilityOff

            val description = if (passwordVisible.value)
                "Hide Password"
            else
                "Show Password"

            IconButton(
                onClick ={
                    passwordVisible.value = !passwordVisible.value}
            ) {
                Icon(
                    imageVector = iconImage,
                    contentDescription = description,
                    tint = Color(0xff999999),
                )
            }
        },
        visualTransformation =
        if (passwordVisible.value)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),


        )

}