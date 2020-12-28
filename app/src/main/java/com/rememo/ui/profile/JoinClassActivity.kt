package com.rememo.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.rememo.MainActivity
import com.rememo.R
import com.rememo.services.api.ClassServices

class JoinClassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_class)

        val data: Uri? = intent?.data
        val classId = data?.toString()?.split("/")?.last()

        val txtClassName = findViewById<TextView>(R.id.txt_class_name)
        val txtJoinClassActionDescription = findViewById<TextView>(R.id.txt_join_class_action_description)
        val btnJoinClass = findViewById<Button>(R.id.btn_join_class)
        val spinnerLoadingClass = findViewById<ProgressBar>(R.id.loading_class)

        if (classId != null) {

            ClassServices.getClassId(classId!!, onResult = { studyClass ->
                txtClassName.text = studyClass.name
                spinnerLoadingClass.isVisible = false
                btnJoinClass.isEnabled = true

                btnJoinClass.setOnClickListener {

                    spinnerLoadingClass.isVisible = true
                    btnJoinClass.isEnabled = false

                    ClassServices.joinClass(classId, onResult = {

                        Toast.makeText(this, "Class joined!", Toast.LENGTH_LONG).show()

                        startActivity(Intent(this, MainActivity::class.java).apply {
                            putExtra("Selected page", R.id.navigation_profile)
                        })
                        finish()

                    }, onError = {
                        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                        spinnerLoadingClass.isVisible = false
                        btnJoinClass.isEnabled = true
                    })
                }

            }, onError = {
                txtJoinClassActionDescription.text = "No class to join found"
                spinnerLoadingClass.isVisible = false
            })

        }

    }
}