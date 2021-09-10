package com.shaun.nqueens

import Heading
import MainScreen
import MainScreenInteractive
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    val state =
                        rememberTransformableState { zoomChange, panChange, rotationChange ->
                            scale *= zoomChange
                        }
                    val scales by animateFloatAsState(targetValue = scale)
                    var offsetX by remember { mutableStateOf(0f) }
                    var offsetY by remember { mutableStateOf(0f) }
                    Box(
                        modifier = Modifier
                            .animateContentSize()
                          ) {
                        AnimatedVisibility(gameVisible, exit = fadeOut(), enter = fadeIn()) {

                            Column {
                                Spacer(modifier = Modifier.height(20.dp))
                                Heading(gridSize)
                                MainScreenInteractive(gridSize, onGridChange = {
                                    gridSize = it
                                }, gotoSolution = {
                                    gameVisible = false
                                }, scales = scales, state = state,offsetX,offsetY){x,y->
                                    offsetX += x
                                    offsetY += y
                                }
                            }
                        }

                        AnimatedVisibility(!gameVisible, exit = fadeOut(), enter = fadeIn()) {
                            Column {
                                Spacer(modifier = Modifier.height(20.dp))
                                Heading(gridSize)
                                MainScreen(gridSize, onGridChange = {
                                    gridSize = it
                                }, gotoGame = {
                                    gameVisible = true
                                }, scales = scales, state = state,offsetX,offsetY){x,y->
                                    offsetX += x
                                    offsetY += y
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val gradient = Brush.verticalGradient(0f to Color.Gray, 1000f to Color.White)
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Column {
            repeat(16) {
                Box(
                    modifier = Modifier
                        .height(128.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        "Scroll here",
                        modifier = Modifier
                            .border(12.dp, Color.DarkGray)
                            .background(brush = gradient)
                            .padding(24.dp)
                            .height(150.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NQueensTheme {
        Greeting("Android")
    }
}