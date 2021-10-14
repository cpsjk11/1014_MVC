package paging;

import javax.xml.ws.Response;

public class Paging {

	int nowPage = 1; //현재 페이지 값 == cPage
	int numPerPage = 10;// 한 페이지당 보여질 게시물 수
  
	//페이징을 위한 변수
	int totalRecord = 0;//총 게시물 수
  
	int pagePerBlock = 2; // 페이지 묶음(한 블럭당 보여질 페이지 수)
  
	//전체 페이지 수 구하기
	int totalPage = 0;
	  
	int begin, end, startPage, endPage;
	
	public Paging() {} //기본생성자
	public Paging(int numPerPage, int pagePerBlock) {
		this.numPerPage = numPerPage;
		this.pagePerBlock = pagePerBlock;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	   
		//현재 페이지 값이 바뀌면 표현할 게시물들이 변경되어야 한다.
		//즉, begin과 end값이 변경되어야 한다.
		
		//현재 페이지 값은 총 페이지 값을 넘으면 안된다.
		if(nowPage > totalPage)
			  nowPage = totalPage;
		
		//각 페이지의 시작과 끝(begin, end)를 지정하자!
		//   현재페이지가 1: begin:1, end:5
		//   현재페이지가 2: begin:6, end:10
		//   현재페이지가 3: begin:11, end:15
		//   현재페이지가 4: begin:16, end:20
		begin = (nowPage-1)*numPerPage+1;
	  	end = nowPage*numPerPage;
	  	
	    //현재페이지 값에 의해 블럭의 시작페이지 값 구하자!
	    startPage = (int)((nowPage-1)/pagePerBlock)*pagePerBlock+1;
	    
	    //블럭의 마지막 페이지 값 구하기
	    endPage = startPage + pagePerBlock - 1;
	    
	    if(endPage > totalPage)
	  	  endPage = totalPage;
	    
	}
	public int getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		
		//총 게시물에 의해서 총 페이지 수가 결정된다.
		//Math클래스에 ceil함수는 결과인 소수점을 가장 가까운 높은 정수로 
		//  전환하는 기능을 가졌다.
		  
		totalPage = (int)Math.ceil((double)totalRecord/numPerPage);
	}
	public int getPagePerBlock() {
		return pagePerBlock;
	}
	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	
}
