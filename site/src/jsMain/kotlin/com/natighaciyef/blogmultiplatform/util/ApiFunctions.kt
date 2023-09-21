package com.natighaciyef.blogmultiplatform.util

import com.natighaciyef.blogmultiplatform.models.User
import com.natighaciyef.blogmultiplatform.models.UserWithoutPassword
import com.varabyte.kobweb.browser.api
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend fun checkUserExistence(user: User): UserWithoutPassword?{
    return try {
        val result = window.api.tryPost(
            apiPath = "usercheck",
            body = Json.encodeToString(user).encodeToByteArray()
        )

        Json.decodeFromString(result.toString())

    }catch (e: Exception){
        println(e.message)
        null
    }
}