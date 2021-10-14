package mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import mybatis.service.FactoryService;
import mybatis.vo.CntVO;

public class CntDAO {
	public static CntVO[] selectList() {
		CntVO[] ar = null;
		
		SqlSession ss = FactoryService.getFactory().openSession();
		
		List<CntVO> list = ss.selectList("country.selectList");
		if(list != null && !list.isEmpty()) {
			ar = new CntVO[list.size()];
			list.toArray(ar);
		}
		ss.close();
		
		return ar;
	}
}
