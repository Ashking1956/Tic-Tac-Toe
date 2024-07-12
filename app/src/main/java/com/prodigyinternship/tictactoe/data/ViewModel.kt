package com.prodigyinternship.tictactoe.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _grid = MutableStateFlow(
        listOf(
            " ", " ", " ",
            " ", " ", " ",
            " ", " ", " "
        )
    )
    val grid: StateFlow<List<String>> = _grid

    private val _playerTurn = MutableStateFlow(0)
    val playerTurn: StateFlow<Int> = _playerTurn

    private val _winner = MutableStateFlow("")
    val winner: StateFlow<String> = _winner

    private fun playerTurn() {
        _playerTurn.value = (_playerTurn.value + 1) % 2
    }

    fun resetGame() {
        _grid.value = listOf(
            " ", " ", " ",
            " ", " ", " ",
            " ", " ", " "
        )
        _playerTurn.value = 0
        _winner.value = ""
    }

    fun onIconClicked(index: Int) {
        if (_grid.value[index] == " " && _winner.value.isEmpty()) {
            _grid.value = _grid.value.toMutableList().also {
                it[index] = if (_playerTurn.value == 0) "X" else "O"
            }
            if (draw()){
                _winner.value = "Draw"
            }
            if (checkWinner()) {
                _winner.value = if (_playerTurn.value == 0) "X" else "O"
            } else {
                playerTurn()
            }
        }
    }

    private fun draw(): Boolean {
        var temp = false
        if (_grid.value.all { it != " " }) {
            temp = true
        }
        return temp
    }

    private fun checkWinner(): Boolean {
        val grid = _grid.value
        val winPatterns = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        )
        return winPatterns.any { pattern ->
            val (a, b, c) = pattern
            grid[a] == grid[b] && grid[b] == grid[c] && grid[a] != " "
        }
    }
}
