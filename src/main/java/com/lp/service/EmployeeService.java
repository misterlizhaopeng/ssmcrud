package com.lp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lp.bean.Employee;
import com.lp.bean.EmployeeExample;
import com.lp.bean.EmployeeExample.Criteria;
import com.lp.dao.EmployeeMapper;

@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;
	
	public List<Employee> getAllEmps() {
		List<Employee> list = employeeMapper.selectByExampleWithDept(null);
		return list;
	}
	
 
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}

 
	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

 
	public boolean checkUser(String empName) {
		EmployeeExample example = new EmployeeExample();
		
		Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		
		long count = employeeMapper.countByExample(example);
		return count == 0;
	}
	
	
	public List<Employee> checkUserBak(Employee employee) {
		EmployeeExample example = new EmployeeExample();
		example.setDistinct(true);
		Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(employee.getEmpName());
		employee.setEmail("abc");
		criteria.andEmailEqualTo(employee.getEmail());
		
		example.setOrderByClause("emp_name");
		return employeeMapper.selectByExampleWithDept(example);
	}
 
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}

	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}
	
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}

	public void deleteBatch(List<Integer> ids) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		//delete from xxx where emp_id in(1,2,3)
		criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(example);
	}
}
