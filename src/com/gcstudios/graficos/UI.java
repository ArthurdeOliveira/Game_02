package com.gcstudios.graficos;


import java.awt.Color;
import java.awt.Graphics;

import com.gcstudios.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(Game.newfont);	
 		g.drawString("Frutas:"+ Game.frutasAtual+"/"+Game.frutasContagem, 10, 25);
	}
	
}
