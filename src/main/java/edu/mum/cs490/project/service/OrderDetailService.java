package edu.mum.cs490.project.service;

import java.util.*;

import edu.mum.cs490.project.domain.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface OrderDetailService {

	@Transactional
	OrderDetail getOne(Integer id);

	@Transactional
	List<OrderDetail> findByDate(Date begin_Date, Date end_Date);

	@Transactional
	List<OrderDetail> findByVendor_Id(List<Integer> vendor_Ids, Date begin_Date, Date end_Date);

	@Transactional
	List<OrderDetail> findByCategory_Id(List<Integer> category_Ids, Date begin_Date, Date end_Date);

	@Transactional
	List<OrderDetail> findByVendor_IdAndCategory_Id(List<Integer> vendor_Ids, List<Integer> category_Ids, Date begin_Date, Date end_Date);
}
