package vn.chapp.vn24h.ui.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vn.chapp.vn24h.R;
import vn.chapp.vn24h.models.service.CategoryAdd;

public class CategorySpinnerAdapter extends BaseAdapter {
    
    Context context;
    List<CategoryAdd> customerTypes;
    LayoutInflater inflter;

    public CategorySpinnerAdapter(Context applicationContext, List<CategoryAdd> customerTypes) {
        this.context = applicationContext;
        this.customerTypes = customerTypes;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return customerTypes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_product_category, null);
        TextView tvTypeCustomer = (TextView) view.findViewById(R.id.tvCategory);
        tvTypeCustomer.setText(customerTypes.get(i).getName());
        return view;
    }

    public List<CategoryAdd> getCustomerTypes() {
        return customerTypes;
    }
}
