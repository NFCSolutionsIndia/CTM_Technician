package com.ctm.technician.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.transition.TransitionManager;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctm.technician.R;

public class BadgedTabLayout extends TabLayout {

    private static final String TAG = "BadgedTabLayout";
    protected ColorStateList badgeBackgroundColors;
    protected ColorStateList badgeTextColors;
    protected float badgeTextSize = 12;
    protected float tabTextSize = 0;
    protected Typeface tabFont = null;
    protected Typeface badgeFont = null;
    protected TextUtils.TruncateAt tabTruncateAt = null;
    protected TextUtils.TruncateAt badgeTruncateAt = null;
    protected boolean isSpanText = false;
    protected int maxWidthText = -1;

    public BadgedTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //set default colors from resources
        badgeBackgroundColors = ContextCompat.getColorStateList(context, R.color.tabcolor);
        badgeTextColors = ContextCompat.getColorStateList(context, R.color.black);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BadgedTabLayout, 0, 0);
        badgeTextColors = getContextColors();

        try {

            // If we have an explicit text color set, use it instead
            if (a.hasValue(R.styleable.BadgedTabLayout_badgeBackgroundColor))
                badgeBackgroundColors = a.getColorStateList(R.styleable.BadgedTabLayout_badgeBackgroundColor);

            if (a.hasValue(R.styleable.BadgedTabLayout_tabTextSize))
                badgeTextColors = a.getColorStateList(R.styleable.BadgedTabLayout_badgeTextColor);

            if (a.hasValue(R.styleable.BadgedTabLayout_tabTextSize))
                badgeTextSize = a.getDimension(R.styleable.BadgedTabLayout_badgeTextSize, getResources().getDimension(R.dimen.tab_text_size));

            if (a.hasValue(R.styleable.BadgedTabLayout_tabTextSize))
                tabTextSize = a.getDimension(R.styleable.BadgedTabLayout_tabTextSize, getResources().getDimension(R.dimen.tab_text_size));

            if (a.hasValue(R.styleable.BadgedTabLayout_badgeSelectedBackgroundColor)) {
                final int selected = a.getColor(R.styleable.BadgedTabLayout_badgeSelectedBackgroundColor, 0);
                //badgeBackgroundColors = createColorStateList(badgeBackgroundColors.getDefaultColor(), selected);
                setBadgeBackgroundColors(ContextCompat.getColorStateList(context, R.color.blue));
                setBadgeTextColors(ContextCompat.getColorStateList(context, R.color.white));
                badgeTextColors = ContextCompat.getColorStateList(context, R.color.white);

            }



        } finally {
            a.recycle();
        }
    }

    public ColorStateList getBadgeBackgroundColors() {
        return badgeBackgroundColors;
    }

    public float getTabTextSize() {
        return tabTextSize;
    }

    /**
     * @param maxWidthText in pixels
     */
    public void setMaxWidthText(int maxWidthText) {
        this.maxWidthText = maxWidthText;
        updateTabViews();
    }

    /**
     * @param isSpanText in boolean
     */
    public void isSpanText(boolean isSpanText) {
        this.isSpanText = isSpanText;
        updateTabViews();
    }

    /**
     * @param tabTextSize in pixels
     */
    public void setTabTextSize(float tabTextSize) {
        this.tabTextSize = tabTextSize;
        updateTabViews();
    }

    /**
     * @param dimensionRes resource value of dimension ex: R.dimen.example
     */
    public void setTabTextSize(@DimenRes int dimensionRes) {
        this.tabTextSize = getResources().getDimension(dimensionRes);
        updateTabViews();
    }

    public float getBadgeTextSize() {
        return badgeTextSize;
    }

    /**
     * @param badgeTextSize in pixels
     */
    public void setBadgeTextSize(float badgeTextSize) {
        this.badgeTextSize = badgeTextSize;
        updateTabViews();
    }

    public Typeface getTabFont() {
        return tabFont;
    }

    public void setTabFont(Typeface tabFont) {
        this.tabFont = tabFont;
        updateTabViews();
    }

    public Typeface getBadgeFont() {
        return badgeFont;
    }

    public void setBadgeFont(Typeface badgeFont) {
        this.badgeFont = badgeFont;
        updateTabViews();
    }

    /**
     * sets badge background color
     *
     * @param badgeBackgroundColors state color list for badge background (selected/unselected)
     */
    public void setBadgeBackgroundColors(ColorStateList badgeBackgroundColors) {
        this.badgeBackgroundColors = badgeBackgroundColors;
        updateTabViews();
    }

    public ColorStateList getBadgeTextColors() {
        return badgeTextColors;
    }

    public TextUtils.TruncateAt getTabTruncateAt() {
        return tabTruncateAt;
    }

    public void setTabTruncateAt(TextUtils.TruncateAt tabTruncateAt) {
        this.tabTruncateAt = tabTruncateAt;
        updateTabViews();
    }

    public TextUtils.TruncateAt getBadgeTruncateAt() {
        return badgeTruncateAt;
    }

    public void setBadgeTruncateAt(TextUtils.TruncateAt badgeTruncateAt) {
        this.badgeTruncateAt = badgeTruncateAt;
        updateTabViews();
    }

    /**
     * sets badge text color
     *
     * @param badgeTextColors state color list for badge text (selected/unselected)
     */
    public void setBadgeTextColors(ColorStateList badgeTextColors) {
        this.badgeTextColors = badgeTextColors;
        updateTabViews();
    }


    @Override
    public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
        super.addTab(tab, position, setSelected);
        onTabAdded(tab);
    }


    /**
     * Invalidates the tab views
     */
    public void updateTabViews() {
        for (int i = 0; i < getTabCount(); i++) {
            Tab tab = getTabAt(i);

            if (tab != null)
                tab.setCustomView(makeCustomView(tab, R.layout.badged_tab));
        }
    }


    private View makeCustomView(Tab tab, int resId) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = tab.getCustomView() == null ?
                inflater.inflate(resId, null, false)
                : tab.getCustomView();


        makeCustomTitle(tab, view);

        makeCustomIcon(tab, view);

        makeBadge(view);

        return view;
    }

    /**
     * @param tab  for which custom icon is created
     * @param view custom view created from badged_tab.xml
     */
    private void makeCustomIcon(Tab tab, View view) {
        if (tab.getIcon() == null) {
            return;
        }

        ImageView icon = view.findViewById(R.id.imageview_tab_icon);

        DrawableCompat.setTintList(tab.getIcon(), getTabTextColors());

        icon.setImageDrawable(tab.getIcon());

        icon.setVisibility(VISIBLE);
    }


    /**
     * @param position of tab where icon need to be set
     * @param resourse drawable resourse of vector icon
     */
    public void setIcon(int position, @DrawableRes int resourse) {
        Tab tab = getTabAt(position);

        if (tab == null) {
            Log.e(TAG, "Tab at position " + position + " is not initialized. Check your tablayout implementation and if you properly initialized the view.");
            return;
        }
        tab.setIcon(resourse);

        makeCustomIcon(tab, tab.getCustomView());
    }


    /**
     * @param view custom view, manually inflated from badged_tab.xml
     */
    private void makeBadge(View view) {
        TextView badge = view.findViewById(R.id.textview_tab_badge);
        badge.setTextColor(badgeTextColors);

        if (badgeTruncateAt != null)
            badge.setEllipsize(badgeTruncateAt);

        if (badgeFont != null)
            badge.setTypeface(badgeFont);

        if (badgeTextSize != 0)
            badge.setTextSize(TypedValue.COMPLEX_UNIT_PX, badgeTextSize);

        DrawableCompat.setTintList(badge.getBackground(), badgeBackgroundColors);
    }


    /**
     * @param tab  for which custom title is created
     * @param view custom view, manually inflated from badged_tab.xml
     */
    private void makeCustomTitle(Tab tab, View view) {
        TextView title = view.findViewById(R.id.textview_tab_title);

        title.setTextColor(getTabTextColors());

        if (tabTextSize != 0)
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);

        if (tabTruncateAt != null)
            title.setEllipsize(tabTruncateAt);

        if (tabFont != null)
            title.setTypeface(tabFont);

        if (isSpanText) {
            title.setSingleLine(false);
            title.setMarqueeRepeatLimit(-1);
            title.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        }

        if (maxWidthText != -1)
            title.setMaxWidth(maxWidthText);

        if (!TextUtils.isEmpty(tab.getText()))
            title.setText(tab.getText());
        else
            title.setVisibility(GONE);
    }

    /**
     * @param index of tab where badge should be added
     * @param text  the text of the badge (null to hide the badge)
     */
    public void setBadgeText(int index, @Nullable String text) {
        Tab tab = getTabAt(index);
        if (tab == null || tab.getCustomView() == null) {
            Log.e("BadgedTabLayout", "Tab is null. Not setting custom view");
            return;
        }

        TextView badge = tab.getCustomView().findViewById(R.id.textview_tab_badge);
        TextView tabText = tab.getCustomView().findViewById(R.id.textview_tab_title);


        int maxWidth = getResources().getDimensionPixelSize(R.dimen.font_sizesmall);
        badge.setText(text);
        badge.setTextSize(maxWidth);
        //     tabText.setMaxWidth(maxWidth);
        badge.setVisibility(View.VISIBLE);

        badge.setTextColor(getResources().getColor(R.color.black));


        TransitionManager.beginDelayedTransition((ViewGroup) tab.getCustomView());
    }


    public void onTabAdded(Tab tab) {
        if (tab == null) {
            Log.e("BadgedTabLayout", "Tab is null. Not setting custom view");
            return;
        }
        tab.setCustomView(makeCustomView(tab, R.layout.badged_tab));
    }


    /**
     * takes primary and primaryDark colors from context
     *
     * @return {@link ColorStateList} object, with primary color at selected state
     * and primaryDark on unselected state
     */
    private ColorStateList getContextColors() {
        TypedValue typedValue = new TypedValue();
        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[]{
                R.attr.colorPrimary, R.attr.colorPrimaryDark});
        int primaryColor = a.getColor(0, 0);
        int primaryDarkColor = a.getColor(1, 0);
        a.recycle();
        return createColorStateList(primaryDarkColor, primaryColor);
    }


    /**
     * Creates color states list out of two given params
     *
     * @param defaultColor  color for state_selected = false
     * @param selectedColor color for state_selected = true
     * @return {@link ColorStateList} object
     */
    private static ColorStateList createColorStateList(int defaultColor, int selectedColor) {
        final int[][] states = new int[2][];
        final int[] colors = new int[2];
        int i = 0;
        states[i] = SELECTED_STATE_SET;
        colors[i] = selectedColor;
        i++;
        // Default enabled state
        states[i] = EMPTY_STATE_SET;
        colors[i] = defaultColor;
        i++;
        return new ColorStateList(states, colors);
    }

}
