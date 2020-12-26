package com.rememo.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rememo.R
import com.rememo.models.StudyClass
import com.rememo.services.api.ClassServices
import com.rememo.services.api.UsersServices

class ProfileFragment : Fragment() {

    private val createdClassList = ArrayList<StudyClass>()
    private var createdClassListAdapter: CreatedClassesListItemAdapter? = null
    private val joinedClassList = ArrayList<StudyClass>()
    private var joinedClassListAdapter: JoinedClassesListItemAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val loadingSpinner = root.findViewById<ProgressBar>(R.id.loading_profile)
        loadingSpinner.isVisible = true

        val txtDisplayName: TextView = root.findViewById(R.id.txt_display_name)
        val txtUsername: TextView = root.findViewById(R.id.txt_username)

        var loadingUsers = true
        var loadingClasses = true

        UsersServices.getUserInfo(onResult = { user ->
            txtDisplayName.text = user.displayName
            txtUsername.text = user.username
            loadingUsers = false
            if (!loadingClasses)
                loadingSpinner.isVisible = false
        }, onError = { error ->
            loadingUsers = false
            if (!loadingClasses)
                loadingSpinner.isVisible = false
        })

        createdClassListAdapter = CreatedClassesListItemAdapter(createdClassList)
        val listCreatedClasses = root.findViewById<RecyclerView>(R.id.list_created_classes)
        listCreatedClasses.adapter = createdClassListAdapter
        listCreatedClasses.layoutManager = LinearLayoutManager(context)
        listCreatedClasses.setHasFixedSize(true)

        joinedClassListAdapter = JoinedClassesListItemAdapter(joinedClassList)
        val listJoinedClasses = root.findViewById<RecyclerView>(R.id.list_joined_classes)
        listJoinedClasses.adapter = joinedClassListAdapter
        listJoinedClasses.layoutManager = LinearLayoutManager(context)
        listJoinedClasses.setHasFixedSize(true)

        ClassServices.getClasses(onResult = { classes ->
            createdClassList.addAll(classes.filter { c -> c.mine == true })
            joinedClassList.addAll(classes.filter { c -> c.mine == false })
            createdClassListAdapter?.notifyDataSetChanged()
            joinedClassListAdapter?.notifyDataSetChanged()
            loadingClasses = false
            if (!loadingUsers)
                loadingSpinner.isVisible = false
        } , onError = {
            loadingClasses = false
            if (!loadingUsers)
                loadingSpinner.isVisible = false        })

        return root
    }
}