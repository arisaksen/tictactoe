package com.github.arisaksen.simplektx.entity

import com.badlogic.gdx.math.Rectangle

class Player : GameObjectBase() {

    companion object {
        const val SIZE = 1f
        const val JUMP_TIMER = 1f
        const val JUMP = 0.2f
    }

    override var bounds: Rectangle = Rectangle(x, y, SIZE, SIZE)

    fun centerPlayer(worldWidth: Float, worldHeight: Float) {
        this.x = worldWidth / 2f
        this.y = worldHeight / 2f
    }

    fun movePlayer(gravity: Float) {
        this.y -= gravity
    }
}