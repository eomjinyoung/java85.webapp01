package example.dao;

import java.util.Map;

import example.vo.Member;

public interface MemberDao {
  Member selectOneByEmailAndPassword(Map<String,Object> paramMap);
}
