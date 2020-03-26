package com.nusantarian.boardgame.snakeGame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View


class SnakeGameBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val heightNum = 40
    private val widthNum = 20
    private val customHandler = Handler()
    private val paint = Paint()
    private var direction = Direction.RIGHT
    private var premium = 0L
    private var snake = Snake()
    private var isBoardCreated = false
    private val node = Node()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initGame(canvas)
        printSnake(canvas)
        printPremium(canvas)
        postDelay()
    }

    private fun postDelay() {
        customHandler.postDelayed(updateTimerThread, 1000 / 10)
    }

    private fun printPremium(canvas: Canvas?) {
        paint.isAntiAlias = true
        paint.textSize = 20F
        paint.color = Color.RED
        canvas?.drawText(premium++.toString(), 15F, 15F, paint);
    }

    private fun printSnake(canvas: Canvas?) {
        try {
            paint.color = Color.GREEN
            paint.strokeWidth = 10F
            for (node in snake.getBody()){

            }
        } catch (e: Exception){
            Log.e("error: ", e.toString())
        }
    }

    private fun initGame(canvas: Canvas?) {
        if (!isBoardCreated) {
            initBoard(canvas)
            initSnake(widthNum, heightNum)
            isBoardCreated = true
        }
    }

    private fun initSnake(widthNum: Int, heightNum: Int) {

    }

    private fun initBoard(canvas: Canvas?) {

    }

    private val updateTimerThread = Runnable {
        val rand = (Math.random() * 30).toInt()
        when (rand) {
            1 -> direction = Direction.RIGHT
            2 -> direction = Direction.LEFT
            3 -> direction = Direction.UP
            4 -> direction = Direction.DOWN
            else -> {
            }
        }
        snake.move(direction)
        invalidate()
    }
}