package de.tu_darmstadt.informatik.fop.main.dorfromantik.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class which handles feedback messages for the player.
 */
public class FeedbackManager {

	/**
	 * singleton
	 */
	private static final FeedbackManager manager = new FeedbackManager();

	/**
	 * The key for an entry in "messages".
	 */
	private long secondsUntilRemoved = 2;
	/**
	 * stores all messages
	 */
	private Map<Long, String> messages;
	
	private FeedbackManager() {
		super();
		messages = new HashMap<>();
	}
	
	public static FeedbackManager getInstance() {
		return manager;
	}
	
	/**
	 * Adds a message to the Manager with the current timestamp as key.
	 * @param message Message to display.
	 */
	public void addFeedbackMessage(String message) {
		this.messages.put(System.currentTimeMillis(), message);
	}
	
	/**
	 * @return List of Messages.
	 */
	public List<String> getFeedbackMessages() {
		return new ArrayList<>(this.messages.values());
	}
	
	/**
	 * Removes messages which are older than the timestamp of the key + secondsUntilRemoved.
	 */
	public void removeFeedbackMessage() {
		List<Long> list = new ArrayList<>(this.messages.keySet()); 
		// Use iterator, otherwise objects cant be removed while iterating
		for (Iterator<Long> iterator = list.iterator(); iterator.hasNext(); ) {
			long value = iterator.next();
		    if (System.currentTimeMillis()-value >= this.secondsUntilRemoved*1000) {
		        iterator.remove();
		        this.messages.remove(value);
		    }
		}
	}
}
