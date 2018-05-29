package edu.mum.cs490.project.repository;

import edu.mum.cs490.project.domain.Category;
import edu.mum.cs490.project.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ChanPiseth on 4/24/2018
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT a FROM Category a WHERE " +
            "(:name IS NULL OR a.name like %:name%) AND " +
            "((:parentId IS NULL AND a.parentCategory.id IS NULL) OR (a.parentCategory.id =:parentId)) AND " +
            "((:status IS NULL AND a.status = 'ENABLED') OR (a.status =:status))")
    List<Category> find(@Param("name") String name, @Param("parentId") Integer parentId, @Param("status") Status status);
    // Test
     public Category findByName(String name);

     // End Test
    List<Category> findByStatusOrderByParentCategoryAsc(Status status);
}
