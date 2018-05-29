package edu.mum.cs490.project.service;

import edu.mum.cs490.project.domain.OrderDetail;
import edu.mum.cs490.project.utils.jpreport.DataSourceReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Transactional(readOnly = true)
public interface ReportService {
	public List<OrderDetail> getReportResultList(List<Integer> lstVendorId, List<Integer> lstCategoryId, Date begin_Date, Date end_Date);

	public void jasperReportFill(String jrName, DataSourceReport dataSourceReport);

	public byte[] exportToPdf(JasperPrint jasperPrint) throws JRException;
}
