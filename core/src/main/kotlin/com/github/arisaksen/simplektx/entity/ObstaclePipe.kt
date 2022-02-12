package com.github.arisaksen.simplektx.entity

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle


class ObstaclePipe : GameObjectBase() {

    companion object {
        const val OBSTACLE_WIDTH = 2f
        const val OBSTACLE_SPAWN_TIME = 2.5f
        const val OBSTACLE_SPEED = 0.1f
        const val MIN_OBSTACLE_HEIGHT = 2f
    }

    override val bounds: Rectangle = Rectangle(x,y, width, height)

    private var xSpeed = OBSTACLE_SPEED

    fun moveRect() {
        x -= xSpeed
    }


}