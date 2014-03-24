/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package titutorial.youtubeplayer;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.Log;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.keyes.youtube.OpenYouTubePlayerActivity;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import java.util.List;

@Kroll.module(name = "Youtubeplayer", id = "titutorial.youtubeplayer")
public class YoutubeplayerModule extends KrollModule {
    
    private static final int REQ_START_STANDALONE_PLAYER = 1;
    private static final int REQ_RESOLVE_SERVICE_MISSING = 2;

	// Standard Debugging variables
	private static final String TAG = "YoutubeplayerModule";

	// You can define constants with @Kroll.constant, for example:
	// @Kroll.constant public static final String EXTERNAL_NAME = value;

	public YoutubeplayerModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		Log.d(TAG, "inside onAppCreate");
		// put module init code that needs to run when the application is
		// created
	}

	// Methods
	@Kroll.method
	public void playVideo(String developerKey, String videoId) {
		if (videoId == null || videoId.length() == 0) {
			return;
		}

		Activity activity = TiApplication.getAppRootOrCurrentActivity();
        
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(activity, developerKey, videoId, 0, true, false);
        
        if (intent != null) {
            if (canResolveIntent(intent)) {
                activity.startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
            } else {
                Log.d(TAG, "Could not resolve the intent - must need to install or update the YouTube API service.");
                // Could not resolve the intent - must need to install or update the YouTube API service.
                // YouTubeInitializationResult.SERVICE_MISSING.getErrorDialog(activity, REQ_RESOLVE_SERVICE_MISSING).show();
                Intent videoIntent = new Intent(null, Uri.parse("ytv://" + videoId.toString()), activity, OpenYouTubePlayerActivity.class);
                activity.startActivity(videoIntent);
            }
        }
	}
	
	private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = TiApplication.getAppRootOrCurrentActivity().getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }

}
