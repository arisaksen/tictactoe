package com.github.arisaksen.simplektx.util

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

// private val gameplayAtlas = assetManager[AssetDescriptors.GAMEPLAY]

//inline operator fun TextureAtlas.invoke(regionName: String): TextureRegion? = findRegion(regionName)
// private val obstacleTexture = gameplayatlas(RegionNames.OBSTACLE)

inline operator fun TextureAtlas.get(regionName: String): TextureRegion? = findRegion(regionName)
// private val obstacleTexture = gameplayAtlas[RegionNames.OBSTACLE]