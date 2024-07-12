package com.prodigyinternship.tictactoe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prodigyinternship.tictactoe.data.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToe(viewModel: MainViewModel = MainViewModel()) {
    val grid by viewModel.grid.collectAsState()
    val playerTurn by viewModel.playerTurn.collectAsState()
    val winner by viewModel.winner.collectAsState()

    var drawBoolean by remember {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Tic Tac Toe",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Normal,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }, modifier = Modifier.fillMaxWidth(), colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            )
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (winner.isEmpty()) "Player ${if (playerTurn == 0) "X" else "O"}'s Turn" else "Winner: $winner",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center,
            ) {
                items(9) { index ->
                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .size(100.dp),
                        elevation = CardDefaults.cardElevation(),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        IconButton(
                            onClick = {
                                viewModel.onIconClicked(index)
                            }, modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        ) {
                            Icon(
                                painter = if (grid[index] == "X") painterResource(id = R.drawable.baseline_close_24)
                                else if (grid[index] == "O") painterResource(id = R.drawable.outline_circle_24)
                                else {
                                    painterResource(id = R.drawable.baseline_check_box_outline_blank_24)
                                },
                                modifier = Modifier.fillMaxSize(),
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 5.dp, end = 5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { viewModel.resetGame() }, modifier = Modifier.padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh, contentDescription = "Refresh"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Reset Game")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TicTacToePreview() {
    TicTacToe()
}
