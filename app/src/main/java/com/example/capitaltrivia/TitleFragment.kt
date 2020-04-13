package com.example.capitaltrivia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.capitaltrivia.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false
        )

        // set onClick listener for when users click the play button
        binding.playButton.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment))

        // Change the title of the support action bar
        (activity as AppCompatActivity).supportActionBar?.title = "Capital Trivia!"

        return binding.root
    }

}
