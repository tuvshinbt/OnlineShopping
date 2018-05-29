package edu.mum.cs490.project;

import edu.mum.cs490.project.domain.Category;
import edu.mum.cs490.project.repository.CategoryRepository;
import edu.mum.cs490.project.service.CategoryService;
import edu.mum.cs490.project.service.impl.CategoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by ChanPiseth on 5/09/2018
 */

@RunWith(SpringRunner.class)
public class CategoryServiceImplTest {
    @TestConfiguration
    static class CategoryServiceImplTestContextConfiguration {

        @Bean
        public CategoryService categoryService() {
            return new CategoryServiceImpl();
        }
    }

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        Category phone = new Category("phone");

        Mockito.when(categoryRepository.findByName(phone.getName()))
                .thenReturn(phone);
    }

    @Test
    public void whenValidName_thenCategoryShouldBeFound() {
        String name = "phone";
        Category found = categoryService.getCategoryByName(name);

        assertThat(found.getName())
                .isEqualTo(name);
    }
}
