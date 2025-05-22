package com.example.educonnect.services.supabase

import android.util.Log
import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import aws.smithy.kotlin.runtime.content.toByteArray
import aws.smithy.kotlin.runtime.net.url.Url
import io.github.jan.supabase.SupabaseClient
import java.io.InputStream

interface FileRepository {
    suspend fun uploadAvatar(inputStream: InputStream, userId: String): String

    suspend fun downloadFile(
        bucketName: String,
        userId: String
    ) : ByteArray

//    fun createS3Client(): S3Client
}

class SupabaseFileRepository(private val supabase : SupabaseClient) : FileRepository {
    private val supabaseStorage = SupabaseModule.provideSupabaseStorage(supabase)

    override suspend fun uploadAvatar(inputStream: InputStream, userId: String): String {
        val bucketName = "userfiles"
        val folderName = "avatars"
        val fileName = "avatar-$userId.jpg"
        val filePath = "$folderName/$fileName"
        val byteArray = inputStream.readBytes()

        val previousAvatar = supabaseStorage[bucketName].list(folderName).find { it.name == fileName }
        try {
            if (previousAvatar != null) {
                supabaseStorage[bucketName].delete("$folderName/${previousAvatar.name}")
            }
            supabaseStorage[bucketName].upload(filePath, byteArray)
            return supabaseStorage[bucketName].publicUrl(filePath)
        } catch (e: Exception) {
            Log.e("Supabase", "Upload thất bại", e)
            throw Exception("Upload thất bại: ${e.message}")
        }
    }

//    override suspend fun downloadFile(
//        bucketName: String,
//        userId: String
//    ): ByteArray {
//        val storage = supabase.storage
//        val bucket = storage[bucketName]
//        val fileKey = "avatars/avatar-$userId.jpg"
//        return try {
//            bucket.downloadPublic(fileKey)
//        } catch (e: Exception) {
//            throw Exception("Lỗi khi tải tệp: ${e.message}")
//        }
//    }

    fun createS3Client(): S3Client {
        val credentials = Credentials(
            accessKeyId = "acaf4f4ed43f324e726b45d3ef72457a",
            secretAccessKey = "31b8aa37a37d31273336fcf684911e18136f1dd1273698da9d7991836bb380cf"
        )

        return S3Client {
            region = "ca-central-1"
            endpointUrl = Url.parse("https://zpdurgxwndlkjcvagvqu.supabase.co/storage/v1/s3")
            forcePathStyle = true
            credentialsProvider = StaticCredentialsProvider(credentials)
        }
    }

    override suspend fun downloadFile(
        bucketName: String,
        userId: String,
    ) : ByteArray {
        val fileKeys = "avatars/avatar-$userId.jpg"
        val s3Client = createS3Client()
        val request = GetObjectRequest {
            bucket = bucketName
            this.key = fileKeys
        }

        return try {
            val response = s3Client.getObject(request) { resp ->
                resp.body?.toByteArray() ?: throw Exception("Tệp không tồn tại")
            }
            response
        } catch (e: Exception) {
            throw Exception("Lỗi khi tải tệp: ${e.message}")
        }
    }
}