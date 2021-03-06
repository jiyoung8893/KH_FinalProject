package com.dht.www.shopping.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dht.www.shopping.model.service.ShoppingService;
import com.dht.www.shopping.model.vo.Basket;
import com.dht.www.shopping.model.vo.OrderProduct;
import com.dht.www.shopping.model.vo.Orders;

import com.dht.www.user.model.vo.Users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

	@Autowired
	private ShoppingService shoppingService;

	//쇼핑 홈 출력 리스트
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public void shoppingHome(Model model) {
		
		model.addAttribute("list1", shoppingService.selectHome("A"));
		model.addAttribute("list2", shoppingService.selectHome("B"));
		model.addAttribute("list3", shoppingService.selectHome("C"));
	}

	//게시판 서브 목록
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String shoppingList(Model model, @RequestParam(required = false, defaultValue = "1") int cPage,
			@RequestParam(required = false, defaultValue = "0") int listno) {

		int cntPerPage = 16;

		Map<String, Object> commandMap = shoppingService.selectList(listno, cPage, cntPerPage);

		model.addAttribute("list", commandMap.get("list"));
		model.addAttribute("paging", commandMap.get("paging"));
		model.addAttribute("listno", listno);

		return "/shopping/list";
	}

	//제품 상세페이지
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void shoppingDetail(Model model, String code) {
		model.addAttribute("detail", shoppingService.selectItem(code));
		model.addAttribute("files", shoppingService.selectFiles(code));
		model.addAttribute("avg", shoppingService.scoreAvg(code));
		model.addAttribute("reviews", shoppingService.selectReview(code));
		model.addAttribute("list", shoppingService.selectHome(code.substring(0, 1)));
		
	}

	//장바구니 모달창 AJAX ============================================
	@RequestMapping(value = "/modalload", method = RequestMethod.GET)
	public String modalLoad(Model model, String code) {
		model.addAttribute("detail", shoppingService.selectItem(code));
		return "/shopping/modalcontent";
	}
	@RequestMapping("/modalcontent")
	@ResponseBody
	public void modalContent() {}
	//==================================================================

	//장바구니 조회
	@RequestMapping(value = "/basket", method = RequestMethod.GET)
	public void shoppingBasket(Model model, HttpSession session) {

		Users user = (Users) session.getAttribute("logInInfo");

		if (user != null) {
			//로그인
			session.setAttribute("basket", shoppingService.selectBasket(user));
		} else {
			//비로그인
			List<Map<String, Object>> sessionBasket = (List<Map<String, Object>>) session.getAttribute("sessionBasket");

			if (sessionBasket != null && session.getId().equals((String) sessionBasket.get(0).get("sessionId"))) {
				session.setAttribute("basket", sessionBasket.get(1).values());
			} else {
				session.setAttribute("basket", null);
			}
		}
	}

	//사이드바 장바구니 조회 AJAX =================================================
	@RequestMapping(value = "/loadcart", method = RequestMethod.GET)
	public String loadCart(Model model, HttpSession session) {

		Users user = (Users) session.getAttribute("logInInfo");

		if (user != null) {
			//로그인
			model.addAttribute("basket", shoppingService.selectBasket(user));
		} else {
			//비로그인
			List<Map<String, Object>> sessionBasket = (List<Map<String, Object>>) session.getAttribute("sessionBasket");

			if (sessionBasket != null && session.getId().equals((String) sessionBasket.get(0).get("sessionId"))) {
				model.addAttribute("basket", sessionBasket.get(1).values());
			} else {
				model.addAttribute("basket", null);
			}
		}
		return "/shopping/cart";
	}
	@RequestMapping("/cart")
	@ResponseBody
	public void cart() { }
	//==============================================================================

	//장바구니 추가
	@RequestMapping(value = "/basket", method = RequestMethod.POST)
	@ResponseBody
	public void addBasket(Model model, HttpSession session,
			@RequestParam(required = false, defaultValue = "guest") String userId, String codes, int amount) {
		Users user = (Users) session.getAttribute("logInInfo");

		//장바구니에 추가할 item
		Basket insert = new Basket();
		insert.setCode(codes);
		insert.setId(userId); //로그인 회원일 때는 userId, 비로그인 회원일 때는 guest
		insert.setAmount(amount);

		//------------------------ 로그인 ------------------------
		if (user != null && userId.equals(user.getId())) {

			int res = shoppingService.checkBasket(insert);

			if (res > 0) {
				shoppingService.addAmount(insert);

			} else {
				shoppingService.insertBasket(insert);
			}
		//----------------------------------------------------------

		//------------------------ 비로그인 ------------------------
		} else {
			List<Map<String, Object>> sessionBasket = (List<Map<String, Object>>) session.getAttribute("sessionBasket");

			//세션에 등록된 장바구니가 없을 때
			if (sessionBasket == null) {
				sessionBasket = new ArrayList<Map<String, Object>>();

				Map<String, Object> sessionId = new HashMap<String, Object>();
				Map<String, Object> items = new HashMap<String, Object>();

				sessionId.put("sessionId", session.getId());

				Map<String, Object> sessionItem = shoppingService.sessionBasket(insert.getCode());
				sessionItem.put("amount", insert.getAmount());

				items.put(insert.getCode(), sessionItem);

				sessionBasket.add(sessionId);
				sessionBasket.add(items);

				session.setAttribute("sessionBasket", sessionBasket);
				session.setAttribute("basket", sessionBasket.get(1).values());

			//세션에 등록된 장바구니가 있을 경우
			} else {
				if (session.getId().equals((String) sessionBasket.get(0).get("sessionId"))) {

					//장바구니 들어있는 map
					Map<String, Object> items = sessionBasket.get(1);

					//해당 상품이 있을 경우
					if (items.get(insert.getCode()) != null) {
						Map<String, Object> sessionItem = (Map<String, Object>) items.get(insert.getCode());

						int updateAmount = (int) sessionItem.get("amount") + insert.getAmount();
						sessionItem.remove("amount");
						sessionItem.put("amount", updateAmount);

					} else { //해당 상품이 없을 경우

						Map<String, Object> sessionItem = shoppingService.sessionBasket(insert.getCode());
						sessionItem.put("amount", insert.getAmount());

						items.put(insert.getCode(), sessionItem);
					}
				}
			}
		}
		//----------------------------------------------------------

	}

	//장바구니 수량 업데이트
	@RequestMapping(value = "/amount", method = RequestMethod.GET)
	@ResponseBody
	public int updateAmount(@RequestParam(required = false, defaultValue = "guest") String userId, int amount,
			String code, HttpSession session) {
		Users user = (Users) session.getAttribute("logInInfo");
		if (user != null && user.getId().equals(userId)) {
			//로그인
			Basket insert = new Basket();
			insert.setId(user.getId());
			insert.setCode(code);
			insert.setAmount(amount);

			return shoppingService.updateAmount(insert);

		} else {
			//비로그인 - 장바구니 있을 때
			List<Map<String, Object>> sessionBasket = (List<Map<String, Object>>) session.getAttribute("sessionBasket");

			if (sessionBasket.get(0).get("sessionId").equals(session.getId())) {
				Map<String, Object> items = sessionBasket.get(1);

				Map<String, Object> item = (Map<String, Object>) items.get(code);
				item.put("amount", amount);

				return 0;

			} else {
				// 비로그인 - 장바구니 없을 때
				return -1;
			}
		}
	}

	//장바구니 개별 삭제 AJAX
	@RequestMapping(value = "/deletebasket", method = RequestMethod.POST)
	@ResponseBody
	public String deleteBasket(HttpSession session, int num, Basket basket) {

		Users user = (Users) session.getAttribute("logInInfo");

		if (user != null) {
			// 로그인
			int res = shoppingService.deleteBasket(basket);

			if (res > 0) {
				return "#b" + num;
			} else {
				return "fail";
			}
		} else {
			// 비로그인
			// basket - code, id
			List<Map<String, Object>> sessionBasket = (List<Map<String, Object>>) session.getAttribute("sessionBasket");

			if (sessionBasket.get(0).get("sessionId").equals(session.getId())) {
				Map<String, Object> items = sessionBasket.get(1);
				items.remove(basket.getCode());

				return "#b" + num;
			}
			return null;
		}
	}

	//장바구니 선택 삭제
	@RequestMapping(value = "/deletelist", method = RequestMethod.POST)
	public String deleteList(HttpSession session, String userId, String codes) {
		Users user = (Users) session.getAttribute("logInInfo");

		String[] array = codes.split(",");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("array", array);

		if (user != null) {
			// 로그인
			int res = shoppingService.deleteList(map);

		} else {
			// 비로그인
			List<Map<String, Object>> sessionBasket = (List<Map<String, Object>>) session.getAttribute("sessionBasket");

			if (sessionBasket.get(0).get("sessionId").equals(session.getId())) {
				Map<String, Object> items = sessionBasket.get(1);

				for (String code : array) {
					items.remove(code);
				}
			}
		}
		return "redirect:/shopping/basket";
	}

	// 결제페이지
	@RequestMapping(value = "/payment", method = RequestMethod.GET, produces = "application/text; charset=UTF-8")
	public String shoppingPayment(Model model, HttpSession session, 
			@RequestParam(required = false, defaultValue = "0") int amount, String userId, String codes, int sale) {

		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		List name = new ArrayList<String>();
		List company = new ArrayList<String>();

		//상세페이지나 모달에서 주문할때 
		// 상품 정보 조회해서 payment.jsp로 전달
		if (amount > 0) {
			map = shoppingService.sessionBasket(codes);
			map.put("amount", amount);
			list.add(map);
				
				Map<String, Object> pro = new HashMap<String,Object>();
				
				name.add(list.get(0).get("name"));
				company.add(list.get(0).get("company"));
				
				pro.put("ext", list.get(0).get("ext"));
				pro.put("path", list.get(0).get("path"));
				pro.put("amount", list.get(0).get("amount"));
				pro.put("code", list.get(0).get("code"));
				pro.put("price", list.get(0).get("price"));
				pro.put("event", list.get(0).get("event"));
				pro.put("renamed", list.get(0).get("renamed"));
				
				result.add(0, pro);
			
		//장바구니에서 주문할때 코드번호를 받아서
		// 상품 정보 조회해서 payment.jsp로 전달
		} else {
			String[] array = codes.split(",");
			map.put("userId", userId);
			map.put("array", array);
			
			list = shoppingService.selectProuct(map);
			
			for(int i=0; i<list.size(); i++) {
				
				Map<String, Object> pro = new HashMap<String,Object>();
				
				name.add(i, list.get(i).get("name"));
				company.add(i, list.get(i).get("company"));
				
				pro.put("ext", list.get(i).get("ext"));
				pro.put("path", list.get(i).get("path"));
				pro.put("amount", list.get(i).get("amount"));
				pro.put("code", list.get(i).get("code"));
				pro.put("price", list.get(i).get("price"));
				pro.put("event", list.get(i).get("event"));
				pro.put("renamed", list.get(i).get("renamed"));
				
				result.add(i, pro);
			}
		}
			
			model.addAttribute("product", result);
			model.addAttribute("productname",name );
			model.addAttribute("productcompany",company );
			
		model.addAttribute("point", shoppingService.selectPoint(userId));
		
		if( sale != 0) {
			
			model.addAttribute("sale",sale);
		}else {
			model.addAttribute("sale",0);
		}
		
		return "shopping/payment";
	}

		//결제 API 완료 시 AJAX 통신으로 결과 데이터 저장
	   @RequestMapping(value="/paymentCheck", method = RequestMethod.POST)
	   @ResponseBody
	   public void shoppingPaymentCheck(@RequestBody String uid, HttpSession session) {
		   
		  //GSON 객체 생성
	      Gson gson = new GsonBuilder().create();
	      
	      //AJAX로 전달 된 JSON 데이터를 GSON 객체로 변환
	      Map<String, String> another =gson.fromJson(uid, new TypeToken<Map<String, String>>(){}.getType());
	      
	      // GSON 객체로 변환 된 객체인데 map 형태의 배열로 되어있는 product 값을 다시 한번 파싱
	      List<Map<String,String>> result = gson.fromJson(another.get("product"), new TypeToken<List<Map<String,String>>>(){}.getType());
	         Orders order = new Orders();
	         
	         Users user = (Users)session.getAttribute("logInInfo");
	         //------------------------------------------------------------
	         
	         order.setId(user.getId());
	         order.setmUid(another.get("imp_uid"));
	         
	         // 기본 배송지로 설정하면 세션에 저장되어있는 정보가 넘어오고
	         // 신규 배송지로 설정하면 name 값으로 정보가 넘어온다
	         
	         // 신규배송지 설정이 아닐때
	         if(another.get("name") != null ) {
	            
	            order.setToName(another.get("name"));
	            order.setToTel(another.get("tel"));
	            order.setToAddr(another.get("addr"));
	            order.setToPost(another.get("post"));
	            
		     // 신규배송지 설정일때        
	         }else {
	            
	            order.setToName(user.getName());
	            order.setToTel(user.getTel());
	            order.setToAddr(user.getAddr());
	            order.setToPost(user.getPost());
	            
	         }
	         
	         int ordersNo = shoppingService.selectOrdersNo();
	         order.setNo(ordersNo);
	         
	         //주문 내역 저장
	         shoppingService.insertOrders(order);
	         
	         Map userPoint = new HashMap();
	         List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
	         
	         int point=0;
	         int mount =0;
	         
	         if(another.get("point") != null && !"".equals(another.get("point"))) {
	            point = Integer.parseInt(another.get("point"));
	         }
	         
	         userPoint.put("id", user.getId());
	         userPoint.put("point", point);
	         
	         // 사용 포인트 내역 저장
	         shoppingService.insertPoint(userPoint);
	         
	         if(point != 0) {
	        	 point = point / result.size();
	         }
	         
	         
	         // 주문 내역 당 품목별 주문상푸 저장
	         StringBuilder sb = new StringBuilder();
	         for(int i=0; i<result.size(); i++) {
	            
	            OrderProduct orderProduct = new OrderProduct();
	            if(result.get(i).get("amount") != null && result.get(i).get("amount") != "") {
	               mount = Integer.parseInt(result.get(i).get("amount"));
	            }
	            orderProduct.setAmount(mount);
	            orderProduct.setPoint(point);
	            orderProduct.setCode(result.get(i).get("code"));
	            orderProduct.setOrdersNo(ordersNo);
	            orderProductList.add(orderProduct);
	            sb.append(result.get(i).get("code")+",");
	         }
	         shoppingService.insertOrderProduct(orderProductList);
	         
	         // 구입한 상품 장바구니 비우기
	         Map<String, Object> delete = new HashMap<String, Object>();
	         delete.put("userId", user.getId());
	         delete.put("array", sb.toString().split(","));
	         shoppingService.deleteList(delete);
	   }

	//AJAX 완료 맨 마지막 페이지 이동
	@RequestMapping(value = "/paymentComplete", method = RequestMethod.GET)
	public void shoppingPaymentComplete() {

	}

	// 배송지정보 선택 시 ajax 통신으로 결과 불러오기
	@RequestMapping(value = "/delivery", method = RequestMethod.GET)
	public String shoppingDelivery(@RequestParam int num) {

		// 기본 개인정보 배송지 전달
		if (num == 0) {
			return "shopping/delivery_basic";
		// 신규 배송지 입력 폼 전달
		} else {
			return "shopping/delivery_new";
		}
	}

	//쇼핑 헤더 - 검색
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public void shoppingSearch(Model model, @RequestParam(required = false, defaultValue = "1") int cPage,
			@RequestParam(required = false, defaultValue = "") String search) {

		int cntPerPage = 16;

		Map<String, Object> commandMap = shoppingService.selectSearch(search, cPage, cntPerPage);

		model.addAttribute("list", commandMap.get("list"));
		model.addAttribute("paging", commandMap.get("paging"));
	}

}
