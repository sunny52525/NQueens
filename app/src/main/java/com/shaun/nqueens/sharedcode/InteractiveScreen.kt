import NQueenSolution.Companion.toSolution
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.shaun.nqueens.R
import kotlin.math.roundToInt


@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MainScreenInteractive(

    gridSize: Int,
    onGridChange: (Int) -> Unit,

    gotoSolution: () -> Unit,
    scales: Float,
    state: TransformableState,
    offsetX: Float,
    offsetY: Float,
    offsetChanges:(Float,Float)->Unit
) {

    var grid: ArrayList<ArrayList<Int>> by remember {
        mutableStateOf(Constants.getGrid(gridSize))
    }
    var results: ArrayList<ArrayList<Int>> by remember {
        mutableStateOf(NQueenSolution.solve(gridSize))
    }

    var isDialog by remember {
        mutableStateOf(false)
    }

    var dialogMessage by remember {
        mutableStateOf("Uh Oh Try Again")
    }
    var checkEnabled by remember {
        mutableStateOf(true)
    }

    var dropDownExpanded by remember {
        mutableStateOf(false)
    }



    Column(modifier = Modifier.animateContentSize() .graphicsLayer {
        scaleX = scales
        scaleY = scales
    }
        .transformable(state = state).fillMaxSize()
        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consumeAllChanges()
                offsetChanges(dragAmount.x,dragAmount.y)

            }
        }) {

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            GridInteractive(
                gridSize = gridSize, grid = grid,
                onBlockClicked = { i: Int, j: Int ->
                    println(grid.size)
                    printGrid(grid)
                    grid[j][i] = 1

                    val grid2: ArrayList<ArrayList<Int>> = arrayListOf()

                    repeat(gridSize) { ii ->
                        val oneRow = arrayListOf<Int>()
                        repeat(gridSize) { jj ->
                            oneRow.add(grid[ii][jj])
                        }
                        grid2.add(oneRow)
                    }

                    grid.clear()

                    grid = grid2

                }, undo = { i, j ->
                    val grid2: ArrayList<ArrayList<Int>> = arrayListOf()
                    grid[j][i] = 0

                    repeat(gridSize) { ii ->
                        val oneRow = arrayListOf<Int>()
                        repeat(gridSize) { jj ->
                            oneRow.add(grid[ii][jj])
                        }
                        grid2.add(oneRow)
                    }
                    grid.clear()

                    grid = grid2
                })
        }



        AnimatedVisibility(isDialog) {
            Text(
                dialogMessage,
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

                var count = 0
                grid.forEach { iArray ->
                    iArray.forEach { jArray ->
                        count += jArray
                    }

                }
                if (count != gridSize) {
                    dialogMessage =
                        "Please place ${gridSize - count} more Queens"

                    isDialog = true
                    return@Button
                }
                val result = NQueenSolution.checkSolutions(grid, gridSize)
                println(
                    result
                )

                val grid2: ArrayList<ArrayList<Int>> = arrayListOf()

                repeat(gridSize) { ii ->
                    val oneRow = arrayListOf<Int>()
                    repeat(gridSize) { jj ->
                        oneRow.add(grid[ii][jj])
                    }
                    grid2.add(oneRow)
                }
                println(grid2.size)


                result?.forEach {
                    grid2[it.first][it.second] = 2
                }

                if (results.contains(grid.toSolution(gridSize))) {
                    dialogMessage = "Woo Hoo, You're correct"
                    isDialog = true
                } else {


                    dialogMessage = "Uh oh , Please try Again"


                    isDialog = true
                }

                grid = grid2


                println(grid.size)




                checkEnabled = false
            }, content = {
                Text(text = "Check")
            }, enabled = checkEnabled)

            Spacer(modifier = Modifier.width(20.dp))

            Button(onClick = gotoSolution, content = {
                Text(text = "Goto Solutions")
            })

            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {
                println("grisSzie" + grid.size)
                val grid2: ArrayList<ArrayList<Int>> = arrayListOf()
                repeat(gridSize) {
                    val oneRow = arrayListOf<Int>()
                    repeat(gridSize) {
                        oneRow.add(0)
                    }
                    grid2.add(oneRow)
                }
                grid.clear()
                grid = grid2
                println(grid.size)

                checkEnabled = true
                isDialog = false
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

private fun printGrid(grid: ArrayList<ArrayList<Int>>) {
    grid.forEach {
        it.forEach { num ->
            print("$num ,")
        }
        println()
    }
}

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
                            mutableStateOf(White)
                        }

                        GridItemInteractive(
                            i = i, j = j,
                            onBlockClicked = onBlockClicked,
                            grid = grid,
                            gridSize = gridSize,
                            undo = undo,
                            blockColor = blockColor
                        ) {
                            blockColor = it
                        }
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
    gridSize: Int,
    undo: (Int, Int) -> Unit,
    blockColor: Color,
    onColorChange: (Color) -> Unit
) {


    Box(modifier = Modifier
        .background(
            if (grid.isNotEmpty()) {
                when {
                    grid[j][i] == 1 -> Green
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
        .border(1.dp, Green)
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

