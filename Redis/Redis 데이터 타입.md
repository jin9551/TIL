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



### 1-2. Bitmaps

* bitmaps은 string의 변형
* bit 단위 연산이 가능하다.
* String이 512 MB 저장할 수 있듯이 2^32 bit까지 사용 가능하다.
* 저장할 때, **저장 공간 절약**에 큰 장점이 있다.


### 1-3. Lists

* array 형식의 데이터 구조, 데이터를 순서대로 저장
* 추가 / 삭제 / 조회하는 것은 O(1)의 속도를 가지지만, 중간의 특정 index 값을 조회할 때는 O(N)의 속도를 가지는 단점이 있다.
* 즉, 중간에 추가 / 삭제가 느리다. 따라서 head-tail에서 추가 / 삭제 한다.
* 메세지 queue로 사용하기 적절하다.


### 1-4. Hashes

* field-value로 구성 되어있는 전형적인 hash의 형태 (파이썬의 딕셔너리나 js객체 정도로 이해하면 된다)
* key는 하위에 subkey를 이용해 추가적인 Hash Table을 제공하는 자료구조
* 메모리가 허용하는 한, 제한없이 field를 넣을 수 있다.

