package mybatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.storeconfig.CredentialHandlerSF;
import org.apache.ibatis.session.SqlSession;

import mybatis.service.FactoryService;
import mybatis.vo.CntVO;
import mybatis.vo.LocVO;

public class LocDAO {
	
	// 카운트 기능
	public static int selectCount(String searchType, String searchValue) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchType", searchType);
		map.put("searchValue", searchValue);
		
		SqlSession ss = FactoryService.getFactory().openSession();
		
		int cnt = ss.selectOne("loc.selectCount", map);
		
		ss.close();
		
		return cnt;
	}
	
	
	// 목록기능
	public static LocVO[] selectList(String searchType, String searchValue) {
		LocVO[] ar = null;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchType", searchType);
		map.put("searchValue", searchValue);
		
		SqlSession ss = FactoryService.getFactory().openSession();
		
		List<CntVO> list = ss.selectList("loc.selectList",map);
		
		if(list != null && !list.isEmpty()) {
			ar = new LocVO[list.size()];
			list.toArray(ar);
		}
		ss.close();
		
		return ar;
	}
	
	public static int addList(String location_id,String street_address, String postal_code, String city, String state_province, String country_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("location_id", location_id);
		map.put("street_address", street_address);
		map.put("postal_code", postal_code);
		map.put("city", city);
		map.put("state_province", state_province);
		map.put("country_id", country_id);
		
		System.out.println(country_id);
		
		SqlSession ss = FactoryService.getFactory().openSession();
		
		int cnt = ss.insert("loc.add", map);
		
		if(cnt > 0)
			ss.commit();
		else
			ss.rollback();
		ss.close();
		
		return cnt;
			
	}
	public static LocVO[] getList(String begin, String end,String searchType, String searchValue) {
		LocVO[] ar = null;
		
		// DB 연결준비!!
		SqlSession ss = FactoryService.getFactory().openSession();
		
		// 필요한 맵구조 생성!!
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("begin",begin);
		map.put("end",end);
		map.put("searchType", searchType);
		map.put("searchValue", searchValue);
		
		// 모든 정보를 DB와 연결하여 목록을 가져오자!
		List<LocVO> list = ss.selectList("loc.getList",map);
		
		if(list != null && !list.isEmpty()) {
			// list가 비어있지 않을때!!
			ar = new LocVO[list.size()];
			list.toArray(ar);
		}
		
		ss.close();
		
		return ar;
	}
}
