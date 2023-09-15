package com.natighaciyef.blogmultiplatform.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.id.ObjectIdGenerator


@Serializable
data class User(
    @SerialName("_id")
    var id: String = ObjectIdGenerator.newObjectId<String>().id.toHexString(),
    var username: String = "",
    var password: String = ""
)
