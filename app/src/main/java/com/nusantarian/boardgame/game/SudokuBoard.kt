package com.nusantarian.boardgame.game

class SudokuBoard (val size: Int, val cells: List<SudokuCell>){
    fun getCell(r: Int, c: Int) = cells[r * size + c]
}