package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	
	private int frames = 0,maxFrames = 10,index = 0, maxIndex = 1;
	
	public boolean moved;
	
	public int lastDir = 1;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	
	public BufferedImage sprite_left, sprite_down,sprite_up; 

	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		sprite_left = Game.spritesheet.getSprite(48, 0, 16, 16);
		sprite_down = Game.spritesheet.getSprite(80, 0, 16, 16);
		sprite_up = Game.spritesheet.getSprite(64, 0, 16, 16);
		
		rightPlayer = new BufferedImage[2];
		leftPlayer = new BufferedImage[2];
		downPlayer = new BufferedImage[2];
		upPlayer = new BufferedImage[2];
		
		for(int i = 0; i < 2; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 , 0+ (i*16), 16, 16);
			}
		for(int i = 0; i < 2; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(48 , 0 +(i*16), 16, 16);
			} 
		for(int i = 0; i < 2; i++) {       
			downPlayer[i] = Game.spritesheet.getSprite(64 , 0+ (i*16), 16, 16 );
			} 
		for(int i = 0; i < 2; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(80, 0+ (i*16), 16, 16);
			}
		
	}
	
	public void tick(){
		depth = 1;
		moved = false;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			x+=speed;
			lastDir = 1;
			moved = true;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			x-=speed;
			lastDir = -1;
			moved = true;
		}
		else if(up && World.isFree(this.getX(),(int)(y-speed))){
			y-=speed;
			lastDir =2;
			moved = true;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))){
			y+=speed;
			lastDir =-2;
			moved = true;
			
		}
		
		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index =0;
				}
			}
		}
		
		verificarFruta();
		
		if(Game.frutasContagem == Game.frutasAtual) {
			System.out.println("Ganhamos o game!!!");
			if(World.level < 3) {
				World.level++;
			World.restartGame();
			
			return;
			}else {
				World.level = 1;
				World.restartGame();
				return;
			}
		}
	}
	
	public void verificarFruta() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Fruta) {
				if(Entity.isColidding(this, current)){
					Game.entities.remove(i);
					Game.frutasAtual++;
					//Game.frutasContagem--;
					return;
					
				}
			}
		}
	}

	public void render(Graphics g) {
		if(lastDir == 1) {
			g.drawImage(rightPlayer[index], this.getX(),this.getY(), null);
		}else if(lastDir == -1){
			g.drawImage(leftPlayer[index], this.getX(),this.getY(), null);
		}else if(lastDir == 2){
			g.drawImage(upPlayer[index], this.getX(),this.getY(), null);
		}else if(lastDir == -2){
			g.drawImage(downPlayer[index], this.getX(),this.getY(), null);
		}
		
	}
	


}
