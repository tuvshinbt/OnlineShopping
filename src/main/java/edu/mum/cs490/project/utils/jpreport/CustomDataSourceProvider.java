package edu.mum.cs490.project.utils.jpreport;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBaseField;

import java.util.Date;

public abstract class CustomDataSourceProvider implements JRDataSourceProvider {

    private class MyField extends JRBaseField {

        /**
         * First constructor for the field
         *
         * @param name        : name of the field
         * @param description : description of the field
         * @param type        : type of the field
         */
        public MyField(String name, String description, Class<?> type) {
            this.name = name;
            this.description = description;
            this.valueClass = type;
            this.valueClassName = type.getName();
        }

        /**
         * Second Constructor, the type of the field is supposed to be
         * String.
         *
         * @param name        : name of the field
         * @param description : description of the field
         */
        public MyField(String name, String description) {
            this(name, description, String.class);
        }
    }

    @Override
    public boolean supportsGetFieldsOperation() {
        return false;
    }

    @Override
    public JRField[] getFields(JasperReport report) throws JRException, UnsupportedOperationException {
        JRField price = new MyField("price", "price of product at the time was bought", Double.class);
        JRField productName = new MyField("product.name", "product name");
        JRField id = new MyField("id", "orderDetail Id", Integer.class);
        JRField quantity = new MyField("quantity", "quantity of this product was ordered", Integer.class);
        JRField productId = new MyField("product.id", "product ID", Integer.class);
        JRField productPrice = new MyField("product.price", "price of product", Double.class);
        JRField vendor = new MyField("product.vendor.companyName", "company name");
        JRField category = new MyField("product.category.name", "category");
        JRField beginDate = new MyField("beginDate", "price of product", Date.class);
        JRField endDate = new MyField("endDate", "price of product", Date.class);
        JRField totalQuantity = new MyField("product.quantity", " total quantity of this product", Integer.class);
        JRField orderId = new MyField("order.id", "orderId", Integer.class);

        return new JRField[]{price, productName, id, quantity, productId, productPrice, vendor, category, beginDate, endDate, totalQuantity, orderId};
    }


    @Override
    public JRDataSource create(JasperReport report) throws JRException {
        return new DataSourceReport();
    }


    @Override
    public void dispose(JRDataSource dataSource) throws JRException {

    }
}
