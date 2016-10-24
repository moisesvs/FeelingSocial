/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moisesvazquez.feelingsocial.feelingsocial.data.cache;

import android.content.Context;

import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.TweetEntity;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.executor.ThreadExecutor;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link TweetCache} implementation.
 */
@Singleton
public class TweetCacheImpl implements TweetCache {

    private static final String SETTINGS_FILE_NAME             = "com.fernandocejas.android10.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "user_";
    private static final long   EXPIRATION_TIME   = 60 * 10 * 1000;

    private final Context        context;
    private final File           cacheDir;
//    private final JsonSerializer serializer;
    private final FileManager    fileManager;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link TweetCacheImpl}.
     *
     * @param context             A
     * @param fileManager         {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public TweetCacheImpl(Context context, /*JsonSerializer userCacheSerializer,*/
                          FileManager fileManager, ThreadExecutor executor) {
        if (context == null || /*userCacheSerializer == null ||*/ fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
//        this.serializer = userCacheSerializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override
    public Observable<TweetEntity> get(final int userId) {
        return Observable.create(subscriber -> {
            File   userEntityFile = TweetCacheImpl.this.buildFile(userId);
            String fileContent    = TweetCacheImpl.this.fileManager.readFileContent(userEntityFile);
//            TweetEntity userEntity     = UserCacheImpl.this.serializer.deserialize(fileContent);
            TweetEntity userEntity = new TweetEntity(0l);

            if (userEntity != null) {
                subscriber.onNext(userEntity);
                subscriber.onCompleted();
            } else {
//                subscriber.onError(new UserNotFoundException());
            }
        });
    }

    @Override
    public void put(TweetEntity tweetEntity) {
        if (tweetEntity != null) {
            File userEntityFile = this.buildFile(tweetEntity.getId());
            if (!isCached(tweetEntity.getId())) {
//                String jsonString = this.serializer.serialize(tweetEntity);
//                this.executeAsynchronously(new CacheWriter(this.fileManager, userEntityFile,
//                                                           jsonString));
//                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached(long userId) {
        File userEntitiyFile = this.buildFile(userId);
        return false;
//        return this.fileManager.exists(userEntitiyFile);
    }

    @Override
    public boolean isExpired() {
        long currentTime    = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public void evictAll() {
        this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param userId The id user to build the file.
     * @return A valid file.
     */
    private File buildFile(long userId) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(userId);

        return new File(fileNameBuilder.toString());
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                                            SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                                                   SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File        fileToWrite;
        private final String      fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override
        public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File        cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override
        public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
}