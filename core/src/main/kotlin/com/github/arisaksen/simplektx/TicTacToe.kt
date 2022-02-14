package com.github.arisaksen.simplektx

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.github.arisaksen.simplektx.util.logger


class TicTacToe : Game() {

    companion object {
        @JvmStatic
        private val log = logger<TicTacToe>()
    }

    override fun create(){
        Gdx.app.logLevel = Application.LOG_DEBUG

        log.debug("Init GameScreen")
        setScreen(GameScreen())
    }


}