<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<html>
<head>
    <title>강사 상세보기</title>

    <link href="https://fonts.googleapis.com/css2?family=IBM+Plex+Sans+KR&family=Noto+Serif+KR:wght@900&display=swap"
          rel="stylesheet">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/reset.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/admin/home.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/adminInfo.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/jscdReset.css"/>">

    <script>
        let msg = "${param.msg}";
        if (msg == "READ_ERR") alert("정보를 가져오는데 실패했습니다. 다시 시도해 주세요.");
        if (msg == "MOD_OK") alert("성공적으로 수정되었습니다.");
        if (msg == "MOD_ERR") alert("수정에 실패하였습니다. 다시 시도해 주세요.");

    </script>


</head>

<body>
<header>
    <jsp:include page="../header.jsp"/>
</header>
<div id="adminContent">
    <nav>
        <jsp:include page="../sidebar.jsp"/>
    </nav>
    <main>

        <div id="infoDetailBox">

            <h2 id="infoTitle">강사 상세보기</h2>
            <input type="hidden" name="mebrNo" id="mebrNo" value="${infoDto.mebrNo}">
            <label style="margin-right: 3px;">강사번호</label>
            <input type="text" class="infoInputBox" readonly value="${infoDto.iscrNo}"><br>
            <label style="margin-right: 15px;">아이디</label>
            <input type="text" class="infoInputBox" readonly value="${infoDto.id}"><br>
            <label style="margin-right: 25px;">이름</label>
            <input type="text" class="infoInputBox" readonly value="${infoDto.name}"><br>
            <label>생년월일</label>
            <input type="text" class="infoInputBox" readonly value="${infoDto.birth}"><br>
            <label>휴대전화</label>
            <input type="text" class="infoInputBox" readonly value="${infoDto.phone}"><br>
            <label style="margin-right: 25px;">소개</label>
            <input type="text" class="infoInputBox" id="intro" readonly value="${infoDto.intro}"><br>
            <label style="margin-right: 25px;">상태</label>
            <input type="text" class="infoInputBox" name="status" readonly value="${infoDto.status}" id="status"><br>
            <label style="margin-right: 25px;">급여</label>
            <input type="text" class="infoModifyInputBox" name="hourPmt" id="hourPmt" readonly value="${infoDto.hourPmt}"><br>
            <label style="margin-right: 25px;">계좌</label>
            <input type="text" class="infoInputBox" readonly value="${infoDto.acct}"><br>
            <label style="margin-right: 15px;">등록일</label>
            <input type="text" class="infoInputBox" readonly
                   value="<fmt:formatDate value="${infoDto.regDate}" pattern="yyyy-MM-dd" type="date"/>"><br>
            <label style="margin-right: 25px;">비고</label>
            <input type="text" class="infoModifyInputBox" name="etc" id="etc" readonly value="${infoDto.etc}"><br>
            <div id="adminBtnBox">
                <input type="submit" value="수정" class="modifyBtn">
                <input type="button" value="목록" class="backBtn"
                       onclick="location.href='/adminManage/instructor/list?page=${page}'">
            </div>
        </div>
    </main>
</div>
</body>


<script>
    const statusArr = {
        1: '이직',
        2: '재직',
        3: '휴직',
        4: '퇴직'
    }

    let statusValue = "${infoDto.status}"; //상태
    const keyArr = Object.keys(statusArr); //상태 번호 배열
    const statusKey = keyArr.filter((key) => statusArr[key] == statusValue); //상태 번호

    $(document).ready(function () {
        $(".modifyBtn").on("click", function () {

            let isReadonly = $("input[name=hourPmt]").attr('readonly');


            if (isReadonly == 'readonly') {
                $("#infoTitle").html("강사 정보 수정");
                $("input[name=hourPmt]").attr('readonly', false);

                let statusInput = $("#status");
                let $statusSelect = $('<select id="status" name="status" class="modifySelect">');
                $statusSelect.append($("<option></option>").attr("value", statusKey).text(statusValue));


                $.each(statusArr, function (key, value) {
                    //상태와 value가 같지 않다면 옵션 추가
                    if (statusValue !== value) {
                        $statusSelect.append($("<option></option>").attr("value", key).text(value))
                    }
                });

                statusInput.replaceWith($statusSelect);

                $("input[name=hourPmt]").attr('readonly', false);
                $("input[name=etc]").attr('readonly', false);
                $("input[name=hourPmt]").css("border-bottom", "1px solid red");
                $("input[name=etc]").css("border-bottom", "1px solid red");
            } else {

                var mebrNo = document.getElementById('mebrNo').value;
                var status = document.getElementById('status').value;
                var hourPmt = document.getElementById('hourPmt').value;
                var etc = document.getElementById('etc').value;


                $.ajax({
                    type : 'PATCH',
                    url : '/adminManage/instructor/'+mebrNo+'/info?page=${page}',
                    headers: {"content-type": "application/json"},
                    data : JSON.stringify({mebrNo:mebrNo,status:status,hourPmt:hourPmt,etc:etc}),
                    success : function (result){
                        if(result.redirect){
                            alert("수정이 완료되었습니다.");
                            window.location.href = result.redirect;
                        }else{
                            throw new Error("Modify Error")
                        }
                    },
                    error : function (){
                        alert("수정이 실패했습니다.");

                    }

                });//ajax

            }


        })
    })



</script>
</html>