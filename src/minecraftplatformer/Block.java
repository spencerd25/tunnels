package minecraftplatformer;

import java.awt.*;

public class Block extends Rectangle
{
	private static final long serialVersionUID = 1L;

	public int[] id =
	{ -1, -1 };

	public Block(Rectangle size, int[] id)
	{
		setBounds(size);
		this.id = id;
	}

	public void CollidingwithBlock(Point point, Point point2)
	{
		for (int x = (int) (this.x / Tile.tileSize); x < (int) (this.x / Tile.tileSize + 3); x++)
		{
			for (int y = (int) (this.y / Tile.tileSize); y < (int) (this.y / Tile.tileSize + 3); y++)
			{
				if (x >= 0 && y >= 0 && x < Component.level.block.length && y < Component.level.block[0].length)
					if (Component.level.block[x][y].id != Tile.air)
					{
						Point pt1 = null;
						Point pt2 = null;
						if (Component.level.block[x][y].contains(pt1) || Component.level.block[x][y].contains(pt2))
						{
					}
				}
			}
		}
	}

	public void render(Graphics g)
	{
		if (id != Tile.air)
		{
			g.drawImage(Tile.tileset_terrain, x - (int) Component.sX, y - (int) Component.sY, x + width - (int) Component.sX, y + height - (int) Component.sY, id[0] * Tile.tileSize, id[1] * Tile.tileSize, id[0] * Tile.tileSize + Tile.tileSize, id[1] * Tile.tileSize
					+ Tile.tileSize, null);
		}
	}
}
