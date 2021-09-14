package com.shaun.nqueens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.shaun.nqueens.sharedcode.components.Heading
import com.shaun.nqueens.sharedcode.screens.MainScreen
import com.shaun.nqueens.sharedcode.screens.MainScreenInteractive
import com.shaun.nqueens.ui.theme.NQueensTheme

@ExperimentalFoundationApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NQueensTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    var gameVisible by remember {
                        mutableStateOf(true)
                    }

                    var gridSize by remember {
                        mutableStateOf(8)
                    }

                    var scale by remember { mutableStateOf(1f) }
                    var offset by remember { mutableStateOf(Offset.Zero) }

                    val state =
                        rememberTransformableState { zoomChange, panChange, _ ->
                            scale *= zoomChange
                            offset += panChange
                        }
                    val scales by animateFloatAsState(targetValue = scale)
                    Column {

                        Spacer(modifier = Modifier.height(20.dp))
                        Heading(gridSize)
                        GameScreen(
                            gameVisible,
                            gridSize,
                            scales,
                            state,
                            offset,
                            onGameVisibleChange = {
                                gameVisible = it
                            },
                            onGridSizeChange = {
                                gridSize = it
                            })
                    }

                }
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
private fun GameScreen(
    gameVisible: Boolean,
    gridSize: Int,
    scales: Float,
    state: TransformableState,
    offset: Offset,
    onGameVisibleChange: (Boolean) -> Unit,
    onGridSizeChange: (Int) -> Unit
) {

    Box(
        modifier = Modifier
            .animateContentSize()
    ) {
        AnimatedVisibility(gameVisible, exit = fadeOut(), enter = fadeIn()) {

            Column {

                MainScreenInteractive(gridSize, onGridChange = {
                    onGridSizeChange(it)
                }, gotoSolution = {
                    onGameVisibleChange(false)
                }, scales = scales, state = state, offset = offset)
            }
        }

        AnimatedVisibility(gameVisible.not(), exit = fadeOut(), enter = fadeIn()) {
            Column {

                MainScreen(gridSize, onGridChange = {
                    onGridSizeChange(it)
                }, gotoGame = {
                    onGameVisibleChange(true)
                }, scales = scales, state = state, offset = offset)
            }
        }
    }
}
