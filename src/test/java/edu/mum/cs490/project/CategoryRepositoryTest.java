package edu.mum.cs490.project;

import edu.mum.cs490.project.domain.Category;
import edu.mum.cs490.project.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by ChanPiseth on 5/09/2018
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void whenFindByName_thenReturnCategory() {
        // given
        Category phone = new Category("phone");
        categoryRepository.save(phone);
        // when
        Category found = categoryRepository.findByName(phone.getName());
        // then
        assertThat(found.getName())
                .isEqualTo(phone.getName());
}
    }
