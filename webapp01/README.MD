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









  



