package com.bank.gestionstock.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bank.gestionstock.R;
import com.bank.gestionstock.fragments.ArticleList;
import com.bank.gestionstock.models.Article;
import com.bank.gestionstock.models.Operation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterArticles extends PagerAdapter implements View.OnClickListener{
    private ArrayList<Article> articles;
    private Context context;
    ArticleList.ArticleListEvent articleListener;
    public AdapterArticles(ArrayList<Article> articles, Context context, ArticleList.ArticleListEvent articleListener) {
        this.articles = articles;
        this.context  = context;
        this.articleListener = articleListener;
    }

    @Override
    public int getCount() {

        return articles.size();
    }
    public View instantiateItem(@NonNull ViewGroup container, int position) {

        View viewItem = LayoutInflater.from(context).inflate(R.layout.layout_for_swipe_articles,container,false);
        addArticleToView(viewItem,position);
        container.addView(viewItem);
        return viewItem;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
@Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    public void addArticleToView(View view,int position){

        CircleImageView articlePicture = view.findViewById(R.id.articlePicture);
        TextView articleName  = view.findViewById(R.id.articleName);
        TextView articleQuantity     = view.findViewById(R.id.articleQte);
        TextView articlePrice        = view.findViewById(R.id.articlePrice);
        Button buyBtn                = view.findViewById(R.id.buyNow);
        buyBtn.setTag(position);
        articlePicture.setImageResource(R.drawable.article);
        articleName.setText(articles.get(position).getLibelle());
        articleQuantity.setText(String.valueOf(articles.get(position).getQuantite()));
        articlePrice.setText(String.format("%s $",articles.get(position).getPrice()));
        buyBtn.setOnClickListener(this);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {


        final int pos   = (int) v.getTag();
        final Article article = articles.get(pos);
        final Dialog dialog  = new Dialog(context);
        dialog.setContentView(R.layout.dialog_buy_article);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(context.getDrawable(R.drawable.background));
        Button btnAdd   =  dialog.findViewById(R.id.buy);
        final EditText Qte    = dialog.findViewById(R.id.addQte);
//       Default Quantite
        Qte.setText("1");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qte =  Qte.getText().toString().trim();
                if(qte.isEmpty()){
                    Qte.setError("Please Fill Quantity Field");
                    return;
                }

                try{
//                Check If Insert Negative Quantity
                    if(Integer.parseInt(qte) <= 0){

                        Toast.makeText(context,"Please Choose Quantity Great Than a 0",Toast.LENGTH_SHORT).show();
                        return;
                    }

                }catch(NumberFormatException e){
                    Toast.makeText(context,"Wrong Number Format",Toast.LENGTH_SHORT).show();
                    return;
                }

//                Check If Has Enough Quantity In Stock
                if(article.getQuantite() - Integer.parseInt(qte)  < 0){

                    Toast.makeText(context,"Sorry we have only " + article.getQuantite() + " Item From this product",Toast.LENGTH_SHORT).show();
                    return;
                }

                Operation tr = new Operation(article.getId(), Integer.parseInt(qte), new SimpleDateFormat("MM-dd-yyyy",new Locale("en")).format(new Date()));
                ContentValues newQte = new ContentValues();
                newQte.put("qte",article.getQuantite() - Integer.parseInt(qte) );
                articleListener.addNewTransaction(tr,newQte,article.getLibelle());
                dialog.dismiss();
            }
        });
        dialog.show();


    }

}
