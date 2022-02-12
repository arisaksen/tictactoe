package com.github.arisaksen.simplektx

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.github.arisaksen.simplektx.config.GameConfig.GROUND_HEIGHT
import com.github.arisaksen.simplektx.config.GameConfig.OBSTACLE_GAP
import com.github.arisaksen.simplektx.config.GameConfig.WORLD_HEIGHT
import com.github.arisaksen.simplektx.config.GameConfig.WORLD_WIDTH
import com.github.arisaksen.simplektx.entity.ObstacleGround
import com.github.arisaksen.simplektx.entity.ObstaclePipe
import com.github.arisaksen.simplektx.entity.Player
import com.github.arisaksen.simplektx.util.GdxArray
import com.github.arisaksen.simplektx.util.clearScreen
import com.github.arisaksen.simplektx.util.isKeyPressed
import com.github.arisaksen.simplektx.util.logger

class GameScreen : Screen {

    companion object {
        @JvmStatic
        private val log = logger<GameScreen>()
    }

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer
    private lateinit var ground: ObstacleGround

    private val player = Player().apply {
        centerPlayer(WORLD_WIDTH, WORLD_HEIGHT)
    }
    private var keyPressTimer = 0f
    private var worldGravity = 0f

    private var obstacleTimer = 0f
    private val obstacleArray = GdxArray<ObstaclePipe>()

    override fun show() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        camera = OrthographicCamera()
        viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()

        ground = ObstacleGround()
        ground.setPosition(0f, 0f)
        ground.setSize(WORLD_WIDTH, GROUND_HEIGHT)

    }

    override fun render(delta: Float) {
        clearScreen()


        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Filled)

        spawnNewPipes(delta)
        drawWorld()
        drawObstacles()
        drawPlayer()

        obstacleArray.forEach {
            if (it.isCollidingWith(player)) {
                log.debug("Collition pipe!")
            }
        }

        if (ground.isCollidingWith(player)) {
            log.debug("Collition ground!")
        }

        playerMovement(delta)
        moveObstacle()

        renderer.end()
//        viewport.drawGrid(renderer)
    }


    private fun moveObstacle() {
        obstacleArray.forEach {
            it.moveRect()
        }
    }

    private fun playerMovement(delta: Float) {
        if ((keyPressTimer >= Player.JUMP_TIMER) && (Input.Keys.SPACE.isKeyPressed())) {
            keyPressTimer = 0f
            worldGravity -= Player.JUMP
        } else {
            worldGravity += delta * 0.1f
            keyPressTimer += delta
        }

        player.movePlayer(worldGravity)
    }

    private fun drawPlayer() {
        renderer.color = Color.YELLOW
        renderer.circle(player.x, player.y, Player.SIZE)
    }

    private fun drawWorld() {
        renderer.color = Color.valueOf("50CEEC")
        renderer.rect(0f, 0f, WORLD_WIDTH, WORLD_HEIGHT)
        renderer.color = Color.BROWN
        renderer.rect(ground.x, ground.y, ground.width, ground.height)
    }

    private fun spawnNewPipes(delta: Float) {
        obstacleTimer += delta

        if (obstacleTimer >= ObstaclePipe.OBSTACLE_SPAWN_TIME) {
            obstacleTimer = 0f // reset timer

            val obstacleLower = ObstaclePipe()
            obstacleLower.setPosition(WORLD_WIDTH, 0f + GROUND_HEIGHT)
            val random = MathUtils.random.nextInt(10)
            obstacleLower.setSize(ObstaclePipe.OBSTACLE_WIDTH, ObstaclePipe.MIN_OBSTACLE_HEIGHT + random)
            obstacleArray.add(obstacleLower)

            val obstacleUpper = ObstaclePipe()
            obstacleUpper.setPosition(
                WORLD_WIDTH,
                GROUND_HEIGHT + obstacleLower.height + OBSTACLE_GAP
            )
            obstacleUpper.setSize(
                ObstaclePipe.OBSTACLE_WIDTH,
                WORLD_HEIGHT - GROUND_HEIGHT - obstacleLower.height - OBSTACLE_GAP
            )
            obstacleArray.add(obstacleUpper)
        }

    }

    private fun drawObstacles() {
        renderer.color = Color.LIME
        obstacleArray.forEach {
            renderer.rect(it.x, it.y, it.width, it.height)
        }
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