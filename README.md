# remindfeedback_android
RemindFeedback 어플리케이션은 일상을 살아가면서 주변에서 받은 피드백을 등록하고, 고쳐나가는 과정을 글과 사진의 형태로 기록하며, 조언자를 등록해 그 과정에 대해서 계속해서 다시 피드백을 받을 수 있습니다.

### 유투브 링크
https://www.youtube.com/watch?v=tq7LZzu04h0&t=1s

### PlayStore 주소
https://play.google.com/store/apps/details?id=com.avon.remindfeedback&hl=en_US

##기능

### 회원가입
![회원가입](https://user-images.githubusercontent.com/46639812/90668442-14789600-e28b-11ea-9896-b0db6b650e71.gif)

- 인증버튼 누르기전엔 회원가입 버튼 비활성화
- 인증버튼 클릭시 AVON 대표메일에서 유저메일로 인증 토큰 발송
- 이메일, 비번 정규표현식 적용
- 개인정보 처리방침 클릭하고 스크롤하고 확인시 체크박스 v표시
- 회원가입 성공시 자동로그인

### Flex Drawer
![flex](https://user-images.githubusercontent.com/46639812/90669485-b482ef00-e28c-11ea-99a7-83f4372695bf.gif)

- 커스텀 네비게이션 드로어
- 개인정보와 각종 메뉴 표시

### 프로필 변경
![프사변경](https://user-images.githubusercontent.com/46639812/90669237-43dbd280-e28c-11ea-8adc-3c2715564b80.gif)
![modify_profile](https://user-images.githubusercontent.com/46639812/90669688-fc097b00-e28c-11ea-87de-853d775b7221.gif)
- 앨범 및 카메라 컨트롤
- 사진 선택하고 이미지 Crop
- 이미지 크기 resizing
- 닉네임 및 상태메세지 데이터 수정

### 친구 관리
![친구신청](https://user-images.githubusercontent.com/46639812/90669973-7508d280-e28d-11ea-9e1f-210e2ee24cd4.gif)
![친구수락](https://user-images.githubusercontent.com/46639812/90670101-a71a3480-e28d-11ea-9581-16836eb58173.gif)
![친구차단및해제](https://user-images.githubusercontent.com/46639812/90672901-d59a0e80-e291-11ea-9f50-b23a8fa86a5c.gif)

- 친구신청하면 친구관계인지 아닌지 혹은 자기자신인지 판단 후 신청
- 상대방이 친구신청을 수락하면 양측 친구목록에 추가
- 친구를 조언자로 등록하여 피드백 생성 가능
- 친구를 차단하면 친구목록에서 사라지고 옵션에서 풀 수 있음

### 주제 관리
![주제생성](https://user-images.githubusercontent.com/46639812/90670420-1bed6e80-e28e-11ea-95b3-379b17a8d531.gif)

- 원하는 타이틀로 주제를 생성할 수 있음
- 태그 색상은 기본적으로 10가지가 주어지고 주제의 갯수는 10개를 초과할 수 없음

### 피드백 관리
![피드백  생성](https://user-images.githubusercontent.com/46639812/90670888-d9786180-e28e-11ea-9b42-11ecb4046257.gif)
![피드백 필터](https://user-images.githubusercontent.com/46639812/90672147-a6cf6880-e290-11ea-932a-72422a842eea.gif)

- 위에서 추가한 친구와 주제 태그를 가지고 피드백을 생성할 수 있음
- 달력으로 목표 날짜를 선택함(당일 이전날짜는 보이지 않음)
- 피드백이 생성되면 좌측에 태그색상이 뜨고 D-Day가 표시됨
- 필터를 적용하여 주제별로 피드백을 모아볼 수 있음

### 포스팅 관리
![text](https://user-images.githubusercontent.com/46639812/90671315-7935ef80-e28f-11ea-97e9-6c370ec4ce2a.gif)
![photo](https://user-images.githubusercontent.com/46639812/90671560-db8ef000-e28f-11ea-85c7-4f0302f1afce.gif)
![댓글](https://user-images.githubusercontent.com/46639812/90671891-49d3b280-e290-11ea-932b-3e98d9b0cb6b.gif)

- 포스팅은 글과 사진 두가지 타입으로 구성됨
- 사진은 최대 3장까지 선택할 수 있음
- 선택된 사진 모두 Crop하여 업로드 가능
- 포스팅된 게시글에 들어가면 인디케이터와 함께 슬라이드로 넘길 수 있음
- 댓글을 달 수 있고, 댓글 정렬방식은 옵션에서 설정
- 필터를 적용하여 포스팅 타입별로 모아볼 수 있음

### 피드백 완료
![완료요청](https://user-images.githubusercontent.com/46639812/90672541-3aa13480-e291-11ea-9dee-f18dd9eacc57.gif)
![완료후](https://user-images.githubusercontent.com/46639812/90672783-9ec3f880-e291-11ea-853f-c986a66bd514.gif)

- 사용자가 한 피드백에 대하여 충분히 개선했다고 생각되면 조언자에게 완료요청할 수 있음(조언자가 없을시 바로 완료됨)
- 조언자는 수락 및 거절을 할 수 있고 수락하게되면 완료된 피드백 목록으로 이동됨

### 기타 기능
- 비밀번호 수정
- 회원탈퇴
- 각종 CRUD


![image](https://user-images.githubusercontent.com/46639812/90673512-d7b09d00-e292-11ea-9729-1d821d3003a3.png)
