package edu.mum.cs490.project.repository;

import edu.mum.cs490.project.domain.Slider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Created by ChanPiseth on 4/24/2018
 */

@Repository
public interface SliderRepository extends CrudRepository<Slider, Integer> {
}
