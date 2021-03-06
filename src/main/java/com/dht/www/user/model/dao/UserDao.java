package com.dht.www.user.model.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dht.www.mypage.model.vo.Files;
import com.dht.www.user.model.vo.Users;


@Repository
public class UserDao {
	
	@Autowired
	SqlSessionTemplate session;
	
	//로그인
	public Users selectUser(Users user) {
		
		return session.selectOne("USERS.selectUser", user);
	}

	//아이디를 찾기 위한 조회
	public Users getUsersId(Map<String, Object> commandMap) {

		return session.selectOne("USERS.getUsersId", commandMap);
	}
	
	//비밀번호를 찾기 위한 회원 조회
	public Users getUsersPw(Map<String, Object> commandMap) {

		return session.selectOne("USERS.getUsersPw", commandMap);
	}
	
	//임시비밀번호로 업데이트
	public int updateUsersPw(Users res) {
		
		return session.update("USERS.updateUsersPw", res);
	}
	
	//회원정보 입력
	public int insertUser(Users user) {

		return session.insert("USERS.insertUser", user);
	}
	
	//회원 프로필 사진 입력
	public int insertUserProfile(Map<String, Object> f) {

		return session.insert("USERS.insertUserProfile", f);
	}

	public int insertBasicProfile(Users user) {

		return session.insert("USERS.insertBasicProfile", user);
	}
	
	//회원 프로필 이미지 정보
	public Files selectUserProfile(Users user) {

		return session.selectOne("USERS.selectUserProfile", user);
	}
	
	//아이디 중복 확인
	public int idCheck(String id) {

		return session.selectOne("USERS.idCheck", id);
	}

	//닉네임 중복 확인
	public int nickCheck(String nick) {

		return session.selectOne("USERS.nickCheck", nick);
	}
	
	//이메일 중복 확인
	public int mailCheck(String mail) {

		return session.selectOne("USERS.mailCheck", mail);
	}

	//카카오 또는 구글로 회원가입한 회원 조회
	public Users selectUserByApiId(String apiId) {

		return session.selectOne("USERS.selectUserByApiId", apiId);
	}
	


}
