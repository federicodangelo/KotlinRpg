package com.fangelo.rawassets.tasks.utils;

import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;

public class LpcTilesSplitter {

	private List<LpcTile> lpcTiles;

	public LpcTilesSplitter(List<LpcTile> lpcTiles) {
		this.lpcTiles = lpcTiles;
	}

	public void extractTiles(FileHandle lpcFileHandle, int offsetX, int offsetY) {
		Pixmap image = new Pixmap(lpcFileHandle);
		for (LpcTile lpcTile : lpcTiles) {
			lpcTile.extractFrom(image, offsetX, offsetY);
		}
	}

	public void export(String targetFolder, String targetFileNamePrefix) {
		for (LpcTile lpcTile : lpcTiles) {
			lpcTile.export(targetFolder, targetFileNamePrefix);
		}
	}
}
