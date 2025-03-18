package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.educonnect.data.model.chat.Conversation
import com.example.educonnect.data.model.chat.Message

@Dao
interface ConversationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: Conversation)

    @Query("SELECT * FROM conversations WHERE user1_id = :userId OR user2_id = :userId")
    suspend fun getConversationsByUser(userId: String): List<Conversation>

    @Query("SELECT * FROM messages WHERE conversation_id = :conversationId")
    suspend fun getMessagesByConversation(conversationId: String): List<Message>
}