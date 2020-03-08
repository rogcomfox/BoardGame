package com.nusantarian.boardgame.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nusantarian.boardgame.R
import com.nusantarian.boardgame.databinding.FragmentTicTacToeBinding


open class TicTacToeFragment : Fragment(), View.OnClickListener {

    private val buttons = Array(3) { arrayOfNulls<Button>(3) }
    private var player1Turn = true
    private var roundCount = 0
    private var player1Point = 0
    private var player2Point = 0
    private var _binding: FragmentTicTacToeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTicTacToeBinding.inflate(inflater, container, false)
        val view = binding.root
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.tic_tac_toe)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        for (i in 0..2) {
            for (j in 0..2) {
                val buttonID = "btn_btn$i$j"
                val resID = resources.getIdentifier(buttonID, "id", activity?.packageName)
                buttons[i][j] = view.findViewById(resID)
                buttons[i][j]?.setOnClickListener(this)
            }
        }
        binding.btnReset.setOnClickListener(this)
        return view
    }


    override fun onClick(v: View) {
        if (v.id == R.id.btn_reset) {
            resetGame()
        }
        if ((v as Button).text.toString() != "") {
            return
        }
        if (player1Turn) {
            v.text = "X"
        } else {
            v.text = "O"
        }
        roundCount++
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins()
            } else {
                player2Wins()
            }
        } else if (roundCount == 9) {
            draw()
        } else {
            player1Turn = !player1Turn
        }
    }

    private fun checkForWin(): Boolean {
        val field = Array(3) { arrayOfNulls<String>(3) }
        for (i in 0..2)
            for (j in 0..2)
                field[i][j] = buttons[i][j]!!.text.toString()
        for (i in 0..2) {
            if (field[i][0] == field[i][1] && field[i][0] == field[i][2] && field[i][0] != "")
                return true
        }
        for (i in 0..2) {
            if (field[0][i] == field[1][i] && field[0][i] == field[2][i] && field[0][i] != "")
                return true
        }
        if (field[0][0] == field[1][1] && field[0][0] == field[2][2] && field[0][0] != "")
            return true
        return field[0][2] == field[1][1] && field[0][2] == field[2][0] && field[0][2] != ""
    }

    private fun player1Wins() {
        player1Point++
        Toast.makeText(activity, "Player 1 Wins", Toast.LENGTH_SHORT).show()
        updatePoints()
        resetBoard()
    }

    private fun player2Wins() {
        player2Point++
        Toast.makeText(activity, "Player 2 Wins", Toast.LENGTH_SHORT).show()
        updatePoints()
        resetBoard()
    }

    private fun draw() {
        Toast.makeText(activity, "Draw, Try Again Later", Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    @SuppressLint("SetTextI18n")
    private fun updatePoints() {
        binding.tvPlayer1.text = "Player 1: $player1Point"
        binding.tvPlayer2.text = "Player 2: $player2Point"
    }

    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j]?.text = ""
            }
        }
        roundCount = 0
        player1Turn = true
    }

    private fun resetGame() {
        if (player1Point == 0 && player2Point == 0) {
            resetBoard()
            Toast.makeText(activity, "Already Reset Game", Toast.LENGTH_SHORT).show()
        } else {
            player1Point = 0
            player2Point = 0
            updatePoints()
            resetBoard()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("roundCount", roundCount)
        outState.putInt("player1Point", player1Point)
        outState.putInt("player2Point", player2Point)
        outState.putBoolean("player1Turn", player1Turn)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            roundCount = savedInstanceState.getInt("roundCount")
            player1Point = savedInstanceState.getInt("player1Point")
            player2Point = savedInstanceState.getInt("player2Point")
            player1Turn = savedInstanceState.getBoolean("player1Turn")
        }
    }
}
