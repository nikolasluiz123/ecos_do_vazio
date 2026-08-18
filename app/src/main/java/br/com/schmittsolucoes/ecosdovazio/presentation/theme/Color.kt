package br.com.schmittsolucoes.ecosdovazio.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

val backgroundDark = Color(0xFF121416)
val surfaceDark = Color(0xFF0C0E10)
val surfaceVariantDark = Color(0xFF1A1C1E)
val surfaceContainerHighestDark = Color(0xFF1E2022)
val surfaceContainerDark = Color(0xFF1E2022)
val primaryDark = Color(0xFFFFB77D)
val primaryContainerDark = Color(0x33FF8C00)
val onPrimaryContainerDark = Color(0xFFFFB77D)
val onSurfaceVariantDark = Color(0xFFDDC1AE)
val secondaryDark = Color(0xFFFFB77D)
val secondaryContainerDark = Color(0x33FF8C00)
val onSecondaryContainerDark = Color(0xFFFFB77D)

val strokeColorDark = Color(0xFF564334)
val orangeForDetailsDark = Color(0xFFFF8C00)
val highlightDark = Color(0xFFFFB77D)
val highlightOutlineDark = Color(0xFFA48C7A)
val highlightedTextFieldBackgroundDark = Color(0xFF0C0E10)
val highlightedTextFieldTextDark = Color(0xFFDDC1AE)
val buttonContainerDark = Color(0xFF282A2C)
val topBarTitleDark = Color(0xFFFFB77D)
val topBarSubtitleDark = Color(0xFFDDC1AE)
val topBarIconsDark = Color(0xFFDDC1AE)
val newCharacterButtonBackgroundDark = Color(0xB30C0E10)
val heroSlotBackgroundTopDark = Color(0xFF1E2022)
val heroSlotBackgroundBottomDark = Color(0xFF0C0E10)

val backgroundLight = Color(0xFFECECE6)
val surfaceLight = Color(0xFFF5F5F5)
val surfaceVariantLight = Color(0xFFECECE6)
val surfaceContainerHighestLight = Color(0xFFFFFFFF)
val surfaceContainerLight = Color(0xD9FFFFFF)
val onSurfaceVariantLight = Color(0xFF49454F)
val primaryLight = Color(0xFFB34E00)
val primaryContainerLight = Color(0xFFFFDCBE)
val onPrimaryContainerLight = Color(0xFFB34E00)
val secondaryLight = Color(0xFFB34E00)
val secondaryContainerLight = Color(0xFFFFDCBE)
val onSecondaryContainerLight = Color(0xFFB34E00)

val strokeColorLight = Color(0xFFA48C7A)
val orangeForDetailsLight = Color(0xFFE08216)
val highlightLight = Color(0xFF904D00)
val highlightOutlineLight = Color(0xFFA48C7A)
val highlightedTextFieldBackgroundLight = Color(0xFFF8F9FA)
val highlightedTextFieldTextLight = Color(0xFF3D1B00)
val buttonContainerLight = Color(0xFFB34E00)
val topBarTitleLight = Color(0xFFB34E00)
val topBarSubtitleLight = Color(0xFF49454F)
val topBarIconsLight = Color(0xFF49454F)
val newCharacterButtonBackgroundLight = Color(0xB3E8E8E8)
val heroSlotBackgroundTopLight = Color(0xFFFFFFFF)
val heroSlotBackgroundBottomLight = Color(0xFFD8D8D0)

val pictureTextHighlightBackground = Color(0x99000000)
val HealthBarRedStart = Color(0xFFE53935)
val HealthBarRedEnd = Color(0xFF7F0000)
val HealthBarTrack = Color(0xFF121416)
val HighlightOnImage = Color(0xFFFFB77D)
val OnSurfaceVariantOnImage = Color(0xFFDDC1AE)

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
val BackgroundGradient: Brush
    get() = if (isSystemInDarkTheme()) {
        Brush.radialGradient(
            colors = listOf(
                Color(0xCC1E2022),
                Color(0xFF121416)
            ),
            tileMode = TileMode.Mirror
        )
    } else {
        Brush.radialGradient(
            colors = listOf(
                Color(0xCCFFFFFF),
                Color(0xFFF5F5F5)
            ),
            tileMode = TileMode.Mirror
        )
    }

@get:Composable
val SurfaceVariantGradient: Brush
    get() = if (isSystemInDarkTheme()) {
        Brush.radialGradient(
            colors = listOf(
                surfaceVariantDark.copy(alpha = 0.8f),
                surfaceVariantDark
            ),
            tileMode = TileMode.Mirror
        )
    } else {
        Brush.radialGradient(
            colors = listOf(
                surfaceVariantLight.copy(alpha = 0.8f),
                surfaceVariantLight
            ),
            tileMode = TileMode.Mirror
        )
    }

@get:Composable
val PrimaryTextColor: Color
    get() = MaterialTheme.colorScheme.onBackground

@get:Composable
val SecondaryTextColor: Color
    get() = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)

@get:Composable
val TopBarTitle: Color
    get() = if (isSystemInDarkTheme()) topBarTitleDark else topBarTitleLight

@get:Composable
val TopBarSubtitle: Color
    get() = if (isSystemInDarkTheme()) topBarSubtitleDark else topBarSubtitleLight

@get:Composable
val TopBarIcons: Color
    get() = if (isSystemInDarkTheme()) topBarIconsDark else topBarIconsLight

@get:Composable
val PhaseCardBorderColor: Color
    get() = if (isSystemInDarkTheme()) strokeColorDark else strokeColorLight

@get:Composable
val CharacterBattleStrokeColor: Color
    get() = if (isSystemInDarkTheme()) strokeColorDark else strokeColorLight

@get:Composable
val SkillBattleStrokeColor: Color
    get() = if (isSystemInDarkTheme()) strokeColorDark else strokeColorLight

@get:Composable
val RoundStrokeColor: Color
    get() = if (isSystemInDarkTheme()) strokeColorDark else strokeColorLight