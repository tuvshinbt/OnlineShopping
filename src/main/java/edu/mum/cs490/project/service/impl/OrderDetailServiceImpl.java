package edu.mum.cs490.project.service.impl;

import edu.mum.cs490.project.domain.OrderDetail;
import edu.mum.cs490.project.repository.OrderDetailRepository;
import edu.mum.cs490.project.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	private final OrderDetailRepository orderDetailRepository;

	@Autowired
	public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
		this.orderDetailRepository = orderDetailRepository;
	}

	@Override
	public OrderDetail getOne(Integer id) {
		return orderDetailRepository.getOne(id);
	}

	@Override
	public List<OrderDetail> findByDate(Date begin_Date, Date end_Date) {
		return orderDetailRepository.findByDate(begin_Date, end_Date);
	}

	@Override
	public List<OrderDetail> findByVendor_Id(List<Integer> vendor_Ids, Date begin_Date, Date end_Date) {
		return orderDetailRepository.findByVendor_Id(vendor_Ids, begin_Date, end_Date);
	}

	@Override
	public List<OrderDetail> findByCategory_Id(List<Integer> category_Ids, Date begin_Date, Date end_Date) {
		return orderDetailRepository.findByCategory_Id(category_Ids, begin_Date, end_Date);
	}

	@Override
	public List<OrderDetail> findByVendor_IdAndCategory_Id(List<Integer> vendor_Ids, List<Integer> category_Ids, Date begin_Date, Date end_Date) {
		return orderDetailRepository.findByVendor_IdAndCategory_Id(vendor_Ids, category_Ids, begin_Date, end_Date);
	}
}
