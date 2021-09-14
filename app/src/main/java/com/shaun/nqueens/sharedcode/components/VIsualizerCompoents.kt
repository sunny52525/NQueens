package com.shaun.nqueens.sharedcode.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
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
import com.shaun.nqueens.sharedcode.utils.Constants


@Composable
fun Grid(gridSize: Int, solutions: ArrayList<Int>?, solutionShown: Int) {

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

                        if (solutions == null) {
                            GridItem(Color.White)
                        } else {
                            if (solutions[i] - 1 == j)
                                GridItem(Color.Green)
                            else
                                GridItem(Color.White)
                        }
                    }
                }
            }

        }



        solutions?.let {
            Text(
                text = "Solution Number ${solutionShown + 1}",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



@Composable
fun GridItem(color: Color) {

    Box(
        modifier =
        Modifier
            .background(color)
            .size(45.dp)
            .border(1.dp, Color.Green)
    ) {

        if (color == Color.Green)
            Image(
                painter = painterResource(R.drawable.ic_queen),
                contentDescription = "Idea logo",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize()
            )
    }
}

@Composable
fun Controller(
    gridSize: Int, onGridChange: (Int) -> Unit, onRunClicked: () -> Unit,
    gotoGame: () -> Unit,
    onResetClicked: () -> Unit
) {


    var dropDownExpanded by remember {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()

    ) {
        Button(onClick = onRunClicked, content = {
            Text(text = "Next Solution")
        })
        Spacer(modifier = Modifier.width(20.dp))

        Button(onClick = gotoGame, content = {
            Text(text = "Go to Game")
        })

        Spacer(modifier = Modifier.width(20.dp))
        Button(onClick = onResetClicked, content = {
            Text(text = "Reset")
        })

        Spacer(modifier = Modifier.width(20.dp))

        GridSelector(dropDownExpanded, gridSize, onGridChange) {
            dropDownExpanded = it
        }

    }
}

@Composable
fun GridSelector(
    dropDownExpanded: Boolean,
    gridSize: Int,
    onGridChange: (Int) -> Unit,
    dropDownChange: (Boolean) -> Unit
) {
//    var dropDownExpanded1 = dropDownExpanded
    Box(
        content = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Button(
                    onClick = {

                        dropDownChange(true)
                    }, content = {
                        Text(gridSize.toString(), color = Color.White)
                    }
                )
                DropdownMenu(expanded = dropDownExpanded, content = {
                    Constants.grids.forEach {
                        DropdownMenuItem(onClick = {
                            onGridChange(it)
                            dropDownChange(false)
                        }, content = {
                            Text(
                                text = it.toString(),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        })
                    }
                }, onDismissRequest = {
                    dropDownChange(false)
                })
            }

        }
    )
}
