package com.dht.www.exercise.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dht.www.exercise.model.service.ExerciseService;
import com.dht.www.exercise.model.vo.Exercise;
import com.dht.www.user.model.vo.Users;

@Controller
@RequestMapping("/exercise")
public class ExerciseController {

	@Autowired
	public ExerciseService exerciseService;
	
	
	// 종류선택 GET
	@RequestMapping(value="/type", method=RequestMethod.GET)
	public String exerciseTypeForm( HttpSession session ,  Model model , HttpServletRequest req) {
		
		// 로그인 세션 얻기
		Users user = (Users)session.getAttribute("logInInfo");
		
		//설정한 목표있는지 찾기
		int goal = exerciseService.selectGoalInfo(user);
		
		//목표값
		model.addAttribute("goal", goal);
			
		return "exercise/exercise1";
	}
	
	// 등급선택 GET
	@RequestMapping(value="/level", method=RequestMethod.GET)
	public String exercise2(HttpSession session, Model model, HttpServletRequest req) {

		// 비정상적인 접근 예외처리
		boolean res = false;
		
		try {
			if(session.getAttribute("level")  == null && session.getAttribute("level").equals("")) { }
		} catch (Exception e) {
			res = true;
		}
		
		if(res) {
			model.addAttribute("alertMsg", "비정상적인 접근입니다.");
			model.addAttribute("url", req.getContextPath()+"/main");
			return "common/result";
		}
		
		return "exercise/exercise2";
	}
	
	// 운동선택 GET - 비정상적인 접근 예외처리
	@RequestMapping(value="/select", method=RequestMethod.GET)
	public String exercise3( HttpSession session, Model model, HttpServletRequest req) {
		model.addAttribute("alertMsg", "비정상적인 접근입니다.");
		model.addAttribute("url", req.getContextPath()+"/main");
		return "common/result";
	}
	
	// 운동시작 GET - 비정상적인 접근 예외처리
	@RequestMapping(value="/trainning", method=RequestMethod.GET)
	public String exercise4( HttpSession session, Model model, HttpServletRequest req) {
		model.addAttribute("alertMsg", "비정상적인 접근입니다.");
		model.addAttribute("url", req.getContextPath()+"/main");
		return "common/result";
	}

	// 운동등급 선택
	@RequestMapping(value="/level", method=RequestMethod.POST)
	public String exerciseTypeToLevel(@RequestParam String exerType, HttpSession session, Model model) {
		session.setAttribute("exerType", exerType);
		
		return "exercise/exercise2";
	}
	
	// 운동 선택
	@RequestMapping(value="/select", method=RequestMethod.POST)
	public String exerciseLevelToSelect(@RequestParam String level, HttpSession session, Model model) {
		
		// 로그인 세션 얻기
		Users user = (Users) session.getAttribute("logInInfo");
		model.addAttribute("user", user);
		
		session.setAttribute("level", level);
		
		return "exercise/exercise3";
	}
	
	// 운동 진행
	@RequestMapping(value="/trainning", method=RequestMethod.POST)
	public String exerciseSelectToTrainning(@RequestParam(value="exerType", required=false) String exerType , @RequestParam(value="exercise", required=false) String exerciseName, HttpSession session, Model model) {
		
		// 로그인 세션 얻기
		Users user = (Users) session.getAttribute("logInInfo");
		
		// 만 나이 모델값 지정
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("id", user.getId());
		userInfo.put("birth", user.getBirth());
		
		model.addAttribute("userAge", exerciseService.selectUserAge(userInfo));
		
		// 새로운 목표 설정 시
		if(exerType == null) {
			
			// 설정한 운동 종류 모델값 지정 / 세션 등록
			String[] newExerArr = exerciseName.split(",");
			model.addAttribute("exerciseInfo", newExerArr);
			
			List<String> exerciseList = new ArrayList<>();
			for(int i=0; i<newExerArr.length; i++) {
				exerciseList.add(newExerArr[i]);
			}
			session.setAttribute("exerciseInfo", exerciseList);
			
			// 운동 개수 세션에 저장
			session.setAttribute("exerciseLength", newExerArr.length);
			
			// session으로 넘어온 등급
			int userLevel = 0;
			if(!session.getAttribute("level").equals("") && session.getAttribute("level") != null) {
				userLevel = Integer.parseInt((String) session.getAttribute("level"));
			}
			
			// 왼쪽 프로그레스바에 해당 운동만 보여줌
			List<Exercise> leftExerInfo = exerciseService.leftExerInfo(exerciseList);
			model.addAttribute("leftExerInfo", leftExerInfo);
			
			// 모델값에 운동*등급 계산한 값 전달 / 세션 등록
			int[] newExerCnt = exerciseService.selectCalcExerCnt(newExerArr, userLevel);
			model.addAttribute("exerciseCount", newExerCnt);
			session.setAttribute("exerciseCount", newExerCnt);
		}
		

		// 설정한 목표 설정 시 
		if (exerciseName == null) {
			
			// 설정한 운동 종류 가져오기
			String exerInfo = exerciseService.selectExerciseMyRoutine(user);
			
			String[] goalExerArr = exerInfo.split(",");
			
			// 설정한 운동 종류 모델값 지정 / 세션 등록
			model.addAttribute("exerciseInfo", goalExerArr);
			
			List<String> exerciseList = new ArrayList<>();
			
			for(int i=0; i<goalExerArr.length; i++) {
				exerciseList.add(goalExerArr[i]);
			}
			
			session.setAttribute("exerciseInfo", exerciseList);
			
			// 운동 개수 세션에 저장
			session.setAttribute("exerciseLength", goalExerArr.length);

			// 설정한 운동 등급 가져오기
			int exerGrade = exerciseService.selectExerciseMyGrade(user);
			
			// 왼쪽 프로그레스바에 해당 운동만 보여줌
			List<Exercise> leftExerInfo = exerciseService.leftExerInfo(exerciseList);
			model.addAttribute("leftExerInfo", leftExerInfo);
			
			// 모델값에 운동*등급 계산한 값 전달 / 세션 등록
			int[] newExerCnt = exerciseService.selectCalcExerCnt(goalExerArr, exerGrade);
			model.addAttribute("exerciseCount", newExerCnt);
			session.setAttribute("exerciseCount", newExerCnt);
		}
		
		return "exercise/exercise4";
	}
	
	// 다음 운동 진행 Ajax
	@RequestMapping(value="/nextexer", method=RequestMethod.GET)
	public String nextExer(@RequestParam String exerName, Model model) {
		return "exercise/"+exerName;
	}
			
	// 운동 완료 후 보상
	@RequestMapping(value="/exercisefinish", method=RequestMethod.GET)
	public String exerciseFinish(Model model, HttpSession session, HttpServletRequest req) {
		
		// 로그인 세션 얻기
		Users user = (Users) session.getAttribute("logInInfo");
		String userid = user.getId();
		
		// 세션에 있는 운동개수 int형으로 파싱
		int exerciseLength = 0;
		if(!session.getAttribute("exerciseLength").equals("") && session.getAttribute("exerciseLength") != null) {
			exerciseLength = (int) session.getAttribute("exerciseLength");
		}

		// 세션에 있는 운동등급 int형으로 파싱
		int exerciseLevel = 0;
		try {
			if(!session.getAttribute("level").equals("") && session.getAttribute("level") != null) {
				exerciseLevel = Integer.parseInt((String) session.getAttribute("level"));
			}			
		} catch (Exception e) {
			exerciseLevel = exerciseService.selectExerciseMyGrade(user);
		}
		
		Map<String, Object> rewardMap = new HashMap<>();
		
		rewardMap.put("userid", userid);
		rewardMap.put("exerciseLength", exerciseLength);
		rewardMap.put("exerciseLevel", exerciseLevel);
		
		// 안내 msg을 위한 point, coin 변수 생성
		int point = 0;
		int coin = 0;
		
		if(exerciseLength != 0 && exerciseLevel != 0) {
			// 포인트지급
			point = exerciseService.insertRewardPoint(rewardMap);
			// 코인지급
			coin = exerciseService.insertRewardCoin(rewardMap);
		}

		// 세션에 있는 운동종류만큼 String, int ArrayList 생성
		
		ArrayList<String> exerciseList = (ArrayList) session.getAttribute("exerciseInfo");
		int[] exerciseCount = (int[]) session.getAttribute("exerciseCount");
		
		Map<String, Object> recordMap = new HashMap<>();
		
		recordMap.put("userid", userid);
		recordMap.put("exerciseList", exerciseList);
		recordMap.put("exerciseCount", exerciseCount);
		recordMap.put("exerciseLength", exerciseCount.length);
		
		// 운동기록 저장
		exerciseService.insertEventRecord(recordMap);
		
		// 세션지우기
		session.removeAttribute("exerType");
		session.removeAttribute("level");
		session.removeAttribute("exerciseInfo");
		session.removeAttribute("exerciseLength");
		session.removeAttribute("exerciseCount");
		
		// 안내 msg
		String msg = "적립 포인트: " + point + "\\n적립 코인: " + coin + "\\n※ 코인은 하루 3개까지 받을 수 있습니다.";
		model.addAttribute("alertMsg", msg);
		model.addAttribute("url", req.getContextPath()+"/main");

		return "/common/result";
	}
}