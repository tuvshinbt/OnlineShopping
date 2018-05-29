package edu.mum.cs490.project.service.impl;

import edu.mum.cs490.project.domain.Admin;
import edu.mum.cs490.project.domain.Status;
import edu.mum.cs490.project.repository.AdminRepository;
import edu.mum.cs490.project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Erdenebayar on 4/20/2018
 */
@Service
public class AdminServiceImpl extends UserServiceImpl<Admin> implements AdminService{

    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        super(adminRepository);
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Admin> find(String username, String firstName, String lastName, Status status) {
        return adminRepository.find(username, firstName, lastName, status);
    }

    @Override
    public Page<Admin> findPage(String username, String firstName, String lastName, Status status, Pageable pageable) {
        return adminRepository.findPage(username, firstName, lastName, status, pageable);
    }
}
