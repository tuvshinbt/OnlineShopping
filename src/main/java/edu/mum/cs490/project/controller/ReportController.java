package edu.mum.cs490.project.controller;

import edu.mum.cs490.project.domain.*;
import edu.mum.cs490.project.model.Message;
import edu.mum.cs490.project.model.form.ReportFilterForm;
import edu.mum.cs490.project.service.*;
import edu.mum.cs490.project.utils.jpreport.DataSourceReport;
import javassist.NotFoundException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final OrderDetailService orderDetailService;
    private final VendorService vendorService;
    private final CategoryService categoryService;
    private final MailService mailService;

    @Autowired
    ReportService reportService;

    @Autowired
    DataSourceReport dataSourceReport;

    @Autowired
    public ReportController(OrderDetailService orderDetailService, VendorService vendorService, CategoryService categoryService, MailService mailService) {
        this.orderDetailService = orderDetailService;
        this.vendorService = vendorService;
        this.categoryService = categoryService;
        this.mailService = mailService;
    }

    @GetMapping(value = "/reportFilter")
    public String ReportLoading(Model model) {
        model.addAttribute("vendors", vendorService.find(null, null, Status.ENABLED));
        model.addAttribute("categories", categoryService.find(null, null, Status.ENABLED));
        if (!model.containsAttribute("reportFilterForm")) {
            model.addAttribute("reportFilterForm", new ReportFilterForm());
        }
        return "report/reportFilter";
    }

    @PostMapping(value = "/reportFilter")
    public String ReportExport(@Valid @ModelAttribute("reportFilterForm") ReportFilterForm reportFilterForm,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal User user, HttpServletResponse response) {

        model.addAttribute("vendors", vendorService.find(null, null, Status.ENABLED));
        model.addAttribute("categories", categoryService.find(null, null, Status.ENABLED));
        if (bindingResult.hasErrors()) {
            return "report/reportFilter";
        } else if (reportFilterForm.getBegin_Date().after(reportFilterForm.getEnd_Date())) {
            model.addAttribute("error", "Please choose a date after From date.");
            return "report/reportFilter";
        }
        try {
//            createJasperReport(reportFilterForm, user, response);
            report(reportFilterForm, user, response);
        } catch (NotFoundException nfe) {
            model.addAttribute("message", new Message(Message.Type.ERROR, nfe.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("message", Message.errorOccurred);
        }
        return "report/reportFilter";
    }

    public void report(ReportFilterForm reportFilterForm, @AuthenticationPrincipal User user, HttpServletResponse response) throws Exception {

        if (reportFilterForm.getLstVendor_Id() == null)
            reportFilterForm.setLstVendor_Id(new ArrayList<>());
        if (reportFilterForm.getLstCategory_Id() == null)
            reportFilterForm.setLstCategory_Id(new ArrayList<>());

        String reportName = "";
        if (user instanceof Vendor) {
            reportName = "reportForVendor";
            if (!reportFilterForm.getLstVendor_Id().isEmpty())
                reportFilterForm.setLstVendor_Id(new ArrayList<>());

            reportFilterForm.getLstVendor_Id().add(user.getId());
        } else if (user instanceof Admin) {
            if(reportFilterForm.getReportType().equals("generalReport"))
                reportName = "report";
            else if(reportFilterForm.getReportType().equals("ProfitReport"))
                reportName = "ProfitReport";
            else if(reportFilterForm.getReportType().equals("ChartReport"))
                reportName = "ChartReport";
        }
        List<OrderDetail> list = collectData(reportFilterForm.getLstVendor_Id(), reportFilterForm.getLstCategory_Id(), reportFilterForm.getBegin_Date(), reportFilterForm.getEnd_Date());

        if (list == null || list.isEmpty()) {
            throw new NotFoundException("no.data.available.on.this.period");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Initialize response
        response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand.
        // set pdf content
        response.setContentType("application/pdf");
        // if you want to download instead of opening inlines
        // response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        try {
            byte[] bytes = generateJasperReportPDF(reportName, outputStream, list);
            // write the content to the output stream
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            bos.write(bytes);
            bos.flush();
            bos.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("<<<<File Not Fount Exception>>>>");
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("<<<<Input Output Stream Exception>>>>");
            throw e;
        } catch (JRException e) {
            e.printStackTrace();
            System.out.println("<<<<JRException Stream Exception>>>>");
            throw e;
        }
    }


    //Send report to vendors monthly/weekly
//    public void sendReportToVendor() {
//
//        try {
//            List<Vendor> lstVendor = vendorService.find(null, null, Status.ENABLED);
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            List<OrderDetail> list = new ArrayList<>();
//            List<Integer> lstVendorId = new ArrayList<>();
//            Date endDate = new Date();
//            Date beginDate = new Date(endDate.getYear(), endDate.getMonth() == 0 ? 11 : endDate.getMonth() - 1, endDate.getDay());
//            String nameOfAttachment = "Selling report - " + (beginDate.getMonth() + 1);
//            for (Vendor vendor : lstVendor) {
//                lstVendorId.add(vendor.getId());
//                list = collectData(lstVendorId, new ArrayList<>(), beginDate, endDate);
//                if (list != null && !list.isEmpty()) {
//                    byte[] bytes = generateJasperReportPDF("reportForVendor", outputStream, list);
//                    mailService.sendReportToVendor(vendor.getEmail(), bytes, nameOfAttachment);
//                }
//                list.clear();
//                lstVendorId.clear();
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } catch (JRException ex) {
//            ex.printStackTrace();
//        }
//    }

    //Send report to vendors weekly/monthly
    public void sendReportToVendor(Date beginDate) {

        try {
            List<Vendor> lstVendor = vendorService.find(null, null, Status.ENABLED);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            List<OrderDetail> list = new ArrayList<>();
            List<Integer> lstVendorId = new ArrayList<>();
            Date endDate = new Date();
            String nameOfAttachment = "Selling report - From " + beginDate.getTime() + " To " + endDate.getTime();
            for (Vendor vendor : lstVendor) {
                lstVendorId.add(vendor.getId());
                list = collectData(lstVendorId, new ArrayList<>(), beginDate, endDate);
                if (list != null && !list.isEmpty()) {
                    byte[] bytes = generateJasperReportPDF("reportForVendor", outputStream, list);
                    mailService.sendReportToVendor(vendor.getEmail(), bytes, nameOfAttachment);
                }
                list.clear();
                lstVendorId.clear();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    public List<OrderDetail> collectData(List<Integer> lstVendorId, List<Integer> lstCategoryId, Date beging_Date, Date end_Date) {
        List<OrderDetail> lstOrderDetail = new ArrayList<>();
        end_Date.setHours(23);
        end_Date.setMinutes(59);
        if (lstVendorId.isEmpty() && lstCategoryId.isEmpty())
            lstOrderDetail.addAll(orderDetailService.findByDate(beging_Date, end_Date));
        else if (lstVendorId.isEmpty())
            lstOrderDetail.addAll(orderDetailService.findByCategory_Id(lstCategoryId, beging_Date, end_Date));
        else if (lstCategoryId.isEmpty())
            lstOrderDetail.addAll(orderDetailService.findByVendor_Id(lstVendorId, beging_Date, end_Date));
        else
            lstOrderDetail.addAll(orderDetailService.findByVendor_IdAndCategory_Id(lstVendorId, lstCategoryId, beging_Date, end_Date));

        if (!lstOrderDetail.isEmpty()) {
            lstOrderDetail.get(0).setBeginDate(beging_Date);
            lstOrderDetail.get(0).setEndDate(end_Date);
        }
        return lstOrderDetail;
    }

    private byte[] generateJasperReportPDF(String jasperReportName, ByteArrayOutputStream outputStream, List<OrderDetail> data) throws IOException, JRException {
        JRPdfExporter exporter = new JRPdfExporter();
        JasperReport jasperReport = null;
        InputStream jrxmlInput = JRLoader.getResourceInputStream("jpreport/" + jasperReportName + ".jrxml");

        try {
            if (jrxmlInput != null) {
                JasperDesign design = JRXmlLoader.load(jrxmlInput);
                jasperReport = JasperCompileManager.compileReport(design);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(data));

                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
                exporter.exportReport();
            }
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            jrxmlInput.close();
        }
        return outputStream.toByteArray();
    }

    private byte[] generateJasperReportXLS(String jasperReportName, ByteArrayOutputStream outputStream, List<OrderDetail> data) throws IOException, JRException {
        JRXlsExporter exporter = new JRXlsExporter();
        JasperReport jasperReport = null;
        InputStream jrxmlInput = JRLoader.getResourceInputStream("jpreport/" + jasperReportName + ".jrxml");

        try {
            if (jrxmlInput != null) {
                JasperDesign design = JRXmlLoader.load(jrxmlInput);
                jasperReport = JasperCompileManager.compileReport(design);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(data));

                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
                exporter.exportReport();
            }
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            jrxmlInput.close();
        }
        return outputStream.toByteArray();
    }


    public void createJasperReport(ReportFilterForm reportFilterForm, @AuthenticationPrincipal User user, HttpServletResponse response) throws NotFoundException {

        if (reportFilterForm.getLstVendor_Id() == null)
            reportFilterForm.setLstVendor_Id(new ArrayList<>());
        if (reportFilterForm.getLstCategory_Id() == null)
            reportFilterForm.setLstCategory_Id(new ArrayList<>());

        String reportName = "";
        if (user instanceof Vendor) {
            reportName = "reportForVendor";
            if (!reportFilterForm.getLstVendor_Id().isEmpty())
                reportFilterForm.setLstVendor_Id(new ArrayList<>());

            reportFilterForm.getLstVendor_Id().add(user.getId());
        } else if (user instanceof Admin) {
            reportName = "report";
        }

        response.reset();
        response.setContentType("application/x-pdf");
        List<OrderDetail> orderDetailsList = reportService.getReportResultList(reportFilterForm.getLstVendor_Id(), reportFilterForm.getLstCategory_Id(), reportFilterForm.getBegin_Date(), reportFilterForm.getEnd_Date());
        dataSourceReport.init(orderDetailsList);
        if (dataSourceReport == null || orderDetailsList.isEmpty()) {
            throw new NotFoundException("no.data.available.on.this.period");

        }
        reportService.jasperReportFill("jpreport/" + reportName + ".jrxml", dataSourceReport);
    }

}
