package com.shaun.nqueens.sharedcode

import com.shaun.nqueens.sharedcode.utils.Constants
import com.shaun.nqueens.sharedcode.utils.NQueenSolution.Companion.toSolution
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shaun.nqueens.sharedcode.utils.ExtensionFunctions.clearList
import com.shaun.nqueens.sharedcode.utils.ExtensionFunctions.countElements
import com.shaun.nqueens.sharedcode.utils.ExtensionFunctions.fillList
import com.shaun.nqueens.sharedcode.utils.NQueenSolution


@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MainScreenInteractive(

    gridSize: Int,
    onGridChange: (Int) -> Unit,

    gotoSolution: () -> Unit,
    scales: Float,
    state: TransformableState,
    offset: Offset,
) {

    var grid: ArrayList<ArrayList<Int>> by remember {
        mutableStateOf(Constants.getGrid(gridSize))
    }
    var results: ArrayList<ArrayList<Int>> by remember {
        mutableStateOf(NQueenSolution.solve(gridSize))
    }

    var isShowingResultMessage by remember {
        mutableStateOf(false)
    }

    var resultMessage by remember {
        mutableStateOf("Uh Oh Try Again")
    }
    var checkButtonEnabled by remember {
        mutableStateOf(true)
    }

    var dropDownExpanded by remember {
        mutableStateOf(false)
    }



    Column(modifier = Modifier
        .animateContentSize()
        .graphicsLayer {
            scaleX = scales
            scaleY = scales
            translationX = offset.x
            translationY = offset.y
        }
        .transformable(state = state)
        .fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            GridInteractive(
                gridSize = gridSize, grid = grid,
                onBlockClicked = { i: Int, j: Int ->

                    //Force Recomposition
                    grid[j][i] = 1
                    val grid2: ArrayList<ArrayList<Int>> = clearList(gridSize, grid)
                    grid.clear()
                    grid = grid2

                }, undo = { i, j ->

                    grid[j][i] = 0
                    val grid2: ArrayList<ArrayList<Int>> = clearList(gridSize, grid)
                    grid.clear()
                    grid = grid2


                })
        }



        AnimatedVisibility(isShowingResultMessage) {
            Text(
                resultMessage,
                color = Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(20.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {

                val count = grid.countElements()

                if (count < gridSize) {
                    resultMessage =
                        "Please place ${gridSize - count} more Queens"
                    isShowingResultMessage = true
                    return@Button
                }

                val result = NQueenSolution.checkSolutions(grid, gridSize)

                val grid2: ArrayList<ArrayList<Int>> = clearList(gridSize, grid)

                result?.forEach {
                    grid2[it.first][it.second] = 2
                }

                if (results.contains(grid.toSolution(gridSize))) {
                    resultMessage = "Woo Hoo, You're correct"
                    isShowingResultMessage = true
                } else {
                    resultMessage = "Uh oh , Please try Again"
                    isShowingResultMessage = true
                }

                grid = grid2

                checkButtonEnabled = false
            }, content = {
                Text(text = "Check")
            }, enabled = checkButtonEnabled)

            Spacer(modifier = Modifier.width(20.dp))

            Button(onClick = gotoSolution, content = {
                Text(text = "Goto Solutions")
            })

            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {

                val grid2: ArrayList<ArrayList<Int>> = gridSize.fillList()

                grid.clear()
                grid = grid2

                checkButtonEnabled = true
                isShowingResultMessage = false
            }, content = {
                Text(text = "Reset")
            })

            Spacer(modifier = Modifier.width(20.dp))

            GridSelector(
                dropDownExpanded = dropDownExpanded,
                gridSize = gridSize,
                onGridChange = {
                    grid = Constants.getGrid(it)
                    results = NQueenSolution.solve(it)
                    onGridChange(it)
                }, dropDownChange = {
                    dropDownExpanded = it
                })
        }
    }

}
