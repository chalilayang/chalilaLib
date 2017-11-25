package com.baogetv.app.model.videodetail.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baogetv.app.BaseItemAdapter;
import com.baogetv.app.ItemViewHolder;
import com.baogetv.app.R;
import com.baogetv.app.customview.LogoImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chalilayang.scaleview.ScaleCalculator;
import com.chalilayang.scaleview.ScaleTextView;

import java.util.List;

public class VideoListAdapter
        extends BaseItemAdapter<VideoListAdapter.IVideoData, VideoListAdapter.ViewHolder> {

    private int imageHeight;
    public VideoListAdapter(Context context) {
        super(context);
        imageHeight = ScaleCalculator.getInstance(mContext).scaleWidth(420);
    }

    @Override
    public ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        ViewGroup.LayoutParams vlp = view.getLayoutParams();
        vlp.height = imageHeight;
        return new ViewHolder(view);
    }

    public class ViewHolder extends ItemViewHolder<IVideoData> {
        public final LogoImageView mContentView;

        public ViewHolder(View view) {
            super(view);
            mContentView = (LogoImageView) view.findViewById(R.id.img);
        }

        @Override
        public void bindData(IVideoData data, int pos) {
            mContentView.setChnLogoVisible(true);
            String pic = data.getPicUrl();
            Glide.with(mContext)
                    .load(pic)
                    .crossFade()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(mContentView);
        }
    }

    public interface IVideoData {
        String getPicUrl();
    }
}
