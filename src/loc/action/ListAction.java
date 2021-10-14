package loc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybatis.dao.CntDAO;
import mybatis.dao.LocDAO;
import mybatis.vo.CntVO;
import mybatis.vo.LocVO;
import paging.Paging;

public class ListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String searchType = request.getParameter("searchType");
		String searchValue = request.getParameter("searchValue");
		LocVO[]ar = null;
		String viewPath = "/list.jsp";
		if(searchType != null && searchValue != null) {
			ar = LocDAO.selectList(searchType, searchValue);
			viewPath = "/search.jsp";
		}else if(searchType == null && searchValue == null) {
			ar = LocDAO.selectList(null, null);
		}
		
		// 페이징 기법
		
		Paging pvo = new Paging();
		
		// 페이징 기법의 필요한 기능
		String cPage = request.getParameter("cPage");
		
		// 총 게시물의 수를 반환하는 기능
		pvo.setTotalRecord(LocDAO.selectCount(null, null));
		
		// 현재페이지의 값이 파라미터를 통해 받아졌을때만 nowPage를 변경해주자!
		if(cPage != null && cPage.trim().length() > 0) {
			int nowPage = Integer.parseInt(cPage);
			pvo.setNowPage(nowPage);
		}else
			pvo.setNowPage(pvo.getNowPage());
		
		// 국적 알아내기.
		CntVO[] vo = CntDAO.selectList(); 
				
		LocVO[]lvo = LocDAO.getList(pvo.getBegin(), pvo.getEnd());
		request.setAttribute("pvo", pvo);
		request.setAttribute("ar", ar);
		request.setAttribute("ar3", lvo);
		request.setAttribute("ar2", vo);
		
		
		return viewPath;
	}

}
