package com.gmail.sirotempest.edit;

import com.gmail.sirotempest.data.db.dao.ProductDao;
import com.gmail.sirotempest.data.db.entity.Product;
import com.gmail.sirotempest.utils.Constants;
import com.gmail.sirotempest.utils.Util;

public class EditPresenter implements EditContract.Presenter {

    private final EditContract.View mView;
    private final ProductDao productDao;

    public EditPresenter(EditContract.View mMainView, ProductDao productDao) {
        this.mView = mMainView;
        this.mView.setPresenter(this);
        this.productDao = productDao;
    }

    @Override
    public void start() {

    }

    @Override
    public void save(Product product) {
        long ids = this.productDao.insertPerson(product);
        mView.close();
    }

    @Override
    public boolean validate(Product product) {
        mView.clearPreErrors();
        if (product.name.isEmpty() || !Util.isValidName(product.name)) {
            mView.showErrorMessage(Constants.FIELD_NAME);
            return false;
        }
        if (product.brand.isEmpty()) {
            mView.showErrorMessage(Constants.FIELD_ADDRESS);
            return false;
        }
        if (product.quantity == 0) {
            mView.showErrorMessage(Constants.FIELD_PHONE);
            return false;
        }
        if (product.price == 0) {
            mView.showErrorMessage(Constants.FIELD_EMAIL);
            return false;
        }
        if (product.expiryDate == null) {
            mView.showErrorMessage(Constants.FIELD_BIRTHDAY);
            return false;
        }

        return true;
    }

    @Override
    public void showDateDialog() {
        mView.openDateDialog();
    }

    @Override
    public void getPersonAndPopulate(long id) {
        Product product = productDao.findPerson(id);
        if (product != null) {
            mView.populate(product);
        }
    }

    @Override
    public void update(Product product) {
        int ids = this.productDao.updatePerson(product);
        mView.close();
    }
}
