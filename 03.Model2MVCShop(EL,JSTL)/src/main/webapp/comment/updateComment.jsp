<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<title>리뷰하기</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script type="text/javascript">
<!-- 
function fncAddComment(){

	document.detailForm.action='/updateComment.do';
	document.detailForm.submit();
}
 -->
 </script>
</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm" method="post">

<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif"	width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">리뷰하기</td>
					<td width="20%" align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif"	width="12" height="37"/>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			물품번호 <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="105">
					${comment.purchase.purchaseProd.prodNo}</td>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			구매자아이디 <img	src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${comment.purchase.buyer.userId}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>

	<tr>
		<td width="104" class="ct_write">상품명</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
		${comment.purchase.purchaseProd.prodName}
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">상품상세정보</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${comment.purchase.purchaseProd.prodDetail}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">제조일자</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${comment.purchase.purchaseProd.manuDate}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">가격</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${comment.purchase.purchaseProd.price}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">상품이미지</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">${comment.purchase.purchaseProd.fileName}</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr height="50px"></tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="150" class="ct_write">별점을 선택해주세요</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			0점<input type="radio" value="0" name="prodGrade" class="ct_input_g" height: 25px"/>
			1점<input type="radio" value="1" name="prodGrade" class="ct_input_g" height: 25px"/>
			2점<input type="radio" value="2" name="prodGrade" class="ct_input_g" height: 25px"/>
			3점<input type="radio" value="3" name="prodGrade" class="ct_input_g" height: 25px"/>
			4점<input type="radio" value="4" name="prodGrade" class="ct_input_g" height: 25px"/>
			5점<input type="radio" value="5" name="prodGrade" class="ct_input_g" height: 25px"/>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="150" class="ct_write">리뷰 입력(100자 이하)</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<input type="text" name="commentDetail" class="ct_input_g" 
						style="width: 50%; height: 25px" maxLength="200"/>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	
	
</table>
<input type="hidden" name="commentId" value="${comment.commentId}">
<input type="hidden" name="tranNo" value="${comment.purchase.tranNo}">
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td width="53%"></td>
		<td align="right">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01"	style="padding-top: 3px;">
						<a href="javascript:fncAddComment();">리뷰등록하기</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
					<td width="30"></td>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01"	style="padding-top: 3px;">
						<a href="javascript:history.go(-1);">이전</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif"width="14" height="23"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>