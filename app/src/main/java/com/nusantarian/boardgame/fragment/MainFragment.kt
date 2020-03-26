package com.nusantarian.boardgame.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.nusantarian.boardgame.R
import com.nusantarian.boardgame.activity.Game2048Activity
import com.nusantarian.boardgame.databinding.FragmentMainBinding

class MainFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var ft:FragmentTransaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btnTicTacToe.setOnClickListener(this)
        binding.btn8puzzle.setOnClickListener(this)
        binding.btnSudoku.setOnClickListener(this)
        binding.btnCatchBall.setOnClickListener(this)
        binding.btnSnake.setOnClickListener(this)
        binding.btnGame2048.setOnClickListener(this)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.app_name)
        ft = activity!!.supportFragmentManager.beginTransaction()
        setHasOptionsMenu(true)
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_snake -> {
                ft.replace(R.id.frame_container, SnakeGameFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.btn_tic_tac_toe -> {
                ft.replace(R.id.frame_container, TicTacToeFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.btn_8puzzle -> {
                ft.replace(R.id.frame_container, EightPuzzleFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.btn_sudoku -> {
                ft.replace(R.id.frame_container, SudokuFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.btn_catchBall -> {
                ft.replace(R.id.frame_container, CatchBallFragment())
                    .addToBackStack(null)
                    .commit()
            }
            R.id.btn_game2048 -> startActivity(Intent(this.activity, Game2048Activity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_about -> {

            }
            R.id.nav_settings -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
