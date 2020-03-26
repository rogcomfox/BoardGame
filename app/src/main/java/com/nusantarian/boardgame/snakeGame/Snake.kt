package com.nusantarian.boardgame.snakeGame

import java.util.*

open class Snake {

    private lateinit var body: LinkedList<Node>
    private var endRow = 0
    private var endColumn = 0
    private lateinit var direction: Direction

    open fun Snake(maxRow: Int, maxColumn: Int) {
        this.endRow = maxRow
        this.endColumn = maxColumn
        this.setBody(LinkedList<Node>())
        this.direction = Direction.RIGHT
    }

    fun move(direction: Direction) {

    }

    private fun validadeDirection(direction: Direction): Boolean {
        if (direction == Direction.LEFT && this.direction == Direction.RIGHT) return false
        if (direction == Direction.RIGHT && this.direction == Direction.LEFT) return false
        if (direction == Direction.UP && this.direction == Direction.DOWN) return false
        return !(direction == Direction.DOWN && this.direction == Direction.LEFT)
    }


    fun getBody(): LinkedList<Node> {
        return body
    }

    fun setBody(body: LinkedList<Node>) {
        this.body = body
    }
}