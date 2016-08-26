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

# step07
- 각 서블릿마다 개별적으로 만든 ApplicationContext(스프링 IoC 컨테이너)를 공유하기
- ApplicationContextHelper 객체 생성 : ApplicationContext를 리턴해주는 역할.
- 서블릿은 ApplicationContextHelper를 이용하여 IoC 컨테이너를 준비한다.
  BoardXxxServlet 클래스 변경하라!

# step08
- 서블릿들이 사용할 공통 자원을 준비시키는 AppInitServlet 생성
- web.xml에 AppInitServlet 정보를 등록한다.
- 모든 서블릿에 대해 SerlvetContext에 보관된 Spring IoC 컨테이너를 사용하도록 변경한다. 
- ApplicationContextHelper 클래스는 더이상 필요없다. 제거한다. 

# step09
- 필터를 이용하여 요청 파라미터의 문자집합을 지정한다. : CharacterEncodingFilter
- 모든 서블릿에서 request.setCharacterEncoding() 호출을 제거하라!
- 서블릿에서 스프링 IoC 컨테이너를 준비하는 대신
  필터에서 준비해보자! : AppInitFilter
- 기존의 AppInitServlet 클래스는 제거한다.  
  
# step10
- 리스너를 이용하여 웹 애플리케이션이 시작될 때 스프링 IoC 컨테이너를 준비하자!
  ContextLoaderListener
- 웹 애플리케이션 DD 파일에 리스너를 등록하라!
  그리고 스프링 설정 파일의 위치 정보를 컨텍스트 파라미터에 설정하라!
- 기존의 AppInitFilter 클래스는 제거한다.







  



