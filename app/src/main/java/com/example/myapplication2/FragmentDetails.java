package com.example.myapplication2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.example.myapplication2.R;

public class FragmentDetails extends Fragment {
    private String[] imgurl;
    private View rootView;
    private ImageView imageView_introduce0;
    private ImageView imageView_introduce1;
    private ImageView imageView_introduce2;
    private ImageView imageView_introduce3;
    private ImageView imageView_introduce4;
    public  FragmentDetails(String[] imgurl){
        this.imgurl=imgurl;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_details, container, false);
        getdata();
        return rootView;
    }

    private void getdata() {
        imageView_introduce1=rootView.findViewById(R.id.photocut_1);
        imageView_introduce2=rootView.findViewById(R.id.photocut_2);
        imageView_introduce3=rootView.findViewById(R.id.photocut_3);
        imageView_introduce4=rootView.findViewById(R.id.photocut_4);
        imageView_introduce0=rootView.findViewById(R.id.photocut_5);
        System.out.println(imageView_introduce0.getHeight());
        imageView_introduce1.setTag(null);
        imageView_introduce2.setTag(null);
        imageView_introduce3.setTag(null);
        imageView_introduce4.setTag(null);
        imageView_introduce0.setTag(null);
        Glide.with(this.getContext()).load(imgurl[1]).asBitmap().animate(R.anim.item_alpha_in).centerCrop().into(new TransformationUtils(imageView_introduce1));
        Glide.with(this.getContext()).load(imgurl[2]).asBitmap().animate(R.anim.item_alpha_in).centerCrop().into(new TransformationUtils(imageView_introduce2));
        Glide.with(this.getContext()).load(imgurl[3]).asBitmap().animate(R.anim.item_alpha_in).centerCrop().into(new TransformationUtils(imageView_introduce3));
        Glide.with(this.getContext()).load(imgurl[4]).asBitmap().animate(R.anim.item_alpha_in).centerCrop().into(new TransformationUtils(imageView_introduce4));
        Glide.with(this.getContext()).load(imgurl[0]).asBitmap().animate(R.anim.item_alpha_in).centerCrop().into(new TransformationUtils(imageView_introduce0));
    }
}
 class TransformationUtils extends ImageViewTarget<Bitmap> {

    private ImageView target;

    public TransformationUtils(ImageView target) {
        super(target);
        this.target = target;
    }

    @Override
    protected void setResource(Bitmap resource) {
        view.setImageBitmap(resource);

        //获取原图的宽高
        int width = resource.getWidth();
        int height = resource.getHeight();

        //获取imageView的高
        int imageViewHeight = target.getHeight();
        System.out.println(imageViewHeight+""+height);
        //计算缩放比例
        float sy = (float) (imageViewHeight * 0.1) / (float) (height * 0.1);
        System.out.println(sy);
        //计算图片等比例放大后的宽
        int imageViewWidth = (int) (width*sy);
        System.out.println(imageViewWidth);
        ViewGroup.LayoutParams params = target.getLayoutParams();
        params.width= imageViewWidth;
        target.setLayoutParams(params);
    }
}
