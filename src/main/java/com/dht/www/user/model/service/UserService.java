package com.dht.www.user.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dht.www.mypage.model.vo.Files;
import com.dht.www.user.model.vo.Users;

public interface UserService {
	
	// 로그인
	public Users selectUser(Users user);
	
	//계정중지 회원확인
	public boolean checkReportedAccount(Users user);
		
	//code를 보내고 access_token을 받아오는 코드 
	public <JsonNode> JsonNode getAccessToken(String code);
	
	//토큰을 통해 회원정보를 가져오는 코드 
	public <JsonNode> JsonNode getKakaoUserInfo(JsonNode access_token);

	//아이디 찾을때 사용하는 메소드 
	public Users getUsersId(Map<String, Object> commandMap);
	
	//회원 메일로 아이디 발송
	public void mailSendingToFindId(Map<String, Object> commandMap, String urlPath, String searchId);
	
	//회원인 경우 임시비밀번호 암호화
	public Users getUsersPw(Map<String, Object> commandMap, String randomPw);
	
	//회원 메일로 비밀번호 발송
	public void mailSendingToFindPw(Map<String, Object> commandMap, String urlPath, String randomPw);
	
	//프로필 이미지 저장
	public void insertUserProfile(List<MultipartFile> files, Users user, String root);
	
	//회원 가입을 위한 이메일 발송
	public void mailSendingToJoin(Users users, String urlPath);
	
	//회원 정보 저장
	public int insertUser(Users users);
	
	//회원 프로필 이미지 파일 정보 
	public Files selectUserProfile(Users user);
	
	//아이디 중복 확인
	public int idCheck(String id);
	
	//닉네임 중복 확인
	public int nickCheck(String nick);
	
	//이메일 중복 확인
	public int mailCheck(String mail);
	
	//카카오 또는 구글로 회원가입한 회원 조회
	public Users selectUserByApiId(String apiId);
	
	
}
