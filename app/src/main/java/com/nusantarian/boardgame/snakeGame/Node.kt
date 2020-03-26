package com.nusantarian.boardgame.snakeGame

import android.graphics.Rect

open class Node {
    private var column = 0
    private var row = 0
    private lateinit var rect: Rect

    open fun Node(row: Int, column: Int, rect: Rect?) {
        this.row = row
        this.column = column
        this.rect = rect!!
    }
    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + column
        result = prime * result + row
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (javaClass != other.javaClass) return false
        val another =
            other as Node
        if (column != another.column) return false
        return row == another.row
    }

    override fun toString(): String {
        return "$row - $column"
    }

    @Throws(CloneNotSupportedException::class)
    protected fun clone(): Any? {
        return Node(row, column, rect)
    }
}