package com.arman.baking.ui.fragment;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arman.baking.R;
import com.arman.baking.app.Constant;
import com.arman.baking.databinding.FragmentStepViewBinding;
import com.arman.baking.model.Recipe;
import com.arman.baking.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.invoke.ConstantCallSite;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StepViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepViewFragment extends Fragment implements EventListener {

    private static final String TAG = StepViewFragment.class.getSimpleName();




    private FragmentStepViewBinding mBinding;

    private int currentPosition;

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private DataSource.Factory mMediaDataSourceFactory;

    private Step currentStep;

    private boolean mShouldAutoPlay;
    private long mVideoPosition;
    private ExoPlayer mExoPlayer;
    private DefaultTrackSelector mTrackSelector;

    private int currentOrientation;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder stateBuilder;

    public StepViewFragment() {
        // Required empty public constructor
    }

    public static StepViewFragment newInstance(Step step, int position) {
        StepViewFragment fragment = new StepViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.key.KEY_STEPS, step);
        args.putInt(Constant.key.KEY_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentOrientation = getResources().getConfiguration().orientation;
        if (getArguments() != null) {
            currentStep = getArguments().getParcelable(Constant.key.KEY_STEPS);
            currentPosition = getArguments().getInt(Constant.key.KEY_POSITION);
        }

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentStepViewBinding.inflate(inflater, container, false);

        View view = mBinding.getRoot();
        if (savedInstanceState == null) {
            mShouldAutoPlay = true;
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (recipeHasVideo()) {
            initializeMediaSession();
            initVideoPlayer();
        } else {
            mBinding.frameLayout.setVisibility(View.GONE);
            mBinding.llDescription.setVisibility(View.VISIBLE);
        }
        mBinding.tvDescription.setText(currentStep.getDescription());

    }

    private void initializeMediaSession() {

        mMediaSession = new MediaSessionCompat(Objects.requireNonNull(getContext()), TAG);

        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(stateBuilder.build());
        mMediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                mExoPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                mExoPlayer.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                mExoPlayer.seekTo(0);
            }
        });
        mMediaSession.setActive(true);
    }


    private void initVideoPlayer() {

        if (mExoPlayer == null) {
            String url = currentStep.getVideoURL();
            TrackSelection.Factory factory = new AdaptiveTrackSelection.Factory();
            mTrackSelector = new DefaultTrackSelector(factory);
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(getActivity());
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), renderersFactory, mTrackSelector, loadControl);
            mBinding.videoView.setPlayer(mExoPlayer);
            mExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            mMediaDataSourceFactory = new DefaultDataSourceFactory(Objects.requireNonNull(getContext()), userAgent);
            mExoPlayer.prepare(buildMediaSource(Uri.parse(url)));
            mExoPlayer.seekTo(mVideoPosition);
            mExoPlayer.setPlayWhenReady(mShouldAutoPlay);

        }

    }



    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == Player.STATE_READY) && playWhenReady) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == Player.STATE_READY)) {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, mExoPlayer.getCurrentPosition(), 1f);
        }

        if (mMediaSession != null) {
            mMediaSession.setPlaybackState(stateBuilder.build());
        }
    }

    private boolean recipeHasVideo() {
        if (currentStep == null) return false;
        return !TextUtils.isEmpty(currentStep.getVideoURL());
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(mMediaDataSourceFactory).createMediaSource(uri);
    }

    private void onPlayerReleased() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mShouldAutoPlay = mExoPlayer.getPlayWhenReady();
            mVideoPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer = null;
            mTrackSelector = null;
        }
        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initVideoPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initVideoPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        onPlayerReleased();
    }

    @Override
    public void onStop() {
        super.onStop();
        onPlayerReleased();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        outState.putLong(Constant.PLAYER_VIDEO_POSITION, mVideoPosition);
        outState.putBoolean(Constant.PLAYER_PLAYING_STATE, mShouldAutoPlay);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: ");
        if (savedInstanceState != null) {
            mVideoPosition = savedInstanceState.getLong(Constant.PLAYER_VIDEO_POSITION);
            mShouldAutoPlay = savedInstanceState.getBoolean(Constant.PLAYER_PLAYING_STATE);
        }
    }


}