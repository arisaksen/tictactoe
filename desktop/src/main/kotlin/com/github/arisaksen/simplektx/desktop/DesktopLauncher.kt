package com.github.arisaksen.simplektx.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.github.arisaksen.simplektx.TicTacToe

// if error class not found. Uncomment class DesktopLaunder
// class DesktopLauncher {}

fun main(args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.title = "TicTacToe"
    config.width = 1792
    config.height = 1008

    LwjglApplication(TicTacToe(), config)
}