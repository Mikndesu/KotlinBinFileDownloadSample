package com.mikn.downloadfilesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        get()
    }

    fun get() {
        // Tesseseract Trained Data
        val target_url = "https://raw.githubusercontent.com/tesseract-ocr/tessdata_best/master/eng.traineddata"
        val httpasync = target_url.httpGet().response {
                request, response, result ->
            when(result) {
                is Result.Success -> {
                    val data = result.get()
                    if(!File(applicationContext.filesDir.toString() + "/tessdata/").mkdir()) {
                        Log.d("Don't Exist", "files/tessdata")
                    }
                    if(!File(applicationContext.filesDir.toString() + "/tessdata/eng.traineddata").createNewFile()) {
                        Log.d("Don't exist", "tessdata/eng.trainddata")
                        File(applicationContext.filesDir.toString() + "/tessdata/eng.traineddata").writeBytes(data)
                    }
                }
                is Result.Failure -> {
                    Log.e("Error:", "Unable Start to Download File")
                }
            }
        }

        httpasync.join()
    }

}