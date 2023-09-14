package com.natighaciyef.blogmultiplatform.models

import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgb

enum class Theme(
    val hex: String,
    val rgb: CSSColorValue
) {
    Primary("#00A2FF", rgb(r =0, g =162, b =255)),
    LightGray("#FAFAFA", rgb(r =250, g =250, b =250)),
}