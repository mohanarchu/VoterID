package com.voterid.imagepload.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.voterid.aashi.voterid.R;
import com.voterid.imagepload.adapter.SearchAdapter;
import com.voterid.imagepload.pojo.SearchArray;

import java.util.ArrayList;

public class DropDownSelector extends PopupWindow implements ClassSelcterInterface {

    Context context;
    View view;
    int id;
    CommonSelectorListener commonSelectorListener;
    @SuppressLint("NewApi")
    public DropDownSelector(Context context, View view, ArrayList<SearchArray> arrays,
                            CommonSelectorListener homeworkListener, int id) {
        super(context);
        this.context = context;
        this.commonSelectorListener = homeworkListener;
        this.id = id;
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dropdown_view, null);
        setContentView(dialogView);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        final RecyclerView complaimntsRecycler;
        SearchView searchAcadamy = dialogView.findViewById(R.id.commonSearch);
        complaimntsRecycler = dialogView.findViewById(R.id.commomRecycler);
        TextView district  = dialogView.findViewById(R.id.party_district_choose);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
        gridLayoutManager.setSpanCount(1);
        if (id == 9) {
            district.setVisibility(View.VISIBLE);
        }
        searchAcadamy.setQueryHint("Search");
        searchAcadamy.setActivated(true);
        searchAcadamy.onActionViewExpanded();
        searchAcadamy.setFocusable(false);
        searchAcadamy.setIconified(false);
        searchAcadamy.clearFocus();
        complaimntsRecycler.setLayoutManager(gridLayoutManager);
        complaimntsRecycler.setItemAnimator(new DefaultItemAnimator());
        searchAcadamy.setVisibility(View.VISIBLE);

        SearchAdapter searchAdapter = new SearchAdapter(context,this);
        searchAcadamy.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText.toString().toLowerCase());
                return true;
            }
        });
        setItems(arrays,complaimntsRecycler,searchAdapter);
    }
    @SuppressLint("NewApi")
    public void setItems(ArrayList<SearchArray> arrays, RecyclerView recyclerView, SearchAdapter searchAdapter) {
        recyclerView.setAdapter(searchAdapter);
        searchAdapter.setItems(arrays);
        searchAdapter.notifyDataSetChanged();

    }

    public void show(View anchor) {
        showAtLocation(anchor, Gravity.CENTER, 0, 0);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        Utils.dimBehind(this);

    }

    @Override
    public void selectedClass(String selectedClass, int position, String id, String alternateID) {
        commonSelectorListener.selectedId(position,selectedClass,id,alternateID);
        dismiss();
    }
}
