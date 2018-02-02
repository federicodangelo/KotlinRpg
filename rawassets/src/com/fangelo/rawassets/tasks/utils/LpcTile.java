package com.fangelo.rawassets.tasks.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

public class LpcTile {

	private static int TILE_SIZE = 32;

	private String name;
	private Pixmap image;

	private int fromX;
	private int fromY;
	private int width;
	private int height;

	LpcTile(String name, int tileX, int tileY) {
		this.name = name;
		this.fromX = tileX * TILE_SIZE;
		this.fromY = tileY * TILE_SIZE;
		this.width = this.height = TILE_SIZE;
	}

	public LpcTile(String name, int fromX, int fromY, int width, int height) {
		this.name = name;
		this.fromX = fromX;
		this.fromY = fromY;
		this.width = width;
		this.height = height;
	}

	public void extractFrom(Pixmap bigImage, int offsetX, int offsetY) {
		image = new Pixmap(width, height, bigImage.getFormat());
		image.drawPixmap(bigImage, 0, 0, fromX + offsetX, fromY + offsetY, width, height);
	}

	public void export(String targetFolder, String targetFileNamePrefix) {
		String fileName = getFileName(targetFolder, targetFileNamePrefix);
		PixmapIO.writePNG(Gdx.files.local(fileName), image);
	}

	private String getFileName(String targetFolder, String targetFileNamePrefix) {
		if (targetFileNamePrefix.length() > 0) {
			return targetFolder + targetFileNamePrefix + "-" + name + ".png";
		} else {
			return targetFolder + name + ".png";
		}
	}
}
