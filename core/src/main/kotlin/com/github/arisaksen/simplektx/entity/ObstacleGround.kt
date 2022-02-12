package com.github.arisaksen.simplektx.entity

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.github.arisaksen.simplektx.entity.ObstaclePipe.Companion.OBSTACLE_SPEED


class ObstacleGround : GameObjectBase() {

    override val bounds: Rectangle = Rectangle(x,y, width, height)

}