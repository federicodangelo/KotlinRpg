package com.fangelo.rawassets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

public class LpcPlayerSplitter {

	public final int ROTATION_NORTH = 0;
	public final int ROTATION_WEST = 1;
	public final int ROTATION_SOUTH = 2;
	public final int ROTATION_EAST = 3;

	public final int TOTAL_ROTATIONS = 4;

	public final int ANIMATION_CAST = 0;
	public final int ANIMATION_THRUST = 1;
	public final int ANIMATION_WALK = 2;
	public final int ANIMATION_SLASH = 3;
	public final int ANIMATION_SHOOT = 4;
	public final int ANIMATION_HURT = 5;
	public final int TOTAL_ANIMATIONS = 6;

	public final int ANIMATION_CAST_FRAMES = 7;
	public final int ANIMATION_THRUST_FRAMES = 8;
	public final int ANIMATION_WALK_FRAMES = 9;
	public final int ANIMATION_SLASH_FRAMES = 6;
	public final int ANIMATION_SHOOT_FRAMES = 13;
	public final int ANIMATION_HURT_FRAMES = 6;

	public final int[] ANIMATION_FRAMES = new int[] { ANIMATION_CAST_FRAMES, ANIMATION_THRUST_FRAMES, ANIMATION_WALK_FRAMES, ANIMATION_SLASH_FRAMES,
			ANIMATION_SHOOT_FRAMES, ANIMATION_HURT_FRAMES };

	private final int PLAYER_WIDTH = 64;
	private final int PLAYER_HEIGHT = 64;

	public Pixmap[][] tiles;

	public LpcPlayerSplitter(FileHandle lpcFileHandle) {
		Pixmap lpcPlayer = new Pixmap(lpcFileHandle);

		int tilesWidth = lpcPlayer.getWidth() / PLAYER_WIDTH;
		int tilesHeight = lpcPlayer.getHeight() / PLAYER_HEIGHT;

		tiles = new Pixmap[tilesHeight][];

		for (int y = 0; y < tilesHeight; y++) {
			tiles[y] = new Pixmap[tilesWidth];

			for (int x = 0; x < tilesWidth; x++) {
				Pixmap tile = new Pixmap(PLAYER_WIDTH, PLAYER_HEIGHT, lpcPlayer.getFormat());

				tile.drawPixmap(lpcPlayer, 0, 0, x * PLAYER_WIDTH, y * PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT);

				tiles[y][x] = tile;
			}
		}

		lpcPlayer.dispose();
	}

	public void exportImagesTo(String targetFolder, String targetFileNamePrefix) {

		for (int animation = 0; animation < TOTAL_ANIMATIONS; animation++) {
			exportTiles(targetFolder, targetFileNamePrefix, animation, ANIMATION_FRAMES[animation]);
		}

	}

	private void exportTiles(String targetFolder, String targetFileNamePrefix, int animation, int frames) {

		int rotations = TOTAL_ROTATIONS;
		if (animation == ANIMATION_HURT) {
			rotations = 1;
		}

		for (int rot = 0; rot < rotations; rot++) {

			Pixmap[] tiles = this.tiles[animation * TOTAL_ROTATIONS + rot];

			for (int frame = 0; frame < frames; frame++) {
				String fileName = getFileName(targetFolder, targetFileNamePrefix, animation, rot, frame);
				Pixmap tile = tiles[frame];

				PixmapIO.writePNG(Gdx.files.local(fileName), tile);
			}
		}
	}

	private String getFileName(String targetFolder, String targetFileNamePrefix, int animation, int rot, int frame) {

		String animationName = getAnimationName(animation);
		String rotationName = getRotationName(animation, rot);

		String fileName = targetFolder + targetFileNamePrefix + "-" + animationName + "-" + rotationName + "-" + frame + ".png";

		return fileName;

	}

	private String getRotationName(int animation, int rot) {
		if (animation == ANIMATION_HURT) {
			return "south";
		}

		switch (rot) {
			case ROTATION_NORTH:
				return "north";
			case ROTATION_SOUTH:
				return "south";
			case ROTATION_WEST:
				return "west";
			case ROTATION_EAST:
				return "east";
		}

		return "unknown";
	}

	private String getAnimationName(int animation) {
		switch (animation) {
			case ANIMATION_CAST:
				return "cast";
			case ANIMATION_THRUST:
				return "thrust";
			case ANIMATION_HURT:
				return "hurt";
			case ANIMATION_SHOOT:
				return "shoot";
			case ANIMATION_SLASH:
				return "slash";
			case ANIMATION_WALK:
				return "walk";
		}

		return "unknown";
	}

}
