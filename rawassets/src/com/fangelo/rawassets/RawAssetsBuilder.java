package com.fangelo.rawassets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class RawAssetsBuilder {

	static private final String REAL_ASSETS_PATH = "../../android/assets/";

	public static void main(String[] args) {
		System.out.println("packaging assets!");

		packUITextures();

		packDefaultTextures();
	}

	private static void packUITextures() {

		TexturePacker.Settings settings = new TexturePacker.Settings();

		//settings.fast = true;
		//settings.grid = true;
		settings.paddingX = 2;
		settings.paddingY = 2;
		//settings.useIndexes = false;
		//settings.maxWidth = settings.maxHeight = 1024;
		settings.duplicatePadding = true;
		settings.filterMag = Texture.TextureFilter.Linear;
		settings.filterMin = Texture.TextureFilter.Linear;

		TexturePacker.process(settings, "ui", REAL_ASSETS_PATH + "ui", "ui");
	}

	private static void packDefaultTextures() {

		TexturePacker.Settings settings = new TexturePacker.Settings();

		settings.fast = true;
		settings.grid = true;
		settings.paddingX = 2;
		settings.paddingY = 2;
		settings.useIndexes = false;
		settings.maxWidth = settings.maxHeight = 1024;
		settings.duplicatePadding = true;
		settings.filterMag = Texture.TextureFilter.Linear;
		settings.filterMin = Texture.TextureFilter.Linear;

		TexturePacker.process(settings, "images", REAL_ASSETS_PATH + "images", "images");
	}
}
