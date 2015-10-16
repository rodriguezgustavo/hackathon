package com.ml.hackathon.ui.views.util;

import com.ml.hackathon.domain.Order;
import com.ml.hackathon.domain.Shipper;
import com.ml.hackathon.util.PropertyReader;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.lang.reflect.Method;
import java.util.*;


public class LazyOrdersDataModel extends LazyDataModel<Order> {

    private List<Order> datasource;

    public LazyOrdersDataModel(List<Order> datasource) {
        this.datasource = datasource;
    }

    @Override
    public Order getRowData(String rowKey) {
        for(Order order : datasource) {
            if(String.valueOf(order.getId()).equals(rowKey))
                return order;
        }

        return null;
    }

    @Override
    public Object getRowKey(Order order) {
        return order.getId();
    }

    @Override
    public List<Order> load(int first, int pageSize,final String sortField,final SortOrder sortOrder, Map<String,Object> filters) {
        try {
            List<Order> data = new ArrayList<>();

            //filter
            for (Order order : datasource) {
                boolean match = true;

                if (filters != null) {
                    for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                        try {
                            String filterProperty = it.next();
                            String filterValue = filters.get(filterProperty).toString();

                            String fieldValue = String.valueOf(PropertyReader.getGetter(Shipper.class, filterProperty).invoke(order));

                            if (filterValue == null || fieldValue.toLowerCase().contains(filterValue.toLowerCase())) {
                                match = true;
                            } else {
                                match = false;
                                break;
                            }
                        } catch (Exception e) {
                            match = false;
                        }
                    }
                }

                if (match) {
                    data.add(order);
                }
            }

            //sort
            if (sortField != null) {
                Collections.sort(data, new Comparator<Order>() {
                    @Override
                    public int compare(Order order1, Order order2) {
                        try {
                            Method method=PropertyReader.getGetter(Shipper.class, sortField);
                            Object value1=method.invoke(order1);
                            Object value2=method.invoke(order2);
                            if(value1==value2){
                                return 0;
                            }
                            if(value1==null){
                                return -1;
                            }
                            if(value2==null){
                                return 1;
                            }
                            int value = ((Comparable)value1).compareTo(value2);
                            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
                        }
                        catch(Exception e) {
                            throw new RuntimeException();
                        }
                    }
                });
            }

            //rowCount
            int dataSize = data.size();
            this.setRowCount(dataSize);

            //paginate
            if (dataSize > pageSize) {
                try {
                    return data.subList(first, first + pageSize);
                } catch (IndexOutOfBoundsException e) {
                    return data.subList(first, first + (dataSize % pageSize));
                }
            } else {
                return data;
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
