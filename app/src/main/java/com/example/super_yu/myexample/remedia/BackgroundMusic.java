package com.example.super_yu.myexample.remedia;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by super_yu on 2017/6/28.
 */

public class BackgroundMusic {

    private static BackgroundMusic backgroundMusic = null;
    private static final String TAG = "Bg_Music";
    private float mLeftVolume;
    private float mRightVolume;
    private static Context mContext;
    private MediaPlayer mBackgroundMediaPlayer;
    private boolean mIsPaused;
    //    private String mCurrentPath;
    private int mCurrentPath;

    private BackgroundMusic(Context context) {
        this.mContext = context;
        initData();
    }

    public static BackgroundMusic getInstance(Context context) {
        if (backgroundMusic == null) {
            backgroundMusic = new BackgroundMusic(context);
        }
        mContext = context;
        return backgroundMusic;
    }

    public static BackgroundMusic getInstance(){
        if (backgroundMusic == null) {
            backgroundMusic = new BackgroundMusic(mContext);
        }
        return backgroundMusic;
    }

    // 初始化一些数据
    private void initData() {
        mLeftVolume = 0.5f;
        mRightVolume = 0.5f;
        mBackgroundMediaPlayer = null;
        mIsPaused = false;
//        mCurrentPath = null;
        mCurrentPath = 0;
    }

    /**
     * 根据path路径播放背景音乐
     *
     * @param path   :assets中的音频路径
     * @param isLoop :是否循环播放
     */
    public void playBackgroundMusic(Context context, int path, boolean isLoop) {
        if (mCurrentPath == 0) {
            // 这是第一次播放背景音乐--- it is the first time to play background music
            // 或者是执行end()方法后，重新被叫---or end() was called
            mBackgroundMediaPlayer = createMediaplayerFromAssets(context, path);
            mCurrentPath = path;
        } else {
            if (!(mCurrentPath == path)) {
                // 播放一个新的背景音乐--- play new background music
                // 释放旧的资源并生成一个新的----release old resource and create a new one
                if (mBackgroundMediaPlayer != null) {
                    mBackgroundMediaPlayer.release();
                }
                mBackgroundMediaPlayer = createMediaplayerFromAssets(context, path);
                // 记录这个路径---record the path
                mCurrentPath = path;
            }
        }

        if (mBackgroundMediaPlayer == null) {
            Log.e(TAG, "playBackgroundMusic: background media player is null");
        } else {
            // 若果音乐正在播放或已近中断，停止它---if the music is playing or paused, stop it
            mBackgroundMediaPlayer.stop();
            mBackgroundMediaPlayer.setLooping(isLoop);
            try {
                mBackgroundMediaPlayer.prepare();
                mBackgroundMediaPlayer.seekTo(0);
                mBackgroundMediaPlayer.start();
                this.mIsPaused = false;
            } catch (Exception e) {
                Log.e(TAG, "playBackgroundMusic: error state");
            }
        }
    }

    /**
     * 停止播放背景音乐
     */
    public void stopBackgroundMusic() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.stop();
            // should set the state, if not , the following sequence will be
            // error
            // play -> pause -> stop -> resume
            this.mIsPaused = false;
        }
    }

    /**
     * 暂停播放背景音乐
     */
    public void pauseBackgroundMusic() {
        if (mBackgroundMediaPlayer != null
                && mBackgroundMediaPlayer.isPlaying()) {
            mBackgroundMediaPlayer.pause();
            this.mIsPaused = true;
        }
    }

    /**
     * 继续播放背景音乐
     */
    public void resumeBackgroundMusic() {
        if (mBackgroundMediaPlayer != null && this.mIsPaused) {
            mBackgroundMediaPlayer.start();
            this.mIsPaused = false;
        }
    }

    /**
     * 重新播放背景音乐
     */
    public void rewindBackgroundMusic() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.stop();
            try {
                mBackgroundMediaPlayer.prepare();
                mBackgroundMediaPlayer.seekTo(0);
                mBackgroundMediaPlayer.start();
                this.mIsPaused = false;
            } catch (Exception e) {
                Log.e(TAG, "rewindBackgroundMusic: error state");
            }
        }
    }

    /**
     * 判断背景音乐是否正在播放
     *
     * @return：返回的boolean值代表是否正在播放
     */
    public boolean isBackgroundMusicPlaying() {
        boolean ret = false;
        if (mBackgroundMediaPlayer == null) {
            ret = false;
        } else {
            ret = mBackgroundMediaPlayer.isPlaying();
        }
        return ret;
    }

    /**
     * 结束背景音乐，并释放资源
     */
    public void end() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.release();
        }
        // 重新“初始化数据”
        initData();
    }

    /**
     * 得到背景音乐的“音量”
     *
     * @return
     */
    public float getBackgroundVolume() {
        if (this.mBackgroundMediaPlayer != null) {
            return (this.mLeftVolume + this.mRightVolume) / 2;
        } else {
            return 0.0f;
        }
    }

    /**
     * 设置背景音乐的音量
     *
     * @param volume ：设置播放的音量，float类型
     */
    public void setBackgroundVolume(float volume) {
        this.mLeftVolume = this.mRightVolume = volume;
        if (this.mBackgroundMediaPlayer != null) {
            this.mBackgroundMediaPlayer.setVolume(this.mLeftVolume,
                    this.mRightVolume);
        }
    }

    /**
     * create mediaplayer for music
     *
     * @param path the path relative to assets
     * @return
     */
//    private MediaPlayer createMediaplayerFromAssets(String path) {
//        MediaPlayer mediaPlayer = null;
//        try {
//            AssetFileDescriptor assetFileDescritor = mContext.getAssets()
//                    .openFd(path);
//            mediaPlayer = new MediaPlayer();
//            mediaPlayer.setDataSource(assetFileDescritor.getFileDescriptor(),
//                    assetFileDescritor.getStartOffset(),
//                    assetFileDescritor.getLength());
//            mediaPlayer.prepare();
//            mediaPlayer.setVolume(mLeftVolume, mRightVolume);
//        } catch (Exception e) {
//            mediaPlayer = null;
//            Log.e(TAG, "error: " + e.getMessage(), e);
//        }
//        return mediaPlayer;
//    }

    /**
     * create mediaplayer for music
     * int
     *
     * @param path the path relative to assets
     * @return
     */
    private MediaPlayer createMediaplayerFromAssets(Context context, int path) {
        MediaPlayer mediaPlayer = null;
        try {
            mediaPlayer = MediaPlayer.create(context, path);
//            mediaPlayer.prepare();
            mediaPlayer.setVolume(mLeftVolume, mRightVolume);
        } catch (Exception e) {
            mediaPlayer = null;
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
        return mediaPlayer;
    }
}