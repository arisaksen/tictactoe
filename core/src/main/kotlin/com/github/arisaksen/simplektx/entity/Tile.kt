package com.github.arisaksen.simplektx.entity

import com.badlogic.gdx.math.Rectangle
import com.github.arisaksen.simplektx.PlayerTurn
import com.github.arisaksen.simplektx.config.GameConfig


class Tile {

    var x = 0f
        set(value) {
            field = value
            updateBounds()
        }
    var y = 0f
        set(value) {
            field = value
            updateBounds()
        }

    var size = GameConfig.TILE_SIZE
    var tileOwner: TileOwner = TileOwner.NOTSET
    lateinit var debugTileName: String

    val bounds = Rectangle(x, y, size, size)

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    private fun updateBounds() = bounds.setPosition(x, y)

    fun setTile(playerTurn: PlayerTurn) {
        tileOwner = when (playerTurn) {
            PlayerTurn.PLAYERX -> TileOwner.PLAYERX
            PlayerTurn.PLAYERO -> TileOwner.PLAYERO
        }
    }
}

enum class TileOwner {
    PLAYERX, PLAYERO, NOTSET
}
