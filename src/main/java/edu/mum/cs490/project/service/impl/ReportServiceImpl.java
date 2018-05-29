package edu.mum.cs490.project.service.impl;

import edu.mum.cs490.project.domain.OrderDetail;
import edu.mum.cs490.project.service.OrderDetailService;
import edu.mum.cs490.project.service.ReportService;
import edu.mum.cs490.project.utils.jpreport.DataSourceReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{

	@Autowired
	private OrderDetailService orderDetailService;

	private JasperReportsContext jasperReportsContext;
	
	@Override
	public List<OrderDetail> getReportResultList(List<Integer> lstVendorId, List<Integer> lstCategoryId, Date begin_Date, Date end_Date) {

		List<OrderDetail> lstOrderDetail = new ArrayList<>();

		if (lstVendorId.isEmpty() && lstCategoryId.isEmpty())
			lstOrderDetail.addAll(orderDetailService.findByDate(begin_Date, end_Date));
		else if (lstVendorId.isEmpty())
			lstOrderDetail.addAll(orderDetailService.findByCategory_Id(lstCategoryId, begin_Date, end_Date));
		else if (lstCategoryId.isEmpty())
			lstOrderDetail.addAll(orderDetailService.findByVendor_Id(lstVendorId, begin_Date, end_Date));
		else
			lstOrderDetail.addAll(orderDetailService.findByVendor_IdAndCategory_Id(lstVendorId, lstCategoryId, begin_Date, end_Date));

		if (!lstOrderDetail.isEmpty()) {
			lstOrderDetail.get(0).setBeginDate(begin_Date);
			lstOrderDetail.get(0).setEndDate(end_Date);
		}
		return lstOrderDetail;
	}

//	@Override
//	public void jasperReportFill(String jrName, OutputStream outputStream, DataSourceReport dataSourceReport) {
//		try {
//			File sourceFile = new File(jrName);
//			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(sourceFile);
//			JasperPrint jr = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSourceReport);
//			JasperExportManager.exportReportToPdfFile(jr, "./Report.pdf");
//		} catch (JRException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public void jasperReportFill(String jrName, DataSourceReport dataSourceReport) {
		InputStream jrxmlInput = JRLoader.getResourceInputStream(jrName);
		JasperReport jasperReport1 = null;
		try {
			if (jrxmlInput != null) {
				JasperDesign design = JRXmlLoader.load(jrxmlInput);
				jasperReport1 = JasperCompileManager.compileReport(design);
				JasperPrint jr = JasperFillManager.fillReport(jasperReport1, new HashMap<>(), dataSourceReport);
				byte[] pdfByte = JasperExportManager.exportReportToPdf(jr);
				Path path = Paths.get("SaleReport.pdf");
				Files.write(path, pdfByte);
				jrxmlInput.close();
			}
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	@Override
	public byte[] exportToPdf(JasperPrint jasperPrint) throws JRException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		JRPdfExporter exporter = new JRPdfExporter(jasperReportsContext);

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));

		exporter.exportReport();

		return baos.toByteArray();
	}
}
