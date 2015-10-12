package com.ml.hackathon.ui.views.util;

 import java.lang.reflect.Method;
 import java.util.*;

 import com.ml.hackathon.domain.Shipper;
 import com.ml.hackathon.util.PropertyReader;
 import org.primefaces.model.LazyDataModel;
 import org.primefaces.model.SortOrder;


public class LazyShippersDataModel extends LazyDataModel<Shipper> {

    private List<Shipper> datasource;

    public LazyShippersDataModel(List<Shipper> datasource) {
        this.datasource = datasource;
    }

    @Override
    public Shipper getRowData(String rowKey) {
        for(Shipper shipper : datasource) {
            if(String.valueOf(shipper.getId()).equals(rowKey))
                return shipper;
        }

        return null;
    }

    @Override
    public Object getRowKey(Shipper shipper) {
        return shipper.getId();
    }

    @Override
    public List<Shipper> load(int first, int pageSize,final String sortField,final SortOrder sortOrder, Map<String,Object> filters) {
        try {
            List<Shipper> data = new ArrayList<>();

            //filter
            for (Shipper shipper : datasource) {
                boolean match = true;

                if (filters != null) {
                    for (Iterator<String> it = filters.keySet().iterator(); it.hasNext(); ) {
                        try {
                            String filterProperty = it.next();
                            String filterValue = filters.get(filterProperty).toString();

                            String fieldValue = String.valueOf(PropertyReader.getGetter(Shipper.class, filterProperty).invoke(shipper));

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
                    data.add(shipper);
                }
            }

            //sort
            if (sortField != null) {
                Collections.sort(data, new Comparator<Shipper>() {
                    @Override
                    public int compare(Shipper shipper1, Shipper shipper2) {
                        try {
                            Method method=PropertyReader.getGetter(Shipper.class, sortField);
                            Object value1=method.invoke(shipper1);
                            Object value2=method.invoke(shipper2);
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
