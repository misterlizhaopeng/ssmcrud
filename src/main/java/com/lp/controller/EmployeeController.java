package com.lp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lp.bean.Employee;
import com.lp.bean.Msg;
import com.lp.service.EmployeeService;

@Controller
public class EmployeeController {
 
	
	
	
	private static final String SUCCESS = "success";
	
	
	
	
	@Autowired
	EmployeeService employeeService;

	@RequestMapping("/empsajax")
	@ResponseBody
	public Msg getEmpsByAjax(@RequestParam(value = "pn", defaultValue = "10") Integer pn,HttpServletRequest request) {
		
		//通过ServletContext获取spring容器，查看容器中的所有的bean start
		
		//org.springframework.web.context.WebApplicationContext.ROOT
		String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";
		ConfigurableWebApplicationContext ctx = (ConfigurableWebApplicationContext)request.getServletContext().getAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		
		String[] names = ctx.getBeanDefinitionNames();
		List<String> asList = Arrays.asList(names);
		asList.forEach(a->{
			System.out.println(a);
		});
		
		WebApplicationContext c = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		System.out.println(c==ctx);
//		request.getServletContext().getServlet("")
		
		//通过ServletContext获取spring容器，查看容器中的所有的bean end
		
		
		
		PageHelper.startPage(pn, 10);
		List<Employee> allEmps = employeeService.getAllEmps();
		PageInfo page = new PageInfo(allEmps, 10);
		return Msg.success().add("pageInfo", page);
	}

	@RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "10") Integer pn, Model model) {
		PageHelper.startPage(pn, 10);
		List<Employee> allEmps = employeeService.getAllEmps();
		PageInfo page = new PageInfo(allEmps, 10);
		model.addAttribute("list", page);
		return "list";
	}

	////////////////////////////////// 网络////////////start
	/**
	 * 单个批量二合一 批量删除：1-2-3 单个删除：1
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("ids") String ids) {
		// 批量删除
		if (ids.contains("-")) {
			List<Integer> del_ids = new ArrayList<>();
			String[] str_ids = ids.split("-");
			// 组装id的集合
			for (String string : str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			employeeService.deleteBatch(del_ids);
		} else {
			Integer id = Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Msg.success();
	}

	/**
	 * 如果直接发送ajax=PUT形式的请求
	 * 封装的数据
	 * Employee 
	 * [empId=1014, empName=null, gender=null, email=null, dId=null]
	 * 
	 * 问题：
	 * 请求体中有数据；
	 * 但是Employee对象封装不上；
	 * update tbl_emp  where emp_id = 1014;
	 * 
	 * 原因：
	 * Tomcat：
	 * 		1、将请求体中的数据，封装一个map。
	 * 		2、request.getParameter("empName")就会从这个map中取值。
	 * 		3、SpringMVC封装POJO对象的时候。
	 * 				会把POJO中每个属性的值，request.getParamter("email");
	 * AJAX发送PUT请求引发的血案：
	 * 		PUT请求，请求体中的数据，request.getParameter("empName")拿不到
	 * 		Tomcat一看是PUT不会封装请求体中的数据为map，只有POST形式的请求才封装请求体为map
	 * org.apache.catalina.connector.Request--parseParameters() (3111);
	 * 
	 * protected String parseBodyMethods = "POST";
	 * if( !getConnector().isParseBodyMethod(getMethod()) ) {
                success = true;
                return;
            }
	 * 
	 * 
	 * 解决方案；
	 * 我们要能支持直接发送PUT之类的请求还要封装请求体中的数据
	 * 1、配置上HttpPutFormContentFilter；
	 * 2、他的作用；将请求体中的数据解析包装成一个map。
	 * 3、request被重新包装，request.getParameter()被重写，就会从自己封装的map中取数据
	 * 员工更新方法
	 * @param employee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
	public Msg saveEmp(Employee employee, HttpServletRequest request) {
		System.out.println("请求体中的值：" + request.getParameter("gender"));
		System.out.println("将要更新的员工数据：" + employee);
		employeeService.updateEmp(employee);
		return Msg.success();
	}

	/**
	 * 根据id查询员工
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id") Integer id) {

		Employee employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}

	
	@ResponseBody
	@RequestMapping("/checkusert")
	public Msg checkuserTest(Employee empName) {
		List<Employee> list= employeeService.checkUserBak(empName);
		return Msg.success().add("list", list);
	}
	
	
	
	/**
	 * 检查用户名是否可用
	 * 
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName") String empName) {
		// 先判断用户名是否是合法的表达式;
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if (!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "用户名必须是6-16位数字和字母的组合或者2-5位中文");
		}

		// 数据库用户名重复校验
		boolean b = employeeService.checkUser(empName);
		if (b) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}

	/**
	 * 员工保存 1、支持JSR303校验 2、导入Hibernate-Validator
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			// 校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			Map<String, Object> map = new HashMap<>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				// fieldError.getField()//错误的字段名
				// fieldError.getDefaultMessage()//错误信息
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		} else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}

	}
	////////////////////////////////// 网络////////////
}
