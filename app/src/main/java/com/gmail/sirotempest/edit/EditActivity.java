package com.gmail.sirotempest.edit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import com.gmail.sirotempest.R;
import com.gmail.sirotempest.data.db.AppDatabase;
import com.gmail.sirotempest.data.db.entity.Product;
import com.gmail.sirotempest.utils.Constants;
import com.gmail.sirotempest.utils.Util;

public class EditActivity extends AppCompatActivity implements EditContract.View, EditContract.DateListener {

    private EditContract.Presenter mPresenter;

    private EditText mNameEditText;
    private EditText mBrandEditText;
    private EditText mPriceEditText;
    private EditText mExpiryDateEditText;
    private EditText mQuantityEditText;

    private TextInputLayout mNameTextInputLayout;
    private TextInputLayout mBrandTextInputLayout;
    private TextInputLayout mPriceTextInputLayout;
    private TextInputLayout mExpiryDateTextInputLayout;
    private TextInputLayout mQuantityTextInputLayout;

    private FloatingActionButton mFab;

    private Product product;
    private boolean mEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        product = new Product();
        checkMode();

        AppDatabase db = AppDatabase.getDatabase(getApplication());
        mPresenter = new EditPresenter(this, db.productModel());

        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mEditMode) {
            mPresenter.getProductAndPopulate(product.id);
        }
    }

    private void checkMode() {
        if (getIntent().getExtras() != null) {
            product.id = getIntent().getLongExtra(Constants.PRODUCT_ID, 0);
            mEditMode = true;
        }
    }

    private void initViews() {
        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mBrandEditText = (EditText) findViewById(R.id.brandEditText);
        mPriceEditText = (EditText) findViewById(R.id.priceEditText);
        mExpiryDateEditText = (EditText) findViewById(R.id.expiryDateEditText);
        mQuantityEditText = (EditText) findViewById(R.id.quantityEditText);

        mNameTextInputLayout = (TextInputLayout) findViewById(R.id.nameTextInputLayout);
        mBrandTextInputLayout = (TextInputLayout) findViewById(R.id.brandTextInputLayout);
        mPriceTextInputLayout = (TextInputLayout) findViewById(R.id.priceTextInputLayout);
        mExpiryDateTextInputLayout = (TextInputLayout) findViewById(R.id.expiryDateTextInputLayout);
        mQuantityTextInputLayout = (TextInputLayout) findViewById(R.id.quantityTextInputLayout);

        mExpiryDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showDateDialog();
            }
        });

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setImageResource(mEditMode ? R.drawable.ic_refresh : R.drawable.ic_done);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product.name = mNameEditText.getText().toString();
                product.brand = mBrandEditText.getText().toString();
                if (mPriceEditText.getText().toString().isEmpty()) {
                    product.price = 0;
                }
                else {
                    product.price = Integer.parseInt(mPriceEditText.getText().toString());
                }
                //product.price = Integer.parseInt(mPriceEditText.getText().toString());
                if (mQuantityEditText.getText().toString().isEmpty()) {
                    product.quantity = 0;
                }
                else {
                    product.quantity = Integer.parseInt(mQuantityEditText.getText().toString());
                }
                //product.quantity = Integer.parseInt(mQuantityEditText.getText().toString());

                boolean valid = mPresenter.validate(product);

                if (!valid) return;

                if (mEditMode) {
                    mPresenter.update(product);
                } else {
                    mPresenter.save(product);
                }
            }
        });
    }

    @Override
    public void setPresenter(EditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showErrorMessage(int field) {
        if (field == Constants.FIELD_NAME) {
            mNameTextInputLayout.setError(getString(R.string.invalid_name));
        } else if (field == Constants.FIELD_PRICE) {
            mPriceTextInputLayout.setError(getString(R.string.invalid_price));
        } else if (field == Constants.FIELD_QUANTITY) {
            mQuantityTextInputLayout.setError(getString(R.string.invalid_quantity));
        } else if (field == Constants.FIELD_BRAND) {
            mBrandTextInputLayout.setError(getString(R.string.invalid_brand));
        } else if (field == Constants.FIELD_EXPIRYDATE) {
            mExpiryDateTextInputLayout.setError(getString(R.string.invalid_date));
        }
    }

    @Override
    public void clearPreErrors() {
        mNameTextInputLayout.setErrorEnabled(false);
        mPriceTextInputLayout.setErrorEnabled(false);
        mQuantityTextInputLayout.setErrorEnabled(false);
        mBrandTextInputLayout.setErrorEnabled(false);
        mExpiryDateTextInputLayout.setErrorEnabled(false);
    }

    @Override
    public void openDateDialog() {
        DateDialogFragment fragment = new DateDialogFragment();
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void populate(Product product) {
        this.product = product;
        mNameEditText.setText(product.name);
        mBrandEditText.setText(product.brand);
        mPriceEditText.setText(String.valueOf(product.price));
        mExpiryDateEditText.setText(Util.format(product.expiryDate));
        mQuantityEditText.setText(String.valueOf(product.quantity));
        //setText(String.valueOf(mValues.get(position).quantity))
    }

    @Override
    public void setSelectedDate(Date date) {
        product.expiryDate = date;
        mExpiryDateEditText.setText(Util.format(date));
    }
}
