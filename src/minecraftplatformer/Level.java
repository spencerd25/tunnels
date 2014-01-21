package minecraftplatformer;

import java.awt.*;
import java.util.*;

public class Level
{
	public int worldW = 1000, worldH = 1000;
	public Block[][] block = new Block[worldW][worldH];

	public Level()
	{
		for (int x = 0; x < block.length; x++)
		{
			for (int y = 0; y < block[0].length; y++)
			{
				block[x][y] = new Block(new Rectangle(x * Tile.tileSize, y * Tile.tileSize, Tile.tileSize, Tile.tileSize), Tile.air);
			}
		}

		generateLevel();
	}

	public void generateLevel()
	{
		// Generation mountains, earth etc.
		for (int y = 0; y < block.length; y++)
		{
			for (int x = 0; x < block[0].length; x++)
			{
				if (y > worldH / 4)
				{
					if (new Random().nextInt(100) > 20)
					{
						try
						{
							if (block[x - 1][y - 1].id == Tile.earth)
							{
								block[x][y].id = Tile.earth;
							}
						}
						catch (Exception e)
						{
						}
					}
					if (new Random().nextInt(100) > 30)
					{
						try
						{
							if (block[x + 1][y - 1].id == Tile.earth)
							{
								block[x][y].id = Tile.earth;
							}
						}
						catch (Exception e)
						{
						}
					}

					try
					{
						if (block[x][y - 1].id == Tile.earth)
						{
							block[x][y].id = Tile.earth;
						}

					}
					catch (Exception e)
					{
					}

					if (new Random().nextInt(100) < 2)
					{
						block[x][y].id = Tile.earth;

					}
				}
			}
		}
		// placing out grass blocks.{
		for (int y = 0; y < block.length; y++)
		{
			for (int x = 0; x < block[0].length; x++)
			{
				if (block[x][y].id == Tile.earth && block[x][y - 1].id == Tile.air)
				{
					block[x][y].id = Tile.grass;
				}
			}
		}
	}

	public void building(int camX, int camY, int renW, int renH)
	{
		if (Component.isMouseLeft || Component.isMouseRight)
		{
			for (int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renW; x++)
			{
				for (int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renH; y++)
				{
					if (x >= 0 && y >= 0 && x < worldW && y < worldH)
					{
						if (block[x][y].contains(new Point(Component.mse.x / Component.pixelSize + (int) Component.sX, Component.mse.y / Component.pixelSize + (int) Component.sY)))
						{
							int sid[] = Inventory.invBar[Inventory.selected].id;
							if (Component.isMouseLeft)
							{
								block[x][y].id = Tile.air;
							}
							else if (Component.isMouseRight)
							{
								if (block[x][y].id == Tile.air)
								{
									if (sid == Tile.earth || sid == Tile.grass || sid == Tile.sand)
									{
										block[x][y].id = sid;

										if (block[x][y + 1].id == Tile.grass)
										{
											block[x][y + 1].id = Tile.earth;
										}
									}
								}
								break;
							}
						}
					}
				}
			}
		}
	}

	public void tick(int camX, int camY, int renW, int renH)
	{
		if (!Inventory.isOpen)
		{
			building(camX, camY, renW, renH);
		}
	}

	public void render(Graphics g, int camX, int camY, int renW, int renH)
	{
		for (int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renW; x++)
		{
			for (int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renH; y++)
			{
				if (x >= 0 && y >= 0 && x < worldW && y < worldH)
				{
					block[x][y].render(g);

					if (block[x][y].id != Tile.air && Inventory.isOpen)
					{
						if (block[x][y].contains(new Point(Component.mse.x / Component.pixelSize + (int) Component.sX, Component.mse.y / Component.pixelSize + (int) Component.sY)))
						{
							g.setColor(new Color(255, 255, 255, 60));
							g.fillRect(block[x][y].x - camX, block[x][y].y - camY, block[x][y].width, block[x][y].height);
						}
					}
				}
			}
		}
	}
}
