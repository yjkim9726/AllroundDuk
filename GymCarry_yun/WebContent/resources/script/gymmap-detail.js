window.onload = function() {
			// 데이터 가져오기
			console.log(mapdata);

			var mapContainer = document.getElementById('map'); // 지도를 표시할 div
			mapOption = {
				// 지도의 중심좌표
				center : new kakao.maps.LatLng(37.570214, 126.98324),
				// 지도의 확대 레벨
				level : 2
			};

			console.log(mapContainer);
			// 지도를 생성합니다
			var map = new kakao.maps.Map(mapContainer, mapOption);

			var imageSrc = '/resources/img/gym-marker.png'; // 마커이미지의 주소입니다
			imageSize = new kakao.maps.Size(30, 35); // 마커이미지의 크기입니다
			// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
			var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

			// 주소-좌표 변환 객체를 생성합니다
			var geocoder = new kakao.maps.services.Geocoder();

			// 주소로 좌표를 검색합니다
				geocoder.addressSearch(mapdata.address, function(result, status) {

							// 정상적으로 검색이 완료됐으면
							if (status === kakao.maps.services.Status.OK) {

								var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
								
								var marker = new kakao.maps.Marker({
									map : map, // 마커를 표시할 지도
									position : coords, // 마커의 위치
									image : markerImage // 마커이미지 설정
								})
								// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
						        map.setCenter(coords);
							}
				});
		}