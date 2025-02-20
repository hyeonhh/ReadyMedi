### ReadyMedi
- 우리나라에서 외국인들이 의료서비스를 보다 편하게 활용할 수 있도록 언어적 도움을 주는 앱

### 사용 기술
- 구조 : MVVM
- 의존성 주입 : Hilt
- 데이터 저장 : proto datastore
- 네트워크 : retrofit

### 사용한 데이터 및 api
1. 카카오맵 api
2. 카카오맵 로컬 api (카테고리별 위치 찾기)
3. 공공데이터 - 외국어 가능 약국 데이터
4. 공공데이터 - 용산구 외국어 가능 의료기관 + 약국 목록
5. 번역 - ML kit 이용

### 1차 MVP
- 제공 언어 : 영어, 중국어 ,일본어
- 진료과 : 내과, 일반외과, 정형외과 , 치과 ,산부인과
