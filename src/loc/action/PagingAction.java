package loc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybatis.dao.LocDAO;
import paging.Paging;

public class PagingAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
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
		request.setAttribute("pvo", pvo);
		
		return null;
	}

}
