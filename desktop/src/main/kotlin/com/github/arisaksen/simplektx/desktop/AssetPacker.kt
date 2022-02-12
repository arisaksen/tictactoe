package com.github.arisaksen.simplektx.desktop

import com.badlogic.gdx.tools.texturepacker.TexturePacker

object AssetPacker {

    const val DRAW_DEBUG_OUTINE = false
    const val RAW_ASSETS_PATH = "desktop/assets-raw"
    const val ASSETS_PATH = "assets"
}

fun main(args: Array<String>) {
    val settings = TexturePacker.Settings().apply {
        debug = AssetPacker.DRAW_DEBUG_OUTINE
    }

    TexturePacker.process(
        settings,
        "${AssetPacker.RAW_ASSETS_PATH}/gameplay",
        "${AssetPacker.ASSETS_PATH}/gameplay",
        "gameplay"
    )
}