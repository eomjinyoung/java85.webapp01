# step01 
- 프로젝트 폴더 준비

# step02
- 게시물 목록 조회 추가 = Spring + Mybatis + Servlet

# step03
- 게시물 상세조회 : BoardDetailServlet
- 게시물 목록에 "게시물 상세조회" 링크 추가

# step04
- 게시물 삭제 : BoardDeleteServlet
- 게시물 상세조회 페이지에 "게시물 삭제" 링크 추가
- 게시물 삭제 서블릿에 리프래시(refresh) 추가 

# step05
- 게시물 입력 폼 추가  : /board/form.html
- 게시물 목록조회 페이지에 "게시물 입력폼" 링크 추가
- 게시물 입력 : BoardAddServlet

# step06
- 게시물 변경 : BoardUpdateServlet
- 게시물 상세보기 페이지를 변경 가능 페이지로 바꾼다: BoardDetailServlet

# step07 (스프링 IoC 컨테이너 응용)
- 각 서블릿마다 개별적으로 만든 ApplicationContext(스프링 IoC 컨테이너)를 공유하기
- ApplicationContextHelper 객체 생성 : ApplicationContext를 리턴해주는 역할.
- 서블릿은 ApplicationContextHelper를 이용하여 IoC 컨테이너를 준비한다.
  BoardXxxServlet 클래스 변경하라!

# step08 (서블릿 응용)
- 서블릿들이 사용할 공통 자원을 준비시키는 AppInitServlet 생성
- web.xml에 AppInitServlet 정보를 등록한다.
- 모든 서블릿에 대해 SerlvetContext에 보관된 Spring IoC 컨테이너를 사용하도록 변경한다. 
- ApplicationContextHelper 클래스는 더이상 필요없다. 제거한다. 

# step09 (필터 응용)
- 필터를 이용하여 요청 파라미터의 문자집합을 지정한다. : CharacterEncodingFilter
- 모든 서블릿에서 request.setCharacterEncoding() 호출을 제거하라!
- 서블릿에서 스프링 IoC 컨테이너를 준비하는 대신
  필터에서 준비해보자! : AppInitFilter
- 기존의 AppInitServlet 클래스는 제거한다.  
  
# step10 (리스너 응용)
- 리스너를 이용하여 웹 애플리케이션이 시작될 때 스프링 IoC 컨테이너를 준비하자!
  ContextLoaderListener
- 웹 애플리케이션 DD 파일에 리스너를 등록하라!
  그리고 스프링 설정 파일의 위치 정보를 컨텍스트 파라미터에 설정하라!
- 기존의 AppInitFilter 클래스는 제거한다.

# step11 (Spring MVC 라이브러리의 필터와 리스너 사용)
- Spring MVC 프레임워크의 일부 기능 사용
  => 클라이언트가 POST 요청으로 보내는 데이터의 문자집합을 지정하는 필터
       org.springframework.web.filter.CharacterEncodingFilter
  => 스프링 IoC 컨테이너를 준비하는 리스너
       org.springframework.web.context.ContextLoaderListener
- 기존의 필터와 리스너는 제거한다.
- 스프링에서 준비한 IoC 컨테이너를 사용하려면 도우미 클래스를 이용해야 한다.
  예전처럼 ServletContext 객체에서 스프링 IoC 컨테이너를 꺼낼 수 없다.
  => 각 서블릿의 코드를 변경하라!

# step12 (프론터 컨트롤러 도입)
- 클라이언트 요청을 처리하는 서블릿의 관리자를 
  서블릿 컨테이너에서 스프링 IoC 컨테이너로 바꾼다.
  => 이점
     1) 스프링 IoC 컨테이너에서 일괄적으로 객체를 관리할 수 있다.
     2) 클라이언트 요청을 처리하는 것은 POJO 가 처리할 수 있다.
        (POJO: Plain Old Java Object. JavaSE 기술로 만든 객체)
  => 기존의 서블릿이 스프링 IoC 컨테이너의 관리 영역으로 넘어가기 때문에
     클라이언트의 요청을 받아서 넘겨주는 역할을 할 서블릿 클래스가 필요하다.
  => "프론트 컨트롤러(클라이언트 요청을 받아서 넘기는 역할을 할 서블릿)"과
     "워커(실제 클라이언트 요청을 처리하는 객체)"
      사이에 호출 규칙도 필요하다.
     
- 절차
1) 프론트 컨트롤러와 워커 사이에 호출 규칙을 정한다.
   => Worker 인터페이스 작성 
2) 기존 서블릿을 스프링이 관리하는 영역으로 옮긴다.
   => 서블릿 클래스를 Worker 구현체로 변경한다.
3) 클라이언트 요청을 전달할 서블릿 객체를 만든다.
   => DispatcherServlet 클래스 작성

# step13 (HttpServlet 클래스와 Redirect, Refresh의 응용)
- DispatcherServlet 클래스의 상위 클래스를 GenericServlet 클래스에서
  HttpServlet 클래스로 교체한다.
- Worker의 execute() 메서드에서 파라미터 타입을 변경한다.
- Worker 구현체를 변경한다.
- Redirect 적용하기
  => BoardAddWorker, BoardDeleteWorker, BoardUpdateWorker
  
# step14 (forward/include, ServletRequest 보관소 응용)
- 오류를 처리할 서블릿 추가한다.
  => ErrorServlet
- 서블릿에 있는 오류 처리 코드를 제거하고 대신에 
  ErrorServlet 으로 포워딩한다.
- ServletRequest 보관소를 사용하여 오류 정보를 ErrorServlet과 공유한다.
- include 응용
  FooterServlet 추가 및 include 
  HeaderServlet 추가 및 include 

# step15 (세션을 활용한 로그인/로그아웃 구현)
- LoginServlet 클래스 추가
  doGet() : 로그인 폼 제공
  doPost() : 로그인 처리
- LogoutServlet 클래스 추가
  doGet() : 세션 무효화처리  
- "Member" Value Object 클래스 추가  
- MemberDao 인터페이스 추가
- MemberMapper.xml 파일 추가
- HeaderServlet 에 로그인 사용의 이름 출력하기

# step16 (쿠키를 이용한 로그인 이메일 저장 구현)
- LoginServlet에 "이메일저장" 기능 추가

# step17 (JSP를 이용하여 서블릿에서 출력 코드를 분리하기)
- LoginServlet에서 로그인폼 출력 코드를 JSP로 분리하기
  => /webapp/auth/LoginForm.jsp

# step18 (JSP를 다른 모든 서블릿(또는 워커)에 적용한다.)
- BoardListWorker에 JSP 적용 => /webapp/board/BoardList.jsp 파일 추가
- BoardDetailWorker에 JSP 적용 => /webapp/board/BoardDetail.jsp 파일 추가  
- FooterServlet을 footer.jsp로 전환
  => FooterServlet 클래스 삭제
- HeaderServlet을 header.jsp로 전환
  => HeaderServlet 클래스 삭제
- ErrorServlet을 error.jsp로 전환
  => ErrorServlet 클래스 삭제

# step19 (로그인/로그아웃 서블릿을 Worker 객체로 전환)
- LoginServlet 클래스를 LoginWorker 클래스로 변경
  => /webapp/auth/LoginForm.jsp에서 <form>의 action 속성 변경
  => header.jsp 에서 링크 변경
  => LoginServlet 클래스 삭제
- LogoutServlet 클래스를 LogoutWorker 클래스로 변경 
  => LogoutServlet 클래스 삭제








  



