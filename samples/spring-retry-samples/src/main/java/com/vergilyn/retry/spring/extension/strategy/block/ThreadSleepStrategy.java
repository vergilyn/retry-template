package com.vergilyn.retry.spring.extension.strategy.block;

import com.vergilyn.retry.spring.extension.strategy.BlockStrategy;

import javax.annotation.concurrent.Immutable;

@Immutable
public class ThreadSleepStrategy implements BlockStrategy {

	@Override
	public void block(long sleepTime) throws InterruptedException {
		Thread.sleep(sleepTime);
	}
}
