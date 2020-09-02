<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 캠 레이아웃 시작 -->
<div class="antialiased max-w-full mx-auto px-8">
	<div class="relative block md:flex items-center">
		<div class="w-auto md:w-1/2 relative z-1 bg-gray-100 rounded shadow-lg overflow-hidden">
			<div class="text-lg font-medium text-black-500 uppercase p-8 text-center border-b border-gray-200 tracking-wide">운동 정보</div>
			<div class="block sm:flex md:block items-center justify-center layout-overflow">
				<div class="w-full text-center">

					<!-- 단계별 설명 탬플릿 시작 -->
				<div class="flex flex-col justify-center m-auto">
					<div class="flex flex-col bg-teal-200 justify-center text-center">
						<div class="flex flex-col justify-center items-center relative">
							<div class="w-56 h-12 hidden justify-center">
								<div class="h-full  border-teal-300 border-dashed"></div>
							</div>
							<div
								class="rounded-full w-12 h-12 text-xl text-teal-100 bg-teal-700 font-black flex justify-center items-center absolute top-0 right-0 mt-16 shadow-lg mr-2">1</div>
							<img alt="step1" class="w-56 h-56 shadow my-5 object-scale-down" src="<%=request.getContextPath() %>/resources/image/exercise/Lunge1-1.png">
							<div class="w-56 h-12 hidden justify-center">
								<div class="h-full border-r-4 border-teal-300 border-dashed"></div>
							</div>
						</div>
						<div class="ml-5 p-10 flex flex-col justify-center max-w-2xl rounded bg-teal-200">
							<div class="text-xs uppercase font-bold text-teal-500">Step 1</div>
							<div class="text-xl font-bold text-teal-700">골반너비로 두 발을 벌리고 허리에 손을 대고 바로 서줍니다. 한쪽 발을 70~100cm 가량 앞으로 벌려 내밀고, 왼발의 뒤꿈치를 세워줍니다.</div>
						</div>
					</div>

					<div class="flex flex-col bg-orange-200 justify-center text-center">
						<div class="flex flex-col justify-center items-center relative">
							<div class="w-56 h-12 hidden justify-center">
								<div class="h-full border-r-4 border-orange-300 border-dashed"></div>
							</div>
							<div class="rounded-full w-12 h-12 text-xl text-orange-100 bg-orange-700 font-black flex justify-center items-center absolute top-0 right-0 mt-16 shadow-lg mr-2">2</div>
							<img alt="step2" class="w-56 h-56 shadow my-5 object-scale-down" src="<%=request.getContextPath() %>/resources/image/exercise/Lunge1-2.png">
							<div class="w-56 h-12 hidden justify-center">
								<div class="h-full border-r-4 border-orange-300 border-dashed"></div>
							</div>
						</div>
						<div class="ml-5 p-10 flex flex-col justify-center max-w-2xl rounded bg-orange-200">
							<div class="text-xs uppercase font-bold text-orange-500">Step 2</div>
							<div class="text-xl font-bold text-orange-700">허리와 등을 똑바로 편 채로 오른편 무릎을 90도로 구부리고 반대편 무릎은 바닥에 닿는 느낌으로 몸을 내려줍니다. 하체 힘을 써서 처음 자세로 천천히 돌아오고 동작으로 반복합니다.</div>
						</div>
					</div>


				</div>
				<!-- 단계별 설명 탬플릿 끝 -->

			</div>
		</div>
	</div>
	
	<div class="w-full md:w-1/2 relative z-0">
		<div
			class="bg-blue-900 text-white rounded-b md:rounded-b-none md:rounded-r shadow-lg overflow-hidden">
			<div class="text-lg font-medium text-white-500 uppercase p-8 text-center border-b border-gray-200 tracking-wide">
<!-- 									<button type="button" onclick="init()">운동 Start</button> -->
				운동 촬영 영상
			</div>
			
			
			
				<!-- 진짜 웹캠 공간 -->
			
				<div class="text-center text-sm sm:text-md max-w-lg mx-auto text-gray-900 mt-8 px-8 lg:px-0 layout-cam">
			
				<div><canvas id="canvas" style="display: inline;"></canvas></div>
				<div id="label-container"></div>

			
			</div>
			<!-- 진짜 웹캠 공간끝 -->
			
			
			
			
			
		
		
		
			
			
			
			
			<div class="mt-8 border border-blue-800 mx-8 lg:mx-16 flex flex-wrap">
			
				<!-- 프로그래스바 템플릿 시작 -->
				<div class="" style="margin: 10px 15px; width: 100%;">
					<div class="" style="width: 100%;">
						<h5>세트</h5>
						<div class="progress">
							<div class="progress-bar-set" role="progressbar"
								aria-valuenow="0" aria-valuemin="0" aria-valuemax="2">0</div>
						</div>
						<h5>횟수</h5>
						<div class="progress" >
							<div class="progress-bar-count" role="progressbar"
								aria-valuenow="0" aria-valuemin="0" aria-valuemax="2">0</div>
						</div>
					</div>
				</div>
				<!-- 프로그래스바 템플릿 끝 -->
					
				</div>
				<a class="block flex items-center justify-center bg-blue-800 hover:bg-blue-700 p-4 text-md font-semibold text-gray-300 uppercase mt-8 complete"
					href="#" > <span>운동 보상 받기</span> <span class="font-medium text-gray-300 ml-2">➔</span>
				</a>
			</div>
		</div>
		
	</div>
	
</div>
<!-- 캠 레이아웃 끝 -->