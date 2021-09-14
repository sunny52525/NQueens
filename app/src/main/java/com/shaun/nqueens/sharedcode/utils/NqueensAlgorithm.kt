package com.shaun.nqueens.sharedcode.utils

class NQueenSolution {
    fun solveBoard(
        board: Array<CharArray>, row: Int,
        rowmask: Int, ldmask: Int,
        rdmask: Int
    ) {
        val n = board.size


        val all_rows_filled = (1 shl n) - 1

        if (rowmask == all_rows_filled) {
            val v: ArrayList<Int> = ArrayList()
            for (i in 0 until n) {
                for (j in 0 until n) {
                    if (board[i][j] == 'Q') v.add(j + 1)
                }
            }
            result.add(v)
            return
        }


        var safe = (all_rows_filled
                and (rowmask or ldmask or rdmask).inv())
        while (safe > 0) {

            val p = safe and -safe
            val col = (Math.log(p.toDouble()) / Math.log(2.0)).toInt()
            board[row][col] = 'Q'

            solveBoard(
                board, row + 1, rowmask or p,
                ldmask or p shl 1,
                rdmask or p shr 1
            )


            safe = safe and safe - 1

            board[row][col] = ' '
        }
    }


    companion object {

        fun ArrayList<ArrayList<Int>>.toSolution(N: Int): ArrayList<Int> {
            val result: ArrayList<Int> = arrayListOf()
            repeat(N) {
                result.add(0)
            }

            for (i in 0 until N) {

                for (j in 0 until N) {
                    if (this[j][i] == 1) {
                        result[i] = j + 1
                    }
                }

            }

            return result

        }

        fun checkSolutions(board: ArrayList<ArrayList<Int>>, N: Int): List<Pair<Int, Int>>? {

            val result: ArrayList<Pair<Int, Int>>? = arrayListOf()


            board.forEachIndexed { i, iArray ->
                iArray.forEachIndexed { j, jArray ->
                    if (jArray == 1) {

                        //horizontal

                        for (k in 0 until N) {

                            if (board[i][k] == 1) {
                                if (j != k)
                                    result?.add(Pair(i, j))
                            }

                        }

                        //vertical
                        for (k in 0 until N) {

                            if (board[k][j] == 1) {
                                if (i != k)
                                    result?.add(Pair(i, j))
                            }

                        }


                        //diagonal left
                        var ii = i
                        var jj = j
                        while (ii >= 0 && jj >= 0) {

                            if (board[ii][jj] == 1) {

                                if (ii != i && jj != j) {
                                    result?.add(Pair(ii, jj))
                                }
                            }
                            ii--
                            jj--
                        }
                        ii = i
                        jj = j
                        while (ii < N && jj < N) {
                            if (board[ii][jj] == 1) {

                                if (ii != i && jj != j) {
                                    result?.add(Pair(ii, jj))
                                }
                            }
                            ii++
                            jj++
                        }


                        //diagonal right

                        ii = i
                        jj = j
                        while (jj >= 0 && ii < N) {
                            if (board[ii][jj] == 1) {
                                if (ii != i && jj != j) {
                                    result?.add(Pair(ii, jj))
                                }

                            }
                            ii++
                            jj--
                        }

                        ii = i
                        jj = j
                        while (ii >= 0 && jj < N) {
                            if (board[ii][jj] == 1) {
                                if (ii != i && jj != j) {
                                    result?.add(Pair(ii, jj))
                                }
                            }
                            ii--
                            jj++
                        }
                    }
                }
            }


            return result?.distinct()
        }

//
//        fun canCheck(board: ArrayList<ArrayList<Int>>, N: Int): Boolean {
//
//            for (i in 0 until N) {
//                var contains = false
//                for (j in 0 until N) {
//                    if (board[i][j] == 1)
//                        contains = true
//                }
//
//                if (!contains)
//                    return false
//            }
//
//            return true
//        }
//
//        fun isSafe(board: ArrayList<ArrayList<Int>>, col: Int, row: Int, N: Int): Boolean {
//
//            println("i $row , j $col ")
//            board.forEach {
//                it.forEach { num ->
//                    print("$num ,")
//                }
//                println()
//            }
//            println()
//            println()
//            /* Check this row on left side */
//
//            for (i in 0 until N) {
//                if (board[row][i] == 1)
//                    return false
//            }
//
//            for (i in 0 until N) {
//                if (board[i][col] == 1)
//                    return false
//            }
//
//
//            /* Check upper diagonal on left side */
//            var i = row
//            var j: Int = col
//            while (i >= 0 && j >= 0) {
//                if (board[i][j] == 1) return false
//                i--
//                j--
//            }
//            i = row
//            j = col
//            while (i < N && j < N) {
//                if (board[i][j] == 1) return false
//                i++
//                j++
//            }
//            /* Check lower diagonal on left side */
//            i = row
//            j = col
//            while (j >= 0 && i < N) {
//                if (board[i][j] == 1) return false
//                i++
//                j--
//            }
//
//            i = row
//            j = col
//
//            while (i >= 0 && j < N) {
//                if (board[i][j] == 1) return false
//                i--
//                j++
//            }
//            return true
//        }

        var result: ArrayList<ArrayList<Int>> = arrayListOf()


        fun solve(n: Int): ArrayList<ArrayList<Int>> {

            val board = Array(n) { CharArray(n) }
            for (i in 0 until n) {
                for (j in 0 until n) {
                    board[i][j] = ' '
                }
            }
            val rowmask = 0
            val ldmask = 0
            val rdmask = 0
            val row = 0
            val solution = NQueenSolution()

            result.clear()
            solution.solveBoard(
                board, row, rowmask, ldmask,
                rdmask
            )
            return result
        }
    }
}