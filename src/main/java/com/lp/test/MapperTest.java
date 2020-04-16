package com.lp.test;

import java.util.Iterator;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lp.bean.Employee;
import com.lp.dao.DepartmentMapper;
import com.lp.dao.EmployeeMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class MapperTest {

	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	SqlSession sqlsession;

	@Test
	public void testMapper() {
		System.out.println(sqlsession);
		System.out.println(departmentMapper);
		System.out.println(employeeMapper);
	}

	@Test
	public void addEmp() {
		Employee employee = new Employee(null, "2", "m", "emassil", null);
		employeeMapper.insertSelective(employee);
		System.err.println("���صĽ����" + employee.getEmpId());
	}

	/**
	 * �������Ա��
	 * @date 2018��5��31��
	 * @author misterLip
	 */
	@Test
	public void batchInsert() {
		EmployeeMapper mapper = sqlsession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 10000; i++) {
			String uid = UUID.randomUUID().toString().substring(0, 5);
			Employee employee = new Employee(null, uid + "-2", "m", uid + "-@emassil", null);
			mapper.insertSelective(employee);
		}
		System.out.println("�������");
	}
}
