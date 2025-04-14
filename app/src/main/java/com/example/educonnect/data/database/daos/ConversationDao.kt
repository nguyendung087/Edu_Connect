package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.educonnect.data.model.chat.Conversation
import com.example.educonnect.data.model.chat.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: Conversation)

    @Query("SELECT * FROM conversations WHERE user1_id = :userId OR user2_id = :userId")
    fun getConversationsByUser(userId: String): Flow<List<Conversation>>

    @Query("SELECT * FROM messages WHERE conversation_id = :conversationId")
    fun getMessagesByConversation(conversationId: String): Flow<List<Message>>
}