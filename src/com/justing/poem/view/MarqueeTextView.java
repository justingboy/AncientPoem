package com.justing.poem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

public class MarqueeTextView extends TextView {
	public MarqueeTextView(Context paramContext) {
		super(paramContext);
	}

	public MarqueeTextView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public MarqueeTextView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public boolean isFocused() {
		return true;
	}

	public void setPressed(boolean paramBoolean) {
		ViewParent localViewParent = getParent();
		if ((paramBoolean) && (localViewParent != null)
				&& ((localViewParent instanceof View))
				&& (((View) localViewParent).isPressed())) {
			return;
		}
		super.setPressed(paramBoolean);
	}
}
