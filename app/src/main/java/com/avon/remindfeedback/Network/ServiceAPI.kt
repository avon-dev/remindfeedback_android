package com.avon.remindfeedback.Network

import com.avon.remindfeedback.ServerModel.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST


interface ServiceAPI {

    //회원가입
    //@POST("auth/tempRegister")
    @POST("auth/register")
    fun SignUp(@Body json_account: SignUp): Call<Object>

    //이메일인증
    @POST("auth/email")
    fun Verify(@Body sendtoken: sendToken): Call<GetData>

    //로그인
    @POST("auth/login")
    fun LogIn(@Body login: LogIn): Call<ResponseBody>

    //로그아웃
    @GET("auth/logout")
    fun LogOut(): Call<ResponseBody>

    //내정보 가져오기
    @GET("auth/me/")
    fun GET_User(): Call<Object>

    //비밀번호 찾기 요청
    @POST("auth/password/")
    fun RequestFindPassword(@Body requestFindPassword: RequestFindPassword): Call<ResponseBody>

    //비밀번호 확인
    @POST("auth/checkpassword/")
    fun CheckPassword(@Body checkingPassword: CheckingPassword): Call<GetSuccessData>

    //이메일 확인
    @POST("auth/checkemail/")
    fun CheckEmail(@Body checkingEmail: CheckingEmail): Call<GetSuccessData>

    //비밀번호 변경
    @PATCH("auth/password/")
    fun ChangingPassword(@Body changingPassword: ChangingPassword): Call<Object>

    //회원정보 삭제
    @DELETE("auth/unregister")
    fun DeleteAccount(): Call<ResponseBody>


    //유저 정보 가져오기
    @GET("users/{id}")
    fun ShowUser(@Path("id") id: String): Call<Object>


    //피드백 생성
    @POST("feedbacks/")
    fun CreateFeedback(@Body createFeedback: CreateFeedback): Call<CreateFeedback>
/*
    //내가 요청한 피드백정보 가져오기
    @GET("feedbacks/my/{start}")
    fun GetFeedback(@Path("start") start: Int): Call<GetFeedback>
*/

    //피드백정보 모두 가져오기
    @GET("feedbacks/{lastid}/{limit}")
    fun GetAllFeedback(@Path("lastid") lastid: Int, @Path("limit") limit: Int): Call<GetAllFeedback>

    // 피드백 수정
    @PUT("feedbacks/{feedback_id}")
    fun ModifyFeedback(@Path("feedback_id") feedback_id: Int, @Body createFeedback: CreateFeedback): Call<CreateFeedback>

    //피드백 삭제
    @DELETE("feedbacks/{feedback_id}")
    fun DeleteFeedback(@Path("feedback_id") feedback_id: Int): Call<ResponseBody>

    //내 주제 정보 가져오기
    @GET("categories")
    fun GetCategory(): Call<GetCategory>

    //주제 생성
    @POST("categories")
    fun CreateCategory(@Body createCategory: CreateCategory): Call<Object>

    // 주제 하나 불러오기
    @GET("categories/{category_id}")
    fun GetOneCategory(@Path("category_id") category_id: Int): Call<Object>

    // 주제 수정
    @PUT("categories/{category_id}")
    fun ModifyCategory(@Path("category_id") category_id: Int, @Body createCategory: CreateCategory): Call<GetCategory>

    //주제 삭제
    @DELETE("categories/{category_id}")
    fun DeleteCategory(@Path("category_id") category_id: Int): Call<ResponseBody>

    //내 정보 가져오기
    @GET("mypage")
    fun GetMyPage(): Call<GetMyPage>

    //내 닉네임 수정하기
    @FormUrlEncoded
    @PATCH("mypage/nickname")
    fun PatchNickname(@Field("nickname") nickname: String): Call<GetMyPage>

    //내 상태메세지 수정하기
    @FormUrlEncoded
    @PATCH("mypage/introduction")
    fun PatchIntoduction(@Field("introduction") introduction: String?): Call<GetMyPage>

    //내 프로필 이미지 수정하기
    @Multipart
    @PATCH("mypage/portrait")
    fun PatchPortrait(@Part portrait: MultipartBody.Part?,
                      @Part("updatefile") updatefile: RequestBody): Call<GetMyPage>

    //하나의 피드백 내부 포스트들 가져오기
    @GET("board/cards/{feedbackid}/{lastid}/20")
    fun GetAllBoard(@Path("feedbackid") feedback_id: Int, @Path("lastid") lastid: Int): Call<GetAllBoard>

    //글타입의 보드 생성
    @POST("board/cards/text")
    fun CreateBoardText(@Body createCategory: CreateBoardText): Call<GetAllBoard>

    // 글 타입의 보드 수정
    @PUT("board/cards/text/{board_id}")
    fun UpdateBoardText(@Path("board_id") board_id: Int, @Body createBoardText: CreateBoardText): Call<GetAllBoard>

    //사진타입의 보드 생성
    @Multipart
    @POST("board/cards/picture/")
    fun CreateBoardPictue(
        @Part("feedback_id") feedback_id: RequestBody,
        @Part("board_title") board_title: RequestBody,
        @Part("board_content") board_content: RequestBody,
        @Part file1: MultipartBody.Part?,
        @Part file2: MultipartBody.Part?,
        @Part file3: MultipartBody.Part?
    ): Call<GetAllBoard>

    // 사진 타입의 보드 수정 - 전체(제목, 내용, 사진)
    @Multipart
    @PUT("board/cards/picture/{board_id}")
    fun UpdateBoardPictureAll(
        @Path("board_id") board_id: Int,
        @Part("board_title") board_title: RequestBody,
        @Part("board_content") board_content: RequestBody,
        @Part("updatefile1") updatefile1: Boolean,
        @Part file1: MultipartBody.Part?,
        @Part("updatefile2") updatefile2: Boolean,
        @Part file2: MultipartBody.Part?,
        @Part("updatefile3") updatefile3: Boolean,
        @Part file3: MultipartBody.Part?
    ): Call<GetAllBoard>

    // 사진 타입의 보드 수정 - 사진만
    @Multipart
    @PATCH("board/cards/picture/files/{board_id}")
    fun UpdateBoardPicture(
        @Part updatefile1: Boolean,
        @Part file1: MultipartBody.Part?,
        @Part updatefile2: Boolean,
        @Part file2: MultipartBody.Part?,
        @Part updatefile3: Boolean,
        @Part file3: MultipartBody.Part?
    ): Call<GetAllBoard>

    //비디오 타입의 보드 생성
    @Multipart
    @POST("board/cards/video")
    fun CreateBoardVideo(
        @Part("feedback_id") feedback_id: RequestBody,
        @Part("board_title") board_title: RequestBody,
        @Part("board_content") board_content: RequestBody,
        @Part videofile: MultipartBody.Part?
    ): Call<GetAllBoard>

    //녹음 타입의 보드 생성
    @Multipart
    @POST("board/cards/record")
    fun CreateBoardRecord(
        @Part("feedback_id") feedback_id: RequestBody,
        @Part("board_title") board_title: RequestBody,
        @Part("board_content") board_content: RequestBody,
        @Part recordfile: MultipartBody.Part?
    ): Call<GetAllBoard>

    //보드 삭제
    @DELETE("board/cards/{board_id}")
    fun DeleteBoard(@Path("board_id") board_id: Int): Call<ResponseBody>

    //댓글 생성
    @POST("comments")
    fun CreateComment(@Body createComment: CreateComment): Call<CreateComment>

    //댓글 수정
    @PUT("comments/{comment_id}")
    fun UpdateComment(@Body updateComment: UpdateComment, @Path("comment_id") comment_id: Int): Call<GetUpdatedComment>

    //댓글 삭제
    @DELETE("comments/{comment_id}")
    fun DeleteComment(@Path("comment_id") comment_id: Int): Call<GetDeletedComment>

    //각 게시글의 댓글 가져오기
    @GET("comments/all/scroll/{board_id}/{lastid}/{sort}")
    fun GetAllComment(@Path("board_id") board_id: Int,@Path("lastid") lastid: Int,@Path("sort") sort: Int): Call<GetAllComments>

    //내 친구 정보 가져오기
    @GET("friends")
    fun GetFriends(): Call<GetFriends>

    //친구 검색하기
    @POST("friends/search")
    fun SearchFriends(@Body searchEmailModel: SearchEmailModel): Call<Object>

    //친구요청&수락 하기
    @POST("friends")
    fun CreateFriend(@Body createFriend: CreateFriend): Call<SearchFriend>

    //친구요청 거절 하기
    @PATCH("friends/rejection/{friend_id}")
    fun RejectFriend(@Path("friend_id") friend_id: Int, @Body rejectFriend: RejectFriend): Call<SearchFriend>

    //친구차단 하기
    @PATCH("friends/block/{friend_id}")
    fun BlockFriend(@Path("friend_id") friend_id: Int,@Body rejectFriend: RejectFriend): Call<SearchFriend>

    //친구차단 해제하기
    @PATCH("friends/unblock/{friend_id}")
    fun UnBlockFriend(@Path("friend_id") friend_id: Int,@Body rejectFriend: RejectFriend): Call<SearchFriend>

    //내 친구 차단목록 가져오기
    @GET("friends/block")
    fun GetBlockedFriends(): Call<GetFriends>

    //받은 친구요청 정보 가져오기
    @GET("friends/reception")
    fun GetReceivedFriendRequest(): Call<GetFriends>

    //보낸 친구요청 정보 가져오기
    @GET("friends/transmission")
    fun GetRequestedFriendRequest(): Call<GetFriends>

    //조언자목록 가져오기
    @GET("friends/adviser")
    fun GetAdviser(): Call<GetFriends>

    //피드백 완료요청하기
    @PATCH("feedbacks/request/{feedback_id}")
    fun CompleteRequest(@Path("feedback_id") feedback_id: Int): Call<ResponseBody>

    //피드백 완료요청 수락하기
    @PATCH("feedbacks/approval/{feedback_id}")
    fun CompleteAccept(@Path("feedback_id") feedback_id: Int): Call<ResponseBody>

    //피드백 완료요청 거절하기
    @PATCH("feedbacks/rejection/{feedback_id}")
    fun CompleteReject(@Path("feedback_id") feedback_id: Int): Call<ResponseBody>



}