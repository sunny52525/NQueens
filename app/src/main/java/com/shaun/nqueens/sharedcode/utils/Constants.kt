package com.shaun.nqueens.sharedcode.utils

object Constants {
    val grids = listOf(4, 5, 6, 7, 8, 9, 10)


    fun getGrid(size:Int):ArrayList<ArrayList<Int>>{
        val grid: ArrayList<ArrayList<Int>> = arrayListOf()
        repeat(size) {
            val oneRow = arrayListOf<Int>()
            repeat(size) {
                oneRow.add(0)
            }
            grid.add(oneRow)
        }
      return  grid
    }


}