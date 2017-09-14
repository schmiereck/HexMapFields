/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.schmiereck.hexMapFields.Map.Orientation;

/**
 * <p>
 *	Main View.
 * </p>
 * 
 * @author smk
 * @version <p>08.05.2017:	created, smk</p>
 */
public class MainView
{
	//**********************************************************************************************
	// Constants:
	
	private static final Color RED = new Color(255, 32, 32);
	private static final Color GREEN = new Color(0, 192, 0);
	private static final Color BLUE = new Color(32, 32, 255);

	//**********************************************************************************************
	// Fields:
	
	private JFrame frame;
	
	private Map map;
	
	private int xSizeView;
	private int ySizeView;
	
	private JPanel drawPanel = null;
	
	private final int xOverlap = 1;
	private final int yOverlap = 0;

	private final double scaleTx;
	
	private final double xScaleTx;
	private final double yScaleTx;

	private final double xOffTx;
	private final double yOffTx;

	private final double xScale;
	private final double yScale;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 *
	 * @param map
	 * 			is the Map.
	 * @param xSizeView
	 * 			is the X-Size of View-Window in Pixels (Inbound).
	 * @param ySizeView
	 * 			is the X-Size of View-Window in Pixels (Inbound).
	 */
	public MainView(final Map map, final int xSizeView, final int ySizeView,
	                final ViewService viewService)
	{
		//==========================================================================================
		this.map = map;
		
		this.xSizeView = xSizeView;
		this.ySizeView = ySizeView;

		final int xSizeMap = map.getXSize();
		final int ySizeMap = map.getYSize();

		//------------------------------------------------------------------------------------------
		this.frame = new JFrame();
		
		this.frame.setTitle("Hex-Map Fields");
        
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		this.frame.setSize(this.xSizeView, this.ySizeView);
		
		this.frame.addKeyListener(new KeyListener() 
		{
		    public void keyPressed(final KeyEvent e) 
		    { 
		    	//System.out.println( "keyPressed"); 
		    }

		    public void keyReleased(final KeyEvent e) 
		    { 
		    	//System.out.println("keyReleased"); 
		    }

		    public void keyTyped(final KeyEvent e) 
		    { 
//		    	System.out.println("keyTyped:" + e.getKeyChar() + ", " + e.getKeyCode() + ", " + ((int)e.getKeyChar()) + ", " + e.getExtendedKeyCode()); 
		    
		    	switch (e.getKeyChar())//(e.getKeyCode())//(e.getKeyChar())//(e.getExtendedKeyCode())
		    	{
		    		case '+'://KeyEvent.VK_PLUS: // KeyEvent.VK_ADD
		    		{
		    			final long fpsView = MainService.getFpsView();
		    			final long fps;

		    			fps = fpsView * 2L;

		    			MainService.setFpsView(fps);
		    			break;
		    		}
		    		case KeyEvent.VK_MINUS:
		    		{
		    			final long fpsView = MainService.getFpsView();
		    			final long fps;
		    			if (fpsView >= 2L)
		    			{
		    				fps = fpsView / 2L;
		    			}
		    			else
		    			{
		    				fps = fpsView;
		    			}
		    			MainService.setFpsView(fps);
		    			break;
		    		}
		    		case '#':
		    		{
		    			MainService.setAsapCalc(!MainService.getAsapCalc());
		    			break;
		    		}
		    	}
		    }
		});
		
		//------------------------------------------------------------------------------------------
		
		// Scale of Drawing (line width, etc.).
		this.scaleTx = 1.0D;
		
		this.xScaleTx = scaleTx;
		this.yScaleTx = -scaleTx;

		// Offeset of Drawing.
		this.xOffTx = 20.0D;
		this.yOffTx = 20.0D;

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		{
			final double xScale = ((xSizeView - (this.xOffTx * 2))) / ((xSizeMap + this.xOverlap) * Map.s2Tri);
			final double yScale = ((ySizeView - (this.yOffTx * 2))) / ((ySizeMap + this.yOverlap) * Map.hTri);
			
			final double scaleFactor;
			
			if (xScale > yScale)
			{
				scaleFactor = yScale / this.scaleTx;
			}
			else
			{
				scaleFactor = xScale / this.scaleTx;
			}
			
			// Scale of MapFields (to fit the Map into the ContentPane).
			this.xScale = scaleFactor;
			this.yScale = scaleFactor;
		}
		//------------------------------------------------------------------------------------------
		final AffineTransform normTx = new AffineTransform();
//		normTx.translate(xOffTx, this.getHeight() - yOffTx);
//		normTx.scale(xScaleTx, yScaleTx); 
		
		//------------------------------------------------------------------------------------------
		this.drawPanel = new JPanel()
		{
			private final long NEAR_DIVISOR = 3L;
			private long lastTimeMillis = System.currentTimeMillis();
			private long lastFps = 0L;
			
			public void paintComponent(final Graphics g)
			{
				//----------------------------------------------------------------------------------
				final Graphics2D g2 = (Graphics2D) g;
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				g2.setColor(Color.LIGHT_GRAY);
				g2.fillRect(0, 0, 
				            this.getWidth(), this.getHeight());
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				final AffineTransform mapTx = new AffineTransform();
		        mapTx.translate(xOffTx, this.getHeight() - yOffTx);
		        mapTx.scale(xScaleTx, yScaleTx); 
				
				g2.setTransform(mapTx);

				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Set Stroke to Pixel width.
		        g2.setStroke(new BasicStroke(1));	
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		        drawMap(g2, MainView.this.map);

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		        g2.setTransform(normTx);

		        final Font currentFont = g2.getFont();
		        
		        final int currentFontSize = currentFont.getSize();
		        
		        // fps:
		        {
					final long timeMillis = System.currentTimeMillis();
					final long divTimeMillis = timeMillis - this.lastTimeMillis;
					this.lastTimeMillis = timeMillis;
					
					if (divTimeMillis > 0)
					{
						final long fps = (1000L * NEAR_DIVISOR) / divTimeMillis;
	
						if (this.lastFps > fps)
						{
							this.lastFps--;
						}
						else
						{
							if (this.lastFps < fps)
							{
								this.lastFps++;
							}
						}
					}
					else
					{
						this.lastFps++;
					}
					
					final String fpsViewStr;
					final String fpsStr = Long.toString(this.lastFps / NEAR_DIVISOR);
			        
			        if (MainService.getAsapCalc() == true)
			        {
			        	fpsViewStr = "ASAP";
			        }
			        else
			        {
			        	fpsViewStr = Long.toString(MainService.getFpsView());
			        }
		        
			        g2.drawString("fps: " + fpsStr + "/" + fpsViewStr, 
			                      20.0F, currentFontSize);
			        g2.drawString("[+ - #]", 
			                      20.0F, currentFontSize * 2);
		        }
		        // Rule-Set size:
		        {
			        g2.drawString("Rule-Sets: " + viewService.getRuleSetsSize(), 
			                      300.0F, currentFontSize);
		        }
		        // Run-Cnt:
		        {
			        g2.drawString("Run-Cnt: " + viewService.getRunCnt(), 
			                      600.0F, currentFontSize);
		        }
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				g2.dispose(); // release Graphics resources
				
				//----------------------------------------------------------------------------------
			}
		};
        
		this.drawPanel.setPreferredSize(new Dimension(this.xSizeView, this.ySizeView));
		this.frame.setContentPane(this.drawPanel);
		this.frame.pack();
		
		final Dimension panelSize = this.drawPanel.getSize();
		
		//==========================================================================================
	}
	
	public JPanel showView()
	{
		//==========================================================================================
		this.frame.setLocationRelativeTo(null);
		
		this.frame.setVisible(true);

		//==========================================================================================
		return this.drawPanel;
	}

	public void repaint()
	{
		//==========================================================================================
		this.drawPanel.repaint();
		
		//==========================================================================================
	}
	
	protected void drawMap(final Graphics2D g2, final Map map)
	{
		//==========================================================================================
		final int xSizeMap = map.getXSize();
		final int ySizeMap = map.getYSize();
		
		//------------------------------------------------------------------------------------------
		{
			final int xPos = (int)(0 * this.xScale);
			final int yPos = (int)(0 * this.yScale);
			final int xSize = (int)(((xSizeMap * Map.s2Tri) + (this.xOverlap * Map.s2Tri)) * this.xScale);
			final int ySize = (int)(((ySizeMap * Map.hTri) + (this.yOverlap * Map.hTri)) * this.yScale);
			
			g2.setColor(Color.WHITE);
			g2.fillRect(xPos, yPos, 
			            xSize, ySize);
		}
		//------------------------------------------------------------------------------------------
		Font currentFont = g2.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 3.2F);
		g2.setFont(newFont);
		
		//------------------------------------------------------------------------------------------
//		final List<MapField> mapFields = map.getMapFields();
//		
//		for (final MapField mapField : mapFields)
//		{
//			drawMapField(g2, map, mapField);
//		}
		final int xSize = map.getXSize();
		final int ySize = map.getYSize();
		
		for (int yPos = 0; yPos < ySize; yPos++)
		{
			for (int xPos = 0; xPos < xSize; xPos++)
			{
				//----------------------------------------------------------------------------------
				final MapField mapField = map.getMapField(xPos, yPos);
				
				drawMapField(g2, map, mapField);
				
				//----------------------------------------------------------------------------------
			}
		}
		//==========================================================================================
	}

	protected void drawMapField(final Graphics2D g2, final Map map, final MapField mapField)
	{
		//==========================================================================================
		final double xPosMapField = mapField.getXPos();
		final double yPosMapField = mapField.getYPos();
		
		final Orientation orientation = mapField.getOrientation();
		
		final double xm = (xPosMapField);
		final double ym = (yPosMapField);
		final double xRadius = Map.ymTri;
		final double yRadius = Map.ymTri;
		
		final Map.Tri oTri = Map.oTri[orientation.ordinal()];
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// From l to r.
		g2.setColor(MainView.RED);
		this.drawLine(g2, xm, ym, oTri.xa-oTri.xa*0.1, oTri.ya-oTri.ya*0.1, oTri.xb-oTri.xb*0.1, oTri.yb-oTri.yb*0.1);
		
		// From r to t.
		g2.setColor(MainView.GREEN);
		this.drawLine(g2, xm, ym, oTri.xb-oTri.xb*0.1, oTri.yb-oTri.yb*0.1, oTri.xc-oTri.xc*0.1, oTri.yc-oTri.yc*0.1);

		// From t to l.
		g2.setColor(MainView.BLUE);
		this.drawLine(g2, xm, ym, oTri.xc-oTri.xc*0.1, oTri.yc-oTri.yc*0.1, oTri.xa-oTri.xa*0.1, oTri.ya-oTri.ya*0.1);
	
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		
		// Prüfen ob der State aktiv ist und dann hervorheben.
		final StateNode caStateNode = mapField.getStateNode();
		final State caState = caStateNode.getState();

		final StateNode bcStateNode = caStateNode.getParentNode();
		final State bcState = bcStateNode.getState();
		
		final StateNode abStateNode = bcStateNode.getParentNode();
		final State abState = abStateNode.getState();
		
		// From Origin to lr (AB).
		this.drawState(g2, xm, ym, abState, oTri.xab, oTri.yab, MainView.RED);
		
		// From Origin to rt (BC).
		this.drawState(g2, xm, ym, bcState, oTri.xbc, oTri.ybc, MainView.GREEN);

		// From Origin to lt (CA).
		this.drawState(g2, xm, ym, caState, oTri.xca, oTri.yca, MainView.BLUE);

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		g2.setColor(Color.BLACK);
		// Inner-Circle.
		this.drawOval(g2,
		              xm, ym, 
		              xRadius, yRadius);

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//		final MetaEntry metaEntry = mapField.getMetaEntry();
//		
//		if (metaEntry != null)
//		{
//			this.drawString(g2,
//			                xm, ym,
//			                "(" + metaEntry.getLevelNo() + ")");
//		}
		//==========================================================================================
	}
	
	private void drawState(final Graphics2D g2, 
	                       double xm, double ym, 
	                       State state, 
	                       double xab, double yab, Color color)
	{
		//==========================================================================================
		if (state == Main.s0EdgeState)
		{
			g2.setColor(Color.LIGHT_GRAY);
		}
		else
		{
			g2.setColor(color);
			
			final double en = state.getEnergie();

//			if (en < 5.0D)
//			{
//				drawStateOval(g2, xm, ym, xab, yab, en);
//			}
//			if (en < 10.0D)
//			{
//				final double e = en / 40.0D;
//				drawStateOval(g2, xm, ym, xab, yab, e);
////				this.drawOval(g2, xm+oTri.xab/2, ym+oTri.yab/2, xRadius/2, yRadius/2);
//			}
			if (en <= 1.0D)
			{
				final double e = 0.025D;
				drawStateOval(g2, xm, ym, xab, yab, e);
			}
			else
			{
				final double e = 0.025D + Math.log(en) / 40.0D;
				drawStateOval(g2, xm, ym, xab, yab, e);
			}
		}
		this.drawLine(g2, xm, ym, xab, yab);
//		this.drawOut(g2, xm, ym, mapField.getABOutField());
		
		//==========================================================================================
		
	}

	private void drawStateOval(	final Graphics2D g2, double xm, double ym, double xab,
								double yab, final double e)
	{
		this.drawOval(g2, xm+xab/2, ym+yab/2, e, e);//xRadius/2, yRadius/2);
	}

	private void drawOut(final Graphics2D g2, final double xm, final double ym, final MapField outField)
	{
		//==========================================================================================
		final double xmo = outField.getXPos();
		final double ymo = outField.getYPos();
		final double x = (xmo - xm) / 6.0D;
		final double y = (ymo - ym) / 6.0D;

		this.drawLine(g2, xm, ym, x, y);
		
		//==========================================================================================
		
	}

	private void drawString(final Graphics2D g2, final double xm, final double ym, final String str)
	{
		//==========================================================================================
		g2.drawString(str, 
		              (int)(xm * this.xScale), (int)(ym * this.yScale));

		//==========================================================================================
	}
	
	private void drawLine(final Graphics2D g2,
	                      final double xm, final double ym,
	                      final double xDir, final double yDir)
	{
		//==========================================================================================
		g2.drawLine((int)(xm * this.xScale), (int)(ym * this.yScale), 
		            (int)((xm + xDir) * this.xScale), (int)((ym + yDir) * this.yScale));

		//==========================================================================================
	}

	private void drawLine(final Graphics2D g2,
	                      final double xm, final double ym,
	                      final double xa, final double ya,
	                      final double xb, final double yb)
	{
		//==========================================================================================
		g2.drawLine((int)((xm + xa) * this.xScale), (int)((ym + ya) * this.yScale), 
		            (int)((xm + xb) * this.xScale), (int)((ym + yb) * this.yScale));

		//==========================================================================================
	}

	private void drawOval(final Graphics2D g2,
	                      final double xm, final double ym,
	                      final double xRadius, final double yRadius)
	{
		//==========================================================================================
		g2.drawOval((int)((xm - xRadius) * this.xScale), (int)((ym - yRadius) * this.yScale), 
		            (int)((xRadius + xRadius) * this.xScale), (int)((yRadius + yRadius) * this.yScale));

		//==========================================================================================
	}
}
