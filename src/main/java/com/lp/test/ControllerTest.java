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
 * Spring4测试的时候，需要servlet3.x的支持
 * 
 * @author misterLip
 * @date 2018年5月31日
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
		// 模拟请求拿到返回值
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/empsajax").param("pn", "100")).andReturn();

		MockHttpServletResponse response = result.getResponse();
		// PageInfo pi = (PageInfo) request.getAttribute("pageInfo");

		System.err.println(response.getContentAsString());

	}

	@Test
	public void testPagesss() throws Exception {
		// 模拟请求拿到返回值
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "100")).andReturn();

		// 请求成功以后，请求域中会有list；我们可以取出list进行验证
		MockHttpServletRequest request = result.getRequest();
		System.out.println("199");
		PageInfo pi = (PageInfo) request.getAttribute("list");
		if (pi == null) {
			System.err.println("无效参数");
		}
		System.out.println("当前页码：" + pi.getPageNum());
		System.out.println("总页码：" + pi.getPages());
		System.out.println("总记录数：" + pi.getTotal());
		System.out.println("在页面需要连续显示的页码");
		int[] nums = pi.getNavigatepageNums();
		for (int i : nums) {
			System.out.print(" " + i);
		}

		// 获取员工数据
		List<Employee> list = pi.getList();
		for (Employee employee : list) {
			System.out.println("ID：" + employee.getEmpId() + "==>Name:" + employee.getEmpName());
		}

	}

}