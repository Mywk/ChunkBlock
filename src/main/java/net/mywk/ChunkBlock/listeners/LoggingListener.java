package net.mywk.ChunkBlock.listeners;

import net.mywk.ChunkBlock.Consumer;

import org.bukkit.event.Listener;

import net.mywk.ChunkBlock.ChunkBlock;

public class LoggingListener implements Listener
{
	protected final Consumer consumer;

	public LoggingListener(ChunkBlock cb) {
		consumer = cb.getConsumer();
	}
}
