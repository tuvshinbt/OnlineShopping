package edu.mum.cs490.project.service;

import edu.mum.cs490.project.domain.Category;
import edu.mum.cs490.project.domain.Status;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by ChanPiseth on 4/20/2018
 */
@Transactional(readOnly = true)
public interface CategoryService {

    Category getCategoryById(Integer categoryId);

    Category getCategoryByName(String name);

    List<Category> find(String name, Integer parentId, Status status);


    List<Category> findAllActiveInList();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    void save(Category category);

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(Integer categoryId);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    void changeStatus(Integer id, Status status);



}
