package com.fangelo.rawassets.tasks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.fangelo.rawassets.tasks.utils.LpcTerrainSplitter;
import com.fangelo.rawassets.tasks.utils.LpcTilesSplitter;

public class BuildTilesAtlas extends AssetBuilderTask {

	private final String OUTPUT_FOLDER = "tiles/";

	@Override
	protected void onExecute() {
		unpackLpcTiles();

		buildAtlas();
	}

	private void unpackLpcTiles() {

		Gdx.files.local("tiles").emptyDirectory();

		unpackTerrain("lpc/tiles/TerrainAndOutside.png", "dirt", 0, 0);
		unpackTerrain("lpc/tiles/TerrainAndOutside.png", "grass", 0, 6);
		unpackTerrain("lpc/tiles/TerrainAndOutside.png", "water", 27, 0);
	}

	private void unpackTerrain(String fileName, String terrainName, int offsetX, int offsetY) {

		FileHandle fileHandle = Gdx.files.internal(fileName);

		LpcTilesSplitter splitter = new LpcTerrainSplitter();

		splitter.extractTiles(fileHandle, offsetX, offsetY);

		splitter.export(OUTPUT_FOLDER, terrainName);
	}

	private void buildAtlas() {
		TexturePacker.Settings settings = new TexturePacker.Settings();

		settings.fast = true;
		settings.grid = true;
		settings.paddingX = 2;
		settings.paddingY = 2;
		settings.useIndexes = false;
		settings.maxWidth = settings.maxHeight = 2048;
		settings.duplicatePadding = true;
		//settings.filterMag = Texture.TextureFilter.Linear;
		//settings.filterMin = Texture.TextureFilter.Linear;

		TexturePacker.process(settings, "tiles", REAL_ASSETS_PATH + "tiles", "tiles");
	}
}
