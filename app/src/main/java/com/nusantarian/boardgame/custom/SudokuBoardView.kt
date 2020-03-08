package com.nusantarian.boardgame.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class SudokuBoardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var sqrtSize = 3
    private var size = 9
    private var cellSizePixel = 0f
    private var selectedRow = -1
    private var selectedCol = -1

    private val thickLine = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4f
    }

    private val thinLine = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.GRAY
        strokeWidth = 2f
    }

    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#008000")
    }

    private val conflictedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#efedef")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixel = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixel, sizePixel)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        cellSizePixel = (width / size).toFloat()
        fillCells(canvas)
        drawLine(canvas)
    }

    private fun fillCells(canvas: Canvas) {
        if (selectedRow == -1 || selectedCol == -1) return
        for (row in 0..size) {
            for (column in 0..size) {
                if (row == selectedRow && column == selectedCol) {
                    fillCell(canvas, row, column, selectedCellPaint)
                } else if (row == selectedRow || column == selectedCol) {
                    fillCell(canvas, row, column, conflictedCellPaint)
                } else if (row / sqrtSize == selectedRow / sqrtSize
                    && column / sqrtSize == selectedCol / sqrtSize
                ) {
                    fillCell(canvas, row, column, conflictedCellPaint)
                }
            }
        }
    }

    private fun fillCell(canvas: Canvas, row: Int, column: Int, paint: Paint) {
        canvas.drawRect(
            column * cellSizePixel,
            row * cellSizePixel,
            (column + 1) * cellSizePixel,
            (row + 1) * cellSizePixel,
            paint
        )
    }

    private fun drawLine(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), thickLine)
        for (i in 1 until size) {
            val paintToUse = when (i % sqrtSize) {
                0 -> thickLine
                else -> thinLine
            }
            canvas.drawLine(i * cellSizePixel, 0f, i * cellSizePixel, height.toFloat(), paintToUse)
            canvas.drawLine(0f, i * cellSizePixel, width.toFloat(), i * cellSizePixel, paintToUse)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handleTouchEvent(event.x, event.y)
                true
            }
            else -> false
        }
    }

    private fun handleTouchEvent(x: Float, y: Float) {
        selectedRow = (y / cellSizePixel).toInt()
        selectedCol = (x / cellSizePixel).toInt()
        invalidate()
    }
}