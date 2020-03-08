package com.nusantarian.boardgame.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.nusantarian.boardgame.R
import com.nusantarian.boardgame.databinding.FragmentEightPuzzleBinding
import com.nusantarian.boardgame.databinding.FragmentMainBinding


class EightPuzzleFragment : Fragment() {

    private var _binding: FragmentEightPuzzleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEightPuzzleBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string._8_puzzle_game)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        return view
    }
}
