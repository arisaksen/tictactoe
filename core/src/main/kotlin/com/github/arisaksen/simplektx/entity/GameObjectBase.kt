package com.github.arisaksen.simplektx.entity

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle

abstract class GameObjectBase {

    var x: Float = 0f
        set(value) {
            field = value
            updateBounds()
        }
    var y: Float = 0f
        set(value) {
            field = value
            updateBounds()
        }

    var width: Float = 0f
        set(value) {
            field = value
            updateBounds()
        }

    var height: Float = 0f
        set(value) {
            field = value
            updateBounds()
        }

    abstract val bounds: Rectangle

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun setSize(width: Float, height: Float) {
        this.width = width
        this.height = height
    }

    open fun isCollidingWith(gameObjectBase: GameObjectBase) = Intersector.overlaps(gameObjectBase.bounds, bounds)

    // == private functions ==
    private fun updateBounds() = bounds.setPosition(x + width / 2f, y + height / 2)

}