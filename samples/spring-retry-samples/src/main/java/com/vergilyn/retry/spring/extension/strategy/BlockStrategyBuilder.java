/*
 * Copyright 2012-2015 Ray Holder
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vergilyn.retry.spring.extension.strategy;

import com.vergilyn.retry.spring.extension.strategy.block.ThreadSleepStrategy;

/**
 * <a href="https://github.com/rholder/guava-retrying/blob/master/src/main/java/com/github/rholder/retry/BlockStrategies.java">
 *     guava-retrying, BlockStrategies.java</a>
 *
 * @author vergilyn
 * @since 2023-02-09
 */
public final class BlockStrategyBuilder {

    private static final BlockStrategy THREAD_SLEEP_STRATEGY = new ThreadSleepStrategy();

    private BlockStrategyBuilder() {
    }

    /**
     * Returns a block strategy that puts the current thread to sleep between
     * retries.
     *
     * @return a block strategy that puts the current thread to sleep between retries
     */
    public static BlockStrategy threadSleepStrategy() {
        return THREAD_SLEEP_STRATEGY;
    }
}