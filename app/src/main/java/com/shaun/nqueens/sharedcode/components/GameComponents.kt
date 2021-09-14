package com.shaun.nqueens.sharedcode

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shaun.nqueens.R


@ExperimentalFoundationApi
@Composable
fun GridInteractive(
    gridSize: Int,
    grid: ArrayList<ArrayList<Int>>,
    onBlockClicked: (Int, Int) -> Unit,
    undo: (Int, Int) -> Unit

) {


    Column(
        modifier = Modifier
            .wrapContentSize(unbounded = true)
            .animateContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .border(width = 1.dp, color = Color.Red)
                .animateContentSize()
        ) {
            repeat(gridSize) { i ->
                Column {
                    repeat(gridSize) { j ->

                        var blockColor by remember {
                            mutableStateOf(Color.White)
                        }

                        GridItemInteractive(
                            i = i, j = j,
                            onBlockClicked = onBlockClicked,
                            grid = grid,

                            undo = undo,
                            blockColor = blockColor
                        )
                    }
                }
            }

        }
    }
}


@ExperimentalFoundationApi
@Composable
fun GridItemInteractive(

    i: Int,
    j: Int,
    onBlockClicked: (Int, Int) -> Unit,
    grid: ArrayList<ArrayList<Int>>,
    undo: (Int, Int) -> Unit,
    blockColor: Color,
) {


    Box(modifier = Modifier
        .background(
            if (grid.isNotEmpty()) {
                when {
                    grid[j][i] == 1 -> Color.Green
                    grid[j][i] == 2 -> {
                        Color.Red
                    }
                    else -> {
                        blockColor

                    }
                }
            } else {
                blockColor
            }
        )
        .size(45.dp)
        .border(1.dp, Color.Green)
        .clickable {

            if (grid.isNotEmpty()) {


                if (grid[j][i] == 1) {
                    undo(i, j)
                } else {
                    onBlockClicked(i, j)

                }
            }
        }
    ) {

        if (grid.isNotEmpty())
            if (grid[j][i] == 1 || grid[j][i] == 2) {

                Image(
                    painter = painterResource(R.drawable.ic_queen),
                    contentDescription = "Idea logo",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize()
                )
            }
    }
}



@Composable
fun Heading(gridSize: Int) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("NQueens Visualizer", color = Color.Black, fontWeight = FontWeight.SemiBold)
        Text(
            text = "Place $gridSize Queens on $gridSize x $gridSize board in such a way that no two queens can attack Each Other" +
                    "\nUse Two fingers to zoom in/out, move right, left",
            modifier = Modifier
                .animateContentSize()
                .padding(8.dp),
            textAlign = TextAlign.Center
        )


    }
}