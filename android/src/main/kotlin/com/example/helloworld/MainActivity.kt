package com.example.helloworld

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val responseText = findViewById<TextView>(R.id.response)

        val url = resources.getString(R.string.server_url)

        runBlocking {
            withContext(Dispatchers.IO) {
                try {
                    val release = client.get<Release>(url)
                    responseText.text = "Latest Kotlin: " + release.name
                }
                catch (e: Exception) {
                    responseText.text = "Could not get latest release"
                }
            }
        }
    }
}
