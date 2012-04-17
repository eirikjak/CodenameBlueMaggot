import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TimerTask;

import entity.Entity;


public abstract class BaseGame extends Canvas implements Runnable {

	private Thread runLoop;
	private BufferStrategy buffer;
	private boolean showFps = true;
	private long lastTime;
	
	
	public void init(double fps){
		setIgnoreRepaint(true);
		
		runLoop = new Thread();
		lastTime =System.currentTimeMillis();
		runLoop.start();
		
		
	}


	@Override
	public void run() {
		
		long deltaTime1 = System.currentTimeMillis() - lastTime;
		onUppdate(deltaTime1);
		long deltaTime2 = System.currentTimeMillis() - lastTime;
	
		
		if(deltaTime2 > 16) return;
		Renderer renderer = new Renderer();
		
		onDraw(renderer);	
		
		
		
		Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		
		
		for(Renderable renderable: renderer.getToBeDrawn()){
			Entity ent = renderable.getEnt();
			Point loc = ent.getLocation();
			g.drawImage(renderable.getImg(),loc.x , loc.y, ent.getXr(), ent.getYr(), Color.black, null);

		}
		
		
		if(showFps)
			DrawfpsCounter(g, deltaTime2);
		
		buffer.show();
		g.dispose();
		lastTime = System.currentTimeMillis();
	
		
		
	}
	
	private void DrawfpsCounter(Graphics2D g,long deltaTime){
		String fpsCounter = "FPS: " + (1000/(int)deltaTime);
		g.drawChars(fpsCounter.toCharArray(), 0, fpsCounter.length(), 10, 10);
	}
	
	public abstract void onUppdate(long deltaTime);
	public abstract void onDraw(Renderer renderer);
	
}
