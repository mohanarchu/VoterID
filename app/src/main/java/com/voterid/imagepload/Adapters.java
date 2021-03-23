package com.voterid.imagepload;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.voterid.aashi.voterid.R;
import com.voterid.aashi.voterid.databinding.ImageviewBinding;
import com.voterid.imagepload.activity.BoothSelectionActivity;
import com.voterid.imagepload.pojo.BoothsPojo;
import com.voterid.imagepload.pojo.SearchArray;
import com.voterid.imagepload.pojo.UserPojo;
import com.voterid.imagepload.sesssion.MainArray;
import com.voterid.imagepload.view.CommonSelectorListener;
import com.voterid.imagepload.view.DropDownSelector;

import java.util.ArrayList;
import java.util.Collections;

import static com.voterid.imagepload.ImagesUpload.scaleBitmaps;


public class Adapters extends RecyclerView.Adapter<Adapters.ViewHolder> implements CommonSelectorListener {

        OnItemReadInterface onItemReadInterface;
        ArrayList<Bitmap> chunkedImages;
        int selectedPosition;
        int selected = 0;
        ArrayList<UserPojo.Result> mainArrayArrayList;
        void setList( ArrayList<Bitmap> chunkedImages, ArrayList<UserPojo.Result> mainArrayArrayList){
            this.mainArrayArrayList = mainArrayArrayList;
            this.chunkedImages = chunkedImages;
        }

        @NonNull
        @Override
        public Adapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ImageviewBinding imageviewBinding =ImageviewBinding.inflate( LayoutInflater.from(viewGroup.getContext()));
            return new Adapters.ViewHolder(imageviewBinding);
        }
        @Override
        public void onBindViewHolder(@NonNull Adapters.ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {


            viewHolder.imageviewBinding.read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemReadInterface.selectedImage(i,chunkedImages.get(i));
                }
            });

            viewHolder.imageviewBinding.upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemReadInterface.uploadData(mainArrayArrayList.get(i),i);
                }
            });
            viewHolder.imageviewBinding.mailImages.setImageBitmap(scaleBitmaps(chunkedImages.get(i), 1000, 1000));
            viewHolder.imageviewBinding.userId.setText(getNull(mainArrayArrayList.get(i).getEpicId()));
            viewHolder.imageviewBinding.names.setText( getNull(mainArrayArrayList.get(i).getFm_name_english())+"/"+ getNull(mainArrayArrayList.get(i).getFm_name_tamil()));
            viewHolder.imageviewBinding.fatherName.setText(getNull(mainArrayArrayList.get(i).getRelation_firstname_english())+"/"+getNull(mainArrayArrayList.get(i).getRelation_firstname_tamil()));
            viewHolder.imageviewBinding.age.setText(getNull(mainArrayArrayList.get(i).getAge()));
            viewHolder.imageviewBinding.gendre.setText(getNull(mainArrayArrayList.get(i).getGendre()));
            viewHolder.imageviewBinding.gaurdianType.setText(getNull(mainArrayArrayList.get(i).getRelation_type()));
            viewHolder.imageviewBinding.partyAff.setText(getNull(mainArrayArrayList.get(i).getParty_affiliation()));
            viewHolder.imageviewBinding.canvasType.setText(getNull(mainArrayArrayList.get(i).getCanvass_type()));



        }
        @Override
        public int getItemCount() {
            return chunkedImages.size();
        }

        @Override
        public void selectedId(int position, String name, String selectedId, String alternateId) {
            if (selected ==  0) {
                mainArrayArrayList.get(selectedPosition).setCanvass_type(name);
                notifyItemChanged(selectedPosition);
            } else if (selected == 1) {
                mainArrayArrayList.get(selectedPosition).setParty_affiliation(name);
                notifyItemChanged(selectedPosition);
            }
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            ImageviewBinding imageviewBinding;
            public ViewHolder(@NonNull  ImageviewBinding imageviewBinding) {
                super(imageviewBinding.getRoot());
                this.imageviewBinding = imageviewBinding;
                imageviewBinding.canvasType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<SearchArray> searchArrays = new ArrayList<>();
                        searchArrays.add(new SearchArray("Phone call","",""));
                        searchArrays.add(new SearchArray("Direct Visit","",""));
                        searchArrays.add(new SearchArray("Other","",""));
                        DropDownSelector dropDownSelector = new
                                DropDownSelector(itemView.getContext(),  imageviewBinding.canvasType,
                                searchArrays, Adapters.this, 2);
                        dropDownSelector.show(imageviewBinding.canvasType);
                        selected = 0;
                        selectedPosition = getAdapterPosition();
                    }
                });
                imageviewBinding.partyAff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<SearchArray> searchArrays = new ArrayList<>();
                        searchArrays.add(new SearchArray("Official/அதிகாரி","",""));
                        searchArrays.add(new SearchArray("Member/உறுப்பினர்","",""));
                        searchArrays.add(new SearchArray("leaning/அனுதாபி","",""));
                        searchArrays.add(new SearchArray("Others/மற்றவை","",""));
                        DropDownSelector dropDownSelector = new DropDownSelector(itemView.getContext(),  imageviewBinding.partyAff, searchArrays, Adapters.this, 2);
                        dropDownSelector.show(imageviewBinding.partyAff);
                        selected = 1;
                        selectedPosition = getAdapterPosition();
                    }
                });
            }
        }

        public void setItem(UserPojo.Result mainArray,int postion) {
            mainArrayArrayList.set(postion,mainArray);
            notifyItemChanged(postion);
        }

        public void removeItem(int postion) {
            mainArrayArrayList.remove(postion);
            notifyItemChanged(postion);
        }

        public void setInterface(OnItemReadInterface onItemReadInterface) {
            this.onItemReadInterface  = onItemReadInterface;
        }

        interface  OnItemReadInterface {
            void selectedImage(int position,Bitmap bitmap);
            void uploadData(UserPojo.Result result,int position);
        }

        String getNull(String value) {
          return value == null ? "- " : value;
        }

}