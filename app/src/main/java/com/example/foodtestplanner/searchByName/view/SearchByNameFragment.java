package com.example.foodtestplanner.searchByName.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.foodtestplanner.R;
import com.example.foodtestplanner.model.network.Repo.MealRepositoryImpl;
import com.example.foodtestplanner.model.dto.MealsItem;
import com.example.foodtestplanner.model.network.network.MealRemotDataSourceImp;
import com.example.foodtestplanner.model.network.db.MealLocalDataSourceImpl;
import com.example.foodtestplanner.searchByName.presenter.SearchMealNameImp;
import com.example.foodtestplanner.searchByName.presenter.SearchmealNamePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class SearchByNameFragment extends Fragment implements OnSearchclick, SearchMealView {
    private Context context;
    private SearchMealAdapter searchByNameAdapter;
    private RecyclerView recyclerView;
    private Single<List<MealsItem>> searchByNameList;
    private SearchmealNamePresenter searchByNamePresenterView;
    private CardView searchByNameCardView;
    private EditText editText;
    private List<MealsItem> originalList;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_by_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recyclerviewname);
        editText=view.findViewById(R.id.edittextname);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext()
        ,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        searchByNameAdapter=new SearchMealAdapter(getContext(),new ArrayList<>(),this);
        recyclerView.setAdapter(searchByNameAdapter);
        searchByNamePresenterView=new SearchMealNameImp(MealRepositoryImpl.getInstance(MealRemotDataSourceImp
           .getInstance()
         , MealLocalDataSourceImpl.getInstance(getContext())), this);

        searchByNamePresenterView.getMealName(" ").subscribe(
        response -> {
            List<MealsItem> items = response.getRandomMealList();
            originalList = items;
            searchByNameAdapter.setSearchMealItem(originalList);
            searchByNameAdapter.notifyDataSetChanged();
        },
                throwable -> {
                    Log.e("SearchByNameFragment", "Error fetching meal list: " + throwable.getMessage());
                }
                );
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().trim(); // Get the search text

                if (originalList == null) {
                    // Original list is null, cannot perform filtering
                    return;
                }

                if (searchText.isEmpty()) {
                    // If the search text is empty, show the original list
                    searchByNameAdapter.setSearchMealItem(originalList);
                } else {
                    // Filter the original list based on the search text
                    List<MealsItem> filteredList = new ArrayList<>();
                    for (MealsItem item : originalList) {
                        if (item.getStrMeal().toLowerCase().contains(searchText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    // Update the adapter with the filtered list
                    searchByNameAdapter.setSearchMealItem(filteredList);
                }
                // Notify the adapter of the data change
                searchByNameAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
CompositeDisposable compositeDisposable1e=new CompositeDisposable();
        compositeDisposable1e.dispose();
    }

    @Override
    public void onSearchClickMeal(MealsItem mealsItem) {
Bundle bundle=new Bundle();
bundle.putSerializable("SearchByName",mealsItem);
 Navigation.findNavController(requireView())
 .navigate(R.id.action_searchByNameFragment_to_listDetailsFragment,bundle);
    }

    @Override
    public void searchMealName(List<MealsItem> searchMealsItemList) {

    }

    @Override
    public void searchMealError(String error) {

    }
}