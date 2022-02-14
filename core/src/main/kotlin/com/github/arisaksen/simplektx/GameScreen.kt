package com.github.arisaksen.simplektx

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.arisaksen.simplektx.config.GameConfig
import com.github.arisaksen.simplektx.config.GameConfig.WORLD_HEIGHT
import com.github.arisaksen.simplektx.config.GameConfig.WORLD_WIDTH
import com.github.arisaksen.simplektx.config.GameConfig.debugMode
import com.github.arisaksen.simplektx.entity.Tile
import com.github.arisaksen.simplektx.entity.TileOwner
import com.github.arisaksen.simplektx.util.GdxArray
import com.github.arisaksen.simplektx.util.clearScreen
import com.github.arisaksen.simplektx.util.debug.DebugCameraController
import com.github.arisaksen.simplektx.util.drawGrid
import com.github.arisaksen.simplektx.util.logger

class GameScreen : Screen {

    companion object {
        @JvmStatic
        private val log = logger<GameScreen>()
    }

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer
    private val debugCameraController = DebugCameraController().apply {
        setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    private var playerTurn = PlayerTurn.PLAYERX
    private var tileArray = GdxArray<Tile>()

    override fun show() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        camera = OrthographicCamera()
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()

        for (i in 0..2) {
            for (y in 0..2) {
                val tile = Tile().apply {
                    setPosition(17f + y * 6f, 17f - i * 6f)
                    debugTileName = "$i$y"
                }
                tileArray.add(tile)
            }
        }

    }

    override fun render(delta: Float) {
        renderer.projectionMatrix = camera.combined
        clearScreen()

        drawBoard()

        if (debugMode) {
            debugCameraController.handleDebugInput()
            debugCameraController.applyTo(camera)
            debugGame()
        }

        playerTurn()
        drawPlayerMark()

    }

    private fun drawPlayerMark() {
        tileArray.forEach {
            if (it.tileOwner == TileOwner.PLAYERX) {
                renderer.begin(ShapeRenderer.ShapeType.Line)
                renderer.color = Color.WHITE
                renderer.line(it.x, it.y, it.x + it.size, it.y + it.size)
                renderer.line(it.x + it.size, it.y, it.x, it.y + it.size)
                renderer.end()
            } else if (it.tileOwner == TileOwner.PLAYERO) {
                renderer.begin(ShapeRenderer.ShapeType.Line)
                renderer.color = Color.WHITE
                renderer.circle(it.x + it.size / 2f, it.y + it.size / 2f, it.size / 2f)
                renderer.end()
            }
        }
    }

    private fun playerTurn() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            val justClicked = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            camera.unproject(justClicked)
            tileArray.forEach {
                if (it.bounds.contains(justClicked.x, justClicked.y)) {
                    if (debugMode) {
                        log.debug("clicked ${it.debugTileName}")
                    }
                    it.setTile(playerTurn)

                    playerTurn = when (playerTurn) {
                        PlayerTurn.PLAYERX -> PlayerTurn.PLAYERO
                        PlayerTurn.PLAYERO -> PlayerTurn.PLAYERX
                    }
                }
            }
        }
    }

    private fun debugGame() {
        viewport.drawGrid(renderer)
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.color = Color.BLUE
        tileArray.forEach {
            renderer.rect(it.x, it.y, it.size, it.size)
        }
        renderer.end()
    }

    private fun drawBoard() {
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.color = Color.RED

        // Vertical lines
        renderer.line(WORLD_WIDTH / 2f + GameConfig.TILE_HALF_SIZE,
            5f,
            WORLD_WIDTH / 2f + GameConfig.TILE_HALF_SIZE,
            23f)
        renderer.line(WORLD_WIDTH / 2f - GameConfig.TILE_HALF_SIZE,
            5f,
            WORLD_WIDTH / 2f - GameConfig.TILE_HALF_SIZE,
            23f)

        // Hor
        renderer.line(17f,
            WORLD_HEIGHT / 2f + GameConfig.TILE_HALF_SIZE,
            WORLD_WIDTH - 17f,
            WORLD_HEIGHT / 2f + GameConfig.TILE_HALF_SIZE)
        renderer.line(17f,
            WORLD_HEIGHT / 2f - GameConfig.TILE_HALF_SIZE,
            WORLD_WIDTH - 17f,
            WORLD_HEIGHT / 2f - GameConfig.TILE_HALF_SIZE)


        renderer.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
    }

}

enum class PlayerTurn {
    PLAYERX, PLAYERO
}
