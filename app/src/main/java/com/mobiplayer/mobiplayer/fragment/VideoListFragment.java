package com.mobiplayer.mobiplayer.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mobiplayer.mobiplayer.R;
import com.mobiplayer.mobiplayer.adapter.PageAdapter;
import com.mobiplayer.mobiplayer.model.VideoData;
import com.mobiplayer.mobiplayer.model.VideoDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoListFragment extends Fragment implements PageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressBar;
    private RequestQueue mRequestQueue;

    public static VideoListFragment newInstance() {
        return new VideoListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        mProgressBar = new ProgressDialog(getActivity());
        mProgressBar.show();
        fetchForVideos();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_list_video, container, false);
        mRecyclerView = (RecyclerView) lView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return lView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(VideoDataModel.mSharedInstance.getVideoList()!=null) {
            mRecyclerView.setAdapter(new PageAdapter(VideoDataModel.mSharedInstance.getVideoList(),
                    getContext(), VideoListFragment.this));
        }
    }

    private void fetchForVideos() {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET,
                "https://interview-e18de.firebaseio.com/media.json?print=pretty",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<VideoData> videoDataModels = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject video = response.getJSONObject(i);
                                String id = video.getString("id");
                                String description = video.getString("description");
                                String title = video.getString("title");
                                String thumb = video.getString("thumb");
                                String url = video.getString("url");
                                VideoData videoData = new VideoData(id, description,
                                        title, thumb, url);
                                videoDataModels.add(videoData);
                            }
                            if (mProgressBar != null) {
                                mProgressBar.dismiss();
                            }
                            VideoDataModel.mSharedInstance.setVideoList(videoDataModels);
                            mRecyclerView.setAdapter(new PageAdapter(VideoDataModel.mSharedInstance.getVideoList(),
                                    getContext(), VideoListFragment.this));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });
        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    @Override
    public void onItemSelected(VideoData videoData) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, VideoPlayerFragment.newInstance(videoData))
                    .addToBackStack(VideoPlayerFragment.class.getSimpleName())
                    .commitAllowingStateLoss();
        }
    }
    /**
     * Could handle back press.
     * @return true if back press was handled
     */
    public boolean onBackPressed() {
        return false;
    }
}
