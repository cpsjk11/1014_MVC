package loc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybatis.dao.LocDAO;
import mybatis.vo.LocVO;
import paging.Paging;

public class SearchAction implements Action {
	
	int total;
	String searchType;
	String searchValue;
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String cPage = request.getParameter("cPage");
		
		if(cPage == null) { // 검색된 결과를 계속 가져가기 위해서는 멤버변수로 지정을 해서 검색된 값을 유지를 해줘야 한다.
		searchType = request.getParameter("searchType");
		searchValue = request.getParameter("searchValue");
		}
		// 페이징 기법
		Paging pvo = new Paging(5, 2);
		
		// 페이징 기법의 필요한 기능
		if(cPage == null) {
		// 총 게시물의 수를 반환하는 기능
			total = LocDAO.selectCount(searchType, searchValue);
		}
		pvo.setTotalRecord(total);
		// 현재페이지의 값이 파라미터를 통해 받아졌을때만 nowPage를 변경해주자!
		if(cPage != null && cPage.trim().length() > 0) {
			int nowPage = Integer.parseInt(cPage);
			pvo.setNowPage(nowPage);
		}else {
			pvo.setNowPage(pvo.getNowPage());

		}

		
		
		LocVO[]ar = LocDAO.getList(String.valueOf(pvo.getBegin()),String.valueOf(pvo.getEnd()),searchType, searchValue);
		//Object obj = session.getAttribute("ar");
		
		//LocVO[] ar = (LocVO[])obj;
		
		request.setAttribute("ar", ar);
		request.setAttribute("pvo", pvo);
	
		return "/search.jsp";
	}

}
