package com.fangelo.rawassets.tasks.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

public class LpcTile {

	static public int TILE_SIZE = 32;

	public String name;
	public int tileX;
	public int tileY;
	public Pixmap image;

	public LpcTile(String name, int tileX, int tileY) {
		this.name = name;
		this.tileX = tileX;
		this.tileY = tileY;
	}

	public void extractFrom(Pixmap bigImage, int offsetX, int offsetY) {
		image = new Pixmap(TILE_SIZE, TILE_SIZE, bigImage.getFormat());
		image.drawPixmap(bigImage, 0, 0, (offsetX + tileX) * TILE_SIZE, (offsetY + tileY) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}

	public void export(String targetFolder, String targetFileNamePrefix) {
		String fileName = getFileName(targetFolder, targetFileNamePrefix);
		PixmapIO.writePNG(Gdx.files.local(fileName), image);
	}

	private String getFileName(String targetFolder, String targetFileNamePrefix) {
		return targetFolder + targetFileNamePrefix + "-" + name + ".png";
	}
}
