package com.natighaciyef.blogmultiplatform.data

import com.natighaciyef.blogmultiplatform.models.User
import com.natighaciyef.blogmultiplatform.util.Constants.DATABASE_NAME
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.coroutines.reactive.awaitFirst
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.reactivestreams.getCollection
import javax.naming.Context

@InitApi
fun initMongoDB(context: InitApiContext) {
    System.setProperty(
        "org.litote.mongo.test.mapping.service",
        "org.lotite.kmongo.serialization.SerializationClassMappingTypeService"
    )
    context.data.add(MongoDB(context))
}

class MongoDB(
    private val context: InitApiContext
) : MongoRepository {
    private val client = KMongo.createClient()
    private val db = client.getDatabase(DATABASE_NAME)
    private val userCollection = db.getCollection<User>()

    override suspend fun checkUserExistence(user: User): User? {
        return try {
            userCollection.find(
                and(
                    User::username eq user.username,
                    User::password eq user.password,
                )
            ).awaitFirst()
        } catch (e: Exception) {
            context.logger.error(e.message.toString())
            null
        }
    }
}