package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.gcstudios.world.World;
import com.gcstudios.main.Sound;
import com.gcstudios.main.Game;
import com.gcstudios.world.AStar;
import com.gcstudios.world.Vector2i;



public class Enemy extends Entity{
	
	private BufferedImage[] ghostSprite;
	
	private int frames = 0,maxFrames = 10,index = 0, maxIndex = 1;
	
	public boolean ghostEnding = false;
	public boolean ghostMode = false;
	public int ghostFrames = 0;
	public int nextTime = Entity.rand.nextInt(60*5 - 60*3) + 60*3;
	
	public Enemy(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		ghostSprite = new BufferedImage[2];
		
		
		ghostSprite[0] = Game.spritesheet.getSprite(32, 32, 16, 16);
		ghostSprite[1] = Game.spritesheet.getSprite(48, 32, 16, 16);
	}

	public void tick(){
		depth = 0;
		if(ghostMode == false) {
		if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
				Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
				path = AStar.findPath(Game.world, start, end);
				if (isCollidingWithPlayer())
					if(new Random().nextInt(100) < 100) {
						Sound.hurt.play();
						World.restartGame();
						
					//System.out.println(Game.player.life);
					}
			}
		
			if(new Random().nextInt(100) < 70)
				followPath(path);
			
			if(x % 16 == 0 && y % 16 == 0) {
				if(new Random().nextInt(100) <100) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
					path = AStar.findPath(Game.world, start, end);
				}
			}
			}
			
		
		if(ghostMode == true)
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index =0;
			}
			}
	
			
				ghostFrames++;
				
				if (ghostFrames == nextTime) {
					nextTime = Entity.rand.nextInt(60*5 - 60*3) + 60*3;
					ghostFrames = 0;
					if (ghostMode == false) {
						ghostMode = true;						
					}else {
						ghostMode = false;
					}
				}
			
	}
	public boolean isCollidingWithPlayer(){
		Rectangle enemyCurrent = new Rectangle(this.getX(), this.getY(), World.TILE_SIZE, World.TILE_SIZE);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), Game.player.getWidth(), Game.player.getHeight()); 
		return enemyCurrent.intersects(player);
	}
	
	

	
	public void render(Graphics g) {
		if(ghostMode ==false) {
		super.render(g);
	}else {
			g.drawImage(ghostSprite[index], this.getX(),this.getY(), null);

		}
		
		
		
	}
	
	
}
