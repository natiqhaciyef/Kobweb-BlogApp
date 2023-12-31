package com.natighaciyef.blogmultiplatform.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.natighaciyef.blogmultiplatform.models.Theme
import com.natighaciyef.blogmultiplatform.models.User
import com.natighaciyef.blogmultiplatform.models.UserWithoutPassword
import com.natighaciyef.blogmultiplatform.styles.LoginInputStyle
import com.natighaciyef.blogmultiplatform.util.Constants
import com.natighaciyef.blogmultiplatform.util.Id
import com.natighaciyef.blogmultiplatform.util.Res
import com.natighaciyef.blogmultiplatform.util.checkUserExistence
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.components.text.SpanTextStyle
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.WindowLocalStorage
import org.w3c.dom.set

@Page
@Composable
fun LoginScreen() {
    val coroutineScope = rememberCoroutineScope()
    val pageContext = rememberPageContext()
    var errorText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(leftRight = 50.px, top = 80.px, bottom = 24.px)
                .background(color = Theme.LightGray.rgb),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(bottom = 50.px)
                    .width(100.px),
                src = Res.Image.logo,
                desc = "Logo image",
            )

            Input(
                type = InputType.Text,
                attrs = LoginInputStyle.toModifier()
                    .id(Id.usernameInput)
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .fontFamily(Constants.FONT_FAMILY)
                    .background(Colors.White)
                    .fontSize(16.px)
                    .outline(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                    .toAttrs {
                        attr("placeholder", "Username")
                    },
            )

            Input(
                type = InputType.Password,
                attrs = LoginInputStyle.toModifier()
                    .id(Id.passwordInput)
                    .margin(bottom = 20.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .background(Colors.White)
                    .fontFamily(Constants.FONT_FAMILY)
                    .fontSize(16.px)
                    .outline(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                    .toAttrs {
                        attr("placeholder", "Password")
                    },
            )

            Button(
                attrs = Modifier
                    .margin(24.px)
                    .width(350.px)
                    .height(54.px)
                    .background(Theme.Primary.rgb)
                    .color(Colors.White)
                    .border(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                    .outline(width = 0.px, style = LineStyle.None, color = Colors.Transparent)
                    .borderRadius(r = 4.px)
                    .fontWeight(FontWeight.Medium)
                    .fontFamily(Constants.FONT_FAMILY)
                    .fontSize(16.px)
                    .cursor(Cursor.Pointer)
                    .onClick {
                        coroutineScope.launch {
                            val username =
                                (document.getElementById(Id.usernameInput) as HTMLInputElement).value
                            val password =
                                (document.getElementById(Id.passwordInput) as HTMLInputElement).value

                            if (username.isNotEmpty() && password.isNotEmpty() && password.length > 8) {
                                val user = checkUserExistence(
                                    user = User(
                                        username = username,
                                        password = password
                                    )
                                )
                                if (user != null) {
                                    rememberLoggedIn(user = user, remember = true)
                                    pageContext.router.navigateTo("admin/home")
                                } else {
                                    errorText = "The user doesn't exist"
                                    delay(3000)
                                    errorText = " "
                                }
                            } else {
                                errorText = "Input fields are empty"
                                delay(3000)
                                errorText = " "
                            }
                        }
                    }
                    .toAttrs()
            ) {
                SpanText(text = "Sign in")
            }

            SpanText(
                modifier = Modifier
                    .width(350.px)
                    .height(54.px)
                    .color(Colors.Red)
                    .textAlign(TextAlign.Center),
                text = "",
            )
        }
    }
}


private fun rememberLoggedIn(remember: Boolean, user: UserWithoutPassword? = null) {
    localStorage["remember"] = remember.toString()
    if (user != null){
        localStorage["userId"] = user.id
        localStorage["username"] = user.username

    }
}
