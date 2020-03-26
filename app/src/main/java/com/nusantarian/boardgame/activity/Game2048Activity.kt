package com.nusantarian.boardgame.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nusantarian.boardgame.R
import com.nusantarian.boardgame.databinding.ActivityGame2048Binding
import com.nusantarian.boardgame.game2048.GameView


class Game2048Activity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGame2048Binding
    private var gameActivity: Game2048Activity? = null
    var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGame2048Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnRestart.setOnClickListener(this)
    }

    fun geGameActivity(): Game2048Activity? {
        return gameActivity
    }

    fun clearScore() {
        score = 0
        showScore()
    }

    @SuppressLint("SetTextI18n")
    fun addScore(i: Int) {
        score += i
        showScore()
        val pref = getSharedPreferences("pMaxScore", Context.MODE_PRIVATE)

        if (score > pref.getInt("maxScore", 0)) {
            val editor = pref.edit()
            editor.putInt("maxScore", score)
            editor.apply()
            binding.tvMax.text = pref.getInt("maxScore", 0).toString() + ""
        }
    }

    @SuppressLint("SetTextI18n")
    fun showScore() {
        binding.tvScore.text = score.toString() + ""
    }

    override fun onBackPressed() {
        createExitTipDialog()
    }

    private fun createExitTipDialog() {
        AlertDialog.Builder(this)
            .setMessage("Do you want to quit?")
            .setTitle("Quit")
            .setIcon(R.drawable.info)
            .setPositiveButton("Yes") { dialogInterface, _ ->
                dialogInterface.dismiss()
                finish()
            }
            .setNegativeButton("No") { dialogInterface, _ -> dialogInterface.dismiss() }
            .show()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_restart -> GameView.startGame()
            R.id.btn_pause -> {
                if (binding.gameView.hasTouched){
                    score = binding.gameView.score
                    showScore()
                    for (y in 0..3) {
                        for (x in 0..3) {
                            GameView.cards[y][x]?.num = binding.gameView.num[y][x]
                        }
                    }
                }
            }
            R.id.btn_back -> {

            }
        }
    }

}
