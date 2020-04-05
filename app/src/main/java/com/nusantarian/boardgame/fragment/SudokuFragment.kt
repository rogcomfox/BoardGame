package com.nusantarian.boardgame.fragment

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.nusantarian.boardgame.R
import com.nusantarian.boardgame.model.OnTouchListener
import com.nusantarian.boardgame.databinding.FragmentSudokuBinding
import com.nusantarian.boardgame.game.SudokuCell
import com.nusantarian.boardgame.viewmodel.SudokuViewModel


class SudokuFragment : Fragment(),
    OnTouchListener {

    private var _binding: FragmentSudokuBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SudokuViewModel
    private lateinit var buttons: List<MaterialButton>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSudokuBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.sudokuBoard.registerListener(this)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.sudoku_game)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        viewModel = ViewModelProvider(this).get(SudokuViewModel::class.java)
        viewModel.sudokuGame.selectedCellLiveData.observe(
            this,
            Observer { updateSelectedCellUI(it) })
        viewModel.sudokuGame.cellsLiveData.observe(
            this, Observer { updateCells(it) })
        viewModel.sudokuGame.isTakingNotesLiveData.observe(
            this,
            Observer { updateNoteTakingUI(it) })
        viewModel.sudokuGame.highlightedKeysLiveData.observe(
            this,
            Observer { updateHighlightedKeys(it) })

        buttons = listOf(
            binding.btnOne, binding.btnTwo, binding.btnThree, binding.btnFour,
            binding.btnFive, binding.btnSix, binding.btnSeven, binding.btnEight, binding.btnNine
        )

        buttons.forEachIndexed { index, materialButton ->
            materialButton.setOnClickListener {
                viewModel.sudokuGame.handleInput(index + 1)
            }
        }

        binding.btnNotes.setOnClickListener { viewModel.sudokuGame.changeNotesTakingState() }
        binding.btnDelete.setOnClickListener { viewModel.sudokuGame.delete() }

        return view
    }

    private fun updateCells(cells: List<SudokuCell>?) = cells?.let {
        binding.sudokuBoard.updateCells(cells)
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        binding.sudokuBoard.updateSelectedCellUI(cell.first, cell.second)
    }

    private fun updateNoteTakingUI(isTakingNotes: Boolean?) = isTakingNotes?.let {
        val color =
            if (it) ContextCompat.getColor(activity!!.applicationContext, R.color.colorPrimaryDark)
            else Color.LTGRAY
        binding.btnNotes.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
    }

    private fun updateHighlightedKeys(set: Set<Int>?) = set?.let {
        buttons.forEachIndexed { index, materialButton ->
            val color =
                if (set.contains(index + 1)) ContextCompat.getColor(
                    activity!!.applicationContext,
                    R.color.colorPrimaryDark
                )
                else Color.LTGRAY
            materialButton.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        }
    }

    override fun onCellTouched(r: Int, c: Int) {
        viewModel.sudokuGame.updateSelectedCell(r, c)
    }

}
