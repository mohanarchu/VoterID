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
import com.voterid.imagepload.sesssion.MainArray;

import java.util.ArrayList;

import static com.voterid.imagepload.ImagesUpload.scaleBitmaps;

public class Adapters extends RecyclerView.Adapter<Adapters.ViewHolder>
    {

        OnItemReadInterface onItemReadInterface;
        ArrayList<Bitmap> chunkedImages;
        ArrayList<MainArray> mainArrayArrayList;
        void setList( ArrayList<Bitmap> chunkedImages, ArrayList<com.voterid.imagepload.sesssion.MainArray> mainArrayArrayList){
            this.mainArrayArrayList = mainArrayArrayList;
            this.chunkedImages = chunkedImages;
        }

        @NonNull
        @Override
        public Adapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View        view   = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.imageview, viewGroup, false);
            return new Adapters.ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull Adapters.ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {


            viewHolder.read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemReadInterface.selectedImage(i,chunkedImages.get(i));
                }
            });
            viewHolder.upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainArrayArrayList.remove(i);
                    chunkedImages.remove(i); 
                     notifyItemChanged(i);
                }
            });
            viewHolder.image.setImageBitmap(scaleBitmaps(chunkedImages.get(i), 1000, 1000));
            String aId="",aName="",aFather="",aGendre="",aAge ="",door="door",alldata="" ;
            if (mainArrayArrayList.get(i).getVoterid() != null)
            {
                viewHolder.id.setText(mainArrayArrayList.get(i).getVoterid());
            }
            else
            {

                viewHolder.id.setText("Error finding..!");
            }


            if (mainArrayArrayList.get(i).getName() != null)
            {

                viewHolder.name.setText(mainArrayArrayList.get(i).getName() .replace("பெயர் ","").trim());
            }
            else
            {

                viewHolder.name.setText("Error finding..!");
            }

                if (mainArrayArrayList.get(i).getSex() != null)
                {

                    viewHolder.gendre.setText(mainArrayArrayList.get(i).getSex().replace("பாலினம் ","").trim());
                }
                else
                {

                    viewHolder.gendre.setText("Cant find data..!");
                }
                if (mainArrayArrayList.get(i).getAge() != null)
                {

                    viewHolder.age.setText(mainArrayArrayList.get(i).getSex() .replace("வயது ","").trim());
                }
                else
                {
                    viewHolder.age.setText("Cant find data..!");
                }

           if (mainArrayArrayList.get(i).getFathername()!= null)
           {
               if ( mainArrayArrayList.get(i).getFathername() .contains("தந்தை பெயர்") )
               {

                   viewHolder.fathername.setText(mainArrayArrayList.get(i).getFathername().replace("தந்தை பெயர் ","").trim());
                   viewHolder.nameTexts.setText("தந்தை பெயர்");
               }
               else if (mainArrayArrayList.get(i).getFathername().contains("தாய் பெயர்"))
               {

                   viewHolder.fathername.setText(mainArrayArrayList.get(i).getFathername().replace("தாய் பெயர்","").trim());
                   viewHolder.nameTexts.setText("தாய் பெயர்");
               }
               else if (mainArrayArrayList.get(i).getFathername().contains("கணவர் பெயர்"))
               {

                   viewHolder.fathername.setText(mainArrayArrayList.get(i).getFathername().replace("கணவர் பெயர்","").trim());
                   viewHolder.nameTexts.setText("கணவர் பெயர்");
               }
               else if (mainArrayArrayList.get(i).getFathername().contains("இதார் பெயர்"))
               {

                   viewHolder.fathername.setText(mainArrayArrayList.get(i).getFathername().replace("இதார் பெயர்","").trim());
                   viewHolder.nameTexts.setText("இதார் பெயர்");
               }
               else
               {
                   viewHolder.fathername.setText(mainArrayArrayList.get(i).getFathername().replace("பெயர்","").trim());
                   viewHolder.nameTexts.setText("Father/Husband name");
               }

           }
           else
           {
               aFather  = "Error finding";
           }
           String alldatass ="";




        }
        @Override
        public int getItemCount() {
            return chunkedImages.size();
        }





        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView id,name,fathername,age,gendre,nameTexts,read;
            CardView upload;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                upload = (CardView)itemView. findViewById(R.id.upload);
                read = itemView.findViewById(R.id.read);
                nameTexts =(TextView)itemView.findViewById(R.id.nameTexts);
                id =(TextView)itemView.findViewById(R.id.userId);
                name =(TextView)itemView.findViewById(R.id.names);
                fathername =(TextView)itemView.findViewById(R.id.username);
                image =(ImageView) itemView.findViewById(R.id.mailImages);
                age =(TextView)itemView.findViewById(R.id.age);
                gendre =(TextView)itemView.findViewById(R.id.gendre);
            }
        }

        public void setItem(MainArray mainArray,int postion) {
            mainArrayArrayList.set(postion,mainArray);
            notifyItemChanged(postion);
        }

        public void setInterface(OnItemReadInterface onItemReadInterface) {
            this.onItemReadInterface  = onItemReadInterface;
        }

        interface  OnItemReadInterface {
            void selectedImage(int position,Bitmap bitmap);
        }

}