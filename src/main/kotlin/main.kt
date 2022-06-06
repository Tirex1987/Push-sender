package ru.netology

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import java.io.FileInputStream
import java.lang.Thread.sleep

fun main() {

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(FileInputStream("fcm.json")))
        .build()

    FirebaseApp.initializeApp(options)

    sendMessage("LIKE", """{
          "userId": 1,
          "userName": "Vasiliy",
          "postId": 2,
          "postAuthor": "Netology"
        }""".trimIndent())

    sleep(1000)

    sendMessage("UNKNOWN", "")

    sleep(1000)

    sendMessage("NEW_POST", """{
          "userId": 1,
          "userName": "Alexey",
          "postId": 2,
          "title": "Pull requests",
          "content": "When merging pull requests, you can allow any combination of merge commits, squashing, or rebasing. At least one option must be enabled. If you have linear history requirement enabled on any protected branch, you must enable squashing or rebasing."
        }""".trimIndent())
}

private fun sendMessage(actionType: String, actionContent: String) {
    val messageNewPost = Message.builder()
        .putData("action", actionType)
        .putData("content", actionContent)
        .setToken("fJJWoWeQQc2IBhibichTGx:APA91bF4nNmdJIFmxc7Y4nL2a-7fLxSbTicPa7KV38AaMxlGHKi5a01ncq9bsgV-5cNPs17v0XK_oVeLCGny8rrkYdm3AFxB0k6z-hg5pqUNETArRYTOT9ysbzzPboV7IxZ2lWdH-J_H")
        .build()

    FirebaseMessaging.getInstance().send(messageNewPost)
}