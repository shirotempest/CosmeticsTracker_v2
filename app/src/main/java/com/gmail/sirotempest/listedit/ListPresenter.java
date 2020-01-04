package com.gmail.sirotempest.listedit;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.List;

import com.gmail.sirotempest.data.db.dao.ProductDao;
import com.gmail.sirotempest.data.db.entity.Product;

public class ListPresenter implements ListContract.Presenter {

    private final ListContract.View mView;
    private final ProductDao productDao;

    public ListPresenter(ListContract.View view, ProductDao productDao) {
        this.mView = view;
        this.mView.setPresenter(this);
        this.productDao = productDao;
    }

    @Override
    public void start() {

    }

    @Override
    public void addNewPerson() {
        mView.showAddPerson();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void populatePeople() {
        productDao.findAllPersons().observeForever(new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                mView.setPersons(products);
                if (products == null || products.size() < 1) {
                    mView.showEmptyMessage();
                }
            }
        });
    }

    @Override
    public void openEditScreen(Product product) {
        mView.showEditScreen(product.id);
    }

    @Override
    public void openConfirmDeleteDialog(Product product) {
        mView.showDeleteConfirmDialog(product);
    }

    @Override
    public void delete(long personId) {
        Product product = productDao.findPerson(personId);
        productDao.deletePerson(product);
    }
}
