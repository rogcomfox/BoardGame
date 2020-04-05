package com.nusantarian.boardgame.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.nusantarian.boardgame.game.SudokuCell
import kotlin.math.min

class SudokuBoardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var sqrtSize = 3
    private var size = 9
    private var noteSizePixel = 0f
    private var cellSizePixel = 0f
    private var selectedRow = -1
    private var selectedCol = -1
    private var listener: com.nusantarian.boardgame.model.OnTouchListener? = null
    private var cells: List<SudokuCell>? = null

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

    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textSize = 24f
    }

    private val startingCellTextPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textSize = 32f
        typeface = Typeface.DEFAULT_BOLD
    }

    private val startingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#acacac")
    }

    private val noteTextPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixel = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixel, sizePixel)
    }

    override fun onDraw(canvas: Canvas) {
        updateMeasurement(width)
        fillCells(canvas)
        drawLine(canvas)
        drawText(canvas)
    }

    private fun updateMeasurement(width: Int) {
        cellSizePixel = width / size.toFloat()
        noteSizePixel = cellSizePixel / sqrtSize.toFloat()
        noteTextPaint.textSize = cellSizePixel / sqrtSize.toFloat()
        textPaint.textSize = cellSizePixel / 1.5f
        startingCellTextPaint.textSize = cellSizePixel / 1.5f
    }

    private fun fillCells(canvas: Canvas) {
        cells?.forEach {
            val row = it.r
            val column = it.c

            if (it.isStartingCell) {
                fillCell(canvas, row, column, startingCellPaint)
            } else if (row == selectedRow && column == selectedCol) {
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

    private fun fillCell(canvas: Canvas, row: Int, column: Int, paint: Paint) {
        canvas.drawRect(
            column * cellSizePixel,
            row * cellSizePixel,
            (column + 1) * cellSizePixel,
            (row + 1) * cellSizePixel,
            paint
        )
    }

    private fun drawText(canvas: Canvas) {
        cells?.forEach { cell ->
            val value = cell.value
            val textBounds = Rect()
            if (value == 0) {
                //draw notes
                cell.notes.forEach { note ->
                    val rowInCell = (note - 1) / sqrtSize
                    val colInCell = (note - 1) % sqrtSize
                    val valueString = note.toString()
                    noteTextPaint.getTextBounds(valueString, 0, valueString.length, textBounds)
                    val textWidth = noteTextPaint.measureText(valueString)
                    val textHeight = textBounds.height()
                    canvas.drawText(
                        valueString,
                        (cell.c * cellSizePixel) + (colInCell * noteSizePixel) + noteSizePixel / 2 - textWidth / 2f,
                        (cell.r * cellSizePixel) + (rowInCell * noteSizePixel) + noteSizePixel / 2 + textHeight/ 2f,
                        noteTextPaint
                    )
                }
            } else {
                val row = cell.r
                val column = cell.c
                val valueString = cell.value.toString()

                val textWidth = textPaint.measureText(valueString)
                val textHeight = textBounds.height()
                val paintToUse = if (cell.isStartingCell) startingCellTextPaint else textPaint

                paintToUse.getTextBounds(valueString, 0, valueString.length, textBounds)

                canvas.drawText(
                    valueString,
                    (column * cellSizePixel) + cellSizePixel / 2 - textWidth / 2,
                    (row * cellSizePixel) + cellSizePixel / 2 + textHeight / 2,
                    textPaint
                )
            }
        }
    }

    private fun drawLine(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), thickLine)
        for (i in 1 until size) {
            val paintToUse = when (i % sqrtSize) {
                0 -> thickLine
                else -> thinLine
            }
            canvas.drawLine(
                i * cellSizePixel,
                0f,
                i * cellSizePixel,
                height.toFloat(),
                paintToUse
            )
            canvas.drawLine(
                0f,
                i * cellSizePixel,
                width.toFloat(),
                i * cellSizePixel,
                paintToUse
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
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
        val possibleSelectedRow = (y / cellSizePixel).toInt()
        val possibleSelectedCol = (x / cellSizePixel).toInt()
        listener?.onCellTouched(possibleSelectedRow, possibleSelectedCol)
    }

    fun updateSelectedCellUI(r: Int, c: Int) {
        selectedRow = r
        selectedCol = c
        invalidate()
    }

    fun updateCells(cells: List<SudokuCell>) {
        this.cells = cells
        invalidate()
    }

    fun registerListener(listener: com.nusantarian.boardgame.model.OnTouchListener) {
        this.listener = listener
    }
}