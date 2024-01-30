package de.tu_darmstadt.informatik.fop.test.dorfromantik.tutor.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.informatik.fop.main.dorfromantik.animation.Animation;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.animation.MoveByBaseAnimation;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.animation.RotateByBaseAnimation;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.animation.ScaleUpEaseOutBounceAnimation;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.constants.Constants;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.controller.TextureManager;
import de.tu_darmstadt.informatik.fop.main.dorfromantik.entity.Tile;

public class AnimationTest {

	@Test
	public final void testMoveByAnimation() {
		TextureManager.getInstance().setDebug(true);
		
		Tile t = new Tile("", 0,0,true);
		t.setPosition(Constants.Base.zeroVector);
		
		assertEquals(Constants.Base.zeroVector, t.getPosition());
		
		// Animation runs for 1 second
		Animation animation = new MoveByBaseAnimation(1f, new Vector2f(100, 100));
		t.runAnimation(animation);
		
		// Check after roughly 500ms that the position is not equal to start and end position.
		try {
			Thread.sleep(500);
			t.update(null, null, 500);
		} catch (InterruptedException e) {}
		
		assertNotEquals("Position can't be the end position before the animation duration is over", new Vector2f(100, 100), t.getPosition());
		assertNotEquals("Position can't be the start position after a few ms.", Constants.Base.zeroVector, t.getPosition());
		assertFalse("Animation should not be finished", animation.isComplete());
		
		// Wait for animation to finish
		try {
			Thread.sleep(600);
			t.update(null, null, 600);
		} catch (InterruptedException e) {}
		// Check if position equals endposition
		assertEquals("The animation should be finished and the position should be the end position", new Vector2f(100, 100), t.getPosition());
		assertTrue("Animation should be finished", animation.isComplete());
	}
	
	@Test
	public final void testRotateByAnimation() {
		TextureManager.getInstance().setDebug(true);
		
		Tile t = new Tile("", 0,0,true);
		t.setRotation(0);
		
		assertEquals(0, t.getRotation(), 0.01);
		
		// Animation runs for 1 second
		Animation animation = new RotateByBaseAnimation(1f, 90);
		t.runAnimation(animation);
		
		// Check after roughly 500ms that the position is not equal to start and end position.
		try {
			Thread.sleep(500);
			t.update(null, null, 500);
		} catch (InterruptedException e) {}
		
		assertNotEquals("Rotation can't be the end rotation before the animation duration is over", 90, t.getRotation(), 0.01);
		assertNotEquals("Rotation can't be the start rotation after a few ms.", 0, t.getRotation(), 0.01);
		assertFalse("Animation should not be finished", animation.isComplete());
		
		// Wait for animation to finish
		try {
			Thread.sleep(600);
			t.update(null, null, 600);
		} catch (InterruptedException e) {}
		// Check if position equals endposition
		assertEquals("The animation should be finished and the rotation should be the end rotation", 90, t.getRotation(), 0.01);
		assertTrue("Animation should be finished", animation.isComplete());
	}
	
	@Test
	public final void testScaleUpAnimation() {
		TextureManager.getInstance().setDebug(true);
		
		Tile t = new Tile("", 0,0,true);
		t.setScale(0);
		
		assertEquals(0, t.getScale(), 0.01);
		
		// Animation runs for 1 second
		Animation animation = new ScaleUpEaseOutBounceAnimation(1f);
		t.runAnimation(animation);
		
		// Check after roughly 500ms that the position is not equal to start and end position.
		try {
			Thread.sleep(500);
			t.update(null, null, 500);
		} catch (InterruptedException e) {}
		
		assertNotEquals("Scale can't be the end scale before the animation duration is over", 1, t.getScale(), 0.01);
		assertNotEquals("Scale can't be the start scale after a few ms.", 0, t.getScale(), 0.01);
		assertFalse("Animation should not be finished", animation.isComplete());
		
		// Wait for animation to finish
		try {
			Thread.sleep(600);
			t.update(null, null, 600);
		} catch (InterruptedException e) {}
		// Check if position equals endposition
		assertEquals("The animation should be finished and the scale should be the end rotation", 1, t.getScale(), 0.01);
		assertTrue("Animation should be finished", animation.isComplete());
	}
	
}
