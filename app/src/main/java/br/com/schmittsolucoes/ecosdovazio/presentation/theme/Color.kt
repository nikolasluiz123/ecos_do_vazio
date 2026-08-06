package br.com.schmittsolucoes.ecosdovazio.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val backgroundDark = Color(0xFF121416)

val strokeColorDark = Color(0xFF564334)
val orangeForDetailsDark = Color(0xFFFF8C00)
val highlightDark = Color(0xFFFFB77D)
val highlightOutlineDark = Color(0xFFA48C7A)
val highlightedTextFieldBackgroundDark = Color(0xFF0C0E10)
val highlightedTextFieldTextDark = Color(0xFFDDC1AE)
val buttonContainerDark = Color(0xFF282A2C)
val newCharacterButtonBackgroundDark = Color(0xB30C0E10)
val heroSlotBackgroundTopDark = Color(0xFF1E2022)
val heroSlotBackgroundBottomDark = Color(0xFF0C0E10)

val backgroundLight = Color(0xFFECECE6)
val strokeColorLight = Color(0xFFA48C7A)
val orangeForDetailsLight = Color(0xFFE08216)
val highlightLight = Color(0xFF904D00)
val highlightOutlineLight = Color(0xFFA48C7A)
val highlightedTextFieldBackgroundLight = Color(0xFFF8F9FA)
val highlightedTextFieldTextLight = Color(0xFF3D1B00)
val buttonContainerLight = Color(0xFFB34E00)
val newCharacterButtonBackgroundLight = Color(0xB3E8E8E8)
val heroSlotBackgroundTopLight = Color(0xFFFFFFFF)
val heroSlotBackgroundBottomLight = Color(0xFFD8D8D0)

val pictureTextHighlightBackground = Color(0x99000000)

@get:Composable
val OrangeForDetails: Color
    get() = if (isSystemInDarkTheme()) orangeForDetailsDark else orangeForDetailsLight

@get:Composable
val Highlight: Color
    get() = if (isSystemInDarkTheme()) highlightDark else highlightLight

@get:Composable
val HighlightOutline: Color
    get() = if (isSystemInDarkTheme()) highlightOutlineDark else highlightOutlineLight

@get:Composable
val ButtonContainer: Color
    get() = if (isSystemInDarkTheme()) buttonContainerDark else buttonContainerLight

@get:Composable
val DividerColor: Color
    get() = if (isSystemInDarkTheme()) strokeColorDark else strokeColorLight

@get:Composable
val HeroButtonStrokeColor: Color
    get() = if (isSystemInDarkTheme()) strokeColorDark else strokeColorLight

@get:Composable
val NewCharacterButtonBackground: Color
    get() = if (isSystemInDarkTheme()) newCharacterButtonBackgroundDark else newCharacterButtonBackgroundLight

@get:Composable
val HeroSlotBackgroundTop: Color
    get() = if (isSystemInDarkTheme()) heroSlotBackgroundTopDark else heroSlotBackgroundTopLight

@get:Composable
val HeroSlotBackgroundBottom: Color
    get() = if (isSystemInDarkTheme()) heroSlotBackgroundBottomDark else heroSlotBackgroundBottomLight

@get:Composable
val HighlightedButtonContent: Color
    get() = if (isSystemInDarkTheme()) highlightDark else Color.White

@get:Composable
val HighlightedTextFieldBackground: Color
    get() = if (isSystemInDarkTheme()) highlightedTextFieldBackgroundDark else highlightedTextFieldBackgroundLight

@get:Composable
val HighlightedTextFieldText: Color
    get() = if (isSystemInDarkTheme()) highlightedTextFieldTextDark else highlightedTextFieldTextLight

@get:Composable
val HighlightedTextFieldBorder: Color
    get() = if (isSystemInDarkTheme()) highlightDark else highlightLight

@get:Composable
val PrimaryTextColor: Color
    get() = MaterialTheme.colorScheme.onBackground

@get:Composable
val SecondaryTextColor: Color
    get() = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
