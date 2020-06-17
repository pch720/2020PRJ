<%@ page import="poly.dto.GroupDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="poly.dto.BoardDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    GroupDTO gDTO = (GroupDTO) request.getAttribute("gDTO");
    List<BoardDTO> bnList = (List<BoardDTO>) request.getAttribute("bnList");
    int Nsize = bnList.size();
    List<BoardDTO> bwList = (List<BoardDTO>) request.getAttribute("bwList");
    int Wsize = bwList.size();
    List<GroupDTO> user = (List<GroupDTO>)request.getAttribute("user");
    String SS_name =(String)session.getAttribute("SS_USER_NAME");
%>
<html lang="en">
<head>
    <style>
        .floatMenu{
            position: absolute;
            margin-top: 120px;
        }
        .Menu1 {
            width: 220px;
            height: 200px;
            left: 5%;
            top: 10px;
        }
        .Menu2 {
             width: 250px;
             height: 200px;
             right: 4%;
             top: 10px;
         }
        .Menu3 {
            width: 220px;
            height: 300px;
        }
        /* front pane, placed above back */
        .front{
            transform: rotateY(0deg);
        }
        /* back, initially hidden pane */
        .back {
            transform: rotateY(180deg);
        }
        /* flip the pane when hovered */
        .flip-container.hover .flipper {
            transform: rotateY(180deg);
        }
        .flip-container, .front, .back {
            width: 250px;
            height: 260px;
        }
        /* flip speed goes here */
        .flipper {
            transition: 0.5s;
            /* 하위요소에 3D 좌표값 지정 */
            transform-style: preserve-3d;
            position: relative;
        }
        /* hide back of pane during swap */
        .front, .back{
            position: absolute;
            backface-visibility:hidden;
        }
        section{
            text-align: -webkit-center;
            margin-top: 130px;
        }
    </style>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>그룹 페이지</title>

    <!-- hover -->
    <link href="/css/hover.css" rel="stylesheet">

    <!-- Bootstrap Core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/stylish-portfolio.min.css" rel="stylesheet">
</head>
<!-- jQuery load -->
<script
        src="https://code.jquery.com/jquery-3.5.0.js"
        integrity="sha256-r/AaFHrszJtwpe+tHyNi/XCfMxYpbsRg2Uqn0x3s2zc="
        crossorigin="anonymous"
></script>
<body id="page-top" style="background-color: deepskyblue;height: auto;">

<!-- 네비게이션바 -->
<%@include file="../include/nav.jsp"%>
    <div class="floatMenu Menu1" style="z-index: 100;">
        <%--떠다니는 진행상황--%>
        <div class="modal-content">
                <div class="modal-body" style="text-align: center;">
                    <a href="/Calander.do?seq=<%=gDTO.getGroupSeq()%>" class="btn btn-primary" type="button">나의 진행 상황 확인</a>
                    <a class="btn btn-secondary" type="button">그룹 진행 상황 확인</a>
                </div>
        </div>
            <%--떠다니는 그룹원--%>
            <div class="modal-content Menu3">
                <div class="modal-header" style="justify-content: center;">
                    <h5 class="modal-title"><%=gDTO.getGroupName()%>(<%=gDTO.getCount()%>명)</h5>
                </div>
                <div class="modal-body" style="text-align: center;">
                    <%for (GroupDTO groupDTO : user) {
                            if (!groupDTO.getUserName().equals(SS_name)) {%>
                    <div><%=groupDTO.getUserName()%></div>
                    <%}}%>
                </div>
            </div>
    </div>
<div class="floatMenu Menu2" style="z-index: 100;">
    <!--떠다니는 해야될 일-->
    <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
        <div class="flipper">
            <!-- front content -->
            <div class="modal-content front" style="position: absolute!important;">
                <div class="modal-header" style="justify-content: center;">
                    <h5 class="modal-title">할 일</h5>
                    <i id="add" class="fas fa-plus-circle" style="align-self: center; margin-left: 5px; font-size: x-large;"></i>
                </div>
                <form>
                    <div class="modal-body" style="display: block;overflow: scroll; height: 120px;">
                       <%for (int i= 0; i<Wsize; i++){%>
                        <div class="custom-control custom-checkbox mr-sm-2">
                            <input type="checkbox" class="custom-control-input" value="<%=bwList.get(i).getBoardSeq()%>" id="do<%=i%>">
                            <label style="word-break: break-all;" class="custom-control-label" for="do<%=i%>"><%=bwList.get(i).getContents()%></label>
                        </div>
                        <%}%>
                    </div>
                    <div class="modal-footer" style="justify-content: center;">
                        <button class="btn btn-secondary" type="button">한 일로 변경</button>
                        <button class="btn btn-danger" id="del" type="button">삭제</button>
                    </div>
                </form>
            </div>
            <!-- 할 일 추가 -->
            <div class="modal-content back">
                <div class="modal-header" style="justify-content: center;">
                    <h5 class="modal-title">할 일 추가</h5>
                </div>
                <div class="modal-footer">
                    <form style="text-align: center;">
                        <label>
                            <textarea cols="100" rows="4" class="form-control" id="contents"></textarea>
                        </label>
                            <input type="hidden" name="notice" value="2">
                        <button class="btn btn-primary" type="button" id="add1">할일보기</button>
                        <button class="btn btn-secondary" type="button" id="add2">추가하기</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <%--떠다니는 메모장--%>
    <div class="modal-content">
        <div class="modal-header" style="justify-content: center;">
            <h5 class="modal-title">메모장</h5>
        </div>
        <div class="modal-footer" style="justify-content: center;">

                <label>
                    <textarea cols="100" rows="7" class="form-control"></textarea>
                </label>
        </div>
    </div>
</div>

<!-- 그룹 게시판 -->
<section id="board">
    <a href="#" class="modal-content" style="margin-top: 130px; width: 56%; height: 150px; min-width: 650px;text-decoration: none;color: black;">
    내용을 입력해 주세요.
    </a>
    <%for(int i=0;i<Nsize;i++){%>
    <div class="modal-content" style="margin-top: 10px; width: 56%; min-width: 650px;">
        <div>
            <div style="text-align:right;">
                <!-- Default dropright button -->
                <div class="btn-group dropright">
                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="margin-top: 5px; margin-right: 5px;">
                        더보기
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <a class="dropdown-item" href="#">수정</a>
                        <a class="dropdown-item" href="#">삭제</a>
                    </div>
                </div>
            </div>
            <hr>
            <div>
                <div style="width:48%;display:inline-block;text-align:left;"><a style="color:gray;">작성자 : </a><%=bnList.get(i).getUserName()%></div>
                <div style="width:48%;display:inline-block;text-align:right;"><a style="color:gray;">작성일 : </a><%=bnList.get(i).getRegDate()%></div>
            </div>
            <hr>
            <div id="content" style="margin:0 auto;width:40%;margin-top:3%;margin-bottom:2%;"><%=bnList.get(i).getContents()%></div>
            <hr>
            <button id="li" value="0"class="btn btn-outline-danger" style="margin-bottom: 5px;border-radius: 20px;"><i id="ke" class="far fa-heart"> 좋아요</i></button>
            <button class="btn btn-outline-info" style="margin-bottom: 5px;border-radius: 20px;">댓글 달기</button>
        </div>
    </div><%}%>
</section>
<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded js-scroll-trigger" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Plugin JavaScript -->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for this template -->
<script src="js/stylish-portfolio.min.js"></script>
<script>
    $(document).ready(function() {

        // 기존 css에서 플로팅 배너 위치(top)값을 가져와 저장한다.
        const floatPosition = parseInt($(".floatMenu").css('top'));
        // 250px 이런식으로 가져오므로 여기서 숫자만 가져온다. parseInt( 값 );

        $(window).scroll(function() {
            // 현재 스크롤 위치를 가져온다.
            const scrollTop = $(window).scrollTop();
            const newPosition = scrollTop + floatPosition + "px";

            /* 애니메이션 없이 바로 따라감
             $("#floatMenu").css('top', newPosition);
             */

            $(".floatMenu").stop().animate({
                "top" : newPosition
            }, 500);

        }).scroll();

    });
    $('#li').click(function () {
        if ($('#li').val() === "0"){
            $('#ke').removeClass('far');
            $('#ke').addClass('fas');
            $('#li').attr('value', '1');
        }
        else {
            $('#ke').removeClass('fas');
            $('#ke').addClass('far');
            $('#li').attr('value', '0')
        }
    });
    /*클릭시 할일한일 뒤집기*/
    $('#add1,#add').click(function() {
        $(this).closest('.flip-container').toggleClass('hover');
        $(this).css('transform, rotateY(180deg)');
    });
    /*할일추가*/
    $('#add2').click(function () {
        if ($("#contents").val()===""){
            alert("할 일을 입력해주세요.");
            return false;
        }
        else {
            $.ajax({
                url: "/writework.do",
                type: "POST",
                data: {
                    "contents": $("#contents").val(),
                    "seq": <%=gDTO.getGroupSeq()%>,
                    "group": '<%=gDTO.getGroupName()%>'
                },
                success: function (data) {
                    if (data === 1)
                        alert("할일이 추가되었습니다.");
                        window.location.reload(true);
                }
            })
        }
    });
    /*할일삭제*/
    $('#del').click(function () {
        let seq=[];
        let checked=0;
        for (let i=0;i<<%=bwList.size()%>;i++){
            if ($('#do'+i).prop("checked")){
                seq[checked]=$('#do'+i).val();
                checked++;
            }
        }
        console.log(seq.join(","))

        if (seq.length<1)
            alert("삭제할 항목을 선택해주세요.");
        else{
            $.ajax({
                url: "/delwork.do",
                type: "POST",
                data:{
                    "seq" : seq.join(",")
                },
                success: function (data) {
                    console.log(data)
                    if (data===1) {
                        alert("할일이 삭제되었습니다.");
                        window.location.reload(true);
                    }
                }
            })
        }
    });
</script>
</body>
<script src="../assets/dist/js/bootstrap.bundle.js"></script>
<script src="js/form-validation.js"></script>
</html>