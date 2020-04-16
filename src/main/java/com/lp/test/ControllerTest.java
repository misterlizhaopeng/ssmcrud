package com.lp.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;
import com.lp.bean.Employee;

/**
 * Spring4���Ե�ʱ����Ҫservlet3.x��֧��
 * 
 * @author misterLip
 * @date 2018��5��31��
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"file:src/main/webapp/WEB-INF/springDispatcher-servlet.xml" })
public class ControllerTest {
	@Autowired
	WebApplicationContext context;
	MockMvc mockMvc;

	@Before
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testCriteria() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/checkusert").param("empName", "ss"))
				.andReturn();
		MockHttpServletResponse response = result.getResponse();
		String asString = response.getContentAsString();
		System.err.println(asString);
		
		
	}

	@Test
	public void testPageByAjax() throws Exception {
		// ģ�������õ�����ֵ
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/empsajax").param("pn", "100")).andReturn();

		MockHttpServletResponse response = result.getResponse();
		// PageInfo pi = (PageInfo) request.getAttribute("pageInfo");

		System.err.println(response.getContentAsString());

	}

	@Test
	public void testPagesss() throws Exception {
		// ģ�������õ�����ֵ
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "100")).andReturn();

		// ����ɹ��Ժ��������л���list�����ǿ���ȡ��list������֤
		MockHttpServletRequest request = result.getRequest();
		System.out.println("199");
		PageInfo pi = (PageInfo) request.getAttribute("list");
		if (pi == null) {
			System.err.println("��Ч����");
		}
		System.out.println("��ǰҳ�룺" + pi.getPageNum());
		System.out.println("��ҳ�룺" + pi.getPages());
		System.out.println("�ܼ�¼����" + pi.getTotal());
		System.out.println("��ҳ����Ҫ������ʾ��ҳ��");
		int[] nums = pi.getNavigatepageNums();
		for (int i : nums) {
			System.out.print(" " + i);
		}

		// ��ȡԱ������
		List<Employee> list = pi.getList();
		for (Employee employee : list) {
			System.out.println("ID��" + employee.getEmpId() + "==>Name:" + employee.getEmpName());
		}

	}

}