package com.fangelo.rawassets.tasks;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.fangelo.rawassets.tasks.utils.LpcTile;
import com.fangelo.rawassets.tasks.utils.LpcTilesSplitter;

public class BuildItemsAtlas extends AssetBuilderTask {

	private final String OUTPUT_FOLDER = "items/";

	private final List<LpcTile> itemTiles = new ArrayList<>();

	@Override
	protected void onExecute() {
		unpackLpcTiles();

		buildAtlas();
	}

	private void unpackLpcTiles() {

		Gdx.files.local("items").emptyDirectory();

		addItem("rock1", 960, 576, 32, 32);
		addItem("rock2", 960, 544, 32, 32);

		unpackItems("lpc/tiles/TerrainAndOutside.png");

		clearItems();

		addItem("tree-trunk", 17, 448, 64, 73);

		addItem("tree-leaves", 0, 224, 32 * 3, 80);

		unpackItems("lpc/tiles/OutsideObjects.png");

	}

	private void clearItems() {
		itemTiles.clear();
	}

	private void addItem(String name, int fromX, int fromY, int width, int height) {
		itemTiles.add(new LpcTile(name, fromX, fromY, width, height));
	}

	private void unpackItems(String fileName) {

		FileHandle fileHandle = Gdx.files.internal(fileName);

		LpcTilesSplitter splitter = new LpcTilesSplitter(itemTiles);

		splitter.extractTiles(fileHandle, 0, 0);

		splitter.export(OUTPUT_FOLDER, "");
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

		TexturePacker.process(settings, "items", REAL_ASSETS_PATH + "items", "items");
	}
}
