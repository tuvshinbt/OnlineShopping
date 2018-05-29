package edu.mum.cs490.project.utils.jpreport;

import edu.mum.cs490.project.domain.OrderDetail;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSourceReport implements JRDataSource {
    List<OrderDetail> lstOrderDetail = null;
    int index = -1;

    public void init(List<OrderDetail> lstOrderDetail) {
        this.lstOrderDetail = lstOrderDetail;
    }

    @Override
    public boolean next() throws JRException {
        index++;
        if ((lstOrderDetail != null || !lstOrderDetail.isEmpty()) && index < lstOrderDetail.size()) {
            return true;
        }
        return false;
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        OrderDetail orderDetail = lstOrderDetail.get(index);
        if (orderDetail == null)
            return null;
        if (jrField.getName().equalsIgnoreCase("price")) {
            return orderDetail.getPrice();
        } else if (jrField.getName().equalsIgnoreCase("product.name")) {
            return orderDetail.getProduct().getName();
        } else if (jrField.getName().equalsIgnoreCase("id")) {
            return orderDetail.getId();
        } else if (jrField.getName().equalsIgnoreCase("quantity")){
            return orderDetail.getQuantity();
        } else if (jrField.getName().equalsIgnoreCase("product.id")) {
            return orderDetail.getProduct().getId();
        } else if (jrField.getName().equalsIgnoreCase("product.price")) {
            return orderDetail.getProduct().getPrice();
        } else if (jrField.getName().equalsIgnoreCase("product.vendor.companyName")) {
            return orderDetail.getProduct().getVendor().getCompanyName();
        } else if (jrField.getName().equalsIgnoreCase("product.category.name")) {
            return orderDetail.getProduct().getCategory().getName();
        } else if (jrField.getName().equalsIgnoreCase("beginDate")) {
            return orderDetail.getBeginDate();
        } else if (jrField.getName().equalsIgnoreCase("endDate")) {
            return orderDetail.getEndDate();
        } else if (jrField.getName().equalsIgnoreCase("product.quantity")) {
            return orderDetail.getProduct().getQuantity();
        } else if (jrField.getName().equalsIgnoreCase("order.id")) {
            return orderDetail.getOrder().getId();
        }
        return null;
    }

}
