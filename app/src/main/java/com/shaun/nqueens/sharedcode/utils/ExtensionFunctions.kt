package com.shaun.nqueens.sharedcode.utils

object ExtensionFunctions {

    fun clearList(
        gridSize: Int,
        grid: ArrayList<ArrayList<Int>>
    ): ArrayList<ArrayList<Int>> {
        val grid2: ArrayList<ArrayList<Int>> = arrayListOf()

        repeat(gridSize) { ii ->
            val oneRow = arrayListOf<Int>()
            repeat(gridSize) { jj ->
                oneRow.add(grid[ii][jj])
            }
            grid2.add(oneRow)
        }
        return grid2
    }

    fun Int.fillList(): ArrayList<ArrayList<Int>> {
        val grid2: ArrayList<ArrayList<Int>> = arrayListOf()

        repeat(this) { ii ->
            val oneRow = arrayListOf<Int>()
            repeat(this) { jj ->
                oneRow.add(0)
            }
            grid2.add(oneRow)
        }
        return grid2
    }

    fun ArrayList<ArrayList<Int>>.countElements(): Int {
        var count = 0
        this.forEach { iArray ->
            iArray.forEach { jArray ->
                count += jArray
            }

        }
        return count
    }
}