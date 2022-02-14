package com.github.arisaksen.simplektx.entity

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
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
    lateinit var tileOwner: TileOwner
    lateinit var debugTileName: String

    val bounds = Rectangle(x, y, size, size)

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    private fun updateBounds() = bounds.setPosition(x,y)

}

enum class TileOwner{
    PLAYERX,PLAYERO
}
