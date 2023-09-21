package com.natighaciyef.blogmultiplatform.models

expect class User {
    val id: String
    val username: String
    val password: String
}

expect class UserWithoutPassword {
    var id: String
    var username: String
}