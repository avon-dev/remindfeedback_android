package com.example.remindfeedback.ServerModel
/*
* id=[number] auto-increment, null[x]
user_uid=[string] 회원정보 기반으로 생성된 고유 식별자, null[x]
email=[string] 회원 이메일. 로그인 할 때 사용됨, null[x]
nickname=[string] 회원이 직접 설정한 회원 이름, null[x]
password=[string] 암호화된 회원 비밀번호, null[x]
portrait=[string] 회원이 직접 설정한 회원 프로필 사진 경로, null[o]
introduction=[string] 회원이 직접 설정한 상태메시지, null[o]
create_date=[date] 회원정보 생성 시 자동 저장된 날짜, null[x]
update_date=[date] 마지막으로 회원정보 수정된 날짜, null[o]
isDeleted=[boolean] 회원탈퇴 시 true, 기본값 false, null[x]
* */
class User (var id:Int, var user_uid:String, var email:String, var nickname:String, var password:String, var portrait:String, var introduction:String, var create_date:String, var update_date:String, var is_deleted:Boolean)