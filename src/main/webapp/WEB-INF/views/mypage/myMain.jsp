<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>     
    
<script type="text/javascript" src="https://code.jquery.com/jquery-2.2.4.min.js"></script>

<!-- 캘린더 -->
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.3.2/main.min.css">
<script type="text/javascript" src="https://cdn.jsdelivr.net/combine/npm/fullcalendar@5.3.2/main.min.js,npm/fullcalendar@5.3.2"></script>


<!-- TAILWIND -->
<link  rel="stylesheet" href="https://unpkg.com/tailwindcss@1.5.2/dist/tailwind.min.css">
<link rel="stylesheet" href="https://unpkg.com/tailwindcss@^1.0/dist/tailwind.min.css">

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
  font-family: 'Droid Sans', sans-serif;
  outline: none;
}
::-webkit-scrollbar {
  background: transparent;
  width: 5px;
  height: 5px;
}
::-webkit-scrollbar-thumb {
  background-color: #888;
}
::-webkit-scrollbar-thumb:hover {
  background-color: rgba(0, 0, 0, 0.3);
}
/* body {background-color: #2a2b3d} */
/* body {background-color:#eee} */

#contents {
  position: relative;
  transition: .3s;
  margin-left: 290px;
  background-color: #2a2b3d;
}
.margin {
  margin-left: 0 !important;
}

/*progress --------------------------------------------------------- */ 


 #progress-wrapper{
    position: relative; 
   top: 35px;
   /* width: 404px; */
}
.progress-center {
   left: 50%;
   -webkit-transform: translate( -50% );
   -ms-transform: translate( -50% );
   transform: translate( -50% );
} 

/*===== The CSS =====*/
.progress{
   width: 200px;
   height: 280px;
}
.progress .track, .progress .fill{
   fill: rgba(0, 0, 0, 0);
   stroke-width: 3;
   transform: rotate(90deg)translate(0px, -80px);
}
.progress .track{
   stroke: rgb(56, 71, 83);
}
.progress .fill {
   stroke: rgb(255, 255, 255);
   stroke-dasharray: 219.99078369140625;
   stroke-dashoffset: -219.99078369140625;
   transition: stroke-dashoffset 1s;
}
.progress.blue .fill {
   stroke: rgb(104, 214, 198);
}
.progress.green .fill {
   stroke: rgb(186, 223, 172);
}
.progress .value, .progress .text {
   font-family: 'Open Sans';
   /* fill: rgb(255, 255, 255); */
   text-anchor: middle;
}
.progress .text {
   font-size: 12px;
}
.noselect {
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
      cursor: default;
}


</style>
</head>
<body>

<!-- 사이드 네비 메뉴 -->
<%@include file="/WEB-INF/views/mypage/navi.jsp" %>

 <div style="padding-left: 320px; padding-top: 100px; display:grid; grid-template-columns: 385px 900px; gap:50px; " >	

 <div style="grid-column: 1/2; grid-row: 1;">	
 
	  <div class="flex bg-indigo-800 max-w-sm mb-4">
	      <div class="w-16 bg-indigo">
	          <div class="p-4">
	              <svg class="h-8 w-8 text-white fill-current" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M468.907 214.604c-11.423 0-20.682 9.26-20.682 20.682v20.831c-.031 54.338-21.221 105.412-59.666 143.812-38.417 38.372-89.467 59.5-143.761 59.5h-.12C132.506 459.365 41.3 368.056 41.364 255.883c.031-54.337 21.221-105.411 59.667-143.813 38.417-38.372 89.468-59.5 143.761-59.5h.12c28.672.016 56.49 5.942 82.68 17.611 10.436 4.65 22.659-.041 27.309-10.474 4.648-10.433-.04-22.659-10.474-27.309-31.516-14.043-64.989-21.173-99.492-21.192h-.144c-65.329 0-126.767 25.428-172.993 71.6C25.536 129.014.038 190.473 0 255.861c-.037 65.386 25.389 126.874 71.599 173.136 46.21 46.262 107.668 71.76 173.055 71.798h.144c65.329 0 126.767-25.427 172.993-71.6 46.262-46.209 71.76-107.668 71.798-173.066v-20.842c0-11.423-9.259-20.683-20.682-20.683z"/><path d="M505.942 39.803c-8.077-8.076-21.172-8.076-29.249 0L244.794 271.701l-52.609-52.609c-8.076-8.077-21.172-8.077-29.248 0-8.077 8.077-8.077 21.172 0 29.249l67.234 67.234a20.616 20.616 0 0 0 14.625 6.058 20.618 20.618 0 0 0 14.625-6.058L505.942 69.052c8.077-8.077 8.077-21.172 0-29.249z"/></svg>
	          </div>
	      </div>
	      <div class="w-auto text-grey-darker items-center p-4">
	          <span class="text-lg font-bold pb-4 text-white">
	              운동단계
	          </span>
	          <p class="text-white">${user.name }님은 
	        	<span style="color: red; font-weight: bold;"><c:if test="${mygoal.grade eq 1 }">왕초보자</c:if></span> 		  
	        	<span style="color: red; font-weight: bold;"><c:if test="${mygoal.grade eq 2 }">초보자</c:if></span> 		  
	        	<span style="color: red; font-weight: bold;"><c:if test="${mygoal.grade eq 3 }">중급자</c:if></span> 		  
	        	<span style="color: red; font-weight: bold;"><c:if test="${mygoal.grade eq 4 }">고급자</c:if></span> 		  
	        	<span style="color: red; font-weight: bold;"><c:if test="${mygoal.grade eq 5 }">마스터</c:if></span> 		  
	          단계를 선택하셨습니다.</p>
	      </div>
	  </div>
   
	  <div class="flex bg-indigo-800 max-w-sm mb-4">
	      <div class="w-16 bg-indigo">
	          <div class="p-4">
	              <svg class="h-8 w-8 text-white fill-current" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M468.907 214.604c-11.423 0-20.682 9.26-20.682 20.682v20.831c-.031 54.338-21.221 105.412-59.666 143.812-38.417 38.372-89.467 59.5-143.761 59.5h-.12C132.506 459.365 41.3 368.056 41.364 255.883c.031-54.337 21.221-105.411 59.667-143.813 38.417-38.372 89.468-59.5 143.761-59.5h.12c28.672.016 56.49 5.942 82.68 17.611 10.436 4.65 22.659-.041 27.309-10.474 4.648-10.433-.04-22.659-10.474-27.309-31.516-14.043-64.989-21.173-99.492-21.192h-.144c-65.329 0-126.767 25.428-172.993 71.6C25.536 129.014.038 190.473 0 255.861c-.037 65.386 25.389 126.874 71.599 173.136 46.21 46.262 107.668 71.76 173.055 71.798h.144c65.329 0 126.767-25.427 172.993-71.6 46.262-46.209 71.76-107.668 71.798-173.066v-20.842c0-11.423-9.259-20.683-20.682-20.683z"/><path d="M505.942 39.803c-8.077-8.076-21.172-8.076-29.249 0L244.794 271.701l-52.609-52.609c-8.076-8.077-21.172-8.077-29.248 0-8.077 8.077-8.077 21.172 0 29.249l67.234 67.234a20.616 20.616 0 0 0 14.625 6.058 20.618 20.618 0 0 0 14.625-6.058L505.942 69.052c8.077-8.077 8.077-21.172 0-29.249z"/></svg>
	          </div>
	      </div>
	      <div class="w-auto text-grey-darker items-center p-4">
	          <span class="text-lg font-bold pb-4 text-white">
	              선택한운동
	          </span>
	          <p class="text-white">${user.name }님은 
	          <c:forEach items="${mygoal.exercises }" var="ex">
				<span style="color: red; font-weight: bold;">
						${ex['name'] }
				</span>           
	          </c:forEach>
	           운동을 선택하셨습니다.</p>
	      </div>
	  </div>
	  
	  	<div class="flex bg-indigo-800 max-w-sm mb-4">
		      <div class="w-16 bg-yellow">
		          <div class="p-4">
	              		<svg class="h-8 w-8 text-white fill-current" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M468.907 214.604c-11.423 0-20.682 9.26-20.682 20.682v20.831c-.031 54.338-21.221 105.412-59.666 143.812-38.417 38.372-89.467 59.5-143.761 59.5h-.12C132.506 459.365 41.3 368.056 41.364 255.883c.031-54.337 21.221-105.411 59.667-143.813 38.417-38.372 89.468-59.5 143.761-59.5h.12c28.672.016 56.49 5.942 82.68 17.611 10.436 4.65 22.659-.041 27.309-10.474 4.648-10.433-.04-22.659-10.474-27.309-31.516-14.043-64.989-21.173-99.492-21.192h-.144c-65.329 0-126.767 25.428-172.993 71.6C25.536 129.014.038 190.473 0 255.861c-.037 65.386 25.389 126.874 71.599 173.136 46.21 46.262 107.668 71.76 173.055 71.798h.144c65.329 0 126.767-25.427 172.993-71.6 46.262-46.209 71.76-107.668 71.798-173.066v-20.842c0-11.423-9.259-20.683-20.682-20.683z"/><path d="M505.942 39.803c-8.077-8.076-21.172-8.076-29.249 0L244.794 271.701l-52.609-52.609c-8.076-8.077-21.172-8.077-29.248 0-8.077 8.077-8.077 21.172 0 29.249l67.234 67.234a20.616 20.616 0 0 0 14.625 6.058 20.618 20.618 0 0 0 14.625-6.058L505.942 69.052c8.077-8.077 8.077-21.172 0-29.249z"/></svg>
		          </div>
		      </div>
		      <div class="w-auto text-grey-darker items-center p-4">
		          <span class="text-lg font-bold pb-4 text-white">
		            이번달 감량 칼로리
		          </span>
		          <p class="text-white">${user.name }님은 현재까지
		          <span style="color: red; font-weight: bold;"> ${decal }칼로리</span>를 감량하셨습니다.</p>
		      </div>
		</div>

 	<!-- progress -->
	  <div style="grid-column: 2/3; grid-row: 1; display: flex; justify-content: center; align-items: center;">
	      <svg class="progress blue noselect" data-progress="${successPercent}" x="0px" y="0px" viewBox="0 0 80 80">
	         <path class="track" d="M5,40a35,35 0 1,0 70,0a35,35 0 1,0 -70,0" />
	         <path class="fill" d="M5,40a35,35 0 1,0 70,0a35,35 0 1,0 -70,0" />
	         <text class="value" x="50%" y="55%">${successPercent}%</text>
	         <text class="text" x="50%" y="115%">목표달성률</text>
	      </svg>
	  </div>
	  
 </div>
  
	 <div id="calendar"></div>

 </div>
 
 <script type="text/javascript">



var dataset = [
		<c:forEach items='${mygoal.days}' var='day'>
		//	<c:forEach items='${mygoal.exercises}' var='ex'>
			{	
			//	title : '${ex['name']}',
				start : '${day}',
	 			color : '#6495ED',
				display : 'background'
			},
		//	</c:forEach>
		</c:forEach>

		<c:forEach items='${myrecord}' var='record'>
			<c:forEach items='${record.value}' var='val'>
				{
					title : '${val['name'] }',
					start : '${val['dates']}',
					color : '#50bcdf'
				},
			</c:forEach>
		</c:forEach>
		
		<c:forEach items='${successDate}' var='sd'>
			{
				start : '${sd}',
				color : '#DC143C',
				display : 'background'
			},
		</c:forEach>
		
	]	
		
		document.addEventListener('DOMContentLoaded', function() {
		    var calendarEl = document.getElementById('calendar');

		    var calendar = new FullCalendar.Calendar(calendarEl, {
		      headerToolbar: {
		        left: 'prev,next today',
		        center: 'title',
		        right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
		      },
		      initialDate: '2020-09-12',
		      navLinks: true, // can click day/week names to navigate views
		      businessHours: true, // display business hours
		      editable: true,
		      selectable: true,
		      events: dataset
		    });

		    calendar.render();
		  });

</script>
 
   
<script>


   $(function () {

     'use strict';

     var aside = $('.side-nav'),
         showAsideBtn = $('.show-side-btn'),
         contents = $('#contents'),
         _window = $(window)

     showAsideBtn.on("click", function () {
       $("#" + $(this).data('show')).toggleClass('show-side-nav');
       contents.toggleClass('margin');
     });

     if (_window.width() <= 767) {
       aside.addClass('show-side-nav');
     }

     _window.on('resize', function () {
       if ($(window).width() > 767) {
         aside.removeClass('show-side-nav');
       }
     });

     // dropdown menu in the side nav
     var slideNavDropdown = $('.side-nav-dropdown');

     $('.side-nav .categories li').on('click', function () {

       var $this = $(this)

       $this.toggleClass('opend').siblings().removeClass('opend');

       if ($this.hasClass('opend')) {
         $this.find('.side-nav-dropdown').slideToggle('fast');
         $this.siblings().find('.side-nav-dropdown').slideUp('fast');
       } else {
         $this.find('.side-nav-dropdown').slideUp('fast');
       }
     });

     $('.side-nav .close-aside').on('click', function () {
       $('#' + $(this).data('close')).addClass('show-side-nav');
       contents.removeClass('margin');
     });

   });
   
   
      
      
      /* progress */
      
   var forEach = function (array, callback, scope) {
      for (var i = 0; i < array.length; i++) {
         callback.call(scope, i, array[i]);
      }
   };
   
      var max = -219.99078369140625;
      forEach(document.querySelectorAll('.progress'), function (index, value) {
      percent = value.getAttribute('data-progress');
         value.querySelector('.fill').setAttribute('style', 'stroke-dashoffset: ' + ((100 - percent) / 100) * max);
         value.querySelector('.value').innerHTML = percent + '%';
      });
   
</script>
</body>
</html>