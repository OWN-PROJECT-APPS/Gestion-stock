package com.bank.gestionstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.app.Dialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.ToxicBakery.viewpager.transforms.TabletTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;
import com.bank.gestionstock.adapters.AdapterViewPager;
import com.bank.gestionstock.fragments.ArticleList;
import com.bank.gestionstock.fragments.OperationList;
import com.bank.gestionstock.models.Article;
import com.bank.gestionstock.models.Database;
import com.bank.gestionstock.models.Operation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        ArticleList.ArticleListEvent,
        BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {
    ViewPager viewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> namesOfFragments = new ArrayList<>();
    BottomNavigationView bottomNavigation;
    ArticleList fragmentOne;
    OperationList fragmentTwo;
    TabLayout tabLayout;
    EditText articleName, articlePrice, articleQte;
    BottomSheetDialog bottomSheetDialog;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        database    = new Database(this,null,null,1);
        fragmentOne = new ArticleList(database.getArticles());
        fragmentTwo = new OperationList(database.getOperations());
        fragments.add(fragmentOne);
        fragments.add(fragmentTwo);
        namesOfFragments.add("Market");
        namesOfFragments.add("Transactions");
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new AdapterViewPager(getSupportFragmentManager(), 0, fragments, namesOfFragments));
        viewPager.setPageTransformer(true, new ZoomOutTranformer());

    }

    @Override
    public void addNewTransaction(Operation transaction, ContentValues article,String articleName) {
        // Get Current Operation
        long status = database.addOperation(transaction,article);
        if(status == 0){
            Toast.makeText(this,"Unsuccessfully Added",Toast.LENGTH_SHORT).show();
        }else {
            fragmentTwo.addNewTransaction(new Operation((int) status, articleName,transaction.getArticleId() ,transaction.getQuantite(), transaction.getDate()));
            fragmentOne.notifyDataChanged(article.getAsInteger("qte"),transaction.getArticleId());
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.addArticle:

                 bottomSheetDialog = new BottomSheetDialog(this);
                View layout = getLayoutInflater().inflate(R.layout.layout_add_new_article, null);
                Button addArticle;
                articleName  = layout.findViewById(R.id.addArticleName);
                articlePrice = layout.findViewById(R.id.addArticlePrice);
                articleQte   = layout.findViewById(R.id.addArticleQte);
                addArticle   = layout.findViewById(R.id.addNewArticle);
                bottomSheetDialog.setContentView(layout, new RelativeLayout.LayoutParams(getWindow().getDecorView().getWidth(), getWindow().getDecorView().getHeight()));
                bottomSheetDialog.setDismissWithAnimation(true);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.show();
                addArticle.setOnClickListener(this);
                break;

            case R.id.info:
                Toast.makeText(this, "Application Under Construction", Toast.LENGTH_SHORT).show();
                Dialog dialog = new Dialog(this);

                break;

        }
        return true;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.addNewArticle){

                String  nameOfArticle =  articleName.getText().toString().trim();
                String  qte           =  articleQte.getText().toString().trim();
                String  price         =  articlePrice.getText().toString().trim();

                if(nameOfArticle.isEmpty()){

                    articleName.setError("Fill Article Name");
                    return;
                }
                if(qte.equals("")){
                    articleQte.setError("Fill Quantity Field");
                    return;
                }

                if(price.equals("")){
                    articlePrice.setError("Fill Price Field");
                    return;
            }
             if(Integer.parseInt(qte) <= 0){

                 articleQte.setError("Add Value Greater Than a 0");
                 return;
             }
            if(Double.parseDouble(price) <= 0){

                articlePrice.setError("Add Value Greater Than a 0");
                return;
            }

            long status = database.addArticle(new Article(nameOfArticle,Integer.parseInt(qte),Double.parseDouble(price)));
            if(status == 0){
                Toast.makeText(this,"Unsuccessfully Added",Toast.LENGTH_SHORT).show();
            }else{

                Snackbar.make(viewPager, "Article Added Successfully", Snackbar.LENGTH_SHORT).show();
                fragmentOne.addNewArticle(new Article((int)status,nameOfArticle,Integer.parseInt(qte),Double.parseDouble(price)));
            }

            bottomSheetDialog.dismiss();
        }
    }

}