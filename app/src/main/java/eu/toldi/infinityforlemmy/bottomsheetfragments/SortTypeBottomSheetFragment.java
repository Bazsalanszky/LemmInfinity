package eu.toldi.infinityforlemmy.bottomsheetfragments;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.toldi.infinityforlemmy.R;
import eu.toldi.infinityforlemmy.SortType;
import eu.toldi.infinityforlemmy.SortTypeSelectionCallback;
import eu.toldi.infinityforlemmy.activities.BaseActivity;
import eu.toldi.infinityforlemmy.customviews.LandscapeExpandedRoundedBottomSheetDialogFragment;
import eu.toldi.infinityforlemmy.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class SortTypeBottomSheetFragment extends LandscapeExpandedRoundedBottomSheetDialogFragment {

    public static final String EXTRA_NO_BEST_TYPE = "ENBT";
    public static final String EXTRA_CURRENT_SORT_TYPE = "ECST";

    @BindView(R.id.best_type_text_view_sort_type_bottom_sheet_fragment)
    TextView bestTypeTextView;
    @BindView(R.id.hot_type_text_view_sort_type_bottom_sheet_fragment)
    TextView hotTypeTextView;
    @BindView(R.id.new_type_text_view_sort_type_bottom_sheet_fragment)
    TextView newTypeTextView;
    @BindView(R.id.old_type_text_view_sort_type_bottom_sheet_fragment)
    TextView oldTypeTextView;
    @BindView(R.id.top_type_text_view_sort_type_bottom_sheet_fragment)
    TextView topTypeTextView;

    private BaseActivity activity;

    public SortTypeBottomSheetFragment() {
        // Required empty public constructor
    }

    public static SortTypeBottomSheetFragment getNewInstance(boolean isNoBestType, SortType currentSortType) {
        SortTypeBottomSheetFragment fragment = new SortTypeBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_NO_BEST_TYPE, isNoBestType);
        bundle.putString(EXTRA_CURRENT_SORT_TYPE, currentSortType.getType().fullName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sort_type_bottom_sheet, container, false);
        ButterKnife.bind(this, rootView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                && (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) != Configuration.UI_MODE_NIGHT_YES) {
            rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        }

        if (getArguments().getBoolean(EXTRA_NO_BEST_TYPE)) {
            bestTypeTextView.setVisibility(View.GONE);
        } else {
            bestTypeTextView.setOnClickListener(view -> {
                ((SortTypeSelectionCallback) activity).sortTypeSelected(new SortType(SortType.Type.ACTIVE));
                dismiss();
            });
        }

        String currentSortType = getArguments().getString(EXTRA_CURRENT_SORT_TYPE);
        if (currentSortType.equals(SortType.Type.ACTIVE.fullName)) {
            bestTypeTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(bestTypeTextView.getCompoundDrawablesRelative()[0], null, AppCompatResources.getDrawable(activity, R.drawable.ic_round_check_circle_day_night_24dp), null);
        } else if (currentSortType.equals(SortType.Type.HOT.fullName)) {
            hotTypeTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(hotTypeTextView.getCompoundDrawablesRelative()[0], null, AppCompatResources.getDrawable(activity, R.drawable.ic_round_check_circle_day_night_24dp), null);
        } else if (currentSortType.equals(SortType.Type.NEW.fullName)) {
            newTypeTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(newTypeTextView.getCompoundDrawablesRelative()[0], null, AppCompatResources.getDrawable(activity, R.drawable.ic_round_check_circle_day_night_24dp), null);
        } else if (currentSortType.equals(SortType.Type.OLD.fullName)) {
            oldTypeTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(oldTypeTextView.getCompoundDrawablesRelative()[0], null, AppCompatResources.getDrawable(activity, R.drawable.ic_round_check_circle_day_night_24dp), null);
        } else if (currentSortType.equals(SortType.Type.TOP.fullName)) {
            topTypeTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(topTypeTextView.getCompoundDrawablesRelative()[0], null, AppCompatResources.getDrawable(activity, R.drawable.ic_round_check_circle_day_night_24dp), null);
        }

        hotTypeTextView.setOnClickListener(view -> {
            ((SortTypeSelectionCallback) activity).sortTypeSelected(new SortType(SortType.Type.HOT));
            dismiss();
        });

        newTypeTextView.setOnClickListener(view -> {
            ((SortTypeSelectionCallback) activity).sortTypeSelected(new SortType(SortType.Type.NEW));
            dismiss();
        });

        oldTypeTextView.setOnClickListener(view -> {
            ((SortTypeSelectionCallback) activity).sortTypeSelected(new SortType(SortType.Type.OLD));
            dismiss();
        });

        topTypeTextView.setOnClickListener(view -> {
            ((SortTypeSelectionCallback) activity).sortTypeSelected(SortType.Type.TOP.name());
            dismiss();
        });


        if (activity.typeface != null) {
            Utils.setFontToAllTextViews(rootView, activity.typeface);
        }

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }
}
