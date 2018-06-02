package com.troya.menuplanner.helpers;

import com.cunoraz.tagview.Tag;

public class CustomTag extends Tag {

    public CustomTag(int id, String text, int color) {
        super(text);
        this.id = id;
        this.layoutColor = color;
        this.tagTextSize = 12.0f;
        this.radius = 100f;
    }
}
