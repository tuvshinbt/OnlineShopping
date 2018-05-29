package edu.mum.cs490.project.service;

import edu.mum.cs490.project.domain.Admin;
import edu.mum.cs490.project.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService extends UserService<Admin>{

    List<Admin> find(String username, String firstName, String lastName, Status status);

    Page<Admin> findPage(String username, String firstName, String lastName, Status status, Pageable pageable);
}
