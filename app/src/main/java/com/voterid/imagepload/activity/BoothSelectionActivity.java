package com.voterid.imagepload.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.voterid.aashi.voterid.R;
import com.voterid.aashi.voterid.databinding.ActivityBoothSelectionBinding;
import com.voterid.imagepload.ImagesUpload;
import com.voterid.imagepload.activity.presenter.BoothPresenter;
import com.voterid.imagepload.pojo.AreaPojo;
import com.voterid.imagepload.pojo.AssemblyPojo;
import com.voterid.imagepload.pojo.BoothsPojo;
import com.voterid.imagepload.pojo.SearchArray;
import com.voterid.imagepload.view.CommonSelectorListener;
import com.voterid.imagepload.view.DropDownSelector;

import java.util.ArrayList;
import java.util.Collections;


public class BoothSelectionActivity extends BaseActivity implements BoothPresenter.BoothInterface , CommonSelectorListener {



    int selected = 0;
    String assembyId = "";
    String bothId = "";
    String areaId = "";
    ArrayList<SearchArray> searchArrays = new ArrayList<>();
    ActivityBoothSelectionBinding boothSelectionBinding;
    BoothPresenter boothPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boothSelectionBinding = ActivityBoothSelectionBinding.inflate(getLayoutInflater());
        setContentView(boothSelectionBinding.getRoot());
        boothPresenter = new BoothPresenter(this);
        boothSelectionBinding.assembly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected= 0;
                boothPresenter.getAssembly();
            }
        });
        boothSelectionBinding.booth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (assembyId.isEmpty()) {
                    error("Select Assembly constituency");
                    return;
                }
                selected= 1;
                boothPresenter.getBooths();
            }
        });
        boothSelectionBinding.area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (assembyId.isEmpty()) {
                    error("Select Assembly constituency");
                    return;
                }
                selected= 2;
                boothPresenter.getArea(bothId);
            }
        });

        boothSelectionBinding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bothId.isEmpty()) {
                    error("Please select the booth");
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), ImagesUpload.class);
                intent.putExtra("boothId",bothId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected String title() {
        return null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showHideProgress() {

    }

    @Override
    public void error(String message) {


        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void area(AreaPojo.Result[] results) {
        searchArrays.clear();
        for (AreaPojo.Result result : results) {
            searchArrays.add(new SearchArray(result.getBooth_name().trim(),
                    result.getArea_name(), ""));
        }
        Collections.sort(searchArrays, (searchArray, t1) -> searchArray.getName().compareTo(t1.getName()));
        DropDownSelector dropDownSelector = new
                DropDownSelector(BoothSelectionActivity.this, boothSelectionBinding.area,
                searchArrays, this, 1);
        dropDownSelector.show(boothSelectionBinding.area);
    }

    @Override
    public void assembly(AssemblyPojo.Result[] results) {
        searchArrays.clear();
        for (AssemblyPojo.Result result : results) {
            searchArrays.add(new SearchArray(result.getAssembly_constituency_name().trim(),
                    result.getAssembly_constituency_number(), ""));
        }
        Collections.sort(searchArrays, (searchArray, t1) -> searchArray.getName().compareTo(t1.getName()));
        DropDownSelector dropDownSelector = new
                DropDownSelector(BoothSelectionActivity.this, boothSelectionBinding.assembly,
                searchArrays, this, 1);
        dropDownSelector.show(boothSelectionBinding.assembly);
    }

    @Override
    public void showBooth(BoothsPojo.Result[] results) {
        searchArrays.clear();
        for (BoothsPojo.Result result : results) {
            searchArrays.add(new SearchArray(result.getBooth_name().trim(),
                    result.getBooth_number(), ""));
        }
        Collections.sort(searchArrays, (searchArray, t1) -> searchArray.getName().compareTo(t1.getName()));
        DropDownSelector dropDownSelector = new
                DropDownSelector(BoothSelectionActivity.this, boothSelectionBinding.booth,
                searchArrays, this, 2);
        dropDownSelector.show(boothSelectionBinding.booth);
    }

    @Override
    public void selectedId(int position, String name, String selectedId, String alternateId) {

        if (selected == 0) {
            boothSelectionBinding.assembly.setText(name);
            assembyId = selectedId;
        } else if (selected == 1) {
            bothId = selectedId;
            boothSelectionBinding.booth.setText(name);
        }else if (selected == 2) {
            areaId = selectedId;
            boothSelectionBinding.area.setText(name);
        }
    }
}