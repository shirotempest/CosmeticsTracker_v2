package com.gmail.sirotempest.edit;

import java.util.Date;

import com.gmail.sirotempest.BasePresenter;
import com.gmail.sirotempest.BaseView;
import com.gmail.sirotempest.data.db.entity.Product;

public interface EditContract {

    interface Presenter extends BasePresenter {
        void save(Product product);

        boolean validate(Product product);

        void showDateDialog();

        void getPersonAndPopulate(long id);

        void update(Product product);
    }

    interface View extends BaseView<EditContract.Presenter> {

        void showErrorMessage(int field);

        void clearPreErrors();

        void openDateDialog();

        void close();

        void populate(Product product);
    }

    interface DateListener {

        void setSelectedDate(Date date);

    }
}
