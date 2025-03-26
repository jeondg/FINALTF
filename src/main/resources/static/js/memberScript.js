// 로그인 화면에서 입력 검사
function checkLogin() {
   var frm = document.form1;  // form 객체 얻기
   // 아이디 입력 검사
   if(!frm.id.value) {
      alert("아이디를 입력하세요");
      frm.id.focus();
      return false;
   }
      
   // 비밀번호 입력 검사
   if(!frm.pwd.value) {
      alert("비밀번호를 입력하세요");
      frm.pwd.focus();
      return false;
   }
   // 데이터 전송 
   frm.submit();
}

function checkWrite() {
   //alert("test")
   var frm = document.form1;  // form 객체 얻기
   
   // 아이디 입력 검사
   if(!frm.id.value) {
      alert("아이디를 입력하세요");
      frm.id.focus();
      return false;
   }
   
   // 이름 입력 검사
   if(!frm.name.value) {
      alert("이름을 입력하세요");
      frm.name.focus();
      return false;
   }
   
   // 비밀번호 입력 검사
   if(!frm.pwd.value) {
      alert("비밀번호를 입력하세요");
      frm.pwd.focus();
      return false;
   }
   // 재확인 입력 검사
   if(!frm.repwd.value) {
      alert("비밀번호를 재확인하세요");
      frm.repwd.focus();
      return false;
   }
   // 비밀번호와 재확인 번호가 맞는 지 검사
   if(frm.pwd.value != frm.repwd.value) {
      alert("비밀번호가 틀립니다.");
      frm.repwd.value = "";  // 입력값 삭제
      frm.repwd.focus();
      return false;
   }
   // 데이터 전송 
   frm.submit();
}

// 입력 검사 및 브라우저 창 새로 띄우기
function checkId() {
   var sId = document.form1.id.value;
   // id 입력 검사
   if(!sId) { 
      alert("먼저 아이디를 입력하세요.")
      document.form1.id.focus()      
   } else {
      // window.open(url, "창이름", "브라우저 설정")
      // width, height : 브라우저 창의 크기
      // left, top : 모니터 기준 브라우저 창의 위치 설정
      window.open("checkId?id=" + sId, "", "width=450 height=100 left=500 top=200");
   }
}