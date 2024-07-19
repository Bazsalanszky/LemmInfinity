package eu.toldi.infinityforlemmy.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.materialswitch.MaterialSwitch;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.toldi.infinityforlemmy.Infinity;
import eu.toldi.infinityforlemmy.R;
import eu.toldi.infinityforlemmy.activities.SettingsActivity;
import eu.toldi.infinityforlemmy.utils.SharedPreferencesUtils;
import eu.toldi.infinityforlemmy.utils.Utils;

public class PostHistoryFragment extends Fragment {

    public static final String EXTRA_ACCOUNT_NAME = "EAN";

    @BindView(R.id.info_text_view_post_history_fragment)
    TextView infoTextView;
    @BindView(R.id.mark_posts_as_read_linear_layout_post_history_fragment)
    LinearLayout markPostsAsReadLinearLayout;
    @BindView(R.id.mark_posts_as_read_text_view_post_history_fragment)
    TextView markPostsAsReadTextView;
    @BindView(R.id.mark_posts_as_read_switch_post_history_fragment)
    MaterialSwitch markPostsAsReadSwitch;
    @BindView(R.id.mark_posts_as_read_after_voting_linear_layout_post_history_fragment)
    LinearLayout markPostsAsReadAfterVotingLinearLayout;
    @BindView(R.id.mark_posts_as_read_after_voting_text_view_post_history_fragment)
    TextView markPostsAsReadAfterVotingTextView;
    @BindView(R.id.mark_posts_as_read_after_voting_switch_post_history_fragment)
    MaterialSwitch markPostsAsReadAfterVotingSwitch;
    @BindView(R.id.mark_posts_as_read_on_scroll_linear_layout_post_history_fragment)
    LinearLayout markPostsAsReadOnScrollLinearLayout;
    @BindView(R.id.mark_posts_as_read_on_scroll_text_view_post_history_fragment)
    TextView markPostsAsReadOnScrollTextView;
    @BindView(R.id.mark_posts_as_read_on_scroll_switch_post_history_fragment)
    MaterialSwitch markPostsAsReadOnScrollSwitch;
    @BindView(R.id.hide_read_posts_automatically_linear_layout_post_history_fragment)
    LinearLayout hideReadPostsAutomaticallyLinearLayout;
    @BindView(R.id.hide_read_posts_automatically_text_view_post_history_fragment)
    TextView hideReadPostsAutomaticallyTextView;
    @BindView(R.id.hide_read_posts_automatically_switch_post_history_fragment)
    MaterialSwitch hideReadPostsAutomaticallySwitch;
    @BindView(R.id.dont_hide_saved_read_posts_automatically_linear_layout_post_history_fragment)
    LinearLayout dontHideSavedReadPostsAutomaticallyLinearLayout;
    @BindView(R.id.dont_hide_saved_read_posts_automatically_text_view_post_history_fragment)
    TextView dontHideSavedReadPostsAutomaticallyTextView;
    @BindView(R.id.dont_hide_saved_read_posts_automatically_switch_post_history_fragment)
    MaterialSwitch dontHideSavedReadPostsAutomaticallySwitch;
    @BindView(R.id.dont_hide_own_read_posts_automatically_linear_layout_post_history_fragment)
    LinearLayout dontHideOwnReadPostsAutomaticallyLinearLayout;
    @BindView(R.id.dont_hide_own_read_posts_automatically_text_view_post_history_fragment)
    TextView dontHideOwnReadPostsAutomaticallyTextView;
    @BindView(R.id.dont_hide_own_read_posts_automatically_switch_post_history_fragment)
    MaterialSwitch dontHideOwnReadPostsAutomaticallySwitch;
    @Inject
    @Named("post_history")
    SharedPreferences postHistorySharedPreferences;
    private SettingsActivity activity;

    public PostHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_post_history, container, false);

        ((Infinity) activity.getApplication()).getAppComponent().inject(this);

        ButterKnife.bind(this, rootView);

        rootView.setBackgroundColor(activity.customThemeWrapper.getBackgroundColor());
        applyCustomTheme();

        if (activity.typeface != null) {
            Utils.setFontToAllTextViews(rootView, activity.typeface);
        }

        String accountName = getArguments().getString(EXTRA_ACCOUNT_NAME);
        if (accountName == null) {
            infoTextView.setText(R.string.only_for_logged_in_user);
            markPostsAsReadLinearLayout.setVisibility(View.GONE);
            markPostsAsReadAfterVotingLinearLayout.setVisibility(View.GONE);
            markPostsAsReadOnScrollLinearLayout.setVisibility(View.GONE);
            hideReadPostsAutomaticallyLinearLayout.setVisibility(View.GONE);
            return rootView;
        }

        markPostsAsReadSwitch.setChecked(postHistorySharedPreferences.getBoolean(
                accountName + SharedPreferencesUtils.MARK_POSTS_AS_READ_BASE, false));
        markPostsAsReadAfterVotingSwitch.setChecked(postHistorySharedPreferences.getBoolean(
                accountName + SharedPreferencesUtils.MARK_POSTS_AS_READ_AFTER_VOTING_BASE, false));
        markPostsAsReadOnScrollSwitch.setChecked(postHistorySharedPreferences.getBoolean(
                accountName + SharedPreferencesUtils.MARK_POSTS_AS_READ_ON_SCROLL_BASE, false));
        hideReadPostsAutomaticallySwitch.setChecked(postHistorySharedPreferences.getBoolean(
                accountName + SharedPreferencesUtils.HIDE_READ_POSTS_AUTOMATICALLY_BASE, false));
        dontHideSavedReadPostsAutomaticallySwitch.setChecked(postHistorySharedPreferences.getBoolean(
                accountName + SharedPreferencesUtils.DONT_HIDE_SAVED_READ_POSTS, false));
        dontHideOwnReadPostsAutomaticallySwitch.setChecked(postHistorySharedPreferences.getBoolean(
                accountName + SharedPreferencesUtils.DONT_HIDE_OWN_READ_POSTS, false));

        if (hideReadPostsAutomaticallySwitch.isChecked()) {
            dontHideSavedReadPostsAutomaticallyLinearLayout.setVisibility(View.VISIBLE);
            dontHideOwnReadPostsAutomaticallyLinearLayout.setVisibility(View.VISIBLE);
        } else {
            dontHideSavedReadPostsAutomaticallyLinearLayout.setVisibility(View.GONE);
            dontHideOwnReadPostsAutomaticallyLinearLayout.setVisibility(View.GONE);
        }

        markPostsAsReadLinearLayout.setOnClickListener(view -> {
            markPostsAsReadSwitch.performClick();
        });

        markPostsAsReadSwitch.setOnCheckedChangeListener((compoundButton, b) ->
                postHistorySharedPreferences.edit().putBoolean(accountName + SharedPreferencesUtils.MARK_POSTS_AS_READ_BASE, b).apply());

        markPostsAsReadAfterVotingLinearLayout.setOnClickListener(view -> markPostsAsReadAfterVotingSwitch.performClick());

        markPostsAsReadAfterVotingSwitch.setOnCheckedChangeListener((compoundButton, b) ->
                postHistorySharedPreferences.edit().putBoolean(accountName + SharedPreferencesUtils.MARK_POSTS_AS_READ_AFTER_VOTING_BASE, b).apply());

        markPostsAsReadOnScrollLinearLayout.setOnClickListener(view -> markPostsAsReadOnScrollSwitch.performClick());

        markPostsAsReadOnScrollSwitch.setOnCheckedChangeListener((compoundButton, b) -> postHistorySharedPreferences.edit().putBoolean(accountName + SharedPreferencesUtils.MARK_POSTS_AS_READ_ON_SCROLL_BASE, b).apply());

        hideReadPostsAutomaticallyLinearLayout.setOnClickListener(view -> hideReadPostsAutomaticallySwitch.performClick());

        hideReadPostsAutomaticallySwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            postHistorySharedPreferences.edit().putBoolean(accountName + SharedPreferencesUtils.HIDE_READ_POSTS_AUTOMATICALLY_BASE, b).apply();
            if (b) {
                dontHideSavedReadPostsAutomaticallyLinearLayout.setVisibility(View.VISIBLE);
                dontHideOwnReadPostsAutomaticallyLinearLayout.setVisibility(View.VISIBLE);
            } else {
                dontHideSavedReadPostsAutomaticallyLinearLayout.setVisibility(View.GONE);
                dontHideOwnReadPostsAutomaticallyLinearLayout.setVisibility(View.GONE);
            }
        });

        dontHideSavedReadPostsAutomaticallyLinearLayout.setOnClickListener(view -> dontHideSavedReadPostsAutomaticallySwitch.performClick());

        dontHideSavedReadPostsAutomaticallySwitch.setOnCheckedChangeListener((compoundButton, b) -> postHistorySharedPreferences.edit().putBoolean(accountName + SharedPreferencesUtils.DONT_HIDE_SAVED_READ_POSTS, b).apply());

        dontHideOwnReadPostsAutomaticallyLinearLayout.setOnClickListener(view -> dontHideOwnReadPostsAutomaticallySwitch.performClick());

        dontHideOwnReadPostsAutomaticallySwitch.setOnCheckedChangeListener((compoundButton, b) -> postHistorySharedPreferences.edit().putBoolean(accountName + SharedPreferencesUtils.DONT_HIDE_OWN_READ_POSTS, b).apply());

        return rootView;
    }

    private void applyCustomTheme() {
        infoTextView.setTextColor(activity.customThemeWrapper.getSecondaryTextColor());
        Drawable infoDrawable = Utils.getTintedDrawable(activity, R.drawable.ic_info_preference_24dp, activity.customThemeWrapper.getPrimaryIconColor());
        infoTextView.setCompoundDrawablesWithIntrinsicBounds(infoDrawable, null, null, null);
        int primaryTextColor = activity.customThemeWrapper.getPrimaryTextColor();
        markPostsAsReadTextView.setTextColor(primaryTextColor);
        markPostsAsReadAfterVotingTextView.setTextColor(primaryTextColor);
        markPostsAsReadOnScrollTextView.setTextColor(primaryTextColor);
        hideReadPostsAutomaticallyTextView.setTextColor(primaryTextColor);
        dontHideSavedReadPostsAutomaticallyTextView.setTextColor(primaryTextColor);
        dontHideOwnReadPostsAutomaticallyTextView.setTextColor(primaryTextColor);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (SettingsActivity) context;
    }
}