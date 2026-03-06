package com.raihan.anicata.data.source.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.raihan.anicata.data.model.auth.UserData
import kotlinx.coroutines.tasks.await

interface FirebaseService {

    val currentUserId: String?

    suspend fun syncUserToFirestore(userData: UserData): Boolean
    suspend fun saveToLibrary(collectionName: String, data: Any, docId: String): Boolean
    suspend fun removeFromLibrary(collectionName: String, docId: String): Boolean
    suspend fun isItemSaved(collectionName: String, docId: String): Boolean
    suspend fun <T> getUserLibrary(collectionName: String, clazz: Class<T>): List<T>
}

class FirebaseServiceImpl(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseService {


    override val currentUserId: String?
        get() = auth.currentUser?.uid

    override suspend fun syncUserToFirestore(userData: UserData): Boolean {

        val uid = auth.currentUser?.uid ?: return false

        val userMap = hashMapOf(
            "userId" to uid,
            "username" to userData.username,
            "email" to auth.currentUser?.email,
            "profilePictureUrl" to userData.profilePictureUrl,
            "lastLogin" to System.currentTimeMillis()
        )

        firestore.collection("users")
            .document(uid)
            .set(userMap, SetOptions.merge())
            .await()
        return true
    }

    override suspend fun saveToLibrary(collectionName: String, data: Any, docId: String): Boolean {
        val uid = auth.currentUser?.uid ?: throw Exception("User Not Logged In")

        firestore.collection("users")
            .document(uid)
            .collection(collectionName)
            .document(docId)
            .set(data)
            .await()
        return true
    }

    override suspend fun removeFromLibrary(collectionName: String, docId: String): Boolean {
        val uid = auth.currentUser?.uid ?: throw Exception("User Not Logged In")

        firestore.collection("users")
            .document(uid)
            .collection(collectionName)
            .document(docId)
            .delete()
            .await()
        return true
    }

    override suspend fun isItemSaved(collectionName: String, docId: String): Boolean {
        val uid = auth.currentUser?.uid ?: return false

        val doc = firestore.collection("users")
            .document(uid)
            .collection(collectionName)
            .document(docId)
            .get()
            .await()

        return doc.exists()
    }

    override suspend fun <T> getUserLibrary(collectionName: String, clazz: Class<T>): List<T> {
        val uid = auth.currentUser?.uid ?: return emptyList()

        val snapshot = firestore.collection("users")
            .document(uid)
            .collection(collectionName)
            .get()
            .await()

        // Mapping dokumen ke Class<T> (UserSavedAnime atau UserSavedManga)
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(clazz)
        }
    }
}