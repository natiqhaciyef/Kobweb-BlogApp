package com.natighaciyef.blogmultiplatform.data

import com.natighaciyef.blogmultiplatform.models.User

interface MongoRepository {
    suspend fun checkUserExistence(user: User): User?
}