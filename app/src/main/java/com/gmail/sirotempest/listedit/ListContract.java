package com.gmail.sirotempest.listedit;

import java.util.List;

import com.gmail.sirotempest.BasePresenter;
import com.gmail.sirotempest.BaseView;
import com.gmail.sirotempest.data.db.entity.Product;

public interface ListContract {

    interface Presenter extends BasePresenter {

        void addNewProduct();

        void result(int requestCode, int resultCode);

        void displayProducts();

        void openEditScreen(Product product);

        void openConfirmDeleteDialog(Product product);

        //void filterStatus(int chosenStatus);

        void delete(long personId);
    }

    interface View extends BaseView<ListContract.Presenter> {

        void showAddProduct();

        void setProducts(List<Product> products);

        void showEditScreen(long id);

        void showDeleteConfirmDialog(Product product);

        void showEmptyMessage();
    }

    interface OnItemClickListener {

        void clickItem(Product product);

        void clickLongItem(Product product);
    }

    interface DeleteListener {

        void setConfirm(boolean confirm, long personId);

    }
}
