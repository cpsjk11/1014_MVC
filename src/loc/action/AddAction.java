package loc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybatis.dao.LocDAO;

public class AddAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		String location_id = request.getParameter("location_id");
		String street_address = request.getParameter("street_address");
		String postal_code = request.getParameter("location_id");
		String city = request.getParameter("city");
		String state_province = request.getParameter("state_province");
		String country_id = request.getParameter("country_id");
		
		int cnt = LocDAO.addList(location_id, street_address, postal_code, city, state_province, country_id);
		
		System.out.println(country_id);
		
		request.setAttribute("cnt", cnt);
		
		return "/add.jsp";
	}

}
