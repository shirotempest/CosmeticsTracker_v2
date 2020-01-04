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
    private EditText mAddressEditText;
    private EditText mEmailEditText;
    private EditText mBirthdayEditText;
    private EditText mPhoneEditText;

    private TextInputLayout mNameTextInputLayout;
    private TextInputLayout mAddressInputLayout;
    private TextInputLayout mEmailInputLayout;
    private TextInputLayout mBirthdayInputLayout;
    private TextInputLayout mPhoneTextInputLayout;

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
        mPresenter = new EditPresenter(this, db.personModel());

        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mEditMode) {
            mPresenter.getPersonAndPopulate(product.id);
        }
    }

    private void checkMode() {
        if (getIntent().getExtras() != null) {
            product.id = getIntent().getLongExtra(Constants.PERSON_ID, 0);
            mEditMode = true;
        }
    }

    private void initViews() {
        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mAddressEditText = (EditText) findViewById(R.id.brandEditText);
        mEmailEditText = (EditText) findViewById(R.id.priceEditText);
        mBirthdayEditText = (EditText) findViewById(R.id.expiryDateEditText);
        mPhoneEditText = (EditText) findViewById(R.id.quantityEditText);

        mNameTextInputLayout = (TextInputLayout) findViewById(R.id.nameTextInputLayout);
        mAddressInputLayout = (TextInputLayout) findViewById(R.id.brandTextInputLayout);
        mEmailInputLayout = (TextInputLayout) findViewById(R.id.priceTextInputLayout);
        mBirthdayInputLayout = (TextInputLayout) findViewById(R.id.expiryDateTextInputLayout);
        mPhoneTextInputLayout = (TextInputLayout) findViewById(R.id.quantityTextInputLayout);

        mBirthdayEditText.setOnClickListener(new View.OnClickListener() {
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
                product.brand = mAddressEditText.getText().toString();
                product.price = Integer.parseInt(mEmailEditText.getText().toString());
                product.quantity = Integer.parseInt(mPhoneEditText.getText().toString());

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
        } else if (field == Constants.FIELD_EMAIL) {
            mEmailInputLayout.setError(getString(R.string.invalid_price));
        } else if (field == Constants.FIELD_PHONE) {
            mPhoneTextInputLayout.setError(getString(R.string.invalid_quantity));
        } else if (field == Constants.FIELD_ADDRESS) {
            mAddressInputLayout.setError(getString(R.string.invalid_brand));
        } else if (field == Constants.FIELD_BIRTHDAY) {
            mBirthdayInputLayout.setError(getString(R.string.invalid_date));
        }
    }

    @Override
    public void clearPreErrors() {
        mNameTextInputLayout.setErrorEnabled(false);
        mEmailInputLayout.setErrorEnabled(false);
        mPhoneTextInputLayout.setErrorEnabled(false);
        mAddressInputLayout.setErrorEnabled(false);
        mBirthdayInputLayout.setErrorEnabled(false);
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
        mAddressEditText.setText(product.brand);
        mEmailEditText.setText(product.price);
        mBirthdayEditText.setText(Util.format(product.expiryDate));
        mPhoneEditText.setText(product.quantity);
    }

    @Override
    public void setSelectedDate(Date date) {
        product.expiryDate = date;
        mBirthdayEditText.setText(Util.format(date));
    }
}
