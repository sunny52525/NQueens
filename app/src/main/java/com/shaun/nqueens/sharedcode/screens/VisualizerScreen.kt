package com.shaun.nqueens.sharedcode.screens

import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.shaun.nqueens.sharedcode.components.Controller
import com.shaun.nqueens.sharedcode.components.Grid
import com.shaun.nqueens.sharedcode.utils.NQueenSolution


@Composable
fun MainScreen(
    gridSize: Int,
    onGridChange: (Int) -> Unit,
    gotoGame: () -> Unit,
    scales: Float,
    state: TransformableState,
    offset: Offset,


    ) {


    var solutions: ArrayList<ArrayList<Int>> by remember {
        mutableStateOf(NQueenSolution.solve(gridSize))
    }
    var solutionShown by remember {
        mutableStateOf(0)
    }


    Column(
        modifier = Modifier
            .graphicsLayer {
                scaleX = scales
                scaleY = scales
                translationX = offset.x
                translationY = offset.y
            }
            .transformable(state = state)
            .fillMaxSize()


    ) {

        Spacer(Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            if (solutions.isNotEmpty())
                Grid(gridSize, solutions[solutionShown], solutionShown)
            else
                Grid(gridSize, null, solutionShown)
        }

        Spacer(Modifier.height(20.dp))
        Controller(gridSize,
            onGridChange = {
                onGridChange(it)
                solutions = NQueenSolution.solve(it)
                solutionShown = 0
            }, onRunClicked = {
                if (solutionShown + 1 == solutions.size) {
                    solutionShown = 0
                } else {
                    solutionShown++
                }
            }, onResetClicked = {
                solutionShown = 0
            }, gotoGame = {
                gotoGame()
            })
    }
}


