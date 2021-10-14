package loc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybatis.dao.LocDAO;
import mybatis.vo.LocVO;

public class SearchAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String searchType = request.getParameter("searchType");
		String searchValue = request.getParameter("searchValue");
		
		LocVO[]ar = LocDAO.selectList(searchType, searchValue);
		
		request.setAttribute("search", ar);
		return "/search.jsp";
	}

}
