package com.nusantarian.boardgame.game2048

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import com.nusantarian.boardgame.activity.Game2048Activity
import kotlin.math.abs


class GameView : GridLayout {
    var num = Array(4) { IntArray(4) }
    var score = 0
    var hasTouched = false
    var gameActivity = Game2048Activity()

    constructor(context: Context?) : super(context) {
        initGameView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initGameView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initGameView()
    }

    private fun initGameView() {
        rowCount = 4
        columnCount = 4
        setOnTouchListener(Listener())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val cardWidth = (w.coerceAtMost(h) - 10) / 4
        addCards(cardWidth, cardWidth)
        startGame()
    }

    private fun addCards(cardWidth: Int, cardHeight: Int) {
        this.removeAllViews()
        var c: Card
        for (y in 0..3) {
            for (x in 0..3) {
                c = Card(context)
                c.num = 0
                addView(c, cardWidth, cardHeight)
                cards[x][y] = c
            }
        }
    }

    private fun swipeLeft() {
        var b = false
        for (y in 0..3) {
            var x = 0
            while (x < 3) {
                for (x1 in x + 1..3) {
                    if (cards[x1][y]!!.num > 0) {
                        if (cards[x][y]!!.num == 0) {
                            cards[x][y]!!.num = cards[x1][y]!!.num
                            cards[x1][y]!!.num = 0
                            --x
                            b = true
                        } else if (cards[x][y]!!.equals(cards[x1][y]!!)) {
                            cards[x][y]!!.num = cards[x][y]!!.num * 2
                            cards[x1][y]!!.num = 0
                            gameActivity.geGameActivity()?.addScore(cards[x][y]!!.num)
                            b = true
                        }
                        break
                    }
                }
                ++x
            }
        }
        if (b) {
            addRandomNum()
            checkGameOver()
        }
    }

    private fun swipeRight() {
        var b = false
        for (y in 0..3) {
            var x = 3
            while (x > 0) {
                for (x1 in x - 1 downTo 0) {
                    if (cards[x1][y]!!.num > 0) {
                        if (cards[x][y]!!.num == 0) {
                            cards[x][y]!!.num = cards[x1][y]!!.num
                            cards[x1][y]!!.num = 0
                            ++x
                            b = true
                        } else if (cards[x][y]!!.equals(cards[x1][y]!!)
                        ) {
                            cards[x][y]!!.num = cards[x][y]!!.num * 2
                            cards[x1][y]!!.num = 0
                            gameActivity.geGameActivity()?.addScore(cards[x][y]!!.num)
                            b = true
                        }
                        break
                    }
                }
                --x
            }
        }
        if (b) {
            addRandomNum()
            checkGameOver()
        }
    }

    private fun swipeUp() {
        var b = false
        for (x in 0..3) {
            var y = 0
            while (y < 3) {
                for (y1 in y + 1..3) {
                    if (cards[x][y1]!!.num > 0) {
                        if (cards[x][y]!!.num == 0) {
                            cards[x][y]!!.num =
                                cards[x][y1]!!.num
                            cards[x][y1]!!.num = 0
                            --y
                            b = true
                        } else if (cards[x][y]!!.equals(cards[x][y1]!!)) {
                            cards[x][y]!!.num = cards[x][y]!!.num * 2
                            cards[x][y1]!!.num = 0
                            gameActivity.geGameActivity()?.addScore(cards[x][y]!!.num)
                            b = true
                        }
                        break
                    }
                }
                ++y
            }
        }
        if (b) {
            addRandomNum()
            checkGameOver()
        }
    }

    private fun swipeDown() {
        var b = false
        for (x in 0..3) {
            var y = 3
            while (y > 0) {
                for (y1 in y - 1 downTo 0) {
                    if (cards[x][y1]!!.num > 0) {
                        if (cards[x][y]!!.num == 0) {
                            cards[x][y]!!.num = cards[x][y1]!!.num
                            cards[x][y1]!!.num = 0
                            ++y
                            b = true
                        } else if (cards[x][y]!!.equals(cards[x][y1]!!)) {
                            cards[x][y]!!.num = cards[x][y]!!.num * 2
                            cards[x][y1]!!.num = 0
                            gameActivity.geGameActivity()?.addScore(cards[x][y]!!.num)
                            b = true
                        }
                        break
                    }
                }
                --y
            }
        }
        if (b) {
            addRandomNum()
            checkGameOver()
        }
    }

    private fun checkGameOver() {
        var isOver = true
        ALL@ for (y in 0..3) {
            for (x in 0..3) {
                if (cards[x][y]!!.num == 0 ||
                    x < 3 && cards[x][y]!!.num == cards[x + 1][y]!!.num ||
                    y < 3 && cards[x][y]!!.num == cards[x][y + 1]!!.num
                ) {
                    isOver = false
                    break@ALL
                }
            }
        }
        if (isOver) {
            AlertDialog.Builder(context).setTitle("Game Over!")
                .setMessage("Current Score is " + gameActivity.score.toString())
                .setPositiveButton("Try Again?") { _, _ -> startGame() }.show()
        }
    }

    internal inner class Listener : OnTouchListener {
        private var startX = 0f
        private var startY = 0f
        private var offsetX = 0f
        private var offsetY = 0f
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean {
            if (!hasTouched) {
                hasTouched = true
            }
            score = gameActivity.score
            for (y in 0..3) {
                for (x in 0..3) {
                    num[y][x] = cards[y][x]!!.num
                }
            }
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = motionEvent.x
                    startY = motionEvent.y
                }
                MotionEvent.ACTION_UP -> {
                    offsetX = motionEvent.x - startX
                    offsetY = motionEvent.y - startY
                    if (abs(offsetX) > abs(offsetY)) {
                        if (offsetX < -5) {
                            swipeLeft()
                        } else if (offsetX > 5) {
                            swipeRight()
                        }
                    } else {
                        if (offsetY < -5) {
                            swipeUp()
                        } else if (offsetY > 5) {
                            swipeDown()
                        }
                    }
                }
            }
            return true
        }
    }

    companion object {
        var cards = Array(4) { arrayOfNulls<Card>(4) }
        private val emptyPoints: MutableList<Point> = ArrayList()

        private fun addRandomNum() {
            emptyPoints.clear()
            for (y in 0..3) {
                for (x in 0..3) {
                    if (cards[x][y]!!.num == 0) {
                        emptyPoints.add(Point(x, y))
                    }
                }
            }
            val p: Point = emptyPoints.removeAt((Math.random() * emptyPoints.size).toInt())
            cards[p.x][p.y]!!.num = if (Math.random() > 0.1) 2 else 4
        }

        fun startGame() {
            val gameActivity = Game2048Activity()
            gameActivity.geGameActivity()?.clearScore()
            for (y in 0..3) {
                for (x in 0..3) {
                    cards[x][y]!!.num = 0
                }
            }
            addRandomNum()
            addRandomNum()
        }
    }
}