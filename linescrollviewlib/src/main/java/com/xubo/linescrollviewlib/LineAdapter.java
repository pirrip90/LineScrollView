package com.xubo.linescrollviewlib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：xubo
 * Time：2017-11-19
 * Description：
 */

public class LineAdapter extends BaseAdapter {
    public List<String> lineList;

    private float textSize;

    private int textSpace;

    private int textColor;

    private Context context;

    private LayoutInflater inflater;

    private int scrollLines;

    public LineAdapter(Context context, float textSize, int textSpace, int textColor, int scrollLines) {
        inflater = LayoutInflater.from(context);
        lineList = new ArrayList<String>();
        this.context = context;
        this.textSize = textSize;
        this.textSpace = textSpace;
        this.textColor = textColor;
        this.scrollLines = scrollLines;
    }

    public int lines() {
        return lineList.size();
    }

    public void setLineList(List<String> lineList) {
        this.lineList.clear();
        this.lineList.addAll(lineList);
        notifyDataSetChanged();
    }

    public void clear() {
        this.lineList.clear();
        notifyDataSetChanged();
    }

    public void addLineList(List<String> lineList) {
        this.lineList.addAll(lineList);
        notifyDataSetChanged();
    }

    public void addLine(String lineItem) {
        this.lineList.add(lineItem);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lineList.size() >= scrollLines ? Integer.MAX_VALUE : lineList.size();
    }

    @Override
    public Object getItem(int position) {
        return lineList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LineTag lineTag;
        if (convertView == null) {
            lineTag = new LineTag();
            convertView = inflater.inflate(R.layout.line_item, null);
            lineTag.textView = convertView.findViewById(R.id.item_tv);
            convertView.setTag(lineTag);
        } else {
            lineTag = (LineTag) convertView.getTag();
        }
        lineTag.textView.setTextSize(textSize);
        lineTag.textView.setTextColor(textColor);
        if (position == 0 && position == getCount() - 1) {
            lineTag.textView.setPadding(0, textSpace * 2, 0, textSpace * 2);
        } else if (position == 0) {
            lineTag.textView.setPadding(0, textSpace * 2, 0, textSpace);
        } else if (position == getCount() - 1) {
            lineTag.textView.setPadding(0, textSpace, 0, textSpace * 2);
        } else {
            lineTag.textView.setPadding(0, textSpace, 0, textSpace);
        }
        int size = lineList.size();
        String text = lineList.get(position % size);
        lineTag.textView.setText(text);
        return convertView;
    }

    class LineTag {
        public TextView textView;
    }
}
