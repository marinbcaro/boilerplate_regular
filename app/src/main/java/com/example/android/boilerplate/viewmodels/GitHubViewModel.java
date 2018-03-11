package com.example.android.boilerplate.viewmodels;

import android.databinding.BaseObservable;
import android.util.Log;

import com.example.android.boilerplate.ICallback;
import com.example.android.boilerplate.model.GitRepo;
import com.example.android.boilerplate.network.Repository;

import org.reactivestreams.Subscription;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by carolinamarin on 2/3/18.
 */

public class GitHubViewModel extends BaseObservable {


    private Repository repository;

    private ICallback callback;
    private Subscription subscription;

    public GitHubViewModel(ICallback callback, Repository repository) {
        this.callback = callback;
        this.repository = repository;

    }

    public void getData() {
        Call<List<GitRepo>> call= repository.getListItems("codepath");
                call.enqueue(new Callback<List<GitRepo>>() {
            @Override
            public void onResponse(Call<List<GitRepo>> call, Response<List<GitRepo>> response) {
                if (response.isSuccessful()) {
                   List<GitRepo> list=response.body();
                    showResults(list);

                } else {
                    Log.i("ERROR", String.valueOf(response.code()));
                }
            }
            @Override
            public void onFailure(Call<List<GitRepo>> call, Throwable t) {

                showError();
               // Log.i("ERROR", String.valueOf(t));

            }
        });

    }

    public void showResults(List data) {
        callback.showResults(data);
    }

    public void showError() {
        callback.showError();
    }
}
