package com.main.dhbworld.Organizer;

import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.main.dhbworld.Navigation.NavigationUtilities;
import com.main.dhbworld.R;


public class OrganizerActivity extends AppCompatActivity {
    ListView listView;
    ViewPager2 viewPager;
    OrganizerFragmentAdapter organizerFragmentAdapter;
    MaterialToolbar toolbar;
    SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.organizer_layout);
            toolbar = findViewById(R.id.topAppBar);
            setSupportActionBar(toolbar);
            NavigationUtilities.setUpNavigation(this, R.id.organizer);
            if(isNetworkConnected()){
                createView();
            }
            else {
                displayError();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.organizer_top_app_bar, menu);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.organizerSearchIcon).getActionView();
        searchView.setQueryHint("Search in all Tabs");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchViewModel.setQuery(newText);
                return true;
            }
        });
        searchView.setOnCloseListener(() -> {
            searchViewModel.setQuery("");
            return false;
        });

       return super.onCreateOptionsMenu(menu);
    }

    public void createView() {
        TabLayout tabLayout = findViewById(R.id.organizerTabLayout);
        viewPager = findViewById(R.id.organizerViewPager);
        listView = findViewById(R.id.org_recylclerview);
        organizerFragmentAdapter = new OrganizerFragmentAdapter(this);
        viewPager.setAdapter(organizerFragmentAdapter);
        viewPager.setOffscreenPageLimit(2);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText(R.string.Courses);
                    tab.setIcon(R.drawable.ic_baseline_class_24);
                    break;
                case 1:
                    tab.setText(R.string.people);
                    tab.setIcon(R.drawable.ic_baseline_person_24);
                    break;
                case 2:
                    tab.setText(R.string.rooms);
                    tab.setIcon(R.drawable.ic_baseline_room_24);
                    break;
                default:
                    break;
            }
        });
        tabLayoutMediator.attach();

    }

    @Override
    public void onBackPressed(){
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public void displayError(){
        Snackbar.make(this.findViewById(android.R.id.content), "Network Error! Couldn't fetch data from Server.", BaseTransientBottomBar.LENGTH_LONG).show();
    }
}


