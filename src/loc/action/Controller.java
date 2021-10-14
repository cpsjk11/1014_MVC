package loc.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import loc.action.Action;

/**
 * Servlet implementation class Controller
 */
@WebServlet(
      urlPatterns = { "/Controller" }, 
      initParams = { 
       @WebInitParam(name = "myParam", value = "/WEB-INF/action.properties")
      })
public class Controller extends HttpServlet {
   private static final long serialVersionUID = 1L;
    
    // 초기 파라미터로 전달된 action.properties파일의 경로를 가지고
   // 그 파일의 내용(클래스의 경로)들을 가져와서 객체로 생성한 후 저장할 곳
   private Map<String, Action> actionMap;
   
    public Controller() {
        super();
        actionMap = new HashMap<String, Action>();
    }

   @Override
   public void init() throws ServletException {
      // 생성자 다음에 수행하는 함수!
      // 첫 요청자에 의해 단 한번만 수행하는 곳
      
      //현재 서블릿이 생성될 때 전달받은 초기 파라미터를 가져온다.
      String props_path = getInitParameter("myParam");
      //   "/WEB-INF/action.properties"
      
      //받은 action.properties파일의 경로를 절대경로화 시킨다.
      // 하지만 jsp에서 마음대로 사용했던 내장객체인 application이 없다.
      // 서블릿에서는 application을 직접 구할 수 있다.
      ServletContext application = getServletContext(); 
      
      String realPath = application.getRealPath(props_path);
      
      //절대경로화 시킨 이유는
      //해당 파일의 내용(클래스 경로)을 스트림을 이용하여
      //읽어와서 Properties객체에 담기 위함이다.
      Properties props = new Properties();
      
      //Properties의 load함수를 이용하여 내용들을 읽기한다. 이때
      // 필요한 것이 InputStream이 필요하다.
      FileInputStream fis = null;
      try {
         //action.properties파일과 연결되는 스트림!
         fis = new FileInputStream(realPath);
         
         props.load(fis);// action.properties파일의 내용들을 읽어서 
         // 비어있던 Properties객체에 키와 값을 쌍으로 저장했다.
         // 예) greet ---> "ex3.GreetAction"
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      //////////////////////////////////////////////////////////
      ///////  생성할 객체들의 경로가 모두ㅡ
      ///////  Properties객체로 저장되었다. 하지만
      ///////  현재 컨트롤러 입장에서는 생성할 객체가 몇개이며,
      ///////  어떤 객체인지 알지 못한다. Properties에 저장된 
      ///////  키들을 모두 가져와서 반복자로 수행해야 한다.
      ////////////////////////////////////////////////////////////
      
      Iterator<Object> it = props.keySet().iterator();
      // greet, hi, now,...
      
      //키들을 모두 얻었으니 키에 연결된 클래스경로를 하나씩
      // 얻어내어 객체를 생성한 후 actionMap에 저장한다.
      while(it.hasNext()) {
         //먼저 키를 하나 가져와서 문자열로 변환!
         String key = (String)it.next();
         
         //위에서 얻어낸 키와 연결된 값을 얻자!
         String value = props.getProperty(key); // "ex3.GreetAction"
         
         try {
            Object obj = Class.forName(value).newInstance();
            // 쉽게 말해서 Class를 통해 정확한 클래스의 경로가 있다면
            // 객체를 생성할 수 있다.
            
            //생성된 객체를 Action으로 형변환하여 actionMap에 저장!
            actionMap.put(key, (Action)obj);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }



   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //요청시 한글처리
      request.setCharacterEncoding("utf-8");
      
      // type이라는 파라미터 값 받기
      String type = request.getParameter("type");
      
      // type이 null이면 기본객체를 인식할 수 있도록 "greet"을 넣는다.
      if(type == null)
         type = "list";
      
      //type으로 받은 값이 actionMap의 key로 사용되고 있으므로 
      // 원하는 객체를 얻어낸다.
      Action action = actionMap.get(type);
      
      String viewPath = action.execute(request, response);
      
      //viewPath가 null이면 total로 sendRedirect하자!
      if(viewPath == null) {
    	  String url = null;
    	  String cPage = request.getParameter("cPage");
    	  if(cPage != null)
    		  url="Controller?type=list&cPage="+cPage;
    	  else
    		  url="Controller";
         response.sendRedirect(url);
      } else {
      
         RequestDispatcher disp = 
               request.getRequestDispatcher(viewPath);
         disp.forward(request, response);
      }
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // TODO Auto-generated method stub
      doGet(request, response);
   }

}