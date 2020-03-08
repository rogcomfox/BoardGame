package com.nusantarian.boardgame.viewmodel

import androidx.lifecycle.ViewModel
import com.nusantarian.boardgame.game.SudokuGame

class SudokuViewModel : ViewModel() {
    val sudokuGame = SudokuGame()
}