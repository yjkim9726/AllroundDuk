$(document).ready(
		function() {
			// 데이터 가져오기
			console.log(positions);

			var mapContainer = document.getElementById('map'); // 지도를 표시할 div
			mapOption = {
				// 지도의 중심좌표
				center : new kakao.maps.LatLng(37.570214, 126.98324),
				// 지도의 확대 레벨
				level : 3
			};

			// 지도를 생성합니다
			var map = new kakao.maps.Map(mapContainer, mapOption);

			// 지도 확대, 축소 컨트롤에서 확대 버튼을 누르면 호출되어 지도를 확대하는 함수
			$('#zoomIn').click(function() {
				map.setLevel(map.getLevel() - 1);
			})

			// 지도 확대, 축소 컨트롤에서 축소 버튼을 누르면 호출되어 지도를 확대하는 함수
			$('#zoomOut').click(function() {
				map.setLevel(map.getLevel() + 1);
			})

			var imageSrc = '/resources/img/gym-marker.png'; // 마커이미지의 주소입니다
			imageSize = new kakao.maps.Size(30, 35); // 마커이미지의 크기입니다
			// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
			var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize)

			// 주소-좌표 변환 객체를 생성합니다
			var geocoder = new kakao.maps.services.Geocoder();

			// 주소로 좌표를 검색합니다
			positions.forEach(function(mapdata, idx) {
				geocoder.addressSearch(mapdata.address,
						function(result, status) {

							// 정상적으로 검색이 완료됐으면
							if (status === kakao.maps.services.Status.OK) {

								var marker = new kakao.maps.Marker({
									map : map, // 마커를 표시할 지도
									position : new kakao.maps.LatLng(
											result[0].y, result[0].x), 
											// 마커의 위치
									image : markerImage
								// 마커이미지 설정
								});

								// 마커에 표시할 인포윈도우를 생성합니다
								var infowindow = new kakao.maps.InfoWindow({
									content : mapdata.name
								// 인포윈도우에 표시할 내용
								});

								// 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
								// 이벤트 리스너로는 클로저를 만들어 등록합니다
								// for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
								kakao.maps.event.addListener(marker,
										'mouseover', makeOverListener(map,
												marker, infowindow));
								kakao.maps.event
										.addListener(marker, 'mouseout',
												makeOutListener(infowindow));
							} else {
							}
						})
			});

			// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
			function makeOverListener(map, marker, infowindow) {
				return function() {
					infowindow.open(map, marker);
				};
			}

			// 인포윈도우를 닫는 클로저를 만드는 함수입니다
			function makeOutListener(infowindow) {
				return function() {
					infowindow.close();
				};
			}

			// //////////////////////////////////

			/*
			 * // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다 var bounds = new
			 * kakao.maps.LatLngBounds();
			 * 
			 * for (var i = 0; i < positions.length; i++) {
			 *  // LatLngBounds 객체에 좌표를 추가합니다
			 * bounds.extend(positions[i].latlng); }
			 * 
			 * function setBounds() { // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를
			 * 재설정합니다 // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다 map.setBounds(bounds); }
			 */

		})