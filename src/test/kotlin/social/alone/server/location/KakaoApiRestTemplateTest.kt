package social.alone.server.location


import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate

import org.assertj.core.api.Assertions.assertThat

@SpringBootTest
@RunWith(SpringRunner::class)
class KakaoApiRestTemplateTest {

    @Autowired
    lateinit var kakaoApiRestTemplate: KakaoApiRestTemplate

    @Test
    @Ignore
    fun test() {
        val kakao = kakaoApiRestTemplate.kakaoLocalSearchApi()
        val result = kakao.getForObject("/v2/local/search/keyword.json?query={query}", String::class.java, "낙성대")
        assertThat(result).isNotEmpty()

        /**
         * {"documents":[{"address_name":"서울 관악구 봉천동 228","category_group_code":"","category_group_name":"","category_name":"여행 \u003e 관광,명소 \u003e 문화유적 \u003e 사당,제단","distance":"","id":"16044819","phone":"02-877-6896","place_name":"낙성대","place_url":"http://place.map.kakao.com/16044819","road_address_name":"서울 관악구 낙성대로 77","x":"126.96108773362158","y":"37.47087306650889"},{"address_name":"서울 관악구 봉천동 1693-39","category_group_code":"SW8","category_group_name":"지하철역","category_name":"교통,수송 \u003e 지하철,전철 \u003e 수도권2호선","distance":"","id":"21160588","phone":"02-6110-2271","place_name":"낙성대역 2호선","place_url":"http://place.map.kakao.com/21160588","road_address_name":"","x":"126.963523905001","y":"37.4770932409965"},{"address_name":"서울 관악구 봉천동 1620-9","category_group_code":"PO3","category_group_name":"공공기관","category_name":"사회,공공기관 \u003e 행정기관 \u003e 경찰서 \u003e 지구대","distance":"","id":"21279538","phone":"02-870-0820","place_name":"낙성대지구대","place_url":"http://place.map.kakao.com/21279538","road_address_name":"서울 관악구 봉천로 540","x":"126.9576512774665","y":"37.478429451435254"},{"address_name":"서울 관악구 봉천동 228","category_group_code":"","category_group_name":"","category_name":"여행 \u003e 공원 \u003e 도시근린공원","distance":"","id":"10203930","phone":"02-879-6519","place_name":"낙성대공원","place_url":"http://place.map.kakao.com/10203930","road_address_name":"서울 관악구 낙성대로 77","x":"126.96035949202104","y":"37.47129808865604"},{"address_name":"서울 관악구 봉천동 1631-16","category_group_code":"","category_group_name":"","category_name":"사회,공공기관 \u003e 행정기관 \u003e 과학기술정보통신부 \u003e 우체국 \u003e 우편취급국","distance":"","id":"1715294010","phone":"02-839-0115","place_name":"서울낙성대우편취급국","place_url":"http://place.map.kakao.com/1715294010","road_address_name":"서울 관악구 낙성대역3길 17","x":"126.962452723533","y":"37.4759324050104"},{"address_name":"서울 관악구 봉천동 1693-44","category_group_code":"PK6","category_group_name":"주차장","category_name":"교통,수송 \u003e 교통시설 \u003e 주차장 \u003e 공영주차장","distance":"","id":"17100671","phone":"","place_name":"낙성대제1공영주차장","place_url":"http://place.map.kakao.com/17100671","road_address_name":"","x":"126.95871649575652","y":"37.4776511860345"},{"address_name":"서울 관악구 봉천동 281","category_group_code":"","category_group_name":"","category_name":"스포츠,레저 \u003e 골프 \u003e 골프연습장","distance":"","id":"16530032","phone":"02-875-8789","place_name":"낙성대골프연습장","place_url":"http://place.map.kakao.com/16530032","road_address_name":"서울 관악구 낙성대로 42","x":"126.958361731008","y":"37.4731595275777"},{"address_name":"서울 관악구 봉천동 1637-10","category_group_code":"BK9","category_group_name":"은행","category_name":"금융,보험 \u003e 금융서비스 \u003e 은행 \u003e 새마을금고","distance":"","id":"17617775","phone":"02-877-5080","place_name":"낙성대새마을금고 본점","place_url":"http://place.map.kakao.com/17617775","road_address_name":"서울 관악구 남부순환로248길 23","x":"126.96578390589062","y":"37.47501198547852"},{"address_name":"서울 관악구 봉천동 179-58","category_group_code":"","category_group_name":"","category_name":"부동산 \u003e 주거시설 \u003e 아파트","distance":"","id":"11347308","phone":"","place_name":"낙성대현대1차아파트","place_url":"http://place.map.kakao.com/11347308","road_address_name":"서울 관악구 남부순환로248길 56","x":"126.9655585988025","y":"37.47281495552714"},{"address_name":"서울 관악구 봉천동 1713","category_group_code":"","category_group_name":"","category_name":"부동산 \u003e 주거시설 \u003e 아파트","distance":"","id":"8248667","phone":"","place_name":"낙성대현대홈타운아파트","place_url":"http://place.map.kakao.com/8248667","road_address_name":"서울 관악구 솔밭로7길 16","x":"126.96348138998914","y":"37.48082340634545"},{"address_name":"서울 관악구 봉천동 302-4","category_group_code":"PK6","category_group_name":"주차장","category_name":"교통,수송 \u003e 교통시설 \u003e 주차장 \u003e 공영주차장","distance":"","id":"27173514","phone":"02-2081-2678","place_name":"낙성대 제2공영주차장","place_url":"http://place.map.kakao.com/27173514","road_address_name":"","x":"126.959364019059","y":"37.4760935648989"},{"address_name":"서울 관악구 봉천동 1693-39","category_group_code":"","category_group_name":"","category_name":"스포츠,레저 \u003e 자전거,싸이클 \u003e 자전거대여소","distance":"","id":"398739594","phone":"","place_name":"낙성대역 3번출구 뒤 대여소","place_url":"http://place.map.kakao.com/398739594","road_address_name":"서울 관악구 남부순환로 지하 1928","x":"126.9633907209651","y":"37.47702948317195"},{"address_name":"서울 관악구 낙성대동 228","category_group_code":"","category_group_name":"","category_name":"교육,학문 \u003e 학습시설 \u003e 도서관 \u003e 작은도서관","distance":"","id":"13561940","phone":"02-872-5575","place_name":"낙성대공원도서관","place_url":"http://place.map.kakao.com/13561940","road_address_name":"서울 관악구 낙성대로 77","x":"126.95938692108504","y":"37.47197530030432"},{"address_name":"서울 관악구 봉천동 296","category_group_code":"","category_group_name":"","category_name":"부동산 \u003e 주거시설 \u003e 아파트","distance":"","id":"11338369","phone":"","place_name":"낙성대현대2차아파트","place_url":"http://place.map.kakao.com/11338369","road_address_name":"서울 관악구 낙성대로 37","x":"126.95946409405153","y":"37.47500517942884"},{"address_name":"서울 관악구 봉천동 298","category_group_code":"","category_group_name":"","category_name":"부동산 \u003e 빌딩","distance":"","id":"952609296","phone":"","place_name":"낙성대 R\u0026D센터","place_url":"http://place.map.kakao.com/952609296","road_address_name":"서울 관악구 낙성대로 38","x":"126.95866159879152","y":"37.47517179763057"}],"meta":{"is_end":false,"pageable_count":43,"same_name":{"keyword":"낙성대","region":[],"selected_region":""},"total_count":320}}
         */
        println(result)
    }
}