package edu.mum.cs490.project.service.impl;

import edu.mum.cs490.project.domain.Product;
import edu.mum.cs490.project.domain.Status;
import edu.mum.cs490.project.repository.CategoryRepository;
import edu.mum.cs490.project.repository.ProductRepository;
import edu.mum.cs490.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pagmaa on 4/23/2018
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getOne(Integer id) {
        return productRepository.getOne(id);
    }

    @Override
    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Integer id) {
        Product product = getOne(id);
        product.setStatus(Status.DELETED);
        productRepository.save(product);
    }

    @Override
    public void changeStatus(Integer id, Status status) {
        Product product = getOne(id);
        product.setStatus(status);
        productRepository.save(product);
    }

    @Override
    public List<Product> find(String name, Integer categoryId, Integer vendorId, Status status, Sort sort) {
        List<Integer> categoryIds = null;
        if (categoryId != null) {
            categoryIds = new ArrayList<>(categoryRepository.getOne(categoryId).getParentIds());
        }
        return productRepository.find(name, categoryId, categoryIds, vendorId, status, sort);
    }

    @Override
    public Page<Product> findPage(String name, Integer categoryId, Integer vendorId, Status status, Pageable pageable) {
        List<Integer> categoryIds = null;
        if (categoryId != null) {
            categoryIds = new ArrayList<>(categoryRepository.getOne(categoryId).getParentIds());
        }
        return productRepository.findPage(name, categoryId, categoryIds, vendorId, status, pageable);
    }
}
