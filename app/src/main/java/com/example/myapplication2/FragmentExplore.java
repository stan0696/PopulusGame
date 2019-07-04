package com.example.myapplication2;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication2.Tools.FindGame;

import com.example.myapplication2.View.Game;
import com.example.myapplication2.View.MyImageView;

import java.net.URI;
import java.util.ArrayList;


public class  FragmentExplore extends Fragment{
    private MyImageView icon;
    private TextView nametext;
    private String gamename;
    private View ROOTVIEW;
ArrayList<Game> tagGames;
FindGame findGame;

public FragmentExplore()
{

}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        ROOTVIEW=rootView;
        setview(rootView);


   return rootView;



    }
void setview(View rootView)
{

    setTagview("单机",rootView);
    setTagview("角色扮演",rootView);
    setTagview("动作",rootView);
    setTagview("MOBA",rootView);
    setTagview("策略",rootView);
    setTagview("卡牌",rootView);
    setTagview("生存",rootView);
    setTagview("模拟",rootView);
    setTagview("益智",rootView);
    setTagview("二次元",rootView);


}
void setTagview( String tag,View rootView)
{

    findGame=new FindGame(URI.create("123"),tag );
    tagGames = findGame.findgamebytag(tag, getContext());





    if(tagGames.size()>0)
    {


    for(int i=0;i<tagGames.size()&&i<9;i++)
    {
        String id=String.valueOf(i+1);

        gamename=tagGames.get(i).getName();


        nametext=rootView.findViewWithTag(tag+"nametext"+id);
        nametext.setText(tagGames.get(i).getName());

        nametext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text=ROOTVIEW.findViewWithTag(view.getTag());
                System.out.println(text.getText());
                Intent intent = new Intent(FragmentExplore.this.getContext(), GameinfoActivity.class);
                intent.putExtra("gamename",text.getText());

                startActivity(intent);

            }
        });


        icon= rootView.findViewWithTag(tag+"imageview"+id);
        icon.setTag(null);
        Glide.with(ROOTVIEW.getContext()).load(tagGames.get(i).getIcon()).animate(R.anim.item_alpha_in).thumbnail(0.1f).into(icon);
        //icon.setImageURL(tagGames.get(i).getIcon());
        icon.setContentDescription(tagGames.get(i).getName());
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyImageView iconimg=ROOTVIEW.findViewWithTag(view.getTag());
                Intent intent = new Intent(FragmentExplore.this.getContext(), GameinfoActivity.class);
                intent.putExtra("gamename",iconimg.getContentDescription());
                startActivity(intent);
            }
        });

    }


    }



}




}
