package com.jakewharton.wakkawallpaper;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Ghost extends Entity {
	enum State { HUNT, FLEE, EYES_ONLY }
	
	private static final int FLEE_LENGTH = 7000;
	private static final int FLEE_BLINK_THRESHOLD = 2000;
	private static final int FLEE_BLINK_INTERVAL = 200;
	private static final int DEFAULT_EYE_BACKGROUND = 0xffffffff;
	private static final int DEFAULT_EYE_FOREGROUND = 0xff000000;
	private static final int DEFAULT_SCARED_BACKGROUND = 0xff0033ff;
	private static final int DEFAULT_SCARED_FOREGROUND = 0xffffcc33;
	private static final int DEFAULT_SCARED_BLINK_BACKGROUND = 0xffff0000;
	private static final int DEFAULT_SCARED_BLINK_FOREGROUND = 0xfffafafa;
	
	private State mState;
	private final Paint mBodyBackground;
	private final Paint mEyeBackground;
	private final Paint mEyeForeground;
	private final Paint mScaredBackground;
	private final Paint mScaredForeground;
	private final Paint mScaredBlinkBackground;
	private final Paint mScaredBlinkForeground;
    private int mFleeLength;
	
	protected Ghost(int backgroundColor) {
		super(0, 0, null);

		this.mState = State.HUNT;
		this.mFleeLength = Ghost.FLEE_LENGTH;
		
		this.mBodyBackground = new Paint();
		this.mBodyBackground.setColor(backgroundColor);
		this.mEyeBackground = new Paint();
		this.mEyeBackground.setColor(Ghost.DEFAULT_EYE_BACKGROUND);
		this.mEyeForeground = new Paint();
		this.mEyeForeground.setColor(Ghost.DEFAULT_EYE_FOREGROUND);
		this.mScaredBackground = new Paint();
		this.mScaredBackground.setColor(Ghost.DEFAULT_SCARED_BACKGROUND);
		this.mScaredForeground = new Paint();
		this.mScaredForeground.setColor(Ghost.DEFAULT_SCARED_FOREGROUND);
		this.mScaredBlinkBackground = new Paint();
		this.mScaredBlinkBackground.setColor(Ghost.DEFAULT_SCARED_BLINK_BACKGROUND);
		this.mScaredBlinkForeground = new Paint();
		this.mScaredBlinkForeground.setColor(Ghost.DEFAULT_SCARED_BLINK_FOREGROUND);
	}
	
	public void tick(Game game) {
		switch (this.mState) {
			case HUNT:
		    	boolean success = false;
		    	while (!success) {
	    			switch (Game.RANDOM.nextInt(10)) {
	    				case 0:
	        				success = this.tryMove(game, Direction.NORTH);
	        				break;
	        			case 1:
	        				success = this.tryMove(game, Direction.SOUTH);
	        				break;
	        			case 2:
	        				success = this.tryMove(game, Direction.EAST);
	        				break;
	        			case 3:
	        				success = this.tryMove(game, Direction.WEST);
	        				break;
	        			default: //4-9, most of the time stay straight (if possible)
	        				if (this.mDirection != null) {
	        					success = this.tryMove(game, this.mDirection);
	        				}
	        				break;
	    			}
	    		}
				break;
				
			case EYES_ONLY:
				break;
				
			case FLEE:
				this.mFleeLength -= 1;
				if (this.mFleeLength < 0) {
					this.mState = State.HUNT;
					this.mFleeLength = Ghost.FLEE_LENGTH;
				}
				break;
		}
	}
	
	public void draw(Canvas c) {
		c.save();
		c.translate(this.getLocationX(), this.getLocationY());
		
		switch (this.mState) {
			case HUNT:
				//draw body only
				c.drawRect(0, 0, this.mCellWidth, this.mCellHeight, this.mBodyBackground);
				
				//fall through to eyes only case
			case EYES_ONLY:
				
				break;
				
			case FLEE:
				if ((this.mFleeLength <= Ghost.FLEE_BLINK_THRESHOLD) && ((this.mFleeLength / Ghost.FLEE_BLINK_INTERVAL) % 2 == 0)) {
					//draw scared blink
				} else {
					//draw normal blink
				}
				break;
		}
		
		c.restore();
	}
	

	public static class Blinky extends Ghost {
		private static final int BACKGROUND_COLOR = 0xfff00000;
		
		public Blinky() {
			super(Blinky.BACKGROUND_COLOR);
		}
	}
	public static class Pinky extends Ghost {
		private static final int BACKGROUND_COLOR = 0xffff00f0;
		
		public Pinky() {
			super(Pinky.BACKGROUND_COLOR);
		}
	}
	public static class Inky extends Ghost {
		private static final int BACKGROUND_COLOR = 0xff01d8ff;
		
		public Inky() {
			super(Inky.BACKGROUND_COLOR);
		}
	}
	public static class Clyde extends Ghost {
		private static final int BACKGROUND_COLOR = 0xffff8401;
		
		public Clyde() {
			super(Clyde.BACKGROUND_COLOR);
		}
	}
}
