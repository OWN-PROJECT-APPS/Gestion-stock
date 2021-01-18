package com.bank.gestionstock.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.bank.gestionstock.R;
import com.bank.gestionstock.adapters.AdapterArticles;
import com.bank.gestionstock.models.Article;
import com.bank.gestionstock.models.Operation;

import java.util.ArrayList;
import java.util.Objects;

public class ArticleList extends Fragment{

    private ArrayList<Article> articles ;
    private ArticleListEvent articleListener;
    private ArrayList<Integer> listOfColors = new ArrayList<>();
    private AdapterArticles articleAdapter;


    public interface ArticleListEvent{

        void addNewTransaction(Operation transaction, ContentValues article,String articleName);

    }
    public ArticleList(ArrayList<Article> articles) {

        this.articles = articles;
        listOfColors.add(android.R.color.holo_orange_dark);
        listOfColors.add(android.R.color.holo_orange_light);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        articleListener = (ArticleListEvent) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.viewPagerArticles);
        articleAdapter = new AdapterArticles(articles ,getActivity(),articleListener);
        viewPager.setAdapter(articleAdapter);
        viewPager.setPageTransformer(false,new ForegroundToBackgroundTransformer());


    }

    public void notifyDataChanged(int newQte,int articleId){
        for (Article item : articles) {
            if (item.getId() == articleId) {
                item.setQuantite(newQte);
                articleAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    public void addNewArticle(Article article){

            articles.add(article);
            articleAdapter.notifyDataSetChanged();

    }

}