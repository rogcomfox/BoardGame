package com.nusantarian.boardgame.game

import androidx.lifecycle.MutableLiveData

class SudokuGame {
    var selectedCell = MutableLiveData<Pair<Int, Int>>()
    private var selectedRow = -1
    private var selectedCol = -1

    init {
        selectedCell.postValue(Pair(selectedRow, selectedCol))
    }

    fun updateSelectedCell(r: Int, c: Int) {
        selectedRow = r
        selectedCol = c
        selectedCell.postValue(Pair(selectedRow, selectedCol))
    }
}