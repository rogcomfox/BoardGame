package com.nusantarian.boardgame.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.nusantarian.boardgame.R
import com.nusantarian.boardgame.databinding.FragmentCatchBallBinding
import com.nusantarian.boardgame.databinding.FragmentEightPuzzleBinding
import kotlin.properties.Delegates


class CatchBallFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCatchBallBinding? = null
    private val binding get() = _binding!!
    private var frameHeight = 0
    private var frameWidth = 0
    private var initialFrameWidth = 0
    private var boxSize = 0
    //position
    private var playerX by Delegates.notNull<Float>()
    private var playerY by Delegates.notNull<Float>()
    private var sparkX by Delegates.notNull<Float>()
    private var sparkY by Delegates.notNull<Float>()
    private var greenX by Delegates.notNull<Float>()
    private var greenY by Delegates.notNull<Float>()
    private var blueX by Delegates.notNull<Float>()
    private var blueY by Delegates.notNull<Float>()
    //score
    private var score by Delegates.notNull<Int>()
    private var highScore by Delegates.notNull<Int>()
    private var timeCount by Delegates.notNull<Int>()
    //status
    private var start_flg = false
    private var action_flg = false
    private var pink_flg = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCatchBallBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.catch_the_ball)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        binding.btnStart.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_start -> {
                start_flg = true
                binding.startLayout.visibility = View.INVISIBLE
                if (frameHeight == 0){
                    frameHeight = binding.frameGame.height
                    frameWidth = binding.frameGame.width
                    initialFrameWidth = frameWidth

                    boxSize = binding.imgPlayer.height
                    playerX = binding.imgPlayer.x
                    playerY = binding.imgPlayer.y
                }
                binding.imgPlayer.x = 0.0f
                binding.imgSpark.y = 3000.0f
                binding.green.y = 3000.0f
                binding.blue.y = 3000.0f
            }
        }
    }
}
