package com.loloof64.simple_tic_tac_toe

import javafx.application.Application
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tornadofx.*

enum class CellValue {
    no_player, cross, circle
}

object GridLogic {
    var currentPlayer = CellValue.cross
    fun togglePlayer() {
        currentPlayer = when(currentPlayer){
            CellValue.cross -> CellValue.circle
            CellValue.circle -> CellValue.cross
            else -> CellValue.no_player
        }
    }
}

fun main(args: Array<String>){
    Application.launch(SimpleTicTacToe::class.java, *args)
}

class SimpleTicTacToe:App(SimpleTicTacToeGrid::class)

class SimpleTicTacToeGrid : View("Simple Tic Tac Toe") {

    override val root = gridpane {
        style = "-fx-background-color:#88ffff; -fx-opacity:1;"
        row {
            ticTacToeCell {  }
            ticTacToeCell {  }
            ticTacToeCell {  }
        }
        row {
            ticTacToeCell {  }
            ticTacToeCell {  }
            ticTacToeCell {  }
        }
        row {
            ticTacToeCell {  }
            ticTacToeCell {  }
            ticTacToeCell {  }
        }
    }

    fun Pane.ticTacToeCell(op: (Button.() -> Unit)? = null) =
            opcr(this, tornadofx.find(SimpleTicTacToeCell::class).root, op)


}

class SimpleTicTacToeCell : ItemFragment<CellValue>() {

    private val associatedPlayerProperty = SimpleIntegerProperty(CellValue.no_player.ordinal)
    var associatedPlayer by associatedPlayerProperty

    override val root = button {
        prefWidth = 60.0
        prefHeight = 60.0
        graphic = group {
            rectangle {
                width = 60.0
                height = 60.0
                arcWidth = 15.0
                arcHeight = 15.0
                stroke = Color.BLACK
                fill = Color.WHITE
                strokeWidth = 5.0
            }

            group {
                line {
                    startX = 10.0
                    startY = 10.0
                    endX = 50.0
                    endY = 50.0
                    stroke = Color.BLUE
                    strokeWidth = 3.0
                }
                line {
                    startX = 10.0
                    startY = 50.0
                    endX = 50.0
                    endY = 10.0
                    stroke = Color.BLUE
                    strokeWidth = 3.0
                }

                visibleWhen {
                    associatedPlayerProperty.booleanBinding{ it == CellValue.cross.ordinal }
                }
            }

            circle {
                centerX = 30.0
                centerY = 30.0
                radius = 20.0
                stroke = Color.RED
                fill = Color.WHITE
                strokeWidth = 3.0

                visibleWhen {
                    associatedPlayerProperty.booleanBinding{ it == CellValue.circle.ordinal }
                }
            }
        }

        setOnMouseClicked {
            if (associatedPlayer == CellValue.no_player.ordinal){
                associatedPlayer = GridLogic.currentPlayer.ordinal
                GridLogic.togglePlayer()
            }
        }
    }
}
