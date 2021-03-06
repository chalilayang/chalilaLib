/*
 *  Copyright 2014 Zhennian Li
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.chalilayang.scaleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * An TextView widget that can scale the child view of ListView according to the
 * screen.
 * Created by lizhennian on 2014/5/29.
 */
public class ScaleTextView extends TextView {
    public ScaleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setTextSize(this.getTextSize());
    }

    public ScaleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTextSize(this.getTextSize());
    }

    public ScaleTextView(Context context) {
        super(context);
        this.setTextSize(this.getTextSize());
    }

    @Override
    public void setTextSize(float textSize) {
        this.setTextSize(0, textSize);
    }

    @Override
    public void setTextSize(int unit, float textSize) {
        textSize = ScaleCalculator.getInstance(getContext().getApplicationContext()).scaleTextSize(textSize);
        super.setTextSize(unit, textSize);
    }
}
