package com.rememo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rememo.R
import com.rememo.services.api.UsersServices

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val loadingSpinner = root.findViewById<ProgressBar>(R.id.loading_profile)
        loadingSpinner.isVisible = true

        val txtDisplayName: TextView = root.findViewById(R.id.txt_display_name)
        val txtUsername: TextView = root.findViewById(R.id.txt_username)

        UsersServices.getUserInfo(onResult = { user ->
            txtDisplayName.text = user.displayName
            txtUsername.text = user.username
            loadingSpinner.isVisible = false
        }, onError = { error ->
            // TODO
        })

        return root
    }
}