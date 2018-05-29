package edu.mum.cs490.project.service.impl;

import edu.mum.cs490.project.domain.*;
import edu.mum.cs490.project.framework.observer.*;
import edu.mum.cs490.project.framework.template.TransactionTemplate;
import edu.mum.cs490.project.framework.template.impl.VendorRegistrationTemplateImpl;
import edu.mum.cs490.project.repository.CardDetailRepository;
import edu.mum.cs490.project.repository.VendorRepository;
import edu.mum.cs490.project.service.MailService;
import edu.mum.cs490.project.service.PaymentService;
import edu.mum.cs490.project.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Erdenebayar on 4/20/2018
 */
@Service
public class VendorServiceImpl extends UserServiceImpl<Vendor> implements VendorService{

    protected final VendorRepository repository;
    private final CardDetailRepository cardDetailRepository;
    private final PaymentService paymentService;
    private final MailService mailService;

    @Value("${card.detail.id.oss}")
    private Integer cardDetailIdOSS;
    @Value("${vendor.registration.fee}")
    private Double vendorRegistrationFee;



    @Autowired
    public VendorServiceImpl(VendorRepository repository, CardDetailRepository cardDetailRepository, PaymentService paymentService, MailService mailService) {
        super(repository);
        this.repository = repository;
        this.cardDetailRepository = cardDetailRepository;
        this.paymentService = paymentService;
        this.mailService = mailService;
    }

    @Override
    public List<Vendor> find(String username, String companyName, Status status) {
        return repository.find(username, companyName, status);
    }

    @Override
    public Page<Vendor> findPage(String username, String companyName, Status status, Pageable pageable) {
        return this.repository.findPage(username, companyName, status, pageable);
    }

    @Override
    public Vendor getByCompanyName(String companyName) {
        return null;
    }

    @Override
    public Integer transferFee(CardDetail vendorCardDetail, Vendor vendor) {
        CardDetail OSSCardDetail = cardDetailRepository.findById(cardDetailIdOSS).get();
        TransactionTemplate transactionTemplate = getVendorRegistrationTemplate(vendorCardDetail, OSSCardDetail, vendor);
        return transactionTemplate.process();
    }



    private TransactionTemplate getVendorRegistrationTemplate(CardDetail vendorCardDetail, CardDetail OSSCardDetail, Vendor vendor) {
        NotifierSubject notifierSubject = new NotifierSubject();
        notifierSubject.addObserver(new MailToVendorAndAdminObserver(vendor, mailService));
        TransactionTemplate purchaseTemplate = new VendorRegistrationTemplateImpl(vendorCardDetail, OSSCardDetail, notifierSubject, new TransferSubject(), paymentService, vendorRegistrationFee);
        return purchaseTemplate;
    }
}
