package com.plugin.demo.bitmaploader;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ding.learn.imageloader.ImageLoader;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jindingwei on 2019/7/17.
 */

public class TestAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> datas = new LinkedList<>();

    public TestAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(List<String> list) {
        datas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String url = getItem(position);
        ImageLoader.with().load(url).into(viewHolder.imageView);
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
    }
}
