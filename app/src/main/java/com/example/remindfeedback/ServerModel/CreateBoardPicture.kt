package com.example.remindfeedback.ServerModel

/*
feedback_id=[integer] 피드백 ID
board_title=[string] 게시물(사진) 제목,
board_content=[string] 게시물(사진) 내용
file1=[file] 사진1 (key 값은 file1)
file2=[file] 사진2 (key 값은 file2)
file3=[file] 사진3 (key 값은 file3)
파일 1은 널 비허용해놓고 2~3은 허용해놓음 없을수도 있기때문
* */
class CreateBoardPicture(
    var feedback_id: Int,
    var board_title: String,
    var board_content: String,
    var file1: String,
    var file2: String?,
    var file3: String?
)