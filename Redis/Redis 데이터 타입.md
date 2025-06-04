# Redis 데이터 타입

> 참조 : https://inpa.tistory.com/entry/REDIS-%F0%9F%93%9A-%EB%8D%B0%EC%9D%B4%ED%84%B0-%ED%83%80%EC%9E%85Collection-%EC%A2%85%EB%A5%98-%EC%A0%95%EB%A6%AC?category=918728

## 1. Redis 데이터 타입 (Collection)

Redis의 Key-Value 스토리지에서 Value는 단순한 Object가 아니라 다양한 자료구조를 갖고 있다.

String, Set, Sorted Set, Hash, List 등 다양한 타입을 지원한다.

> Redis Collections을 사용할 때, 너무 많은 아이템을 하나의 컬렉션에 담으면 좋지 않다. 10,000개 이하의, 몇 천개 수준의 데이터 셋을 유지하는게 Redis 성능에 영향을 주지 않는다.


### 1-1. Strings

Strings의 특징 :

* 일반적인 문자열
* 값은 최대 512 MB이며, String으로 될 수 있는 binary data도, JPEG 이미지도 저장 가능하다.
* 단순 증감 연산에 좋음
* string-string 매핑을 이용하여 연결되는 자료 매핑을 할 수도 있다. HTML 매핑도 가능

#### 1-1-1. Strings 명령어 리스트




